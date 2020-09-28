package com.prolog.eis.dispatch;

import com.prolog.eis.boxbank.out.BZEnginee;
import com.prolog.eis.boxbank.out.BZEngineeTakeJxd;
import com.prolog.eis.configuration.EisProperties;
import com.prolog.eis.dto.enginee.OrderDto;
import com.prolog.eis.dto.enginee.PickOrderDto;
import com.prolog.eis.dto.enginee.BoxLibDto;
import com.prolog.eis.dto.enginee.StationDto;
import com.prolog.eis.dto.orderpool.OpOrderHz;
import com.prolog.eis.orderpool.service.OrderPoolService;
import com.prolog.eis.util.FileLogHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 箱库的播种调度
 */
@Service
public class BoxOutDispatch {

	private static final Logger logger = LoggerFactory.getLogger(BoxOutDispatch.class);

	@Autowired
	private EisProperties eisProperties;
	@Autowired
	private BZEnginee bzEnginee;
	@Autowired
	private BZEngineeTakeJxd bzEngineeTakeJxd;
	@Autowired
	private OrderPoolService orderPoolService;

    /**
     * 检查正在作业的运单下所有未绑定满料箱的运单详细，站台的作业情况判断应该出哪些料箱
     * @throws Exception
     */
    public synchronized void check() throws Exception {
        //初始化箱库
		/**
		 * 1.每次出库计算
		 */
        BoxLibDto boxLib = bzEnginee.init();
        if (boxLib == null || boxLib.getStations() ==null || boxLib.getStations().size() == 0 ) {
			return;
		}

        //获取可用订单
        List<OrderDto> orderPoolList = this.initOrder(boxLib);
        if(orderPoolList!=null && orderPoolList.size()>0){
			// 为所需料箱全部出库到线体的站台向订单池索取一个新的拣选单
			bzEngineeTakeJxd.checkStationPickOrder(boxLib,orderPoolList);
        }
        // 计算巷道的各个站台需要出库的拣选单
        this.checkStationNeedOutboundPickOrder(boxLib);

        this.outbound(boxLib);
    }

	/**
	 * 初始化订单数据
	 * @param boxLib
	 * @return
	 * @throws Exception
	 */
	private List<OrderDto> initOrder(BoxLibDto boxLib) throws Exception {
		// 获得订单池的订单
		List<OpOrderHz> opOrderList = orderPoolService.getOrderPool();
		List<OrderDto> orderPoolList = new ArrayList<OrderDto>();
		if (opOrderList != null) {
			FileLogHelper.writeLog("engineDingDanPool", "原始订单池订单数：" + opOrderList.size());
			orderPoolList = this.computeUsedDingDan(opOrderList,boxLib);
			FileLogHelper.writeLog("engineDingDanPool", "可用订单数：" + opOrderList.size());
		// 获得订单池的订单
		}
		return orderPoolList;
	}

	/**
	 * 	计算订单池中的满足库存的订单
	 */
	private List<OrderDto> computeUsedDingDan(List<OpOrderHz> opOrderList, BoxLibDto boxLib) throws Exception {
		List<OrderDto> orderPoolList = new ArrayList<OrderDto>();
	// 计算订单池中的满足库存的订单
		// 按优先级和时效排序，优先保留时效靠前的订单
		opOrderList.sort((dd1, dd2) -> {
			if(!dd1.getPriority().equals(dd2.getPriority())) {
				return dd1.getPriority() - dd2.getPriority();
			}
			
			if (dd1.getExpectTime().getTime() < dd2.getExpectTime().getTime()) {
				return -1;
			} else if (dd1.getExpectTime().getTime() > dd2.getExpectTime().getTime()) {
				return 1;
			} else {
				return 0;
			}
		});

		// 计算巷道哪些订单满足库存
		for (OpOrderHz opOrder : opOrderList) {
			if (boxLib.checkOrder(opOrder)) {
				boxLib.subtractingOrder(opOrder);
				OrderDto dd = OrderDto.copyOrder(opOrder);
				orderPoolList.add(dd);
			}
		}
		return orderPoolList;
	}

	private void outbound(BoxLibDto boxLib) throws Exception {
		List<StationDto> stations = boxLib.getStations().stream().filter(station -> station.getIsLock() == StationDto.STATUS_UNLOCK && station.getNeedOutboundPickOrder() != null && !station.isContainerLimitMax() ).collect(Collectors.toList());
		while (true) {
			StationDto station = this.getBestStation(stations);

			if (station == null) {
				break;
			}

			boolean isAllChuKu = this.containerOutbound(station,boxLib);

			// 如果站台所需料箱已经全部出库,则从集合移除该站台
			if (isAllChuKu) {
				stations.remove(station);
			}
			else{
				// 如果站台的出库料箱数达到最大出库料箱数，则该站台不再出库料箱
				if (station.getChuKuLxCount() >= station.getMaxLxCacheCount()) {
					stations.remove(station);
				}
			}

		}
	}


	/**
	 * 为一个站台出库一个料箱,返回该站台所需料箱是否出库料箱完成
	 * @param station
	 * @param boxLib
	 * @return
	 * @throws Exception
	 */
	private boolean containerOutbound(StationDto station, BoxLibDto boxLib) throws Exception {
		PickOrderDto pickOrder = station.getNeedOutboundPickOrder();
		Integer spId = pickOrder.getOutboundSpId(boxLib.getChuKuFailSpIdHs());

		if (spId == null) {
			return true;
		}

		// 出库一个商品
		boolean isOutboundSuccess = bzEnginee.outbound(spId,station);

		if (isOutboundSuccess) {
			station.setChuKuLxCount(station.getChuKuLxCount() + 1);
			// 如果料箱数量达到最大数量，则不再返回
			if (station.isContainerLimitMax())
			{
				return true;
			}

			return station.checkIsAllBinding();
		} else {
			logger.info("++++++++++++++++++"+spId+"商品出库失败,请查询所在层小车状态++++++++++++++++++");
			// 如果出库失败，则记录在当前巷道的出库失败商品Map里
			boxLib.getChuKuFailSpIdHs().add(spId);
			return false;
		}
	}

	/**
	 * 	计算巷道的各个站台需要出库的拣选单
 	 */
	private void checkStationNeedOutboundPickOrder(BoxLibDto boxLib) {
		for (StationDto station : boxLib.getStations()) {
			for (PickOrderDto pickOrder : station.getPickOrderList()) {
				if (pickOrder.getIsAllContainerArrive() != 1) {
					station.setNeedOutboundPickOrder(pickOrder);
					break;
				}
			}
		}
	}

	/**
	 * 	获得当前出库料箱数最少的站台
 	 */
	private StationDto getBestStation(List<StationDto> stations) {
		if (stations.size() == 0) {
			return null;
		}

		StationDto station = stations.get(0);
		int bestZtLxCount = station.computeContainerCount();
		for (int i = 1; i < stations.size(); i++) {
			StationDto zt = stations.get(i);
			int ztLxCount = zt.computeContainerCount();
			if (ztLxCount < bestZtLxCount) {
				bestZtLxCount = ztLxCount;
				station = zt;
			}
		}

		return station;
	}
}
