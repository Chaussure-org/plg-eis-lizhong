package com.prolog.eis.boxbank.out;

import com.prolog.eis.dto.enginee.OrderDto;
import com.prolog.eis.dto.enginee.BoxLibDto;
import java.util.List;

/**
 * @author wangkang
 */
public interface BZEngineeTakeJxd {
    /**
     * 为播种站台从订单池索取一个拣选单
     * @param boxLib
     * @param orderPoolList
     * @throws Exception
     */
    void checkStationPickOrder(BoxLibDto boxLib, List<OrderDto> orderPoolList) throws Exception;
}
