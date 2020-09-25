package com.prolog.eis.dispatch;

import com.prolog.eis.boxbank.out.BZEnginee;
import com.prolog.eis.boxbank.out.BZEngineeTakeJxd;
import com.prolog.eis.configuration.EisProperties;
import com.prolog.eis.dto.enginee.DingDanDto;
import com.prolog.eis.dto.enginee.JianXuanDanDto;
import com.prolog.eis.dto.enginee.XiangKuDto;
import com.prolog.eis.dto.enginee.ZhanTaiDto;
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
        XiangKuDto xiangKu = bzEnginee.init();
        if (xiangKu == null || xiangKu.getZtList() ==null || xiangKu.getZtList().size() == 0 )
            return;

        //获取可用订单
        List<DingDanDto> dingDanPoolList = this.initDingDan(xiangKu);
        if(dingDanPoolList!=null && dingDanPoolList.size()>0){
			// 为所需料箱全部出库到线体的站台向订单池索取一个新的拣选单
			bzEngineeTakeJxd.checkZhanTaiJXD(xiangKu,dingDanPoolList);
        }
        // 计算巷道的各个站台需要出库的拣选单
        this.checkZhanTaiNeedChuKuJXD(xiangKu);

        this.chuKu(xiangKu);
    }

	/**
	 * 初始化订单数据
	 * @param xiangKu
	 * @return
	 * @throws Exception
	 */
	private List<DingDanDto> initDingDan(XiangKuDto xiangKu) throws Exception {
		// 获得订单池的订单
		List<OpOrderHz> opOrderList = orderPoolService.getOrderPool();
		List<DingDanDto> dingDanPoolList = new ArrayList<DingDanDto>();
		if (opOrderList != null) {
			FileLogHelper.WriteLog("engineDingDanPool", "原始订单池订单数：" + opOrderList.size());
			dingDanPoolList = this.computeUsedDingDan(opOrderList,xiangKu);
			FileLogHelper.WriteLog("engineDingDanPool", "可用订单数：" + opOrderList.size());
		}
		return dingDanPoolList;
	}

	// 计算订单池中的满足库存的订单
	private List<DingDanDto> computeUsedDingDan(List<OpOrderHz> opOrderList, XiangKuDto xiangKu) throws Exception {
		List<DingDanDto> dingDanPoolList = new ArrayList<DingDanDto>();
		// 按优先级和时效排序，优先保留时效靠前的订单
		opOrderList.sort((dd1, dd2) -> {
			if(dd1.getPriority() != dd2.getPriority())
				return dd1.getPriority() - dd2.getPriority();
			
			if (dd1.getExpectTime().getTime() < dd2.getExpectTime().getTime())
				return -1;
			else if (dd1.getExpectTime().getTime() > dd2.getExpectTime().getTime())
				return 1;
			else
				return 0;
		});

		// 计算巷道哪些订单满足库存
		for (OpOrderHz opOrder : opOrderList) {
			if (xiangKu.checkDingDan(opOrder)) {
				xiangKu.subtractingDingDan(opOrder);
				DingDanDto dd = DingDanDto.CopyDingDan(opOrder);
				dingDanPoolList.add(dd);
			}
		}
		return dingDanPoolList;
	}

	private void chuKu(XiangKuDto xiangKu) throws Exception {
		List<ZhanTaiDto> ztList = xiangKu.getZtList().stream().filter(zt -> zt.getIsLock() == ZhanTaiDto.STATUS_UNLOCK && zt.getNeedChuKuJXD() != null && !zt.IsLiaoXiangLimitMax() ).collect(Collectors.toList());
		while (true) {
			ZhanTaiDto zt = this.getBestZt(ztList);

			if (zt == null)
				break;

			boolean isAllChuKu = this.chuKuLiaoXiang(zt,xiangKu);

			// 如果站台所需料箱已经全部出库,则从集合移除该站台
			if (isAllChuKu) {
				ztList.remove(zt);
			}
			else{
				// 如果站台的出库料箱数达到最大出库料箱数，则该站台不再出库料箱
				if (zt.getChuKuLxCount() >= zt.getMaxLxCacheCount())
					ztList.remove(zt);
			}

		}
	}


	/**
	 * 为一个站台出库一个料箱,返回该站台所需料箱是否出库料箱完成
	 * @param zt
	 * @param xiangKu
	 * @return
	 * @throws Exception
	 */
	private boolean chuKuLiaoXiang(ZhanTaiDto zt, XiangKuDto xiangKu) throws Exception {
		JianXuanDanDto jxd = zt.getNeedChuKuJXD();
		Integer spId = jxd.GetChuKuSpId(xiangKu.getChuKuFailSpIdHs());

		if (spId == null)
			return true;

		// 出库一个商品
		boolean isChukuSuccess = bzEnginee.chuku(spId,zt);

		if (isChukuSuccess) {
			zt.setChuKuLxCount(zt.getChuKuLxCount() + 1);
			if (zt.IsLiaoXiangLimitMax())// 如果料箱数量达到最大数量，则不再返回
				return true;

			return zt.CheckIsAllBinding();
		} else {
			logger.info("++++++++++++++++++"+spId+"商品出库失败,请查询所在层小车状态++++++++++++++++++");
			// 如果出库失败，则记录在当前巷道的出库失败商品Map里
			xiangKu.getChuKuFailSpIdHs().add(spId);
			return false;
		}
	}

	// 计算巷道的各个站台需要出库的拣选单
	private void checkZhanTaiNeedChuKuJXD(XiangKuDto xiangKu) {
		for (ZhanTaiDto zt : xiangKu.getZtList()) {
			for (JianXuanDanDto jxd : zt.getJxdList()) {
				if (jxd.getIsAllLiaoXiangArrive() != 1) {
					zt.setNeedChuKuJXD(jxd);
					break;
				}
			}
		}
	}

	// 获得当前出库料箱数最少的站台
	private ZhanTaiDto getBestZt(List<ZhanTaiDto> ztList) {
		if (ztList.size() == 0)
			return null;

		ZhanTaiDto bestZhanTai = ztList.get(0);
		int bestZtLxCount = bestZhanTai.ComputeLiaoXiangCount();
		for (int i = 1; i < ztList.size(); i++) {
			ZhanTaiDto zt = ztList.get(i);
			int ztLxCount = zt.ComputeLiaoXiangCount();
			if (ztLxCount < bestZtLxCount) {
				bestZtLxCount = ztLxCount;
				bestZhanTai = zt;
			}
		}

		return bestZhanTai;
	}
}
