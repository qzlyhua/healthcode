package cn.qzlyhua.healthcode.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author yanghua
 * @Description spring获取bean工具类
 * @date 2018-08-21
 */
@Component
public class SpringUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
	if(SpringUtils.applicationContext == null) {
	    SpringUtils.applicationContext = applicationContext;
	}
    }

    /**
     * 获取applicationContext
     *
     * @return
     */
    public static ApplicationContext getApplicationContext() {
	return applicationContext;
    }


    /**
     * 通过class获取Bean.
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
	return getApplicationContext().getBean(clazz);
    }
}
