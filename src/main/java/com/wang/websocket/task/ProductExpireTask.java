package com.wang.websocket.task;

import com.wang.websocket.push.ProductWebSocket;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 王建起
 * @create 2020-06-16 16:02
 */
@Component
public class ProductExpireTask {

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


	@Scheduled(fixedRate = 2000)
	public void productExpire() throws IOException{
		String[] strs={
				"Test随机消息：32.1021",
				"Test随机消息：33.1774",
				"Test随机消息：33.2372",
				"Test随机消息：31.0281",
				"Test随机消息：30.0222",
				"Test随机消息：32.1322",
				"Test随机消息：33.3221",
				"Test随机消息：31.2311",
				"Test随机消息：32.3112"};
		ProductWebSocket.sendInfo( "    Test 消息---->"+ RandomStr(strs));
		}


	/**
	 * 随机返回字符串
 	 * @param strs
	 * @return
	 */
	public static String RandomStr(String[] strs) {

		int random_index=(int)(Math.random()*strs.length);
		return strs[random_index];

	}

}

