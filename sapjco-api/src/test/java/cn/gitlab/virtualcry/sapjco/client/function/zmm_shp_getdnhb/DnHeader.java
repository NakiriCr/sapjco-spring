package cn.gitlab.virtualcry.sapjco.client.function.zmm_shp_getdnhb;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;

/**
 * Somethings
 *
 * @author VirtualCry
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DnHeader {

    @JSONField(name = "VBELN")
    private String dnNo;

}
