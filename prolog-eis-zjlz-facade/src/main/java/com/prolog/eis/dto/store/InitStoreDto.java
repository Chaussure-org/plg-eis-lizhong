package com.prolog.eis.dto.store;

import java.util.List;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-10-12 13:37
 */
public class InitStoreDto {

    private Integer layerCount;

    private Integer xStart;

    private Integer xCount;

    private Integer yCount;

    private Integer ascent;

    private List<Integer> exList;

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
