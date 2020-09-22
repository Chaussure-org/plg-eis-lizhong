package com.prolog.eis.wcs.service.impl;

import com.prolog.eis.model.point.PointLocation;
import com.prolog.eis.service.pointlocation.IPointLocationService;
import com.prolog.eis.util.CollectionUtils;
import com.prolog.eis.wcs.common.Constant;
import com.prolog.eis.wcs.dao.WCSHistoyTaskMapper;
import com.prolog.eis.wcs.dao.WCSTaskMapper;
import com.prolog.eis.wcs.model.WCSHistoryTask;
import com.prolog.eis.wcs.model.WCSTask;
import com.prolog.eis.wcs.service.IWCSTaskService;
import com.prolog.framework.core.exception.ParameterException;
import com.prolog.framework.core.restriction.Criteria;
import com.prolog.framework.core.restriction.Restrictions;
import com.prolog.framework.utils.MapUtils;
import com.prolog.framework.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class WCSTaskServiceImpl implements IWCSTaskService {

    @Autowired
    private WCSTaskMapper taskMapper;
    @Autowired
    private WCSHistoyTaskMapper histoyTaskMapper;
    @Autowired
    private IPointLocationService locationService;
    /**
     * 添加任务
     *
     * @param task
     * @throws Exception
     */
    @Override
    public void add(WCSTask task) throws Exception {
        if(task==null || StringUtils.isBlank(task.getId()))
            throw new ParameterException("id不能为空");

        task.setGmtCreateTime(new Date());
        task.setStatus(WCSTask.STATUS_WAITTING);
        taskMapper.save(task);
    }

    /**
     * 更新任务
     *
     * @param task
     * @throws Exception
     */
    @Override
    public void update(WCSTask task) throws Exception {
        if(task==null || StringUtils.isBlank(task.getId()))
            throw new ParameterException("id不能为空");
        taskMapper.update(task);
    }

    /**
     * 转历史
     *
     * @param task
     * @throws Exception
     */
    @Override
    public void toHistory(WCSTask task) throws Exception {
        if(task==null || StringUtils.isBlank(task.getId()))
            throw new ParameterException("参数不能为空");

        WCSHistoryTask historyTask = task.getHistoryTask();
        histoyTaskMapper.save(historyTask);
        this.delete(task.getId());
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public void delete(String... id) {
        if(id==null || id.length==0)
            return;
        Criteria ctr = Criteria.forClass(WCSTask.class);
        ctr.setRestriction(Restrictions.in("id",id));
        taskMapper.deleteByCriteria(ctr);
    }

    /**
     * 删除历史
     *
     * @param id
     */
    @Override
    public void deleteHistory(String... id) {
        if(id==null || id.length==0)
            return;
        Criteria ctr = Criteria.forClass(WCSHistoryTask.class);
        ctr.setRestriction(Restrictions.in("id",id));
        histoyTaskMapper.deleteByCriteria(ctr);
    }

    /**
     * 查询任务
     *
     * @param id
     * @return
     */
    @Override
    public WCSTask getTask(String id) {
        return taskMapper.findById(id,WCSTask.class);
    }

    /**
     * 根据map查询任务
     *
     * @param params
     * @return
     */
    @Override
    public List<WCSTask> getTaskByMap(Map<String, Object> params) {
        return taskMapper.findByMap(params,WCSTask.class);
    }

    @Override
    public long getTaskCountByMap(Map<String, Object> params) {
        return taskMapper.findCountByMap(params,WCSTask.class);
    }
    /**
     * 根据map查询历史任务
     *
     * @param params
     * @return
     */
    @Override
    public List<WCSHistoryTask> getHistoryTaskByMap(Map<String, Object> params) {
        return histoyTaskMapper.findByMap(params,WCSHistoryTask.class);
    }

    /**
     * 开始任务
     *
     * @param taskId
     */
    @Override
    public void startTask(String taskId) throws Exception {
        WCSTask task = taskMapper.findById(taskId,WCSTask.class);
        task.setStatus(WCSTask.STATUS_RUNNING);
        task.setGmtStartTime(new Date());
        taskMapper.update(task);
    }

    /**
     * 结束任务
     *
     * @param taskId
     * @param success
     * @throws Exception
     */
    @Override
    public void finishTask(String taskId,boolean success) throws Exception {
        WCSTask task = taskMapper.findById(taskId,WCSTask.class);
        task.setStatus(success ? WCSTask.STATUS_FINISH:WCSTask.STATUS_FAILURE);
        if(task.getGmtCreateTime()==null){
            task.setGmtStartTime(new Date());
        }
        task.setGmtFinishTime(new Date());
        //taskMapper.update(task);
        //转历史
        this.toHistory(task);
    }

    /**
     * 根据料箱号结束任务
     *
     * @param containerNo
     * @param success
     * @throws Exception
     */
    @Override
    public void finishTaskByContainerNo(String containerNo, boolean success) throws Exception {
        List<WCSTask> tasks = taskMapper.findByMap(MapUtils.put("containerNo",containerNo).getMap(),WCSTask.class);;
        if(tasks.size()<1)
            throw new RuntimeException("找不到料箱号（"+containerNo+"）对应的任务");
        WCSTask task = tasks.get(0);
        task.setStatus(success ? WCSTask.STATUS_FINISH:WCSTask.STATUS_FAILURE);
        if(task.getGmtCreateTime()==null){
            task.setGmtStartTime(new Date());
        }
        task.setGmtFinishTime(new Date());
        //taskMapper.update(task);
        //转历史
        this.toHistory(task);
    }

    /**
     * 根据料箱号获取任务
     *
     * @param containerNo
     * @return
     */
    @Override
    public WCSTask getByContainerNo(String containerNo) {
        List<WCSTask> list = taskMapper.findByMap(MapUtils.put("containerNo",containerNo).getMap(),WCSTask.class);
        return list.size()>0?list.get(0):null;
    }

    /**
     * 根据起始点位获取任务信息
     *
     * @param address
     * @return
     */
    @Override
    public List<WCSTask> getByAdrress(String address) {
        List<WCSTask> list = taskMapper.findByMap(MapUtils.put("address",address).getMap(),WCSTask.class);
        return list;
    }

    /**
     * 根据目标点位获取任务信息
     *
     * @param target
     * @return
     */
    @Override
    public List<WCSTask> getByTarget(String target) {
        List<WCSTask> list = taskMapper.findByMap(MapUtils.put("target",target).getMap(),WCSTask.class);
        return list;
    }


    /**
     * 寻找出库任务最少的出库口
     * @return
     */
    @Override
    public PointLocation getBestOutboundLocation(){
        List<PointLocation> pos = locationService.getPointByType(PointLocation.TYPE_OUT_STORE);
        List<WCSTask> list = taskMapper.findByMap(MapUtils.put("wcsType",Constant.TASK_TYPE_CK).getMap(),WCSTask.class);
        Map<String,List<WCSTask>> maps = CollectionUtils.mapList(list, x->x.getTarget(),x-> 1==1);
        if(maps.size()==0){
            pos = pos.stream().filter(x -> x.getStationId()>0).collect(Collectors.toList());
            return  pos.get(0);
        }

        pos.forEach(x->{
            if(!maps.keySet().contains(x.getPointId()) && x.getStationId()>0){
                maps.put(x.getPointId(),new ArrayList<>());
            }
        });

        String target = CollectionUtils.findSmallest(maps,x->x.size());
        //安全门
        PointLocation result = pos.stream().filter(x->x.getPointId().equals(target)&&!x.isDisable()).findFirst().get();

        return result;
    }

    @Override
    public PointLocation getBestInboundLocation(){
        //List<PointLocation> pos = locationService.getPointByType(PointLocation.TYPE_IN_STORE);
        List<PointLocation> pos = locationService.getPointByType(PointLocation.TYPE_IN);
        //List<String> posIds = pos.stream().map(x->x.getPointId()).collect(Collectors.toList());
        // 入库时若安全门打开,则屏蔽部分点位
        pos = pos.stream().filter(x->!x.isDisable()).collect(Collectors.toList());
        List<String> posIds = pos.stream().map(x->x.getPointId()).collect(Collectors.toList());
        //List<WCSTask> list = taskMapper.findByMap(MapUtils.put("wcsType",Constant.TASK_TYPE_RK).getMap(),WCSTask.class);

        Criteria ctr = Criteria.forClass(WCSTask.class);
        ctr.setRestriction(Restrictions.and(Restrictions.eq("wcsType",Constant.TASK_TYPE_XZ),Restrictions.in("target",posIds.toArray())));
        List<WCSTask> list = taskMapper.findByCriteria(ctr);
        if(list.size() < 2) {
  //      if(list.size()==0){
            //pos = pos.stream().filter(x -> x.getStationId()>0).collect(Collectors.toList());
            //转换成线体点位
            //int stationId = pos.get(0).getStationId();
            //return locationService.getPointByStation(stationId,PointLocation.TYPE_IN).get(0);
            return pos.get(0);
        }

        Map<String,List<WCSTask>> maps = CollectionUtils.mapList(list, x->x.getTarget(),x-> 1==1);
        pos.forEach(x->{
            if(!maps.keySet().contains(x.getPointId()) && x.getStationId()>0){
                maps.put(x.getPointId(),new ArrayList<>());
            }
        });

        String target = CollectionUtils.findSmallest(maps,x->x.size());
        PointLocation result = pos.stream().filter(x->x.getPointId().equals(target)).findFirst().get();
        //转换成线体点位
        //int stationId = result.getStationId();
        //return locationService.getPointByStation(stationId,PointLocation.TYPE_IN).get(0);
        return result;
    }

    /**
     * 获取出库任务
     *
     * @param stationId
     * @return
     */
    @Override
    public List<WCSTask> getOutboundTask(int stationId) {
        if(stationId==0){
            return taskMapper.findByMap(MapUtils.put("wcsType",Constant.TASK_TYPE_CK).getMap(),WCSTask.class);
        }else{
            return taskMapper.findByMap(MapUtils.put("wcsType",Constant.TASK_TYPE_CK).put("stationId",stationId).getMap(),WCSTask.class);
        }
    }

    /**
     * 获取入库任务
     *
     * @param stationId
     * @return
     */
    @Override
    public List<WCSTask> getInboundTask(int stationId) {
        if(stationId==0){
            return taskMapper.findByMap(MapUtils.put("wcsType",Constant.TASK_TYPE_RK).getMap(),WCSTask.class);
        }else{
            return taskMapper.findByMap(MapUtils.put("wcsType",Constant.TASK_TYPE_RK).put("stationId",stationId).getMap(),WCSTask.class);
        }
    }

    @Override
    public List<WCSTask> getTaskByContainer(String containerNo) {
        return taskMapper.findByMap(MapUtils.put("containerNo",containerNo).getMap(),WCSTask.class);
    }


}
