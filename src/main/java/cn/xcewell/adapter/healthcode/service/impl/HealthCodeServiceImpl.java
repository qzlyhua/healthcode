package cn.xcewell.adapter.healthcode.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.XmlUtil;
import cn.hutool.crypto.digest.MD5;
import cn.xcewell.adapter.healthcode.dto.Data;
import cn.xcewell.adapter.healthcode.service.HealthCodeService;
import cn.xcewell.adapter.healthcode.util.WsClient;
import cn.xcewell.adapter.healthcode.vo.HealthCodeResponse;
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
    @Value("${webservice.address}")
    private String url;
    @Value("${webservice.appid}")
    private String appId;

    @Override
    public HealthCodeResponse queryHealthCode(String idCard) {
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
	String resCode = (String) BeanUtil.beanToMap(invResMap.get("code")).get("value");
	String resData = (String) BeanUtil.beanToMap(invResMap.get("data")).get("value");
	String resMessage = (String) BeanUtil.beanToMap(invResMap.get("message")).get("value");
	log.info("请求出参，code：{}；data：{}，message：{}", resCode, resData, resMessage);

	Map<String, Object> res = XmlUtil.xmlToMap(resData);
	HealthCodeResponse healthCodeResponse = new HealthCodeResponse();
	healthCodeResponse.setStatus(res.get("CodeStatus").toString());
	healthCodeResponse.setReason(res.get("StatusReason").toString());
	return healthCodeResponse;
    }

    private String getPwd(String appId, String method) {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	String date = sdf.format(new Date());
	String pwd = MD5.create().digestHex(appId + method + date, StandardCharsets.UTF_8);
	return pwd;
    }
}
