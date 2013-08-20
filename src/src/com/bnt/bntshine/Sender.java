package com.bnt.bntshine;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Sender {
	Socket client = null;
	DataOutputStream outputStream;
	DataInputStream inputStream;
	String address = "192.168.1.109";

	public boolean sendCommand(int group, int address, int type) {
		byte[] buffer = new byte[9];
		buffer[0] = (byte) ((address == -1) ? 2 : 1);
		buffer[1] = (byte) 9;
		buffer[2] = (byte) address;
		buffer[3] = (byte) (((type << 4) | (group & 0x0F)) & 0xFF);
		buffer[4] = (byte) 3;
		buffer[5] = 0; buffer[6] = 0; buffer[7] = 0; buffer[8] = 0;
		
		try {
			outputStream.write(buffer);
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	public boolean connect(int port) {
		try {
			InetAddress serverAddr = InetAddress.getByName(address);
			client = new Socket(serverAddr, port);
			outputStream = new DataOutputStream(client.getOutputStream());
			inputStream = new DataInputStream(client.getInputStream());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void disconnect() {
		if (client != null && client.isConnected())
			try {
				client.close();
			} catch (IOException e) {
			}
	}
}
