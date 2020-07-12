package cn.qzlyhua.healthcode.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * webservice客户端工厂类
 *
 * @author yanghua
 */
@Component
@Slf4j
public class WsClientFactory {
    private static final ClassLoader CLASS_LOADER = Thread.currentThread().getContextClassLoader();
    private final Map<String, Object> webClientLock = new ConcurrentHashMap<>();
    private final Map<String, Client> clientCache = new ConcurrentHashMap<>();

    public Client create(String url) {
	Object lock = webClientLock.get(url);
	if(lock == null) {
	    lock = new Object();
	    webClientLock.put(url, lock);
	}

	synchronized (lock) {
	    Client client = getClient(url);
	    return client;
	}
    }

    private Client getClient(String url) {
	if(clientCache.containsKey(url)) {
	    return clientCache.get(url);
	} else {
	    if(!urlValidityCheck(url)) {
		return null;
	    }

	    log.info("正在为{}创建客户端", url);
	    JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();

	    try {
		// 在一个方法中连续调用多次WebService接口,每次调用前需要重置上下文在加载WebServiceClientFactory类时将上下文初始化为静态常量
		Client client = factory.createClient(url, CLASS_LOADER);
		log.info("为{}创建客户端完成", url);
		clientCache.put(url, client);
		return client;
	    } catch (Exception e) {
		log.info("-------------------------------------------------------");
		log.info("为{}创建客户端出错", url);
		log.info("-------------------------------------------------------");
		e.printStackTrace();
		log.info("-------------------------------------------------------");
	    }
	    return null;
	}
    }

    /**
     * URL地址有效性校验
     * 使用RestTemplate对该URL进行一次GET请求，测试地址是否可访问
     *
     * @param url
     * @return
     */
    private boolean urlValidityCheck(String url) {
	log.info("测试地址是否可访问：{}", url);
	try {
	    SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
	    requestFactory.setConnectTimeout(3000);
	    RestTemplate restTemplate = new RestTemplate(requestFactory);
	    ResponseEntity responseEntity = restTemplate.getForEntity(url, String.class);
	    log.info("测试结束，响应码：{}", responseEntity.getStatusCode().toString());
	    return responseEntity.getStatusCodeValue() == 200;
	} catch (Exception e) {
	    log.info("测试结束，地址无法访问：{}", e.getMessage());
	    return false;
	}
    }
}
