package com.prolog.eis.mcs.controller;

import com.alibaba.fastjson.JSONObject;
import com.prolog.eis.dto.mcs.InBoundRequestDto;
import com.prolog.eis.dto.mcs.McsRequestTaskDto;
import com.prolog.eis.dto.mcs.TaskReturnInBoundRequestResponseDto;
import com.prolog.eis.mcs.service.IMCSCallBackService;
import com.prolog.eis.util.PrologApiJsonHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-09-22 15:53
 */
@RestController
@RequestMapping("/mcs")
@Api(tags = "MCS回调接口(堆垛机)")
public class MCSController {

    @Autowired
    private IMCSCallBackService mcsCallbackService;

    @ApiOperation(value = "提升机请求", notes = "提升机请求")
    @PostMapping("/mcsRequest")
    public void mcsRequest(@RequestBody String json, HttpServletResponse response) throws Exception {

        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        OutputStream out = response.getOutputStream();
        PrologApiJsonHelper helper = PrologApiJsonHelper.createHelper(json);

        try {
            List<InBoundRequestDto> inBoundRequests = helper.getObjectList("carryList", InBoundRequestDto.class);
            //根据原点，去除重复集合
            List<InBoundRequestDto> newInBoundRequests = dictinctInBoundRequest(inBoundRequests);

            String errorMsg = "";

            List<McsRequestTaskDto> sendList = new ArrayList<McsRequestTaskDto>();
            for(int i=0;i<newInBoundRequests.size();i++) {
                synchronized ("kucun".intern()) {
                    try {
						/*McsRequestTaskDto mcsRequestTaskDto = qcInBoundTaskService.inBoundTask(inBoundRequests.get(i));
						if(null != mcsRequestTaskDto) {
							sendList.add(mcsRequestTaskDto);

							if(!mcsRequestTaskDto.isSuccess()) {
								errorMsg = errorMsg + " " + mcsRequestTaskDto.getErrorMessage();
							}
						}*/
                    }
                    catch (Exception e) {
                        // TODO: handle exception
                        errorMsg = errorMsg + " " + e.getMessage();
                    }
                }
            }

            String resultStr = getMcsValue(true,errorMsg,"200",new ArrayList<TaskReturnInBoundRequestResponseDto>());
            out.write(resultStr.getBytes("UTF-8"));
            out.flush();
            out.close();
            //int type, String containerNo, String address, String target, String weight, String priority,int state
            //给mcs发指令
			/*for (McsRequestTaskDto mcsRequestTaskDto : sendList) {
				mcsInterfaceServiceSend.sendMcsTaskWithOutPathAsyc(mcsRequestTaskDto.getType(),
						mcsRequestTaskDto.getStockId(),
						mcsRequestTaskDto.getSource(),
						mcsRequestTaskDto.getTarget(),
						"0", "99",0);


			}*/
        }catch (Exception e) {
            // TODO: handle exception
            String resultStr = getMcsValue(false,e.toString(),"100",new ArrayList<TaskReturnInBoundRequestResponseDto>());
            out.write(resultStr.getBytes("UTF-8"));
            out.flush();
            out.close();
            //LogServices.logSys(e);
        }
    }

    private List<InBoundRequestDto> dictinctInBoundRequest(List<InBoundRequestDto> list){

        HashMap<String,InBoundRequestDto> hashMap = new HashMap<String,InBoundRequestDto>();

        for (InBoundRequestDto inBoundRequest : list) {
            if(!hashMap.containsKey(inBoundRequest.getSource())) {
                hashMap.put(inBoundRequest.getSource(),inBoundRequest);
            }else {
                //判断后面的条码是否为noRead
                if(!"noRead".equals(inBoundRequest.getStockId())) {
                    hashMap.put(inBoundRequest.getSource(),inBoundRequest);
                }
            }
        }

        return new ArrayList<InBoundRequestDto>(hashMap.values());
    }

    @ApiOperation(value = "提升机任务回告", notes = "提升机任务回告")
    @PostMapping("/callback")
    public void taskReturn(@RequestBody String json, HttpServletResponse response) throws Exception {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        OutputStream out = response.getOutputStream();
        PrologApiJsonHelper helper = PrologApiJsonHelper.createHelper(json);

        try {

            //下发任务号
            String taskId = helper.getString("taskId");
            //状态 1:任务开始 2：任务完成 3：任务异常
            int status = helper.getInt("status");
            //任务类型：1：入库 2：出库 3:移库 4:小车换层 5:输送线行走
            int type = helper.getInt("type");
            //料箱编号
            String containerNo = helper.getString("containerNo");
            //小车编号
            String rgvId = helper.getString("rgvId");
            //当前点位
            String address = helper.getString("address");

            try {
                //qcInBoundTaskService.taskReturn(taskId,status,type,containerNo,rgvId,address);
                mcsCallbackService.mcsCallback(taskId, status);


                String resultStr = getMcsValue(true,"操作成功","200",new ArrayList<TaskReturnInBoundRequestResponseDto>());
                out.write(resultStr.getBytes("UTF-8"));
                out.flush();
                out.close();

            }catch (Exception e) {
                // TODO: handle exception

                String resultStr = getMcsValue(false,e.toString(),"100",new ArrayList<TaskReturnInBoundRequestResponseDto>());
                out.write(resultStr.getBytes("UTF-8"));
                out.flush();
                out.close();

                //LogServices.logSys(e);
            }
        } catch (Exception e) {

            String resultStr = getMcsValue(false,e.toString(),"100",new ArrayList<TaskReturnInBoundRequestResponseDto>());
            out.write(resultStr.getBytes("UTF-8"));
            out.flush();
            out.close();
            //LogServices.logSys(e);
        }
    }

    private <T> String getMcsValue(Boolean success,String msg,String code, List<T> data) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ret", success);
        jsonObject.put("msg", msg);
        jsonObject.put("code", code);
        jsonObject.put("data", data);
        return jsonObject.toString();
    }
}
