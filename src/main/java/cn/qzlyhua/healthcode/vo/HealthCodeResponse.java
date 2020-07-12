package cn.qzlyhua.healthcode.vo;

import lombok.Data;

/**
 * @author yanghua
 */
@Data
public class HealthCodeResponse {
    private String status;
    private String reason;
    private String code;
    private String message;
}
