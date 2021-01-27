package com.prolog.eis.location.service.impl;

import com.prolog.eis.dto.location.InStoreLocationGroupDto;
import com.prolog.eis.dto.location.sxk.StoreLocationDistanceDto;
import com.prolog.eis.dto.store.SxStoreLocationDto;
import com.prolog.eis.location.dao.ContainerPathTaskMapper;
import com.prolog.eis.location.dao.SxStoreLocationGroupMapper;
import com.prolog.eis.location.dao.SxStoreLocationMapper;
import com.prolog.eis.location.service.SxkLocationService;
import com.prolog.eis.model.GoodsInfo;
import com.prolog.eis.model.location.ContainerPathTask;
import com.prolog.eis.model.location.StoreArea;
import com.prolog.eis.model.store.SxStoreLocation;
import com.prolog.eis.model.store.SxStoreLocationGroup;
import com.prolog.eis.store.service.IContainerStoreService;
import com.prolog.eis.util.ListHelper;
import com.prolog.eis.util.location.LocationConstants;
import com.prolog.framework.core.restriction.Criteria;
import com.prolog.framework.core.restriction.Restrictions;
import com.prolog.framework.utils.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SxkLocationServiceImpl implements SxkLocationService {

	@Autowired
	private SxStoreLocationGroupMapper sxStoreLocationGroupMapper;
	@Autowired
	private SxStoreLocationMapper sxStoreLocationMapper;
	@Autowired
	private ContainerPathTaskMapper containerPathTaskMapper;
	@Autowired
	private IContainerStoreService containerStoreService;

	@Override
	public SxStoreLocation findLoacationByArea(String area,int layer, int originX, int originY, int reserveCount, double weight, String taskProperty1, String taskProperty2) throws Exception {
		// TODO Auto-generated method stub

		Integer storeLocationGroupId = findLocationGroup(layer,taskProperty1, taskProperty2, originX, originY,
				area, weight);

		SxStoreLocation result = findLocationId(storeLocationGroupId, taskProperty1, taskProperty2, weight);

		return result;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void computeLocation(SxStoreLocationGroup sxStoreLocationGroup) throws Exception {
		
		int sxStoreLocationGroupId = sxStoreLocationGroup.getId();
		if (sxStoreLocationGroup.getEntrance() == 1) {
			// 入库朝上的 找最小的有货的索引值，其他的货位减索引值
			List<SxStoreLocationDto> sxStoreLocations = sxStoreLocationMapper.findMinHaveStore(sxStoreLocationGroupId, LocationConstants.PATH_TASK_STATE_START, LocationConstants.PATH_TASK_DETAIL_STATE_SEND);
			Integer haveStoreIndex = 0;
			if (sxStoreLocations.size() > 0) {
				SxStoreLocationDto sxStoreLocation3 = sxStoreLocations.get(0);
				for (SxStoreLocationDto sxStoreLocation2 : sxStoreLocations) {
					int index = sxStoreLocation2.getLocationIndex();
					int count = Math.abs(index - sxStoreLocation3.getLocationIndex());
					sxStoreLocation2.setDeptNum(count);
					if (count == 0) {
						haveStoreIndex = index - 1;
					}
					sxStoreLocationMapper.updateMapById(sxStoreLocation2.getId(),
							MapUtils.put("deptNum", count).getMap(), SxStoreLocation.class);
				}
				// 找移位数位0的，应该为1个
				SxStoreLocationDto sxStoreLocations2 = ListHelper.firstOrDefault(sxStoreLocations, p -> p.getDeptNum() == 0);
				String containerNo = this.getContainerNo(sxStoreLocations2.getSourceContainerNo(),sxStoreLocations2.getTargetContainerNo());
				
				GoodsInfo goodsInfo = containerStoreService.findContainerStockInfo(containerNo);
				//获取商品库存属性
				String taskProperty1 = containerStoreService.buildTaskProperty1(goodsInfo);
				String taskProperty2 = containerStoreService.buildTaskProperty2(goodsInfo);
				
				sxStoreLocationGroupMapper.updateMapById(sxStoreLocationGroupId,
						MapUtils.put("entrance1Property1", taskProperty1)
								.put("entrance1Property2", taskProperty2).getMap(),
						SxStoreLocationGroup.class);
			} else {
				// 找索引最大的货位
				haveStoreIndex = sxStoreLocationGroup.getLocationNum();
				sxStoreLocationGroupMapper.updateMapById(sxStoreLocationGroupId,
						MapUtils.put("entrance1Property1", null).put("entrance1Property2", null).getMap(),
						SxStoreLocationGroup.class);
			}
			List<SxStoreLocation> sxStoreLocations2 = sxStoreLocationMapper.findByMap(MapUtils
					.put("storeLocationGroupId", sxStoreLocationGroupId).put("locationIndex", haveStoreIndex).getMap(),
					SxStoreLocation.class);
			if (sxStoreLocations2.size() == 1) {
				sxStoreLocationMapper.updateMapById(sxStoreLocations2.get(0).getId(),
						MapUtils.put("isInBoundLocation", 1).getMap(), SxStoreLocation.class);
			}
			sxStoreLocationMapper.updateNotIsInboundLocation(sxStoreLocationGroupId,haveStoreIndex);
		} else if (sxStoreLocationGroup.getEntrance() == 2) {
			Integer haveStoreIndex = 0;
			List<SxStoreLocationDto> sxStoreLocations = sxStoreLocationMapper.findMaxHaveStore(sxStoreLocationGroupId, LocationConstants.PATH_TASK_STATE_START, LocationConstants.PATH_TASK_DETAIL_STATE_SEND);
			if (sxStoreLocations.size() > 0) {
				SxStoreLocationDto sxStoreLocation3 = sxStoreLocations.get(0);
				for (SxStoreLocationDto sxStoreLocation2 : sxStoreLocations) {
					int index = sxStoreLocation2.getLocationIndex();
					int count = Math.abs(index - sxStoreLocation3.getLocationIndex());
					sxStoreLocation2.setDeptNum(count);
					if (count == 0) {
						haveStoreIndex = index + 1;
					}
					sxStoreLocationMapper.updateMapById(sxStoreLocation2.getId(),
							MapUtils.put("deptNum", count).getMap(), SxStoreLocation.class);
				}
				SxStoreLocationDto sxStoreLocations2 = ListHelper.firstOrDefault(sxStoreLocations, p -> p.getDeptNum() == 0);
				String containerNo = this.getContainerNo(sxStoreLocations2.getSourceContainerNo(),sxStoreLocations2.getTargetContainerNo());
				
				GoodsInfo goodsInfo = containerStoreService.findContainerStockInfo(containerNo);
				//获取商品库存属性
				String taskProperty1 = containerStoreService.buildTaskProperty1(goodsInfo);
				String taskProperty2 = containerStoreService.buildTaskProperty2(goodsInfo);
			
				sxStoreLocationGroupMapper.updateMapById(sxStoreLocationGroupId,
						MapUtils.put("entrance2Property1", taskProperty1)
								.put("entrance2Property2", taskProperty2).getMap(),
						SxStoreLocationGroup.class);
			} else {
				haveStoreIndex = 1;
				List<SxStoreLocation> sxStoreLocations2 = sxStoreLocationMapper.findByMap(
						MapUtils.put("storeLocationGroupId", sxStoreLocationGroupId).put("locationIndex", 1).getMap(),
						SxStoreLocation.class);
				sxStoreLocationMapper.updateMapById(sxStoreLocations2.get(0).getId(),
						MapUtils.put("deptNum", 0).put("isInBoundLocation", 1).getMap(), SxStoreLocation.class);
				sxStoreLocationGroupMapper.updateMapById(sxStoreLocationGroupId,
						MapUtils.put("entrance2Property1", null).put("entrance2Property2", null).getMap(),
						SxStoreLocationGroup.class);
			}
			List<SxStoreLocation> sxStoreLocations2 = sxStoreLocationMapper.findByMap(MapUtils
					.put("storeLocationGroupId", sxStoreLocationGroupId).put("locationIndex", haveStoreIndex).getMap(),
					SxStoreLocation.class);
			if (sxStoreLocations2.size() == 1) {
				sxStoreLocationMapper.updateMapById(sxStoreLocations2.get(0).getId(),
						MapUtils.put("isInBoundLocation", 1).getMap(), SxStoreLocation.class);
			}
			sxStoreLocationMapper.updateNotIsInboundLocation(sxStoreLocationGroupId,haveStoreIndex);

		} else if (sxStoreLocationGroup.getEntrance() == 3) {
			List<SxStoreLocationDto> sxStoreLocations = sxStoreLocationMapper.findHaveStore(sxStoreLocationGroupId, LocationConstants.PATH_TASK_STATE_START, LocationConstants.PATH_TASK_DETAIL_STATE_SEND);
			if (sxStoreLocations.size() > 0) {
				Integer bigHaveStoreIndex = 0;
				Integer smallHaveStoreIndex = 0;
				for (SxStoreLocationDto sxStoreLocation2 : sxStoreLocations) {
					int index = sxStoreLocation2.getLocationIndex();
					int bigCount = this.findBigIndexCount(sxStoreLocations, index);
					int smallCount = this.findSmallIndexCount(sxStoreLocations, index);
					if (bigCount > smallCount) {
						sxStoreLocation2.setDeptNum(smallCount);
						if (smallCount == 0) {
							smallHaveStoreIndex = index - 1;
						}
						sxStoreLocationMapper.updateMapById(sxStoreLocation2.getId(),
								MapUtils.put("deptNum", smallCount).getMap(), SxStoreLocation.class);
					} else if (bigCount == smallCount && bigCount == 0) {
						smallHaveStoreIndex = index - 1;
						bigHaveStoreIndex = index + 1;
						sxStoreLocation2.setDeptNum(0);
						sxStoreLocationMapper.updateMapById(sxStoreLocation2.getId(),
								MapUtils.put("deptNum", 0).getMap(), SxStoreLocation.class);
					} else {
						if (bigCount == 0) {
							bigHaveStoreIndex = index + 1;// 8
						}
						sxStoreLocation2.setDeptNum(bigCount);
						sxStoreLocationMapper.updateMapById(sxStoreLocation2.getId(),
								MapUtils.put("deptNum", bigCount).getMap(), SxStoreLocation.class);
					}
				}
				// 找出移库数为0的两个或一个，将入库属性赋值
				List<SxStoreLocationDto> sxStoreLocations2 = ListHelper.where(sxStoreLocations, p -> p.getDeptNum() == 0);
				if (sxStoreLocations2.size() == 1) {
					String containerNo = this.getContainerNo(sxStoreLocations2.get(0).getSourceContainerNo(),sxStoreLocations2.get(0).getTargetContainerNo());
					
					GoodsInfo goodsInfo = containerStoreService.findContainerStockInfo(containerNo);
					//获取商品库存属性
					String taskProperty1 = containerStoreService.buildTaskProperty1(goodsInfo);
					String taskProperty2 = containerStoreService.buildTaskProperty2(goodsInfo);
					
					sxStoreLocationGroupMapper.updateMapById(sxStoreLocationGroupId,
							MapUtils.put("entrance2Property1", taskProperty1)
									.put("entrance2Property2", taskProperty2)
									.put("entrance1Property1", taskProperty1)
									.put("entrance1Property2", taskProperty2).getMap(),
							SxStoreLocationGroup.class);
				} else if (sxStoreLocations2.size() == 2) {
					SxStoreLocationDto sxStoreLocation2 = sxStoreLocations2.get(0);
					SxStoreLocationDto sxStoreLocation3 = sxStoreLocations2.get(1);

					String containerNoA = this.getContainerNo(sxStoreLocation2.getSourceContainerNo(),sxStoreLocation2.getTargetContainerNo());
					GoodsInfo goodsInfoA = containerStoreService.findContainerStockInfo(containerNoA);
					//获取商品库存属性
					String taskPropertyA1 = containerStoreService.buildTaskProperty1(goodsInfoA);
					String taskPropertyA2 = containerStoreService.buildTaskProperty2(goodsInfoA);
					
					String containerNoB = this.getContainerNo(sxStoreLocation3.getSourceContainerNo(),sxStoreLocation3.getTargetContainerNo());
					GoodsInfo goodsInfoB = containerStoreService.findContainerStockInfo(containerNoB);
					//获取商品库存属性
					String taskPropertyB1 = containerStoreService.buildTaskProperty1(goodsInfoB);
					String taskPropertyB2 = containerStoreService.buildTaskProperty2(goodsInfoB);
					
					if (sxStoreLocation2.getLocationIndex() < sxStoreLocation3.getLocationIndex()) {
						sxStoreLocationGroupMapper.updateMapById(sxStoreLocationGroupId,
								MapUtils.put("entrance2Property1", taskPropertyB1)
										.put("entrance2Property2", taskPropertyB2)
										.put("entrance1Property1", taskPropertyA1)
										.put("entrance1Property2", taskPropertyA2).getMap(),
								SxStoreLocationGroup.class);
					} else {
						sxStoreLocationGroupMapper.updateMapById(sxStoreLocationGroupId,
								MapUtils.put("entrance2Property1", taskPropertyA1)
										.put("entrance2Property2", taskPropertyA2)
										.put("entrance1Property1", taskPropertyB1)
										.put("entrance1Property2", taskPropertyB2).getMap(),
								SxStoreLocationGroup.class);
					}
				} else {
					throw new Exception("移库数的货位有误，货位组ID为【" + sxStoreLocationGroupId + "】");
				}
				// 重新计算入库货位
				List<SxStoreLocation> sxStoreLocationsBig = sxStoreLocationMapper
						.findByMap(MapUtils.put("storeLocationGroupId", sxStoreLocationGroupId)
								.put("locationIndex", bigHaveStoreIndex).getMap(), SxStoreLocation.class);
				List<SxStoreLocation> sxStoreLocationsSmall = sxStoreLocationMapper
						.findByMap(MapUtils.put("storeLocationGroupId", sxStoreLocationGroupId)
								.put("locationIndex", smallHaveStoreIndex).getMap(), SxStoreLocation.class);
				if (sxStoreLocationsBig.size() == 1) {
					sxStoreLocationMapper.updateMapById(sxStoreLocationsBig.get(0).getId(),
							MapUtils.put("isInBoundLocation", 1).getMap(), SxStoreLocation.class);
				}
				if (sxStoreLocationsSmall.size() == 1) {
					sxStoreLocationMapper.updateMapById(sxStoreLocationsSmall.get(0).getId(),
							MapUtils.put("isInBoundLocation", 1).getMap(), SxStoreLocation.class);
				}
				sxStoreLocationMapper.updateNotIsInboundLocationTwo(sxStoreLocationGroupId,bigHaveStoreIndex, smallHaveStoreIndex);
			} else {
				// 奇数货位则最中间为入库货位
				int locationNum = sxStoreLocationGroup.getLocationNum();
				if (locationNum % 2 == 0) {
					int index1 = locationNum / 2;
					List<SxStoreLocation> sxStoreLocations2 = sxStoreLocationMapper.findByMap(MapUtils
							.put("storeLocationGroupId", sxStoreLocationGroupId).put("locationIndex", index1).getMap(),
							SxStoreLocation.class);
					sxStoreLocationMapper.updateMapById(sxStoreLocations2.get(0).getId(),
							MapUtils.put("deptNum", 0).put("isInBoundLocation", 1).getMap(), SxStoreLocation.class);
					int index2 = locationNum / 2 + 1;
					List<SxStoreLocation> sxStoreLocations3 = sxStoreLocationMapper.findByMap(MapUtils
							.put("storeLocationGroupId", sxStoreLocationGroupId).put("locationIndex", index2).getMap(),
							SxStoreLocation.class);
					sxStoreLocationMapper.updateMapById(sxStoreLocations3.get(0).getId(),
							MapUtils.put("deptNum", 0).put("isInBoundLocation", 1).getMap(), SxStoreLocation.class);
					sxStoreLocationMapper.updateNotIsInboundLocationTwo(sxStoreLocationGroupId, index1, index2);
				} else {
					int index = locationNum / 2 + 1;
					List<SxStoreLocation> sxStoreLocations2 = sxStoreLocationMapper.findByMap(MapUtils
							.put("storeLocationGroupId", sxStoreLocationGroupId).put("locationIndex", index).getMap(),
							SxStoreLocation.class);
					sxStoreLocationMapper.updateMapById(sxStoreLocations2.get(0).getId(),
							MapUtils.put("deptNum", 0).put("isInBoundLocation", 1).getMap(), SxStoreLocation.class);
					sxStoreLocationMapper.updateNotIsInboundLocation(sxStoreLocationGroupId,index);
				}

				sxStoreLocationGroupMapper.updateMapById(sxStoreLocationGroupId,
						MapUtils.put("entrance2Property1", null).put("entrance2Property2", null)
								.put("entrance1Property1", null).put("entrance1Property2", null).getMap(),
						SxStoreLocationGroup.class);
			}
		}
	}
	
	private String getContainerNo(String sourceContainerNo,String targetContainerNo) {
		if(StringUtils.isBlank(sourceContainerNo)) {
			return targetContainerNo;
		}else {
			return sourceContainerNo;
		}
	}
	
	private int findBigIndexCount(List<SxStoreLocationDto> sxStoreLocations, int index) {
		int bigCount = 0;
		for (SxStoreLocationDto sxStoreLocationDto : sxStoreLocations) {
			if(sxStoreLocationDto.getLocationIndex() > index) {
				bigCount++;
			}
		}
		
		return bigCount;
	}
	
	private int findSmallIndexCount(List<SxStoreLocationDto> sxStoreLocations, int index) {
		int smallCount = 0;
		for (SxStoreLocationDto sxStoreLocationDto : sxStoreLocations) {
			if(sxStoreLocationDto.getLocationIndex() < index) {
				smallCount++;
			}
		}
		
		return smallCount;
	}
	
	//public void 

	/**
	 * 根据区域查找货位组
	 * @param area
	 * @param originX
	 * @param originY
	 * @param weight
	 * @param taskProperty1
	 * @param taskProperty2
	 * @return
	 */
	private Integer findLocationGroup(int layer,String taskProperty1, String taskProperty2, Integer originX,
			Integer originY,String area, double weight) {

		Integer storeLocationGroupId = null;
		List<InStoreLocationGroupDto> findStoreLocationGroup = new ArrayList<>();
		List<InStoreLocationGroupDto> findStoreLocationGroup1 = sxStoreLocationGroupMapper.findStoreLocationGroupByArea(area, weight);

		//箱库只能同层换层  28层层以上点位为入库点位
		if (StoreArea.SAS01.equals(area) && layer <= 27){
			findStoreLocationGroup = findStoreLocationGroup1.stream().filter(x -> layer == x.getLayer()).collect(Collectors.toList());
		}else {
			findStoreLocationGroup = findStoreLocationGroup1;
		}
		if (findStoreLocationGroup1.size() == 0) {
			return storeLocationGroupId;
		}

		List<InStoreLocationGroupDto> inStoreLocationGroupDtos = findSamePropertyLocationGroup(findStoreLocationGroup
				, taskProperty1, taskProperty2);
		if (inStoreLocationGroupDtos.size() == 0) {
			List<InStoreLocationGroupDto> emptyInStoreLocationGroupDto = ListHelper.where(findStoreLocationGroup, p->p.getContainerCount() == 0);
			if(emptyInStoreLocationGroupDto.size() == 0) {
				// 没有相同属性 1.托盘数小于出口数的货位组 2.托盘数减去出口数小的
				storeLocationGroupId = findLocationGroupId(findStoreLocationGroup, originX, originY);
			}else {
				//找不同属性的货位组
				storeLocationGroupId = findLocationGroupId(emptyInStoreLocationGroupDto, originX, originY);	
			}
		}else if (inStoreLocationGroupDtos.size() > 1) {
			// 有两个相同属性的货位组则找 预留货位1.托盘数小于出口数的货位组 2.托盘数减去出口数小的
			storeLocationGroupId = findLocationGroupId(inStoreLocationGroupDtos, originX, originY);
		} else {
			// 只发现一个，直接锁定
			storeLocationGroupId = inStoreLocationGroupDtos.get(0).getStoreLocationGroupId();
		}

		return storeLocationGroupId;
	}

	/**
	 * 
	 * @param locationGroupId
	 * @param taskProperty1
	 * @param taskProperty2
	 * @param weight
	 * @return
	 * @throws Exception
	 */
	private SxStoreLocation findLocationId(Integer locationGroupId, String taskProperty1, String taskProperty2, double weight)
			throws Exception {
		SxStoreLocation result = null;
		SxStoreLocationGroup sxStoreLocationGroup = sxStoreLocationGroupMapper.findById(locationGroupId, SxStoreLocationGroup.class);
		/**
		 * 优先级最高的是满足重量 1.找到相同属性的 1.1 找到一个出入口直接入相邻货位 1.2 找到两个出入口则找出口近的一侧货位
		 * 
		 * 2.没有相同的属性，找出 2.1一个出入口直接入相邻货位 2.2找出两个出入口找出口近的一侧货位 2.2.1如果没有货位可用，则入另一侧
		 */

		Criteria criteria = Criteria.forClass(SxStoreLocation.class);
		criteria.setRestriction(Restrictions.and(
				Restrictions.eq("storeLocationGroupId",locationGroupId),
				Restrictions.eq("isInBoundLocation",1),
				Restrictions.ge("limitWeight",weight)));
		List<SxStoreLocation> sxStoreLocations1 = sxStoreLocationMapper.findByCriteria(criteria);

		//List<SxStoreLocation> sxStoreLocations1 = sxStoreLocationMapper.checkWeight(locationGroupId, weight);

		if (sxStoreLocations1.size() == 0) {
			throw new Exception("所选货位组没有找到入库货位,请检查货位组Id为【" + locationGroupId + "】");
		} else if (sxStoreLocations1.size() == 1) {
			result = sxStoreLocations1.get(0);
		} else if (sxStoreLocations1.size() == 2) {
			// 找离入口1属性相同的有托盘的货位
			List<SxStoreLocation> sxStoreLocationsProperty1 = sxStoreLocationMapper.findByProperty1(locationGroupId,
					taskProperty1, taskProperty2);
			// 找离入口2属性相同的有托盘的货位
			List<SxStoreLocation> sxStoreLocationsProperty2 = sxStoreLocationMapper.findByProperty2(locationGroupId,
					taskProperty1, taskProperty2);
			
			if (sxStoreLocationsProperty1.size() > 0 && sxStoreLocationsProperty2.size() > 0) {
				// 两个入口都是相同属性
				// 找出口近的一侧货位
				if (sxStoreLocationsProperty1.size() == 1 && sxStoreLocationsProperty2.size() == 1) {
					SxStoreLocation sxStoreLocationProperty1 = sxStoreLocationsProperty1.get(0);
					SxStoreLocation sxStoreLocationProperty2 = sxStoreLocationsProperty2.get(0);
					// 比较位置
					int locationNum = sxStoreLocationGroup.getLocationNum();
					result = chooseBestLocation(locationNum, sxStoreLocationProperty1,
							sxStoreLocationProperty2);
				} else {
					throw new Exception("单出口查询出有两个货位在最外侧！货位组ID为【" + locationGroupId + "】");
				}
			} else if (sxStoreLocationsProperty1.size() > 0 && sxStoreLocationsProperty2.size() == 0) {
				// 入口1属性相同
				// 找到一个出入口直接入相邻货位
				// 找出离出口最近的空货位
				if (sxStoreLocationsProperty1.size() == 1) {
					SxStoreLocation sxStoreLocation2 = sxStoreLocationsProperty1.get(0);
					result = findLocationAscentId(sxStoreLocation2,sxStoreLocationGroup);
				} else {
					throw new Exception("单出口查询出有两个货位在最外侧！货位组ID为【" + locationGroupId + "】");
				}
			} else if (sxStoreLocationsProperty1.size() == 0 && sxStoreLocationsProperty2.size() > 0) {
				// 入口2属性相同
				if (sxStoreLocationsProperty2.size() == 1) {
					SxStoreLocation sxStoreLocation2 = sxStoreLocationsProperty2.get(0);
					result = findLocationAscentId(sxStoreLocation2,sxStoreLocationGroup);
				} else {
					throw new Exception("单出口查询出有两个货位在最外侧！货位组ID为【" + locationGroupId + "】");
				}
			}else {
				// 没有属性相同的，混批次
				// 查询离入口最近的有容器的货位
				List<SxStoreLocation> sxStoreLocations = sxStoreLocationMapper
						.findStoreLocation(sxStoreLocationGroup.getId());
				if (sxStoreLocations.size() == 0) {
					// 该货位组上没有托盘
					result = findEmptyLocation(sxStoreLocationGroup);
				} else {
					if (sxStoreLocationGroup.getInOutNum() == 1) {
						if (sxStoreLocations.size() == 1) {
							// 单入口直接找有货的离入口最近的货位
							result = findLocationAscentId(sxStoreLocations.get(0),sxStoreLocationGroup);
						}
					} else if (sxStoreLocationGroup.getInOutNum() == 2) {
						// 双入口
						// 判断货位相邻货位是否为null
						if (sxStoreLocations.size() == 1) {
							// 找出离入口最近的货位
							result = findSingleLocation(sxStoreLocationGroup.getLocationNum(),
									sxStoreLocations.get(0));
						} else if (sxStoreLocations.size() == 2) {
							// 判断查出的货位是否为最左或者最右
							List<SxStoreLocation> sxStoreLocations2 = ListHelper.where(sxStoreLocations,
									p -> p.getLocationIndex() == 1);
							if (sxStoreLocations2.size() > 0) {
								// 最左侧已满则找另一侧
								SxStoreLocation sxLocation = new SxStoreLocation();
								for (SxStoreLocation sxStoreLocation2 : sxStoreLocations) {
									if (!sxStoreLocations2.get(0).getId().equals(sxStoreLocation2.getId())) {
										sxLocation = sxStoreLocation2;
									}
								}
								result = findLocationAscentId(sxLocation,sxStoreLocationGroup);
							} else {
								List<SxStoreLocation> sxStoreLocations3 = ListHelper.where(sxStoreLocations,
										p -> p.getLocationIndex().equals(sxStoreLocationGroup.getLocationNum()));
								if (sxStoreLocations3.size() > 0) {
									// 有则找另一侧
									SxStoreLocation sxLocation = new SxStoreLocation();
									for (SxStoreLocation sxStoreLocation2 : sxStoreLocations) {
										if (!sxStoreLocations3.get(0).getId().equals(sxStoreLocation2.getId())) {
											sxLocation = sxStoreLocation2;
										}
									}
									result = findLocationAscentId(sxLocation,sxStoreLocationGroup);
								} else {
									// 如果两个货位都不是最侧货位，找最合适的
									result = chooseBestLocation(sxStoreLocationGroup.getLocationNum(),
											sxStoreLocations.get(0), sxStoreLocations.get(1));
								}
							}
						}
					} else {
						throw new Exception("货位与容器数据存在异常！！！请检查！货位组ID【" + sxStoreLocationGroup.getId() + "】");
					}
				}
			}
		}else {
			throw new Exception("所选货位组查询到两个以上的入库货位,请检查货位组Id为【" + locationGroupId + "】");
		}
		
		return result;
	}

	private SxStoreLocation findSingleLocation(int locationNum, SxStoreLocation sxStoreLocation) throws Exception {
		
		SxStoreLocation result = null;
		//Integer storeLocationId = null;
		StoreLocationDistanceDto storeLocationDistance = new StoreLocationDistanceDto();
		StoreLocationDistanceDto storeLocationDistance1 = new StoreLocationDistanceDto();
		StoreLocationDistanceDto storeLocationDistance2 = new StoreLocationDistanceDto();
		storeLocationDistance1.setStoreLocationId(sxStoreLocation.getId());
		storeLocationDistance1.setDistance(sxStoreLocation.getLocationIndex() - 1);
		storeLocationDistance1.setFlag(1);
		storeLocationDistance2.setStoreLocationId(sxStoreLocation.getId());
		storeLocationDistance2.setDistance(locationNum - sxStoreLocation.getLocationIndex());
		storeLocationDistance2.setFlag(2);
		List<StoreLocationDistanceDto> distances = new ArrayList<StoreLocationDistanceDto>();
		distances.add(storeLocationDistance1);
		distances.add(storeLocationDistance2);
		for (StoreLocationDistanceDto storeLocationDistancesss : distances) {
			if (storeLocationDistance.getDistance() > storeLocationDistancesss.getDistance()) {
				storeLocationDistance = storeLocationDistancesss;
			}
		}
		if (storeLocationDistance.getFlag() == 1) {
			List<SxStoreLocation> sxStoreLocations = sxStoreLocationMapper.findByMap(
					MapUtils.put("locationIndex", sxStoreLocation.getLocationIndex() - 1)
					.put("storeLocationGroupId", sxStoreLocation.getStoreLocationGroupId()).getMap(),
					SxStoreLocation.class);
			result = sxStoreLocations.get(0);
		} else {
			List<SxStoreLocation> sxStoreLocations = sxStoreLocationMapper.findByMap(
					MapUtils.put("locationIndex", sxStoreLocation.getLocationIndex() + 1)
					.put("storeLocationGroupId", sxStoreLocation.getStoreLocationGroupId()).getMap(),
					SxStoreLocation.class);
			result = sxStoreLocations.get(0);
		}

		return result;
	}
	
	private SxStoreLocation findEmptyLocation(SxStoreLocationGroup sxStoreLocationGroup) throws Exception {
		
		SxStoreLocation result = null;
		// 空货位组找出离出口最远的货位
		//Integer storeLocationId = null;
		// 单出口时，直接入最里面的
		if (sxStoreLocationGroup.getEntrance() == 1) {
			// 只有出口1，则入index为最大的
			int locationNum = sxStoreLocationGroup.getLocationNum();
			List<SxStoreLocation> sxStoreLocations = sxStoreLocationMapper
					.findByMap(
							MapUtils.put("locationIndex", locationNum)
							.put("storeLocationGroupId", sxStoreLocationGroup.getId()).getMap(),
							SxStoreLocation.class);
			result = sxStoreLocations.get(0);
		} else if (sxStoreLocationGroup.getEntrance() == 2) {
			// 只有出口2，则入index为1的
			List<SxStoreLocation> sxStoreLocations = sxStoreLocationMapper.findByMap(
					MapUtils.put("locationIndex", 1).put("storeLocationGroupId", sxStoreLocationGroup.getId()).getMap(),
					SxStoreLocation.class);
			result = sxStoreLocations.get(0);
		} else if (sxStoreLocationGroup.getEntrance() == 3) {
			int locationNum = sxStoreLocationGroup.getLocationNum();
			double ceil = Math.ceil(locationNum / 2);
			int index = new Double(ceil).intValue() + 1;
			List<SxStoreLocation> sxStoreLocations = sxStoreLocationMapper
					.findByMap(
							MapUtils.put("locationIndex", index)
							.put("storeLocationGroupId", sxStoreLocationGroup.getId()).getMap(),
							SxStoreLocation.class);
			result = sxStoreLocations.get(0);
		}
		return result;
	}

	// 查询相同属性的货位组
	private List<InStoreLocationGroupDto> findSamePropertyLocationGroup(List<InStoreLocationGroupDto> findSameTypeLocationGroup, String taskProperty1, String taskProperty2) {
		List<InStoreLocationGroupDto> inStoreLocationGroupDtosUsed = new ArrayList<InStoreLocationGroupDto>();
		for (InStoreLocationGroupDto inStoreLocationGroupDto : findSameTypeLocationGroup) {

			inStoreLocationGroupDto.setEntrance1Property1(inStoreLocationGroupDto.getEntrance1Property1() == null ? ""
					: inStoreLocationGroupDto.getEntrance1Property1());
			inStoreLocationGroupDto.setEntrance1Property2(inStoreLocationGroupDto.getEntrance1Property2() == null ? ""
					: inStoreLocationGroupDto.getEntrance1Property2());
			inStoreLocationGroupDto.setEntrance2Property1(inStoreLocationGroupDto.getEntrance2Property1() == null ? ""
					: inStoreLocationGroupDto.getEntrance2Property1());
			inStoreLocationGroupDto.setEntrance2Property2(inStoreLocationGroupDto.getEntrance2Property2() == null ? ""
					: inStoreLocationGroupDto.getEntrance2Property2());
			taskProperty1 = taskProperty1 == null ? "" : taskProperty1;
			taskProperty2 = taskProperty2 == null ? "" : taskProperty2;
			if ((inStoreLocationGroupDto.getEntrance1Property1().equals(taskProperty1)
					&& inStoreLocationGroupDto.getEntrance1Property2().equals(taskProperty2))
					|| (inStoreLocationGroupDto.getEntrance2Property1().equals(taskProperty1)
							&& inStoreLocationGroupDto.getEntrance2Property2().equals(taskProperty2))) {
				inStoreLocationGroupDtosUsed.add(inStoreLocationGroupDto);
			}
		}
		return inStoreLocationGroupDtosUsed;
	}

	// 根据 0.预留货位组1.托盘数小于出口数的货位组 2.托盘数减去出口数小的 3.离目标提升机最近的
	private Integer findLocationGroupId(List<InStoreLocationGroupDto> inStoreLocationGroupDtos, Integer originX,
                                        Integer originY) {
		Integer storeLocationGroupId = null;
		// 算出预留货位

		//			List<InStoreLocationGroupDto> newInStoreLocationGroupDtos = new ArrayList<InStoreLocationGroupDto>();
		for (InStoreLocationGroupDto inStoreLocationGroupDto : inStoreLocationGroupDtos) {
			Integer targetX = inStoreLocationGroupDto.getX();
			Integer targetY = inStoreLocationGroupDto.getY();
			Integer distance = Math.abs(targetX - originX) + Math.abs(targetY - originY);
			int subtract = inStoreLocationGroupDto.getContainerCount() - inStoreLocationGroupDto.getInOutNum();
			inStoreLocationGroupDto.setDistance(distance);
			inStoreLocationGroupDto.setSubtract(subtract);
		}
		storeLocationGroupId = bubblingSort(inStoreLocationGroupDtos);

		return storeLocationGroupId;
	}

	private Integer bubblingSort(List<InStoreLocationGroupDto> inStoreLocationGroupDtos) {
		Integer storeLocationGroupId = null;
		// 冒泡排序筛选出离提升机最近的

		InStoreLocationGroupDto inStoreLocationGroupDto2 = inStoreLocationGroupDtos.get(0);
		// reservedLocation 1.空托盘预留货位、2.理货预留货位、3.不用预留货位
		if (inStoreLocationGroupDtos.size() > 1) {
			for (int i = 1; i < inStoreLocationGroupDtos.size(); i++) {
				InStoreLocationGroupDto inStoreLocationGroupDto = inStoreLocationGroupDtos.get(i);
				inStoreLocationGroupDto2 = chooseBestLocationGroup(inStoreLocationGroupDto2, inStoreLocationGroupDto);
			}
		}
		storeLocationGroupId = inStoreLocationGroupDto2.getStoreLocationGroupId();
		return storeLocationGroupId;
	}

	private InStoreLocationGroupDto chooseBestLocationGroup(InStoreLocationGroupDto inStoreLocationGroupDto2,
                                                            InStoreLocationGroupDto inStoreLocationGroupDto) {
		if (inStoreLocationGroupDto2.getSubtract() > inStoreLocationGroupDto.getSubtract()) {
			inStoreLocationGroupDto2 = inStoreLocationGroupDto;
		} else if (inStoreLocationGroupDto2.getSubtract() == inStoreLocationGroupDto.getSubtract()) {
			if (inStoreLocationGroupDto2.getDistance() > inStoreLocationGroupDto.getDistance()) {
				inStoreLocationGroupDto2 = inStoreLocationGroupDto;
			}
		}
		return inStoreLocationGroupDto2;
	}
	
	private SxStoreLocation chooseBestLocation(int locationNum, SxStoreLocation sxStoreLocationProperty1,
                                               SxStoreLocation sxStoreLocationProperty2) throws Exception {
		SxStoreLocation result = null;
		StoreLocationDistanceDto storeLocationDistance = new StoreLocationDistanceDto();
		StoreLocationDistanceDto storeLocationDistance1 = new StoreLocationDistanceDto();
		StoreLocationDistanceDto storeLocationDistance2 = new StoreLocationDistanceDto();
		StoreLocationDistanceDto storeLocationDistance3 = new StoreLocationDistanceDto();
		StoreLocationDistanceDto storeLocationDistance4 = new StoreLocationDistanceDto();
		storeLocationDistance1.setStoreLocationId(sxStoreLocationProperty1.getId());
		storeLocationDistance1.setDistance(sxStoreLocationProperty1.getLocationIndex() - 1);
		storeLocationDistance1.setFlag(1);
		storeLocationDistance2.setStoreLocationId(sxStoreLocationProperty1.getId());
		storeLocationDistance2.setDistance(locationNum - sxStoreLocationProperty1.getLocationIndex());
		storeLocationDistance2.setFlag(2);
		storeLocationDistance3.setStoreLocationId(sxStoreLocationProperty2.getId());
		storeLocationDistance3.setDistance(sxStoreLocationProperty2.getLocationIndex() - 1);
		storeLocationDistance3.setFlag(1);
		storeLocationDistance4.setStoreLocationId(sxStoreLocationProperty2.getId());
		storeLocationDistance4.setDistance(locationNum - sxStoreLocationProperty2.getLocationIndex());
		storeLocationDistance4.setFlag(2);
		List<StoreLocationDistanceDto> distances = new ArrayList<StoreLocationDistanceDto>();
		distances.add(storeLocationDistance1);
		distances.add(storeLocationDistance2);
		distances.add(storeLocationDistance3);
		distances.add(storeLocationDistance4);
		for (StoreLocationDistanceDto storeLocationDistancesss : distances) {
			if (storeLocationDistance.getDistance() > storeLocationDistancesss.getDistance()) {
				storeLocationDistance = storeLocationDistancesss;
			}
		}
		if (storeLocationDistance.getStoreLocationId() == sxStoreLocationProperty1.getId()) {
			if (storeLocationDistance.getFlag() == 1) {
				List<SxStoreLocation> sxStoreLocations = sxStoreLocationMapper.findByMap(MapUtils
						.put("locationIndex", sxStoreLocationProperty1.getLocationIndex() - 1)
						.put("storeLocationGroupId", sxStoreLocationProperty1.getStoreLocationGroupId()).getMap(),
						SxStoreLocation.class);
				result = sxStoreLocations.get(0);
			} else {
				List<SxStoreLocation> sxStoreLocations = sxStoreLocationMapper.findByMap(MapUtils
						.put("locationIndex", sxStoreLocationProperty1.getLocationIndex() + 1)
						.put("storeLocationGroupId", sxStoreLocationProperty1.getStoreLocationGroupId()).getMap(),
						SxStoreLocation.class);
				result = sxStoreLocations.get(0);
			}
		} else {
			if (storeLocationDistance.getFlag() == 1) {
				List<SxStoreLocation> sxStoreLocations = sxStoreLocationMapper.findByMap(MapUtils
						.put("locationIndex", sxStoreLocationProperty2.getLocationIndex() - 1)
						.put("storeLocationGroupId", sxStoreLocationProperty1.getStoreLocationGroupId()).getMap(),
						SxStoreLocation.class);
				result = sxStoreLocations.get(0);
			} else {
				List<SxStoreLocation> sxStoreLocations = sxStoreLocationMapper.findByMap(MapUtils
						.put("locationIndex", sxStoreLocationProperty2.getLocationIndex() + 1)
						.put("storeLocationGroupId", sxStoreLocationProperty1.getStoreLocationGroupId()).getMap(),
						SxStoreLocation.class);
				result = sxStoreLocations.get(0);
			}
		}
		return result;
	}
	
	private SxStoreLocation findLocationAscentId(SxStoreLocation sxLocation, SxStoreLocationGroup sxStoreLocationGroup) throws Exception {
		SxStoreLocation result = null;
		Integer sxStoreLocationId = null;
		
		Integer storeLocationId1 = sxLocation.getStoreLocationId1();
		Integer storeLocationId2 = sxLocation.getStoreLocationId2();
		if (storeLocationId1 == null) {
			sxStoreLocationId = storeLocationId2;
		} else if (storeLocationId2 == null) {
			sxStoreLocationId = storeLocationId1;
		} else {
			List<ContainerPathTask> sxStores = containerPathTaskMapper.findByMap(MapUtils.put("sourceLocation", sxLocation.getStoreNo())
					.put("sourceArea", sxStoreLocationGroup.getBelongArea()).getMap(), ContainerPathTask.class);
			// 查询一侧的相邻货位是否有实物，无则入，有则从另一个货位入
			if (sxStores.size() > 0) {
				sxStoreLocationId = sxLocation.getStoreLocationId2();
			} else {
				sxStoreLocationId = storeLocationId1;
			}
		}
		result = sxStoreLocationMapper.findById(sxStoreLocationId, SxStoreLocation.class);
		
		return result;
	}
}
