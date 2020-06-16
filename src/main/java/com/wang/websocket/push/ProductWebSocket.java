package com.wang.websocket.push;

import com.wang.websocket.config.MyEndpointConfigure;
import org.apache.logging.log4j.spi.CopyOnWrite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 王建起
 * @create 2020-06-16 14:34
 */
@Component
@ServerEndpoint(value = "/productWebSocket/{userId}", configurator = MyEndpointConfigure.class)
public class ProductWebSocket {

	//静态变量，用来记录当前连线数。应该设计成线程安全的
	private static final AtomicInteger OnlineCount=new AtomicInteger(0);

	//concurrent包的线程安全set，用来存放每个客户端对应的productwebsocket对象
	private static CopyOnWriteArraySet<ProductWebSocket>webSocketSet =new CopyOnWriteArraySet<ProductWebSocket>();

	//与客户端的连接会话，需要通过它来给客户端发送数据
	private Session session;

	private Logger log =LoggerFactory.getLogger(ProductWebSocket.class);

	/**
	 * 连接建立成功调用方法
	 */
	@OnOpen
	public void onPen(@PathParam("userId")String userId,Session session){
		log.info("新客户端连入，用户id: "+ userId);
		this.session=session;
		webSocketSet.add(this);//加入set中
		addOnlineCount();//在线人数加1
		if(userId!=null){
			List<String> totalPushMsgs = new ArrayList<String>();
			totalPushMsgs.add(userId+"连接成功-"+ "-当前在线人数为："+ getOnlineCount());

			if (totalPushMsgs !=null && !totalPushMsgs.isEmpty()){
				totalPushMsgs.forEach(e -> sendMessage(e));
			}

		}
	}

	/**
	 * 连接关闭调用方法
	 *
	 */
	@OnClose
	public void onClose(){
		log.info("一个客户端关闭连接");
		webSocketSet.remove(this);//从set中删除
		subOnlineCount();//在线减一
	}

	/**
	 * 收到客户端消息后调用的方法
	 * @param message
	 * @param session
	 */
	@OnMessage
	public void onMessage(String message,Session session){
		log.info("用户发送过来的消息为：" + message);
	}

	/**
	 * 发生错误的时候调用
	 */
	@OnError
	public void onError(Session session,Throwable error){
		log.error("websocket出现错误");
		error.printStackTrace();
	}



	public void sendMessage(String message) {
		try {
			this.session.getBasicRemote().sendText(message);
			log.info("推送消息成功，消息为：" + message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 群发消息
	 */
	public static void sendInfo(String message) throws IOException{
		for(ProductWebSocket productWebSocket : webSocketSet){
			productWebSocket.sendMessage(message);
		}
	}



	public static synchronized void addOnlineCount() {
		OnlineCount.incrementAndGet();
	}

	public static synchronized void subOnlineCount() {
		OnlineCount.decrementAndGet(); // 在线数-1
	}

	public static synchronized int getOnlineCount() {
		return OnlineCount.get();
	}



}
