package com.prolog.eis.boxbank.out;

import com.prolog.eis.dto.enginee.BoxLibDto;
import com.prolog.eis.dto.enginee.StationDto;

public interface BZEnginee {

    /**
     * 初始化箱库数据
     * @return
     * @throws Exception
     */
    BoxLibDto init() throws Exception;

    /**
     * 料箱出库调度
     * @param spId
     * @param stationDto
     * @return
     * @throws Exception
     */
    boolean outbound(int spId,  StationDto stationDto) throws Exception;
}
