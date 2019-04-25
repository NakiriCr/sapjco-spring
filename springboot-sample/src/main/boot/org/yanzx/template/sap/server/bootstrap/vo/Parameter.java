package org.yanzx.template.sap.server.bootstrap.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * Description:
 *
 * @author VirtualCry
 * @date 2019/3/7 13:28
 */
@Getter @Setter
@Builder
public class Parameter {

    private String name;

    private String description;

    private String type;

    private int length;

    private int byteLength;

    private int unicodeByteLength;

    private int decimals;

    @Override
    public boolean equals(Object _obj) {
        if (this == _obj) return true;
        if (_obj == null || getClass() != _obj.getClass()) return false;
        Parameter parameter = (Parameter) _obj;
        return length == parameter.length &&
                byteLength == parameter.byteLength &&
                unicodeByteLength == parameter.unicodeByteLength &&
                decimals == parameter.decimals &&
                Objects.equals(name, parameter.name) &&
                Objects.equals(description, parameter.description) &&
                Objects.equals(type, parameter.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, type, length, byteLength, unicodeByteLength, decimals);
    }
}
