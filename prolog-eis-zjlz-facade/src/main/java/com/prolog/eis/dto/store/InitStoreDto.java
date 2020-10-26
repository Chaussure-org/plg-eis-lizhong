package com.prolog.eis.dto.store;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-10-12 13:37
 */
public class InitStoreDto {

    @ApiModelProperty("层 实际层+1")
    private Integer layerCount;

    @ApiModelProperty("起始巷道  0开始")
    private Integer xStart;

    @ApiModelProperty("实际巷道")
    private Integer xCount;

    @ApiModelProperty("列数  实际列数+1")
    private Integer yCount;

    @ApiModelProperty("深位")
    private Integer ascent;

    @ApiModelProperty("排除列  没有则为空")
    private List<Integer> exList;

    @ApiModelProperty("区域")
    private String areaNo;

    public Integer getLayerCount() {
        return layerCount;
    }

    public void setLayerCount(Integer layerCount) {
        this.layerCount = layerCount;
    }

    public Integer getxStart() {
        return xStart;
    }

    public void setxStart(Integer xStart) {
        this.xStart = xStart;
    }

    public Integer getxCount() {
        return xCount;
    }

    public void setxCount(Integer xCount) {
        this.xCount = xCount;
    }

    public Integer getyCount() {
        return yCount;
    }

    public void setyCount(Integer yCount) {
        this.yCount = yCount;
    }

    public Integer getAscent() {
        return ascent;
    }

    public void setAscent(Integer ascent) {
        this.ascent = ascent;
    }

    public List<Integer> getExList() {
        return exList;
    }

    public void setExList(List<Integer> exList) {
        this.exList = exList;
    }

    public String getAreaNo() {
        return areaNo;
    }

    public void setAreaNo(String areaNo) {
        this.areaNo = areaNo;
    }

    @Override
    public String toString() {
        return "InitStoreDto{" +
                "layerCount=" + layerCount +
                ", xStart=" + xStart +
                ", xCount=" + xCount +
                ", yCount=" + yCount +
                ", ascent=" + ascent +
                ", exList=" + exList +
                ", areaNo='" + areaNo + '\'' +
                '}';
    }
}
