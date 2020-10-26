package com.prolog.eis.dto.out;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 * @Author SunPP
 * Description:三十功名尘与土，八千里路云和月
 * @return
 * @date:2020/10/26 10:33
 */
@Data
public class EmptyBoxBackDto {

    private String TASKID;
    private String BILLNO;
    private String STATUS;
    @Size(max = 3,min = 1)
    private int BILLTYPE;
    @Min(1)
    private int QTY;
}
