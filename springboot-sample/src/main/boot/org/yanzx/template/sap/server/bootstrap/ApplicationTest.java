package org.yanzx.template.sap.server.bootstrap;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multimap;
import com.sap.conn.jco.*;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.yanzx.core.extend.sap.jco.beans.JCoServerConfig;
import org.yanzx.core.extend.sap.jco.client.proxy.JCoClientProxy;
import org.yanzx.core.extend.sap.jco.client.proxy.trait.JCoClient;
import org.yanzx.core.extend.sap.jco.server.proxy.JCoServerProxy;
import org.yanzx.core.extend.sap.jco.server.proxy.trait.JCoServer;
import org.yanzx.core.spring.util.SpringUtils;
import org.yanzx.template.sap.server.bootstrap.config.JCoConfiguration;
import org.yanzx.template.sap.server.bootstrap.vo.Parameter;
import org.yanzx.template.sap.server.bootstrap.vo.SapFuncType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @author VirtualCry
 * @date 2019/3/7 12:26
 */
//@Component
public class ApplicationTest implements ApplicationRunner {

    private static Object analysisJCoFieldProperties(JCoField _field){
        Multimap<Parameter, Object> _result = ArrayListMultimap.create();
        if (JCoMetaData.TYPE_TABLE == _field.getType()) {
            for (JCoField _jCoField : _field.getTable()) {
                _result.put(parse(_field), analysisJCoFieldProperties(_jCoField));
            }
        }
        else if (JCoMetaData.TYPE_STRUCTURE == _field.getType()) {
            for (JCoField _jCoField : _field.getStructure()) {
                _result.put(parse(_field), analysisJCoFieldProperties(_jCoField));
            }
        }
        else {
            return parse(_field);
        }
        return _result;
    }

    private static Object analysisJCoFieldType(JCoField _field){
        Multimap<String, Object> _result = ArrayListMultimap.create();
        if (JCoMetaData.TYPE_TABLE == _field.getType()) {
            for (JCoField _jCoField : _field.getTable()) {
                _result.put(_field.getName() + "-" + _field.getTypeAsString(), analysisJCoFieldType(_jCoField));
            }
        }
        else if (JCoMetaData.TYPE_STRUCTURE == _field.getType()) {
            for (JCoField _jCoField : _field.getStructure()) {
                _result.put(_field.getName() + "-" + _field.getTypeAsString(), analysisJCoFieldType(_jCoField));
            }
        }
        else {
            return parse(_field);
        }
        return _result;
    }

    private static Parameter parse(JCoField _field){
        return Parameter.builder()
                .name(_field.getName())                             /* 变量 - 名称 */
                .description(_field.getDescription())               /* 变量 - 描述 */
                .type(_field.getTypeAsString())                     /* 变量 - 类型 */
                .length(_field.getLength())                         /* 变量 - 长度 */
                .byteLength(_field.getByteLength())                 /* 变量 - Byte长度 */
                .unicodeByteLength(_field.getUnicodeByteLength())   /* 变量 - UnicodeByte长度 */
                .decimals(_field.getDecimals())                     /* 变量 - 小数 */
                .build();
    }

    /**
     * JCoParameterList → List<Parameter>
     * @param _parameterList _parameterList
     */
    public static List<Object> analysisJCoFieldProperties(JCoParameterList _parameterList) {
        List<Object> _parameters = new ArrayList<>();
        if (_parameterList != null) {
            JCoFieldIterator _fieldIterator = _parameterList.getFieldIterator();
            while (_fieldIterator.hasNextField()) {
                _parameters.add(
                        analysisJCoFieldProperties(_fieldIterator.nextField())
                );
            }
        }
        return _parameters;
    }

    /**
     * JCoParameterList → List<Parameter>
     * @param _parameterList _parameterList
     */
    public static List<Object> analysisJCoFieldType(JCoParameterList _parameterList) {
        List<Object> _parameters = new ArrayList<>();
        if (_parameterList != null) {
            JCoFieldIterator _fieldIterator = _parameterList.getFieldIterator();
            while (_fieldIterator.hasNextField()) {
                _parameters.add(
                        analysisJCoFieldType(_fieldIterator.nextField())
                );
            }
        }
        return _parameters;
    }

    /**
     * Analysis JCoField for Json. (key is transferred to str.)
     * @param _field _field
     */
    public static Object analysisJCoFieldPropertiesForJson(JCoField _field){
        Multimap<String, Object> _result = ArrayListMultimap.create();
        if (JCoMetaData.TYPE_TABLE == _field.getType()) {
            for (JCoField _jCoField : _field.getTable()) {
                _result.put(JSON.toJSONString(parse(_field)), analysisJCoFieldPropertiesForJson(_jCoField));
            }
        }
        else if (JCoMetaData.TYPE_STRUCTURE == _field.getType()) {
            for (JCoField _jCoField : _field.getStructure()) {
                _result.put(JSON.toJSONString(parse(_field)), analysisJCoFieldPropertiesForJson(_jCoField));
            }
        }
        else {
            return JSON.toJSONString(parse(_field));
        }
        return _result;
    }

    /**
     * Analysis JCoField for Json. (key is transferred to str.)
     * @param _parameterList _parameterList
     */
    public static List<Object> analysisJCoFieldPropertiesForJson(JCoParameterList _parameterList) {
        List<Object> _parameters = new ArrayList<>();
        if (_parameterList != null) {
            JCoFieldIterator _fieldIterator = _parameterList.getFieldIterator();
            while (_fieldIterator.hasNextField()) {
                _parameters.add(
                        analysisJCoFieldPropertiesForJson(_fieldIterator.nextField())
                );
            }
        }
        return _parameters;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        JCoServerConfig  _config = SpringUtils.getBean(JCoConfiguration.SpringJCoServerConfig.class);
        /* Start JCoServer. */
        JCoServer _server = new JCoServerProxy(_config);
        _server.initAndStart();
        _server.stop();
        /* Start JCoClient. */
        JCoServerConfig _config2 = new JCoServerConfig();
        BeanUtils.copyProperties(_config, _config2);
        _config2.setDestinationName(_config2.getDestinationName() + "1");
        JCoClient _client = new JCoClientProxy(_config);
        _client.init();

        _client.destroy();

        String _funcName = "ZMM_TOSRM_PO2WAN_RFC_INTER";
        _client.init();
        JCoFunction _func = _client.getRepository().getFunction(_funcName);

        Map<SapFuncType, List<Object>> _funcInfo = ImmutableMap.<SapFuncType, List<Object>>builder()
                .put(SapFuncType.IMPORT,    analysisJCoFieldPropertiesForJson(_func.getImportParameterList()))
                .put(SapFuncType.EXPORT,    analysisJCoFieldPropertiesForJson(_func.getExportParameterList()))
                .put(SapFuncType.TABLE,     analysisJCoFieldPropertiesForJson(_func.getTableParameterList()))
                .put(SapFuncType.CHANGING,  analysisJCoFieldPropertiesForJson(_func.getChangingParameterList()))
                .build();

        System.out.println(JSON.toJSONString(_funcInfo));
    }
}
