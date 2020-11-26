package com.prolog.eis.page.service;

import com.prolog.eis.dto.page.*;
import com.prolog.eis.dto.station.StationInfoDto;
import com.prolog.eis.dto.store.ContainerInfoDto;
import com.prolog.eis.vo.station.StationInfoVo;
import com.prolog.framework.core.pojo.Page;

import java.util.List;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/10 11:02
 * page前端页面访问层
 */
public interface IPageService {


    /**
     * 查询所有站台信息
     * @return
     */
    List<StationInfoVo> findStation();

    /**
     * 修改站台信息
     * @param stationInfoDto
     * @throws Exception
     */
    void updateStationInfo(StationInfoDto stationInfoDto) throws Exception;

    /**
     * 根据id查看站台信息
     * @param stationId
     * @throws Exception
     * @return
     */
    StationInfoVo findStationById(int stationId) throws Exception;

    /**
     * 容器分页查询
     * @param containerQueryDto
     * @return
     */
    Page<ContainerInfoDto> getContainerPage(ContainerQueryDto containerQueryDto);

    /**
     * 商品资料分页查询
     * @param queryPageDto
     */
    Page<GoodsInfoDto> getGoodsPage(GoodsQueryPageDto queryPageDto);

    /**
     * 查设备信息
     * @param facilityType 1、小车 2、提升机 3、堆垛机
     * @return
     * @throws Exception
     */
    List<FacilityInfoDto> getFacilityInfo(int facilityType) throws Exception;


    /**
     * 日志分页查询
     * @param infoDto
     * @return
     * @throws Exception
     */
    Page<LogInfoDto> getLogPage(LogQueryDto infoDto) throws Exception;

    /**
     * 拣选单打印查询
     * @param printDto
     * @return
     */
    Page<PickingPrintDto> getPickingPrintPage(PickingPrintQueryDto printDto);

    /**
     * 派工单打印
     * @param seedPrintDto
     */
    void seedPrint(SeedPrintDto seedPrintDto);


    /**
     * 分页查订单详情
     * @param orderQueryDto
     * @return
     */
    Page<OrderInfoDto> getOrderPage(OrderQueryDto orderQueryDto);

    /**
     * 根据汇总id查订单明细
     * @param orderId
     * @return
     */
    List<OrderDetailInfoDto> getOrderDetail(int orderId);

    /**
     * 多条件分页查入库
     * @param inboundQueryDto
     * @return
     */
    Page<WmsInboundInfoDto> getInboundPage(InboundQueryDto inboundQueryDto);

    /**
     * 分页查盘点计划
     * @param inboundQueryDto
     * @return
     */
    Page<InventoryInfoDto> getInventoryPage(InventoryQueryDto inboundQueryDto);

    /**
     * 获取盘点信息
     * @param inventoryId
     * @return
     */
    List<InventoryDetailInfoDto> getInventoryDetail(int inventoryId);

    /**
     * 分页查盘点历史
     * @param inventoryQueryDto
     * @return
     */
    Page<InventoryHistoryDto> getInventoryHistoryPage(InventoryHistoryQueryDto inventoryQueryDto);

    /**
     * 分页查箱库货位信息
     * @param storeQueryDto
     * @return
     */
    Page<StoreInfoDto> getBxoStorePage(StoreInfoQueryDto storeQueryDto);

    /**
     * 解锁货位组
     * @param groupNo
     */
    void updateBoxGroupLock(String groupNo,int isLock) throws Exception;

    /**
     * 立库货位信息查询
     * @param storeQueryDto
     * @return
     */
    Page<StoreInfoDto> getTrayStorePage(StoreInfoQueryDto storeQueryDto);

    /**
     * 立库货位组解锁
     * @param groupNo
     */
    void updateTrayGroupLock(String groupNo,int isLock) throws Exception;

    /**
     * agv区域货位信息
     * @param agvQueryDto
     * @return
     */
    Page<AgvStoreInfoDto> getAgvStorePage(AgvStoreQueryDto agvQueryDto);

    /**
     * agv货位锁修改
     * @param agvStoreId
     * @param storagelock
     */
    void updateAgvStoreLock(int agvStoreId, int storagelock) throws Exception;
}
