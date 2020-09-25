package com.prolog.eis.boxbank.out;

import com.prolog.eis.dto.enginee.DingDanDto;
import com.prolog.eis.dto.enginee.XiangKuDto;

import java.util.List;

public interface BZEngineeTakeJxd {
    /**
     * 为播种站台从订单池索取一个拣选单
     * @param xiangKu
     * @param dingDanPoolList
     * @throws Exception
     */
    void checkZhanTaiJXD(XiangKuDto xiangKu, List<DingDanDto> dingDanPoolList) throws Exception;
}
