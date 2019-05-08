package cn.gitlab.virtualcry.jcospring.connect.util.data.vo;

import cn.gitlab.virtualcry.jcospring.connect.util.data.vo.trait.ParameterTree;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * ParameterNestTree
 *
 * @author VirtualCry
 */
@Getter
@Setter
@NoArgsConstructor
public class ParameterNestTree extends Parameter implements ParameterTree {

    private List<ParameterNestTree> children;
}
