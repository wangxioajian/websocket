package com.wang.websocket.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author 王建起
 * @create 2020-06-16 14:18
 */
@Configuration
public class WebSocketConfig {

	@Bean
	public ServerEndpointExporter serverEndpointExporter(){
		return new ServerEndpointExporter();

	}

	@Bean
	public MyEndpointConfigure newConfigure(){
		return  new MyEndpointConfigure();

	}

}
