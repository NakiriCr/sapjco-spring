package cn.gitlab.virtualcry.sapjco.util.data.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * ParameterFlatTreeNode
 */
@Data
@Builder
public class ParameterFlatTreeNode implements Serializable {

    private int                             level;
    private String                          nodeName;
    private String                          parentNodeName;
    private Parameter                       nodeInfo;
}
