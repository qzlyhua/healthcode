package cn.qzlyhua.healthcode.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.XmlUtil;
import cn.hutool.crypto.digest.MD5;
import cn.qzlyhua.healthcode.dto.Data;
import cn.qzlyhua.healthcode.service.HealthCodeService;
import cn.qzlyhua.healthcode.util.WsClient;
import cn.qzlyhua.healthcode.vo.HealthCodeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author yanghua
 */
@Service
@Slf4j
public class HealthCodeServiceImpl implements HealthCodeService {
    private static final String KEY_SUCCESS = "200";
    @Value("${webservice.address}")
    private String url;
    @Value("${webservice.appid}")
    private String appId;

    @Override
    public HealthCodeResponse queryHealthCode(String idCard) {
        log.info("===================================================");
        Data queryHealthCodeReq = new Data();
        // 注意：身份证的X大写
        queryHealthCodeReq.setIDCard(idCard.toUpperCase());
        String data = XmlUtil.toStr(XmlUtil.beanToXml(queryHealthCodeReq), "UTF-8", false, true);
        String pwd = getPwd(appId, "QueryHealthCode");
        log.info("请求入参 appId：{}", appId);
        log.info("请求入参 pwd：{}", pwd);
        log.info("请求入参 method：{}", "QueryHealthCode");
        log.info("请求入参 data：{}", data);
        Object invRes = WsClient.invoke(url, "Invoke", appId, pwd, "QueryHealthCode", data);
        Map<String, Object> invResMap = BeanUtil.beanToMap(invRes);
        String resCode = BeanUtil.beanToMap(invResMap.get("code")).get("value").toString();

        HealthCodeResponse healthCodeResponse = new HealthCodeResponse();
        healthCodeResponse.setCode(resCode);

        if (KEY_SUCCESS.equalsIgnoreCase(resCode)) {
            String resData = BeanUtil.beanToMap(invResMap.get("data")).get("value").toString();
            log.info("请求出参，code：{}；data：{}", resCode, resData);

            Map<String, Object> res = XmlUtil.xmlToMap(resData);
            healthCodeResponse.setStatus(res.get("CodeStatus").toString());
            healthCodeResponse.setReason(res.get("StatusReason").toString());
            healthCodeResponse.setMessage("调用成功");
        } else {
            String resMessage = BeanUtil.beanToMap(invResMap.get("message")).get("value").toString();
            healthCodeResponse.setMessage(resMessage);
            log.info("请求异常，code：{}；message：{}", resCode, resMessage);
        }

        log.info("处理完成：{}", healthCodeResponse);
        return healthCodeResponse;
    }

    /**
     * 密钥生成方法：md5(appId+method+date)
     *
     * @param appId
     * @param method
     * @return
     */
    private String getPwd(String appId, String method) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String date = sdf.format(new Date());
        String pwd = MD5.create().digestHex(appId + method + date, StandardCharsets.UTF_8);
        return pwd;
    }
}
