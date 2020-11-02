package com.prolog.eis.dto.rcs;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author wangkang
 * @Description rcs返回实体
 * @CreateTime 2020-11-02 9:10
 */
@ApiModel("rcs回告实体")
public class RcsCallbackDto {

    @ApiModelProperty("任务id")
    private String taskCode;

    @ApiModelProperty("方法代码")
    private String method;

    public String getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return "RcsCallbackDto{" +
                "taskCode='" + taskCode + '\'' +
                ", method='" + method + '\'' +
                '}';
    }
}
