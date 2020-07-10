package cn.xcewell.adapter.healthcode.vo;

import lombok.Data;

/**
 * @author yanghua
 */
@Data
public class HealthCodeResponse {
    private String status;
    private String reason;
    // 密接
    private String data;
}
