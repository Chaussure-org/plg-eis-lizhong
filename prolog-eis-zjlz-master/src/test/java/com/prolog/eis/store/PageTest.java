package com.prolog.eis.store;

import com.github.pagehelper.PageHelper;
import com.prolog.eis.ZjlzApplication;
import com.prolog.eis.base.service.IGoodsService;
import com.prolog.eis.dto.page.*;
import com.prolog.eis.dto.store.ContainerInfoDto;
import com.prolog.eis.inventory.service.IInventoryHistoryService;
import com.prolog.eis.inventory.service.IInventoryTaskService;
import com.prolog.eis.location.service.AgvLocationService;
import com.prolog.eis.log.dao.WcsLogMapper;
import com.prolog.eis.log.dao.WmsLogMapper;
import com.prolog.eis.log.service.ILogService;
import com.prolog.eis.model.log.WcsLog;
import com.prolog.eis.model.log.WmsLog;
import com.prolog.eis.model.order.ContainerBindingDetail;
import com.prolog.eis.order.dao.OrderBillMapper;
import com.prolog.eis.order.dao.OrderDetailMapper;
import com.prolog.eis.page.service.IPageService;
import com.prolog.eis.pick.service.IStationBZService;
import com.prolog.eis.store.service.IStoreLocationService;
import com.prolog.eis.util.EisStringUtils;
import com.prolog.eis.warehousing.service.IWareHousingService;
import com.prolog.eis.wms.service.IWmsService;
import com.prolog.framework.core.pojo.Page;
import com.prolog.framework.dao.util.PageUtils;
import com.prolog.framework.utils.MapUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/10 15:41
 */
@SpringBootTest(classes = ZjlzApplication.class)
@RunWith(SpringRunner.class)
public class PageTest {
    @Autowired
    private IPageService pageService;
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private ILogService logService;
    @Autowired
    private WmsLogMapper wmsLogMapper;
    @Autowired
    private OrderBillMapper orderBillMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private IWareHousingService inboundService;
    @Autowired
    private IInventoryTaskService iInventoryTaskService;
    @Autowired
    private IInventoryHistoryService inventoryHistoryService;
    @Autowired
    private IStoreLocationService storeLocationService;
    @Autowired
    private AgvLocationService agvLocationService;
    @Autowired
    private WcsLogMapper wcsLogMapper;
    @Autowired
    private IWmsService wmsService;
    @Autowired
    private IStationBZService stationBZService;
    @Test
    public void pageContainer(){
        System.out.println("aaaa");
        ContainerQueryDto containerQueryDto = new ContainerQueryDto();
        containerQueryDto.setPageNum(1);
        containerQueryDto.setPageSize(2);
        containerQueryDto.setGoodsName("王康大佬");
//        containerQueryDto.setContainerNo("1");
//        containerQueryDto.setStartTime(new Date());
        Page<ContainerInfoDto> containerPage = pageService.getContainerPage(containerQueryDto);
        System.out.println(containerPage);
    }
    @Test
    public void pageGoods(){
        GoodsQueryPageDto goodsQueryPageDto = new GoodsQueryPageDto();
        goodsQueryPageDto.setPageNum(2);
        goodsQueryPageDto.setPageSize(10);
        Page<GoodsInfoDto> goodsPage = pageService.getGoodsPage(goodsQueryPageDto);
        System.out.println("aaaa");
    }

    @Test
    public void pageLog() throws Exception {
        LogQueryDto logQueryDto = new LogQueryDto();
        logQueryDto.setLogType(1);
        logQueryDto.setPageNum(1);
        logQueryDto.setPageSize(4);
        logQueryDto.setEndTime(new Date());
        List<LogInfoDto> logPage1 = wmsLogMapper.getLogPage(logQueryDto,"wms_log","WMS");
        List<LogInfoDto> logPage = logService.getLogPage(logQueryDto, "wms_log", "WMS系统");
        System.out.println("aaaa");
    }

    @Test
    public void seedPage(){
        PickingPrintQueryDto pickingPrintQueryDto = new PickingPrintQueryDto();
        pickingPrintQueryDto.setPageNum(1);
        pickingPrintQueryDto.setPageSize(10);
        Page<PickingPrintDto> pickingPrintPage = pageService.getPickingPrintPage(pickingPrintQueryDto);
        System.out.println("aaaaa");
    }

    @Test
    public void orderPage(){
        OrderQueryDto orderQueryDto = new OrderQueryDto();
        orderQueryDto.setPageNum(1);
        orderQueryDto.setPageSize(10);
        PageHelper.startPage(orderQueryDto.getPageNum(),orderQueryDto.getPageSize());
        List<OrderInfoDto> orderPage = orderBillMapper.getOrderPage(orderQueryDto);
        Page<OrderInfoDto> page = PageUtils.getPage(orderPage);
        System.out.println("aaaaa");
    }
    @Test
    public void testDetail(){
        List<OrderDetailInfoDto> orderDetailById = orderDetailMapper.getOrderDetailById(2000083);
        System.out.println("sssss");
    }

    @Test
    public void testInbound(){
        InboundQueryDto inboundQueryDto = new InboundQueryDto();
        inboundQueryDto.setPageNum(1);
        inboundQueryDto.setPageSize(10);
        inboundQueryDto.setBranchType("X");
        Page<WmsInboundInfoDto> inboundPage = inboundService.getInboundPage(inboundQueryDto);
        System.out.println("sssss");
    }

    @Test
    public void testInventory(){
        InventoryQueryDto inboundQueryDto = new InventoryQueryDto();
        inboundQueryDto.setPageNum(1);
        inboundQueryDto.setPageSize(10);
//        inboundQueryDto.setGoodsName("奔");
        Page<InventoryInfoDto> inventoryPage = iInventoryTaskService.getInventoryPage(inboundQueryDto);
        System.out.println("sssss");
    }


    @Test
    public void testInventoryHistory(){
        InventoryHistoryQueryDto inventoryHistoryQueryDto = new InventoryHistoryQueryDto();
        inventoryHistoryQueryDto.setPageNum(1);
        inventoryHistoryQueryDto.setPageSize(10);
        inventoryHistoryQueryDto.setDifferent(1);
        inventoryHistoryQueryDto.setEndTime(new Date());
        Page<InventoryHistoryDto> inventoryHistoryPage = inventoryHistoryService.getInventoryHistoryPage(inventoryHistoryQueryDto);
        System.out.println("qq");
    }
    @Test
    public void testStorePage(){
        StoreInfoQueryDto storeInfoQueryDto = new StoreInfoQueryDto();
        storeInfoQueryDto.setPageNum(1);
        storeInfoQueryDto.setPageSize(10);
        Page<StoreInfoDto> boxStorePage = storeLocationService.getBoxStorePage(storeInfoQueryDto);
        System.out.println("aaaa");
    }

    @Test
    public void testAgv(){
        AgvStoreQueryDto agvStoreQueryDto = new AgvStoreQueryDto();
        agvStoreQueryDto.setPageNum(1);
        agvStoreQueryDto.setPageSize(10);
        agvStoreQueryDto.setLocationType(3);
        Page<AgvStoreInfoDto> agvStorePage = agvLocationService.getAgvStorePage(agvStoreQueryDto);
        System.out.println("aaaa");
    }

    @Test
    public void test11(){
        wcsLogMapper.deleteByMap(MapUtils.put("type",5).getMap(), WcsLog.class);
    }


    @Test
    public void testStr(){
        String remouldId = EisStringUtils.getRemouldId(12345678);
        System.out.println(remouldId);
    }


    @Test
    public void testToWms() throws Exception {
        ContainerBindingDetail containerBindingDetail = new ContainerBindingDetail();
        containerBindingDetail.setOrderDetailId(1060);
        stationBZService.seedToWms(containerBindingDetail);
    }
}
