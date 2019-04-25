package org.yanzx.template.sap.server.bootstrap.vo;

import com.sap.conn.jco.AbapException;
import lombok.Getter;
import lombok.Setter;

/**
 * Description:
 *
 * @author VirtualCry
 * @date 2019/3/7 13:27
 */
@Setter @Getter
public class JCoFuncInterface {

    private String functionName;

    private Parameter importParameter;

    private Parameter tableParameter;

    private Parameter changingParameter;

    private Parameter exportParameter;;

    private AbapException[] abapExceptions;
}
