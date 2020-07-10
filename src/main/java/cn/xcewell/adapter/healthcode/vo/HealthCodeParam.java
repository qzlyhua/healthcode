package cn.xcewell.adapter.healthcode.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class HealthCodeParam {
    @JsonProperty("IDNO")
    private String IDNO;
    private String name;
}
