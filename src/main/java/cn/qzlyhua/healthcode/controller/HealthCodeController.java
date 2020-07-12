package cn.qzlyhua.healthcode.controller;

import cn.qzlyhua.healthcode.service.HealthCodeService;
import cn.qzlyhua.healthcode.vo.HealthCodeResponse;
import cn.qzlyhua.healthcode.dto.Response;
import cn.qzlyhua.healthcode.vo.HealthCodeParam;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author yanghua
 */
@RestController
public class HealthCodeController {
    @Resource
    private HealthCodeService healthCodeService;

    /**
     * 根据身份证号码查询健康码状态（绿码、黄码、红码）及其原因
     * 入参举例：{"IDNO":"330987198710109876"}
     * @param param
     * @return
     */
    @RequestMapping("/getHealthCodeStatus")
    private Response<HealthCodeResponse> getHealthCodeStatus(@RequestBody HealthCodeParam param) {
	Assert.hasText(param.getIDNO(), "请输入身份证号码");
	HealthCodeResponse response = healthCodeService.queryHealthCode(param.getIDNO());
	return Response.yes(response);
    }
}
