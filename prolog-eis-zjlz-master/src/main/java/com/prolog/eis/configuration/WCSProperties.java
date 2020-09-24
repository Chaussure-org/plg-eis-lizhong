package com.prolog.eis.configuration;

public class WCSProperties {
    private String host;
    private int port;
    private int bankId=1;

    private String taskUrl="/Interface/Request";//出库（入库/同层移库）任务接口
    private String requestCarUrl="/Interface/SearchRgv";//查询车辆信息
    private String moveCarUrl = "/Interface/CrossLayer";//小车换层
    private String lineMoveUrl="/Interface/WalkLower";//输送线行走
    private String orderBoxReqUrl="/Interface/Station";//订单框请求
    private String lightControlUrl="/Interface/StationLightOn";//灯光控制
    private String requestHoisterUrl = "/Interface/SearchHoist";//提升机
    private String requestBankTaskUrl = "/Interface/deleteBankTask";//异常入库删除

    public String getTaskUrl() {
        return taskUrl;
    }

    public void setTaskUrl(String taskUrl) {
        this.taskUrl = taskUrl;
    }

    public String getOrderBoxReqUrl() {
        return orderBoxReqUrl;
    }

    public void setOrderBoxReqUrl(String orderBoxReqUrl) {
        this.orderBoxReqUrl = orderBoxReqUrl;
    }

    public String getLightControlUrl() {
        return lightControlUrl;
    }

    public void setLightControlUrl(String lightControlUrl) {
        this.lightControlUrl = lightControlUrl;
    }

    public String getLineMoveUrl() {
        return lineMoveUrl;
    }

    public void setLineMoveUrl(String lineMoveUrl) {
        this.lineMoveUrl = lineMoveUrl;
    }

    public String getMoveCarUrl() {
        return moveCarUrl;
    }

    public void setMoveCarUrl(String moveCarUrl) {
        this.moveCarUrl = moveCarUrl;
    }

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getRequestCarUrl() {
        return requestCarUrl;
    }

    public void setRequestCarUrl(String requestCarUrl) {
        this.requestCarUrl = requestCarUrl;
    }

    public String getRequestHoisterUrl() {
        return requestHoisterUrl;
    }

    public void setRequestHoisterUrl(String requestHoisterUrl) {
        this.requestHoisterUrl = requestHoisterUrl;
    }

    public String getRequestBankTaskUrl() {
        return requestBankTaskUrl;
    }

    public void setRequestBankTaskUrl(String requestBankTaskUrl) {
        this.requestBankTaskUrl = requestBankTaskUrl;
    }
}
