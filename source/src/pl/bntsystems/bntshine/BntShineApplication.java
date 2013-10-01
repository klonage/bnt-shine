package pl.bntsystems.bntshine;

import android.app.Application;

public class BntShineApplication extends Application {
	private TCPClient tcpClient;
	
	@Override
	public void onCreate() {
		tcpClient = new TCPClient();
		super.onCreate();
	}
	
	public TCPClient getTCPClient() {
		
		return tcpClient;
	}
}
