package com.prolog.eis.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prolog.framework.common.message.Message;
import com.prolog.framework.common.message.RestMessage;
import io.swagger.annotations.ApiModelProperty;

import java.io.IOException;
import java.util.List;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/17 14:52
 */
public class EisRestMessage<T> implements Message {

    @ApiModelProperty(value = "是否成功")
    private boolean SUCCESS;
    @ApiModelProperty(value = "消息对象")
    private String MESSAGE;
    @ApiModelProperty(value = "消息代码")
    private String CODE;
    @ApiModelProperty(value = "返回对象")
    private T DATA;

    public EisRestMessage(){

    }


    public static <T> EisRestMessage<T> newInstance(boolean success,String message){
        return new EisRestMessage<T>(success,message,null);
    }

    public static <T> EisRestMessage<T> newInstance(boolean success,String message,T data){
        return new EisRestMessage<T>(success,message,data);
    }

    public static <T> EisRestMessage<T> newInstance(boolean success,String code,String message,T data){
        return new EisRestMessage<T>(success,code,message,data);
    }

    public EisRestMessage(boolean SUCCESS,String MESSAGE,T DATA){
        this.SUCCESS = SUCCESS;
        this.MESSAGE = MESSAGE;
        this.DATA = DATA;
        this.CODE="200";
    }

    public EisRestMessage(boolean SUCCESS,String CODE,String MESSAGE,T DATA){
        this.SUCCESS = SUCCESS;
        this.MESSAGE = MESSAGE;
        this.DATA = DATA;
        this.CODE=CODE;
    }

    public boolean isSuccess() {
        return SUCCESS;
    }
    public void setSuccess(boolean SUCCESS) {
        this.SUCCESS = SUCCESS;
    }
    public String getMessage() {
        return MESSAGE;
    }
    public void setMessage(String MESSAGE) {
        this.MESSAGE = MESSAGE;
    }
    public T getData() {
        return DATA;
    }
    public void setData(T DATA) {
        this.DATA = DATA;
    }

    public String getCode() {
        return CODE;
    }


    public void setCode(String CODE) {
        this.CODE = CODE;
    }


    @Override
    public String toJsonString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <S> EisRestMessage<S> parseJsonString(String jsonstr, TypeReference<EisRestMessage<S>> typeReference) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        EisRestMessage<S> rest = mapper.readValue(jsonstr, typeReference);
        return rest;
    }

    public static <S> EisRestMessage<S> parseJsonString(String jsonstr) throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        EisRestMessage<S> rest = mapper.readValue(jsonstr, new TypeReference<EisRestMessage<S>>(){});
        return rest;
    }

    public static <S> EisRestMessage<List<S>> parseJsonStringForList(String jsonstr) throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        EisRestMessage<List<S>> rest = mapper.readValue(jsonstr, new TypeReference<EisRestMessage<List<S>>>(){});
        return rest;
    }

    public static void main(String[] args) throws IOException {
        //String jsonstr = "{\"success\":true,\"message\":\"查询成功\",\"data\": [{\"id\": 50,\"number\":\"001\",\"name\": \"苹果\",\"price\": 12},{\"id\": 50,\"number\":\"001\",\"name\": \"苹果\",\"price\": 12}]}";
        //String jsonstr = "{\"success\":true,\"message\":\"查询成功\",\"data\": {\"id\": 50,\"number\":\"001\",\"name\": \"苹果\",\"price\": 12}}";
        //String jsonstr = "{\"success\":true,\"message\":\"查询成功\",\"data\": 123}";
        //EisRestMessage<List<Product>> rest= EisRestMessage.parseJsonString(jsonstr,new TypeReference<EisRestMessage<List<Product>>>(){});
        //List<Product> list = rest.getData();
        //System.out.println(list.get(0).getName());
    }
}
