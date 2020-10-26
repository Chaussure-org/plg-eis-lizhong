DELETE FROM store_area;
DELETE FROM store_area_direction;
COMMIT;
-- 堆垛机库
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark) VALUES('MCS01',10,
                                                                                                          'MCS',100,1,
                                                                                                          9316,
                                                                                                          '堆垛机库导轨1');
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark) VALUES('MCS02',10,
                                                                                                          'MCS',100,1,
                                                                                                          9316,'堆垛机库导轨2');
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark) VALUES('MCS03',10,
                                                                                                          'MCS',100,1,
                                                                                                          9316,'堆垛机库导轨3');
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark) VALUES('MCS04',10,
                                                                                                          'MCS',100,1,
                                                                                                          9316,'堆垛机库导轨4');

-- 二层agv入库接驳口
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('WCS011',20,'WCS',100,1,10,'二层agv1入库口','MTR0202',2,0,0);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('WCS021',20,'WCS',100,1,10,'二层agv2入库口','MTR0204',2,0,0);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('WCS031',20,'WCS',100,1,10,'二层agv3入库口','MTR0206',2,0,0);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('WCS041',20,'WCS',100,1,10,'二层agv4入库口','MTR0208',2,0,0);

-- 方向
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('WCS011',
                                                                                                       'MCS01',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('WCS021',
                                                                                                       'MCS02',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('WCS031',
                                                                                                       'MCS03',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('WCS041',
                                                                                                       'MCS04',
                                                                                                       100,10,1);


-- 二层agv入库点
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('RCS011',20,'RCS',100,1,10,'二层agv1入库点','000001XY000001',1,1,1);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('RCS021',20,'RCS',100,1,10,'二层agv2入库点','000003XY000001',1,3,1);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('RCS031',20,'RCS',100,1,10,'二层agv3入库点','000005XY000001',1,5,1);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('RCS041',20,'RCS',100,1,10,'二层agv4入库点','000007XY000001',1,7,1);


-- 方向
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('RCS011',
                                                                                                       'WCS011',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('RCS021',
                                                                                                       'WCS021',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('RCS031',
                                                                                                       'WCS031',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('RCS041',
                                                                                                       'WCS041',
                                                                                                       100,10,1);



-- agv
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('RCS01',
                                                                                                       'RCS011',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('RCS01',
                                                                                                       'RCS021',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('RCS01',
                                                                                                       'RCS031',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('RCS01',
                                                                                                       'RCS041',
                                                                                                       100,10,1);




INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark) VALUES('MCS05',10,
                                                                                                          'MCS',100,1,
                                                                                                          9316,'堆垛机库导轨5');


-- 二层出库agv
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('WCS012',20,'WCS',100,1,10,'二层agv1出库口','RTM0202',2,0,0);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('WCS022',20,'WCS',100,1,10,'二层agv2出库口','RTM0204',2,0,0);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('WCS032',20,'WCS',100,1,10,'二层agv3出库口','RTM0206',2,0,0);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('WCS042',20,'WCS',100,1,10,'二层agv4出库口','RTM0208',2,0,0);

-- 方向
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('MCS01',
                                                                                                       'WCS012',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('MCS02',
                                                                                                       'WCS022',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('MCS03',
                                                                                                       'WCS032',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('MCS04',
                                                                                                       'WCS042',
                                                                                                       100,10,1);


-- 二层agv出库点
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('RCS012',20,'RCS',100,1,10,'二层agv1出库点','000002XY000001',1,2,1);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('RCS022',20,'RCS',100,1,10,'二层agv2出库点','000004XY000001',1,4,1);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('RCS032',20,'RCS',100,1,10,'二层agv3出库点','000006XY000001',1,6,1);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('RCS042',20,'RCS',100,1,10,'二层agv4出库点','000008XY000001',1,8,1);


-- 二层方向
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('WCS012',
                                                                                                       'RCS012',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('WCS022',
                                                                                                       'RCS022',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('WCS032',
                                                                                                       'RCS032',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('WCS042',
                                                                                                       'RCS042',
                                                                                                       100,10,1);

-- agv库
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('RCS012',
                                                                                                       'RCS01',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('RCS022',
                                                                                                       'RCS01',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('RCS032',
                                                                                                       'RCS01',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('RCS042',
                                                                                                       'RCS01',
                                                                                                       100,10,1);






--  一层堆垛机导轨5出库接驳口
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('WCS051',20,'WCS',100,1,10,'堆垛机库导轨5BCR','BCR0104',2,5,0);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('WCS051',
                                                                                                       'MCS05',
                                                                                                       100,10,1);

INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('WCS091',20,'WCS',100,1,10,'堆垛机库借道左BCR','BCR0209',2,5,0);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('WCS092',20,'WCS',100,1,10,'堆垛机库借道右BCR','BCR0210',2,5,0);

INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('WCS052',20,'WCS',100,1,10,'堆垛机库导轨5BCR','C0102',2,5,0);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('WCS051',
                                                                                                       'MCS05',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('BCR0209',
                                                                                                       'WCS052',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('BCR0210',
                                                                                                       'WCS052',
                                                                                                       100,10,1);

--  一层堆垛机导轨5入库接驳口
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('WCS071',20,'WCS',100,1,10,'堆垛机库导轨1-4BCR','BCR0102',2,5,0);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('WCS072',20,'WCS',100,1,10,'堆垛机库导轨1-4BCR','BCR0103',2,5,0);

INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('WCS071',
                                                                                                       'MCS04',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('WCS071',
                                                                                                       'MCS03',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('WCS071',
                                                                                                       'MCS02',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('WCS071',
                                                                                                       'MCS01',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('WCS072',
                                                                                                       'MCS04',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('WCS072',
                                                                                                       'MCS03',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('WCS072',
                                                                                                       'MCS02',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('WCS072',
                                                                                                       'MCS01',
                                                                                                       100,10,1);











-- agv
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark) VALUES('RCS01',10,
                                                                                                          'RCS',100,1,
                                                                                                          2592,
                                                                                                          'AGV库');

-- SN站台区
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark) VALUES('SN01',10,
                                                                                                          'RCS',100,1,
                                                                                                          20,
                                                                                                          '站台区');


-- 方向
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('SN01',
                                                                                                       'RCS01',
                                                                                                       100,10,1);

-- 方向
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('RCS01',
                                                                                                       'SN01',
                                                                                                       100,10,1);


-- 拆盘机区
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('BK01',20,'RCS',100,1,10,'拆盘机入口','100003XY100001',1,100003,100001);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('BK02',20,'RCS',100,1,10,'拆盘机出口','100003XY100002',1,100003,100002);


-- 入方向
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('RCS021',
                                                                                                       'BK01',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('RCS022',
                                                                                                       'BK01',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('RCS023',
                                                                                                       'BK01',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('RCS024',
                                                                                                       'BK01',
                                                                                                       100,10,1);


-- 出方向
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('BK02',
                                                                                                       'OD01',
                                                                                                       100,10,1);



-- OD订单托区
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark) VALUES('OD01',10,
                                                                                                          'RCS',100,1,
                                                                                                          20,
                                                                                                          '订单拖区');



-- 贴标区
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark) VALUES('LB01',10,
                                                                                                          'RCS',100,1,
                                                                                                          100,
                                                                                                          '贴标区');

-- 方向
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('OD01',
                                                                                                       'LB01',
                                                                                                       100,10,1);




-- 暂存区
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark) VALUES('CH01',10,
                                                                                                          'RCS',100,1,
                                                                                                          100,
                                                                                                          '暂存区');
-- 方向
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('OD01',
                                                                                                       'CH01',
                                                                                                       100,10,1);








-- 四向库
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark) VALUES('SAS01',10,
                                                                                                          'SAS',100,1,
                                                                                                          30348,
                                                                                                          '四向库');


-- 一层出入输送线点位
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('WCS061',20,'WCS',100,1,10,'四向库入库BCR','BCR0101',2,0,0);

-- 一层方向
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('WCS061',
                                                                                                       'SAS01',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('SAS01',
                                                                                                       'WCS061',
                                                                                                       100,10,1);

INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('WCS081',20,'WCS',100,1,10,'四向库二楼出库BCR','LXJZ01',15,0,0);


INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('WCS082',20,'WCS',100,1,10,'四向库二楼入库BCR','LXHK02',15,0,0);


INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('SAS01',
                                                                                                       'LXJZ01',
                                                                                                       100,10,1);

INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('LXHK02',
                                                                                                       'SAS01',
                                                                                                       100,10,1);



COMMIT;

