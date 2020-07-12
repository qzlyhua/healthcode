package cn.qzlyhua.healthcode.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author yanghua
 */
@Data
public class HealthCodeParam {
    @JsonProperty("IDNO")
    private String IDNO;
}
