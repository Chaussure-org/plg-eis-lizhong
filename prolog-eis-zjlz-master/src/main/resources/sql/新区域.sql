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
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark) VALUES('MCS05',10,
                                                                                                          'MCS',100,1,
                                                                                                          9316,'堆垛机库导轨5');
-- 一层堆垛机入库口
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('MCS011',20,'MCS',100,1,10,'堆垛机库导轨1入库叉取位','020001000001',2,1,0);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('MCS021',20,'MCS',100,1,10,'堆垛机库导轨2入库叉取位','020002000001',2,2,0);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('MCS031',20,'MCS',100,1,10,'堆垛机库导轨3入库叉取位','020003000001',2,3,0);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('MCS041',20,'MCS',100,1,10,'堆垛机库导轨4入库叉取位','020004000001',2,4,0);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('MCS051',20,'MCS',100,1,10,'堆垛机库导轨5入库叉取位','020005000001',2,5,0);
-- 一层堆垛机入库口-方向
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('MCS011',
                                                                                                       'MCS01',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('MCS021',
                                                                                                       'MCS02',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('MCS031',
                                                                                                       'MCS03',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('MCS041',
                                                                                                       'MCS04',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('MCS051',
                                                                                                       'MCS05',
                                                                                                       100,10,1);
-- 一层堆垛机入库接驳口
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('WCS011',20,'WCS',100,1,10,'堆垛机库入库1接驳口','R0102',1,0,0);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('WCS021',20,'WCS',100,1,10,'堆垛机库入库2接驳口','R0103',1,0,0);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('WCS031',20,'WCS',100,1,10,'堆垛机库入库3接驳口','R0104',1,0,0);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('WCS041',20,'WCS',100,1,10,'堆垛机库入库4接驳口','R0105',1,0,0);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('WCS051',20,'WCS',100,1,10,'堆垛机库入库5接驳口','R0106',1,0,0);

INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('WCS011',
                                                                                                       'MCS011',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('WCS021',
                                                                                                       'MCS021',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('WCS031',
                                                                                                       'MCS031',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('WCS041',
                                                                                                       'MCS041',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('WCS051',
                                                                                                       'MCS011',
                                                                                                       100,10,1);


-- 一层堆垛机导轨5出库口
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('MCS052',20,'MCS',100,1,10,'堆垛机库导轨5出库口','020005000002',2,5,0);
-- 一层堆垛机导轨5出库口-方向
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('MCS05',
                                                                                                       'MCS052',
                                                                                                       100,10,1);

--  一层堆垛机导轨5出库接驳口
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('WCS052',20,'WCS',100,1,10,'堆垛机库导轨5出库接驳口','C0102',2,5,0);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('MCS052',
                                                                                                       'WCS052',
                                                                                                       100,10,1);




-- 二层堆垛机导轨入库口
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('MCS012',20,'MCS',100,1,10,'堆垛机库导轨1入库口','090001000001',9,1,0);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('MCS022',20,'MCS',100,1,10,'堆垛机库导轨2入库口','090002000001',9,2,0);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('MCS032',20,'MCS',100,1,10,'堆垛机库导轨3入库口','090003000001',9,3,0);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('MCS042',20,'MCS',100,1,10,'堆垛机库导轨4入库口','090004000001',9,4,0);

INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('MCS012',
                                                                                                       'MCS01',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('MCS022',
                                                                                                       'MCS02',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('MCS032',
                                                                                                       'MCS03',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('MCS042',
                                                                                                       'MCS04',
                                                                                                       100,10,1);

-- -- 二层入库接驳口
-- INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
--                        x,y) VALUES('WCS012',20,'WCS',100,1,10,'二层1入库口','MTR0201',2,0,0);
-- INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
--                        x,y) VALUES('WCS022',20,'WCS',100,1,10,'二层2入库口','MTR0203',2,0,0);
-- INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
--                        x,y) VALUES('WCS032',20,'WCS',100,1,10,'二层3入库口','MTR0205',2,0,0);
-- INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
--                        x,y) VALUES('WCS042',20,'WCS',100,1,10,'二层4入库口','MTR0207',2,0,0);

-- -- 方向
-- INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('WCS012',
--                                                                                                        'MCS012',
--                                                                                                        100,10,1);
-- INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('WCS022',
--                                                                                                        'MCS022',
--                                                                                                        100,10,1);
-- INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('WCS032',
--                                                                                                        'MCS032',
--                                                                                                        100,10,1);
-- INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('WCS042',
--                                                                                                        'MCS042',
--                                                                                                        100,10,1);

-- 二层agv入库接驳口
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('WCS014',20,'WCS',100,1,10,'二层agv1入库口','MTR0202',2,0,0);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('WCS024',20,'WCS',100,1,10,'二层agv2入库口','MTR0204',2,0,0);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('WCS034',20,'WCS',100,1,10,'二层agv3入库口','MTR0206',2,0,0);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('WCS044',20,'WCS',100,1,10,'二层agv4入库口','MTR0208',2,0,0);

-- 方向
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('WCS014',
                                                                                                       'WCS012',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('WCS024',
                                                                                                       'WCS022',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('WCS034',
                                                                                                       'WCS032',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('WCS044',
                                                                                                       'WCS042',
                                                                                                       100,10,1);

-- 二层agv入库点
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('RCS011',20,'RCS',100,1,10,'二层agv1入库点','000001XY000001',1,1,1);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('RCS012',20,'RCS',100,1,10,'二层agv2入库点','000003XY000001',1,3,1);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('RCS013',20,'RCS',100,1,10,'二层agv3入库点','000005XY000001',1,5,1);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('RCS014',20,'RCS',100,1,10,'二层agv4入库点','000007XY000001',1,7,1);

-- 方向
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('RCS011',
                                                                                                       'WCS014',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('RCS012',
                                                                                                       'WCS024',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('RCS013',
                                                                                                       'WCS034',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('RCS014',
                                                                                                       'WCS044',
                                                                                                       100,10,1);

-- agv
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('RCS01',
                                                                                                       'RCS011',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('RCS01',
                                                                                                       'RCS012',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('RCS01',
                                                                                                       'RCS013',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('RCS01',
                                                                                                       'RCS014',
                                                                                                       100,10,1);






-- -- 二层堆垛机导轨出库口
-- INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
--                        x,y) VALUES('MCS013',20,'MCS',100,1,10,'堆垛机库导轨1出库口','090001000002',9,1,0);
-- INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
--                        x,y) VALUES('MCS023',20,'MCS',100,1,10,'堆垛机库导轨2出库口','090002000002',9,2,0);
-- INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
--                        x,y) VALUES('MCS033',20,'MCS',100,1,10,'堆垛机库导轨3出库口','090003000002',9,3,0);
-- INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
--                        x,y) VALUES('MCS043',20,'MCS',100,1,10,'堆垛机库导轨4出库口','090004000002',9,4,0);

-- 堆垛机方向

INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('MCS01',
                                                                                                       'MCS013',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('MCS02',
                                                                                                       'MCS023',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('MCS03',
                                                                                                       'MCS033',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('MCS04',
                                                                                                       'MCS043',
                                                                                                       100,10,1);
--
-- -- 二层出库接驳口
-- INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
--                        x,y) VALUES('WCS013',20,'WCS',100,1,10,'堆垛机库导轨1出库接驳口','RTM0201',2,0,0);
-- INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
--                        x,y) VALUES('WCS023',20,'WCS',100,1,10,'堆垛机库导轨2出库接驳口','RTM0203',2,0,0);
-- INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
--                        x,y) VALUES('WCS033',20,'WCS',100,1,10,'堆垛机库导轨3出库接驳口','RTM0205',2,0,0);
-- INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
--                        x,y) VALUES('WCS043',20,'WCS',100,1,10,'堆垛机库导轨4出库接驳口','RTM0207',2,0,0);


-- 方向
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('MCS013',
                                                                                                       'WCS013',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('MCS023',
                                                                                                       'WCS023',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('MCS033',
                                                                                                       'WCS033',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('MCS043',
                                                                                                       'WCS043',
                                                                                                       100,10,1);
-- 二层出库agv
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('WCS015',20,'WCS',100,1,10,'二层agv1出库口','RTM0202',2,0,0);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('WCS025',20,'WCS',100,1,10,'二层agv2出库口','RTM0204',2,0,0);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('WCS035',20,'WCS',100,1,10,'二层agv3出库口','RTM0206',2,0,0);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('WCS045',20,'WCS',100,1,10,'二层agv4出库口','RTM0208',2,0,0);

-- 方向
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('WCS013',
                                                                                                       'WCS015',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('WCS023',
                                                                                                       'WCS025',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('WCS033',
                                                                                                       'WCS035',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('WCS043',
                                                                                                       'WCS045',
                                                                                                       100,10,1);

-- 二层agv出库点
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('RCS021',20,'RCS',100,1,10,'二层agv1出库点','000002XY000001',1,2,1);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('RCS022',20,'RCS',100,1,10,'二层agv2出库点','000004XY000001',1,4,1);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('RCS023',20,'RCS',100,1,10,'二层agv3出库点','000006XY000001',1,6,1);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('RCS024',20,'RCS',100,1,10,'二层agv4出库点','000008XY000001',1,8,1);

-- 二层方向
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('WCS015',
                                                                                                       'RCS021',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('WCS025',
                                                                                                       'RCS022',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('WCS035',
                                                                                                       'RCS023',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('WCS045',
                                                                                                       'RCS024',
                                                                                                       100,10,1);

-- agv库
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('RCS021',
                                                                                                       'RCS01',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('RCS022',
                                                                                                       'RCS01',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('RCS023',
                                                                                                       'RCS01',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('RCS024',
                                                                                                       'RCS01',
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

-- 一层出入
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('SAS011',20,'SAS',100,1,10,'四向库入库口','020000000001',2,0,0);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('SAS012',20,'SAS',100,1,10,'四向库出库口','020000000002',2,0,0);

-- 一层出入方向
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('SAS011',
                                                                                                       'SAS01',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('SAS01',
                                                                                                       'SAS012',
                                                                                                       100,10,1);

-- 一层出入输送线点位
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('WCS061',20,'WCS',100,1,10,'四向库入库口','R0101',2,0,0);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('WCS062',20,'WCS',100,1,10,'四向库出库口','C0101',2,0,0);

-- 一层方向
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('WCS061',
                                                                                                       'SAS011',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('SAS012',
                                                                                                       'WCS062',
                                                                                                       100,10,1);


-- 15层出入
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('SAS013',20,'SAS',100,1,10,'四向库入库口','150000000001',15,0,0);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('SAS014',20,'SAS',100,1,10,'四向库出库口','150000000002',15,0,0);


-- 15层出入方向
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('SAS013',
                                                                                                       'SAS01',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('SAS01',
                                                                                                       'SAS014',
                                                                                                       100,10,1);

-- 15层出入输送线点位
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('WCS063',20,'WCS',100,1,10,'四向库入库口','R0201',15,0,0);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,
                       x,y) VALUES('WCS064',20,'WCS',100,1,10,'四向库出库口','C0201',15,0,0);


-- 15层方向
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('WCS063',
                                                                                                       'SAS013',
                                                                                                       100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('SAS014',
                                                                                                       'WCS064',
                                                                                                       100,10,1);

COMMIT;

