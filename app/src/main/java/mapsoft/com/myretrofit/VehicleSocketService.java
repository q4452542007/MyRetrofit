package mapsoft.com.myretrofit;

import android.app.Application;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;


import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class VehicleSocketService extends Service {

	public Application application;
	public Socket client;
	public long oldTime;
	public boolean isRead;

	public String registerMsg, pingMsg;
	public String tcpIp, tcpPort;

	// 定时器
	public Timer pingTimer;

	class PingTask extends TimerTask {
		@Override
		public void run() {

			if (System.currentTimeMillis() - oldTime > 30 * 1000) { // 等待超时断开
				if (client != null) {
					Log.i(getString(R.string.TAG), "等待超时，注销并关闭套接字");
					try {
						isRead = false;
						TcpRequestUtils.closeSocket(client); // 关闭指令
					} catch (Exception e) {
						Log.i(getString(R.string.TAG), e.getMessage());
					}
				}
				isRead = true;
				new Thread(socketRunnable).start(); //启动socket
				Log.i(getString(R.string.TAG), "等待超时，重新启动连接");
			}
			if (client != null && !client.isClosed()) {
				// 发送心跳包
				TcpRequestUtils.sendPacket(client, pingMsg);
				Log.i(getString(R.string.TAG), "发送心跳包");
			}
		}
	}

	public VehicleSocketService() {
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		application = getApplication();
		SharedPreferences sharedPreferences =
				getSharedPreferences(getPackageName(), MODE_PRIVATE);
		tcpIp = "60.191.133.133";
		tcpPort = "18007";
		String id = "131";
		if (tcpIp != null && tcpPort != null && id != null) {
			registerMsg = "$GPRS,0,1,8," + id.length() + "," + id + ",$END$";
            pingMsg = "$GPRS,0,-1,-1," + id.length() + "," + id + ",$END$";
			// 启动套接字读取线程
			isRead = true;
			new Thread(socketRunnable).start();
		}
		return START_STICKY;
	}

	public void onDestroy() {
		super.onDestroy();
		if (client != null) {
			try {
				TcpRequestUtils.closeSocket(client); // 关闭指令
				client = null;
			} catch (IOException localIOException) {
				localIOException.printStackTrace();
			}
		}
		if(pingTimer != null){
			pingTimer.cancel();
			pingTimer = null;
		}
	}

	/**
	 * 套接字读取
	 */
	public Runnable socketRunnable = new Runnable() {
		@Override
		public void run() {

			while (true) {//进行socket链接判断
				try {
					client = TcpRequestUtils.startSocket(tcpIp, Integer.parseInt(tcpPort));
					if (client.isConnected()) { 	// 判断套接字是否连接
						break;
					} else {
						Thread.sleep(10000);
					}
				} catch (IOException | InterruptedException e1) {
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e2) {
						e2.printStackTrace();
					}
				}
			}
			try {
				TcpRequestUtils.sendPacket(client, registerMsg);

				byte[] bytes = new byte[1024];

				InputStream inputStream = client.getInputStream();
				StringBuilder builder = null;
				while (true) {
					int lenght = inputStream.read(bytes);
					if (lenght == -1) {//没有读取内容则5秒进行一次休息
						Thread.sleep(5000);
						continue;
					}
					byte[] use = new byte[lenght];
					for (int i = 0; i < lenght; i++) {
						use[i] = bytes[i];
					}
					String context = new String(use, "gb2312");
					if (builder != null) {//有缓存则追加
						builder.append(context);
						context = builder.toString();
					}
					//?? 每次清空  哪里有缓存
					builder = null;
					// 判断是否为完整协议
					if (context.startsWith("$GPRS") && context.endsWith("$END$")) {
						//Log.i(getString(R.string.TAG),context);
						String[] str = context.split(",");
						int flag = Integer.parseInt(str[2]);
						switch (flag) {
							case 0://心跳应答
								Log.i(getString(R.string.TAG), "心跳应答");
								oldTime = System.currentTimeMillis();
								break;
							case 1://注册应答
								// 判断是否需要启动心跳定时器
								oldTime = System.currentTimeMillis();
								if (pingTimer == null) {
									pingTimer = new Timer();
									// 10秒进行一次心跳发送
									pingTimer.schedule(new PingTask(), 0, 10000);
								}
								break;
							case 13://车辆分布信息
								Intent intent3 = new Intent();
								intent3.setAction(getString(R.string.action_vehicle));
								intent3.putExtra(getString(R.string.DATA_FLAG), str[5]);
								Log.e("Test",str.toString());
								sendBroadcast(intent3); // 推送车辆信息
								break;
							case 39://sms信息
								Intent intent2 = new Intent();
								intent2.setAction(getString(R.string.action_sms));
								intent2.putExtra(getString(R.string.DATA_FLAG), str[5]);
								sendBroadcast(intent2); // 推送SMS滚动信息
								break;
						}
					} else if (context.startsWith("$GPRS")) {
						//协议不完整判断该协议是否有缓存的价值,协议有开头没有结尾
						builder = new StringBuilder(context);
					}
				}

			} catch (IOException ie) {
				Log.i(getString(R.string.TAG), ie.getMessage());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	};

}
