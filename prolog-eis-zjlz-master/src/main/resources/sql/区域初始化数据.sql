DELETE FROM store_area;
DELETE FROM store_area_direction;
COMMIT;
-- 堆垛机库
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark) VALUES('D010',10,'SAS',100,1,100,'堆垛机库导轨1');
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark) VALUES('D020',10,'SAS',100,1,100,'堆垛机库导轨2');
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark) VALUES('D030',10,'SAS',100,1,100,'堆垛机库导轨3');
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark) VALUES('D040',10,'SAS',100,1,100,'堆垛机库导轨4');
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark) VALUES('D050',10,'SAS',100,1,100,'堆垛机库导轨5');
-- 堆垛机入库口
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,x,y) VALUES('D011',20,'SAS',100,1,10,'堆垛机库导轨1入库叉取位','01001001',1,1,1);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,x,y) VALUES('D021',20,'SAS',100,1,10,'堆垛机库导轨2入库叉取位','01002002',1,2,2);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,x,y) VALUES('D031',20,'SAS',100,1,10,'堆垛机库导轨3入库叉取位','01003003',1,3,3);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,x,y) VALUES('D041',20,'SAS',100,1,10,'堆垛机库导轨4入库叉取位','01003003',1,4,4);
-- 堆垛机入库口-方向
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('D011','D010',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('D021','D020',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('D031','D030',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('D041','D040',100,10,1);
-- 堆垛机导轨5出库口
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark) VALUES('D051',10,'SAS',100,1,100,'堆垛机库导轨5出库口');
-- 堆垛机导轨5出库口-方向
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('D050','D051',100,10,1);
-- 堆垛机导轨5入库口
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,x,y) VALUES('D052',20,'SAS',100,1,10,'堆垛机库导轨5入库口A018','02005010',2,5,18);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,x,y) VALUES('D053',20,'SAS',100,1,10,'堆垛机库导轨5入库口A020','02005020',2,5,20);
-- 堆垛机导轨5入库口-方向
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('D052','D050',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('D053','D050',100,10,1);
-- 堆垛机输送线点位
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,x,y) VALUES('D012',20,'SAS',100,1,10,'堆垛机库导轨1输送线A002','02001002',2,1,2);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,x,y) VALUES('D013',20,'SAS',100,1,10,'堆垛机库导轨1输送线A004','02001004',2,1,4);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,x,y) VALUES('D022',20,'SAS',100,1,10,'堆垛机库导轨2输送线A006','02002006',2,2,6);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,x,y) VALUES('D023',20,'SAS',100,1,10,'堆垛机库导轨2输送线A008','02002008',2,2,8);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,x,y) VALUES('D032',20,'SAS',100,1,10,'堆垛机库导轨3输送线A010','02003010',2,3,10);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,x,y) VALUES('D033',20,'SAS',100,1,10,'堆垛机库导轨3输送线A012','02003012',2,3,12);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,x,y) VALUES('D042',20,'SAS',100,1,10,'堆垛机库导轨4输送线A014','02004014',2,4,14);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,x,y) VALUES('D043',20,'SAS',100,1,10,'堆垛机库导轨4输送线A016','02004016',2,4,16);
-- 堆垛机-堆垛机输送线点位-方向
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('D012','D010',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('D010','D013',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('D022','D020',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('D020','D023',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('D032','D030',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('D030','D033',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('D042','D040',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('D040','D043',100,10,1);

-- AGV库
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark) VALUES('A100',10,'RCS',100,1,100,'AGV货位区');
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark) VALUES('A200',10,'RCS',100,1,100,'AGV贴标区');
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark) VALUES('A300',10,'RCS',100,1,100,'AGV暂存区');
-- AGV库-方向
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A100','A200',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A100','A300',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A200','A100',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A200','A300',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A300','A100',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A300','A200',100,10,1);
-- AGV站台
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,x,y) VALUES('A101',20,'RCS',100,1,10,'AGV站台C158','001001XY001001',1,1001,1001);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,x,y) VALUES('A102',20,'RCS',100,1,10,'AGV站台C146','001001XY001002',1,1001,1002);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,x,y) VALUES('A103',20,'RCS',100,1,10,'AGV站台C133','001001XY001003',1,1001,1003);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,x,y) VALUES('A104',20,'RCS',100,1,10,'AGV站台C119','001001XY001004',1,1001,1004);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,x,y) VALUES('A105',20,'RCS',100,1,10,'AGV站台C106','001001XY001005',1,1001,1005);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,x,y) VALUES('A106',20,'RCS',100,1,10,'AGV站台C093','001001XY001006',1,1001,1006);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,x,y) VALUES('A107',20,'RCS',100,1,10,'AGV站台C079','001001XY001007',1,1001,1007);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,x,y) VALUES('A108',20,'RCS',100,1,10,'AGV站台C066','001001XY001008',1,1001,1008);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,x,y) VALUES('A109',20,'RCS',100,1,10,'AGV站台C053','001001XY001009',1,1001,1009);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,x,y) VALUES('A110',20,'RCS',100,1,10,'AGV站台C039','001001XY001010',1,1001,1010);
-- AGV库-AGV站台方向
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A100','A101',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A100','A102',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A100','A103',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A100','A104',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A100','A105',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A100','A106',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A100','A107',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A100','A108',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A100','A109',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A100','A110',100,10,1);

INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A101','A100',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A101','A200',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A101','A300',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A102','A100',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A102','A200',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A102','A300',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A103','A100',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A103','A200',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A103','A300',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A104','A100',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A104','A200',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A104','A300',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A105','A100',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A105','A200',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A105','A300',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A106','A100',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A106','A200',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A106','A300',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A107','A100',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A107','A200',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A107','A300',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A108','A100',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A108','A200',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A108','A300',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A109','A100',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A109','A200',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A109','A300',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A110','A100',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A110','A200',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A110','A300',100,10,1);
-- AGV输送线点位
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,x,y) VALUES('A111',20,'RCS',100,1,10,'AGV输送线A001','001000XY001001',1,1000,1001);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,x,y) VALUES('A112',20,'RCS',100,1,10,'AGV输送线A003','001000XY001002',1,1000,1002);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,x,y) VALUES('A113',20,'RCS',100,1,10,'AGV输送线A005','001000XY001003',1,1000,1003);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,x,y) VALUES('A114',20,'RCS',100,1,10,'AGV输送线A007','001000XY001004',1,1000,1004);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,x,y) VALUES('A115',20,'RCS',100,1,10,'AGV输送线A009','001000XY001005',1,1000,1005);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,x,y) VALUES('A116',20,'RCS',100,1,10,'AGV输送线A0011','001000XY001006',1,1000,1006);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,x,y) VALUES('A117',20,'RCS',100,1,10,'AGV输送线A0013','001000XY001007',1,1000,1007);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,x,y) VALUES('A118',20,'RCS',100,1,10,'AGV输送线A0015','001000XY001008',1,1000,1008);
-- AGV库-AGV输送线
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A100','A111',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A100','A113',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A100','A115',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A100','A117',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A200','A111',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A200','A113',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A200','A115',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A200','A117',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A300','A111',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A300','A113',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A300','A115',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A300','A117',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A112','A100',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A112','A200',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A112','A300',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A114','A100',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A114','A200',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A114','A300',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A116','A100',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A116','A200',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A116','A300',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A118','A100',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A118','A200',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A118','A300',100,10,1);

-- 输送线
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,x,y) VALUES('M001',20,'MCS',100,1,10,'A001输送线','0200990002',2,99,2);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,x,y) VALUES('M002',20,'MCS',100,1,10,'A002输送线','0210000002',2,100,2);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,x,y) VALUES('M003',20,'MCS',100,1,10,'A003输送线','0200990006',2,99,4);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,x,y) VALUES('M004',20,'MCS',100,1,10,'A004输送线','0210000008',2,100,4);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,x,y) VALUES('M005',20,'MCS',100,1,10,'A005输送线','0200990006',2,99,6);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,x,y) VALUES('M006',20,'MCS',100,1,10,'A006输送线','0210000006',2,100,6);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,x,y) VALUES('M007',20,'MCS',100,1,10,'A007输送线','0200990008',2,99,8);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,x,y) VALUES('M008',20,'MCS',100,1,10,'A008输送线','0210000008',2,100,8);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,x,y) VALUES('M009',20,'MCS',100,1,10,'A009输送线','0200990010',2,99,10);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,x,y) VALUES('M010',20,'MCS',100,1,10,'A010输送线','0210000010',2,100,10);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,x,y) VALUES('M011',20,'MCS',100,1,10,'A011输送线','0200990012',2,99,12);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,x,y) VALUES('M012',20,'MCS',100,1,10,'A012输送线','0210000012',2,100,12);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,x,y) VALUES('M013',20,'MCS',100,1,10,'A013输送线','0200990014',2,99,14);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,x,y) VALUES('M014',20,'MCS',100,1,10,'A014输送线','0210000014',2,100,14);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,x,y) VALUES('M015',20,'MCS',100,1,10,'A015输送线','0200990016',2,99,16);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,x,y) VALUES('M016',20,'MCS',100,1,10,'A016输送线','0210000016',2,100,16);
-- 输送线-方向
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('M001','M002',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('M004','M003',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('M005','M006',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('M008','M007',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('M009','M010',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('M012','M011',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('M013','M014',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('M016','M015',100,10,1);
-- 堆垛机输送线点位-输送线-方向
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('M002','D012',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('D013','M004',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('M006','D022',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('D023','M008',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('M010','D032',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('D033','M012',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('M014','D042',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('D043','M016',100,10,1);
-- AGV输送线点位-输送线-方向
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A111','M001',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('M003','A112',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A113','M005',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('M007','A114',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A115','M009',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('M011','A116',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A117','M013',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('M015','A118',100,10,1);

-- 箱库
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark) VALUES('G100',10,'GCS',100,1,100,'箱库货位区');
-- 箱库输送线点位
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,x,y) VALUES('G111',20,'GCS',100,1,10,'箱库1层输送线入库点位','0101110001',1,111,1);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,x,y) VALUES('G112',20,'GCS',100,1,10,'箱库1层输送线出库点位','0101120002',1,112,2);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,x,y) VALUES('G121',20,'GCS',100,1,10,'箱库2层输送线入库点位','0201210001',2,121,1);
INSERT INTO store_area(area_no,area_type,device_system,max_height,temporary_area,max_count,remark,location_no,layer,x,y) VALUES('G122',20,'GCS',100,1,10,'箱库2层输送线出库点位','0201220002',2,122,2);
-- 箱库-箱库输送线-方向
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('G100','G112',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('G100','G122',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('G111','G100',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('G121','G100',100,10,1);
-- 箱库2楼输送线-AGV站台-方向
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('G122','A101',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('G122','A102',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('G122','A103',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('G122','A104',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('G122','A105',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('G122','A106',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('G122','A107',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('G122','A108',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('G122','A109',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('G122','A110',100,10,1);

INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A101','G121',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A102','G121',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A103','G121',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A104','G121',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A105','G121',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A106','G121',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A107','G121',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A108','G121',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A109','G121',100,10,1);
INSERT INTO store_area_direction(source_area_no,target_area_no,max_height,path_step,path_power) VALUES('A110','G121',100,10,1);
COMMIT;

