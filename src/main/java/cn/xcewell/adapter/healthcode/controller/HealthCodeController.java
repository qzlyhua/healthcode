package cn.xcewell.adapter.healthcode.controller;

import cn.xcewell.adapter.healthcode.service.HealthCodeService;
import cn.xcewell.adapter.healthcode.vo.HealthCodeParam;
import cn.xcewell.adapter.healthcode.vo.HealthCodeResponse;
import com.xcewell.common.Response;
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

    @RequestMapping("/getHealthCodeStatus")
    private Response<HealthCodeResponse> getHealthCodeStatus(@RequestBody HealthCodeParam param) {
	Assert.hasText(param.getIDNO(), "请输入身份证号码");
	HealthCodeResponse response = healthCodeService.queryHealthCode(param.getIDNO());
	return Response.yes(response);
    }
}
