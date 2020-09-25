package com.prolog.eis.boxbank.out;

public interface IOutService {

    boolean checkOut(String lxNo,int taskType,int stationId) throws Exception;
}
