package com.prolog.eis.dto.location;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: wuxl
 * @create: 2020-08-25 15:18
 * @Version: V1.0
 */
@Data
public class PathDTO implements Serializable {
    private static final long serialVersionUID = -3564183485278560848L;

    /**
     * 路线总长度
     */
    private int pathStepSum;

    /**
     * 路线
     */
    private String pathString;

    /**
     * 对应的点下表
     */
    private String vertexId;

    /**
     * 下一个边节点
     */
    private PathDTO next;

    /**
     * 容器是否达到最大限制
     */
    private boolean maxCount;

    /**
     * 区域相关信息集合
     */
    private List<StoreAreaDirectionDTO> storeAreaDirectionDTOList;
}
