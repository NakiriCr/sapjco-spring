package cn.gitlab.virtualcry.jcospring.connect.util.data;

import cn.gitlab.virtualcry.jcospring.connect.util.data.trait.JCoDataAbstractUtils;
import cn.gitlab.virtualcry.jcospring.connect.util.data.vo.ParameterFlatTree;
import cn.gitlab.virtualcry.jcospring.connect.util.data.vo.ParameterNestTree;
import com.sap.conn.jco.JCoField;
import com.sap.conn.jco.JCoFieldIterator;
import com.sap.conn.jco.JCoParameterList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JCo data operation utils:
 *
 * @author VirtualCry
 */
public class JCoDataUtils extends JCoDataAbstractUtils {

    /**
     * Get JCoParameterList value.
     * @param parameterList parameterList
     */
    public static Map<String, Object> getJCoParameterListValue(JCoParameterList parameterList) {
        if (parameterList == null) {
            return new HashMap<>();
        }
        else {
            Map<String, Object> parameterListValue = new HashMap<>();
            JCoFieldIterator fieldIterator = parameterList.getFieldIterator();
            while (fieldIterator.hasNextField()) {
                JCoField field = fieldIterator.nextField();
                parameterListValue.put(field.getName(), getJCoFieldValue(field));
            }
            return parameterListValue;
        }
    }

    /* ============================================================================================================= */

    /**
     * Set JCoParameterList value.
     * @param parameterList parameterList
     * @param parameter parameter
     */
    public static void setJCoParameterListValue(JCoParameterList parameterList, Map<String, Object> parameter) {
        if (parameterList != null) {
            JCoFieldIterator fieldIterator = parameterList.getFieldIterator();
            while (fieldIterator.hasNextField()) {
                setJCoFieldValue(fieldIterator.nextField(), parameter);
            }
        }
    }

    /* ============================================================================================================= */

    /**
     * Analysis JCoField.
     * @param parameterList parameterList
     */
    public static List analysisJCoParameterList(JCoParameterList parameterList) {
        List<Object> parameters = new ArrayList<>();
        if (parameterList != null) {
            JCoFieldIterator fieldIterator = parameterList.getFieldIterator();
            while (fieldIterator.hasNextField()) {
                parameters.add(
                        analysisJCoFieldProperties(fieldIterator.nextField())
                );
            }
        }
        return parameters;
    }

    /**
     * Analysis JCoField for NestTree.
     * @param parameterList parameterList
     */
    public static List<ParameterNestTree> analysisJCoParameterListForNestTree(JCoParameterList parameterList) {
        List<ParameterNestTree> parameters = new ArrayList<>();
        if (parameterList != null) {
            JCoFieldIterator fieldIterator = parameterList.getFieldIterator();
            while (fieldIterator.hasNextField()) {
                parameters.add(
                        analysisJCoFieldPropertiesForNestTree(fieldIterator.nextField())
                );
            }
        }
        return parameters;
    }

    /**
     * Analysis JCoField for FlatTree.
     * @param parameterList parameterList
     */
    public static List<ParameterFlatTree> analysisJCoParameterListForFlatTree(JCoParameterList parameterList) {
        List<ParameterFlatTree> parameters = new ArrayList<>();
        if (parameterList != null) {
            JCoFieldIterator fieldIterator = parameterList.getFieldIterator();
            while (fieldIterator.hasNextField()) {
                parameters.add(
                        analysisJCoFieldPropertiesForFlatTree(fieldIterator.nextField())
                );
            }
        }
        return parameters;
    }
}
