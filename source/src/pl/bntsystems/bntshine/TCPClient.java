package pl.bntsystems.bntshine;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class TCPClient {

	private String SERVERIP = "192.168.1.109";
	public static final int SERVERPORT = 4444;
	private OnConnectionStatusChanged statusChangedListener = null;
	private boolean mRun = false;

	private PrintWriter out;
	private BufferedReader in;

	public void setListener(OnConnectionStatusChanged statusChangedListener) {
		this.statusChangedListener = statusChangedListener;
	}

	public void sendMessage(String message){
		if (out != null && !out.checkError()) {
			out.println(message);
			out.flush();
		}
	}

	public void stopClient(){
		mRun = false;
	}

	public void run() {
		mRun = true;
		
		try {
			InetAddress serverAddr = InetAddress.getByName(SERVERIP);
			Socket socket = new Socket(serverAddr, SERVERPORT);

			try {
				out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				
				if (statusChangedListener != null)
					statusChangedListener.connectionStatusChanged(0);
				
				while (mRun) {
					in.readLine();
				}
			} catch (Exception e) {
				if (statusChangedListener != null) {
					statusChangedListener.connectionStatusChanged(1);
				}
			} finally {
				socket.close();
			}

		} catch (Exception e) {
			if (statusChangedListener != null) {
				statusChangedListener.connectionStatusChanged(2);
			}
		}
	}
	
	public interface OnConnectionStatusChanged {
		public void connectionStatusChanged(int status);
	}

	public void setAddress(String ipAddress) {

		SERVERIP = ipAddress;
	}
}