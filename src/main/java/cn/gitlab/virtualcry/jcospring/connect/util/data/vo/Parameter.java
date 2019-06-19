package cn.gitlab.virtualcry.jcospring.connect.util.data.vo;

import lombok.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * Parameter
 *
 * @author VirtualCry
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Parameter implements Serializable {

    private String name;

    private String description;

    private String type;

    private int length;

    private int byteLength;

    private int unicodeByteLength;

    private int decimals;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Parameter parameter = (Parameter) obj;
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
