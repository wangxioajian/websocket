package com.wang.websocket.config;


import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.websocket.server.ServerEndpointConfig;

/**
 * @author 王建起
 * @create 2020-06-16 12:10
 */
public class MyEndpointConfigure extends ServerEndpointConfig.Configurator implements ApplicationContextAware {
	private  static volatile BeanFactory context;


	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		MyEndpointConfigure.context=applicationContext;

	}

	@Override
	public<T> T getEndpointInstance(Class<T> clazz) throws InstantiationException{
		return context.getBean(clazz);
	}
}
