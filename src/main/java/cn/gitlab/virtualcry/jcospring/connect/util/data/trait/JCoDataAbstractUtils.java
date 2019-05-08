package cn.gitlab.virtualcry.jcospring.connect.util.data.trait;

import cn.gitlab.virtualcry.jcospring.connect.util.data.vo.Parameter;
import cn.gitlab.virtualcry.jcospring.connect.util.data.vo.ParameterFlatTree;
import cn.gitlab.virtualcry.jcospring.connect.util.data.vo.ParameterFlatTreeNode;
import cn.gitlab.virtualcry.jcospring.connect.util.data.vo.ParameterNestTree;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.sap.conn.jco.JCoField;
import com.sap.conn.jco.JCoMetaData;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JCo data operation utils:
 *
 * @author VirtualCry
 */
public abstract class JCoDataAbstractUtils {

    /**
     * Get JCoField value
     * @param field field
     */
    public static Object getJCoFieldValue(JCoField field) {
        if (field.getType() == JCoMetaData.TYPE_TABLE ) {
            List<Map<String, Object>> tableValue = new ArrayList<>();
            JCoTable table = field.getTable();
            for (int i = 0; i < table.getNumRows(); i++) {
                Map<String, Object> rowValue = new HashMap<>();
                table.setRow(i);
                table.forEach(tableField -> rowValue.put(tableField.getName(), getJCoFieldValue(tableField)));
                tableValue.add(rowValue);
            }
            return tableValue;
        }
        else if (field.getType() == JCoMetaData.TYPE_STRUCTURE ) {
            Map<String, Object> structureValue = new HashMap<>();
            JCoStructure structure = field.getStructure();
            structure.forEach(structureField -> structureValue.put(structureField.getName(), getJCoFieldValue(structureField)));
            return structureValue;
        }
        else {
            return field.getValue();
        }
    }

    /* ============================================================================================================= */

    /**
     * Set JCoField value
     * @param field field
     * @param parameter parameter
     */
    public static void setJCoFieldValue(JCoField field, Map<String, Object> parameter) {
        if (parameter == null)
            return;
        Object currentParameter = parameter.get(field.getName());
        if (currentParameter == null)
            return;
        if (field.getType() == JCoMetaData.TYPE_TABLE ) {
            @SuppressWarnings("unchecked") List<Map<String, Object>> currentParameterList =
                    (List<Map<String, Object>>) currentParameter;
            JCoTable table = field.getTable();
            for (int i = 0; i < currentParameterList.size(); i++) {
                final Map<String, Object> currentParameterRow = currentParameterList.get(i);
                table.appendRow();
                table.forEach(_tableField -> setJCoFieldValue(_tableField, currentParameterRow));
            }
        }
        else if (field.getType() == JCoMetaData.TYPE_STRUCTURE) {
            @SuppressWarnings("unchecked") Map<String, Object> currentParameterRow =
                    (Map<String, Object>) currentParameter;
            JCoStructure structure = field.getStructure();
            structure.forEach(_structureField -> setJCoFieldValue(_structureField, currentParameterRow));
        }
        else {
            field.setValue(currentParameter);
        }
    }

    /* ============================================================================================================= */

    /**
     * Analysis JCoField
     * @param field field
     */
    public static Object analysisJCoFieldProperties(JCoField field){
        Multimap<Parameter, Object> result = ArrayListMultimap.create();
        if (JCoMetaData.TYPE_TABLE == field.getType()) {
            field.getTable().forEach(tableField -> result.put(parse(field), analysisJCoFieldProperties(tableField)));
        }
        else if (JCoMetaData.TYPE_STRUCTURE == field.getType()) {
            field.getStructure().forEach(structureField -> result.put(parse(field), analysisJCoFieldProperties(structureField)));
        }
        else {
            return parse(field);
        }
        return result;
    }

    /* ============================================================================================================= */

    /**
     * Analysis JCoField for NestTree
     * @param field field
     */
    public static ParameterNestTree analysisJCoFieldPropertiesForNestTree(JCoField field) {
        ParameterNestTree nestTree = parseToNestTree(field);
        if (JCoMetaData.TYPE_TABLE == field.getType()) {
            nestTree.setChildren(new ArrayList<>());
            field.getTable().forEach(tableField -> nestTree.getChildren().add(analysisJCoFieldPropertiesForNestTree(tableField)));
        }
        else if (JCoMetaData.TYPE_STRUCTURE == field.getType()) {
            nestTree.setChildren(new ArrayList<>());
            field.getStructure().forEach(structureField -> nestTree.getChildren().add(analysisJCoFieldPropertiesForNestTree(structureField)));
        }
        return nestTree;
    }

    /* ============================================================================================================= */

    /**
     * Analysis JCoField for FlatTree
     * @param jCoField jCoField
     */
    public static ParameterFlatTree analysisJCoFieldPropertiesForFlatTree(JCoField jCoField) {
        ParameterFlatTree flatTree = new ParameterFlatTree();

        Object field = analysisJCoFieldProperties(jCoField);
        if (field instanceof Parameter) {
            flatTree.add(ParameterFlatTreeNode.builder()
                    .level(0)
                    .nodeName(((Parameter) field).getName())
                    .nodeInfo((Parameter) field)
                    .parentNodeName(((Parameter) field).getName())
                    .build());
        }
        else if (field instanceof Multimap){
            @SuppressWarnings("unchecked") Multimap<Parameter, Object> currentField =
                    (Multimap<Parameter, Object>) field;
            currentField.keySet().forEach(key -> {
                ParameterFlatTreeNode currentNode = ParameterFlatTreeNode.builder()
                        .level(0)
                        .nodeName(key.getName())
                        .nodeInfo(key)
                        .parentNodeName(key.getName())
                        .build();
                flatTree.add(currentNode);
                currentField.get(key).forEach(value -> analysisJCoFieldPropertiesForFlatTree(flatTree, currentNode, value));
            });
        }

        return flatTree;
    }

    private static void analysisJCoFieldPropertiesForFlatTree(ParameterFlatTree fieldTree, ParameterFlatTreeNode parentNode, Object field) {
        if (field instanceof Parameter) {
            ParameterFlatTreeNode currentNode = ParameterFlatTreeNode.builder()
                    .level(parentNode.getLevel() + 1)
                    .nodeName(((Parameter) field).getName())
                    .nodeInfo((Parameter) field)
                    .parentNodeName(parentNode.getNodeName())
                    .build();
            fieldTree.add(currentNode);
        }
        else if (field instanceof Multimap) {
            @SuppressWarnings("unchecked") Multimap<Parameter, Object> currentField =
                    (Multimap<Parameter, Object>) field;
            currentField.keySet().forEach(key -> {
                ParameterFlatTreeNode currentNode = ParameterFlatTreeNode.builder()
                        .level(parentNode.getLevel() + 1)
                        .nodeName(key.getName())
                        .nodeInfo(key)
                        .parentNodeName(parentNode.getNodeName())
                        .build();
                fieldTree.add(currentNode);
                currentField.get(key).forEach(value -> analysisJCoFieldPropertiesForFlatTree(fieldTree, currentNode, value));
            });
        }
    }

    /* ============================================================================================================= */

    /**
     * JCoField → Parameter
     * @param field field
     */
    private static Parameter parse(JCoField field){
        return Parameter.builder()
                .name(field.getName())                             /* 变量 - 名称 */
                .description(field.getDescription())               /* 变量 - 描述 */
                .type(field.getTypeAsString())                     /* 变量 - 类型 */
                .length(field.getLength())                         /* 变量 - 长度 */
                .byteLength(field.getByteLength())                 /* 变量 - Byte长度 */
                .unicodeByteLength(field.getUnicodeByteLength())   /* 变量 - UnicodeByte长度 */
                .decimals(field.getDecimals())                     /* 变量 - 小数 */
                .build();
    }

    /**
     * JCoField → Parameter TableTree.
     * @param field field
     */
    private static ParameterNestTree parseToNestTree(JCoField field){
        ParameterNestTree nestTree = new ParameterNestTree();
        nestTree.setName(field.getName());                             /* 变量 - 名称 */
        nestTree.setDescription(field.getDescription());               /* 变量 - 描述 */
        nestTree.setType(field.getTypeAsString());                     /* 变量 - 类型 */
        nestTree.setLength(field.getLength());                         /* 变量 - 长度 */
        nestTree.setByteLength(field.getByteLength());                 /* 变量 - Byte长度 */
        nestTree.setUnicodeByteLength(field.getUnicodeByteLength());   /* 变量 - UnicodeByte长度 */
        nestTree.setDecimals(field.getDecimals());                     /* 变量 - 小数 */
        return nestTree;
    }
}
