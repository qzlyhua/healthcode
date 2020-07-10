package cn.xcewell.adapter.healthcode.service;

import cn.xcewell.adapter.healthcode.vo.HealthCodeResponse;

/**
 * @author yanghua
 */
public interface HealthCodeService {
    HealthCodeResponse queryHealthCode(String idCard);
}
