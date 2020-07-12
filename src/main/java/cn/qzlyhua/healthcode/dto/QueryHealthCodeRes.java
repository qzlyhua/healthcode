package cn.qzlyhua.healthcode.dto;

import lombok.Data;

/**
 * @author yanghua
 */
@Data
public class QueryHealthCodeRes {
    private String IDCard;
    private String Name;
    private String Phone;
    private String CodeStatus;
    private String StatusReason;
}
