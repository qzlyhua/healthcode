package cn.xcewell.adapter.healthcode.util;

import com.xcewell.common.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.service.model.BindingOperationInfo;

import javax.xml.namespace.QName;

/**
 * web service 客户端
 *
 * @author qzlyhua
 */
@Slf4j
public class WsClient {
    private static WsClientFactory factory;

    /**
     * 远程调用
     *
     * @param url    调用的url
     * @param method 方法
     * @param params 参数
     * @return
     */
    public static Object invoke(String url, String method, Object... params) throws RuntimeException {
	if(factory == null) {
	    factory = SpringUtils.getBean(WsClientFactory.class);
	}
	Client client = factory.create(url);
	if(client == null) {
	    throw new RuntimeException("调用webservice服务失败：" + url + "创建客户端失败!");
	}
	Endpoint endpoint = client.getEndpoint();
	//处理端口号丢失的问题，直接使用我们指定的url
	endpoint.getEndpointInfo().setAddress(url.substring(0, url.indexOf("?")));
	//处理webService接口和实现类namespace不同的情况，CXF动态客户端在处理此问题时，会报No operation was found with the name的异常
	QName opName = new QName(endpoint.getService().getName().getNamespaceURI(), method);
	BindingInfo bindingInfo = endpoint.getEndpointInfo().getBinding();
	if(bindingInfo.getOperation(opName) == null) {
	    for(BindingOperationInfo operationInfo : bindingInfo.getOperations()) {
		if(method.equals(operationInfo.getName().getLocalPart())) {
		    opName = operationInfo.getName();
		    break;
		}
	    }
	}
	Object[] results;
	Object response;
	try {
	    results = client.invoke(opName, params);
	    response = results[0];
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
	return response;
    }
}
