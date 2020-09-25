package com.prolog.eis.boxbank.out;

import com.prolog.eis.dto.enginee.XiangKuDto;
import com.prolog.eis.dto.enginee.ZhanTaiDto;

public interface BZEnginee {

    /**
     * 初始化箱库数据
     * @return
     * @throws Exception
     */
    XiangKuDto init() throws Exception;

    /**
     * 料箱出库调度
     * @param spId
     * @param zhanTaiDto
     * @return
     * @throws Exception
     */
    boolean chuku(int spId,  ZhanTaiDto zhanTaiDto) throws Exception;
}
