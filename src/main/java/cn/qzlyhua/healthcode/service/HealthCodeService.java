package cn.qzlyhua.healthcode.service;

import cn.qzlyhua.healthcode.vo.HealthCodeResponse;

/**
 * @author yanghua
 */
public interface HealthCodeService {
    /**
     * 调用webservice接口：QueryHealthCode
     * @param idCard
     * @return
     */
    HealthCodeResponse queryHealthCode(String idCard);
}
