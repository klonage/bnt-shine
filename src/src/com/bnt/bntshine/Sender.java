package com.bnt.bntshine;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Sender {
	Socket client = null;
	boolean isConnected = false;
	DataOutputStream outputStream;
	DataInputStream inputStream;
	String address = "192.168.1.64";

	public boolean sendToggleCommand(int group, int address, int type) {
		if (!isConnected)
			return false;
		
		byte[] buffer = new byte[9];
		buffer[0] = (byte) ((address == -1) ? 2 : 1);
		buffer[1] = (byte) 9;
		buffer[2] = (byte) address;
		if (address == -1) {
			type = type  / 4;
		}
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
			isConnected = true;
			return true;
		} catch (Exception e) {
			isConnected = false;
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

	public boolean sendGlobalOffCommand() {
		if (!isConnected)
			return false;
		
		byte[] buffer = new byte[9];
		buffer[0] = 3;
		buffer[1] = 9;
		buffer[2] = 0;
		buffer[3] = 1;
		buffer[4] = 2;
		buffer[5] = 0;
		buffer[6] = 0;
		buffer[7] = 0;
		buffer[8] = 0;
		
		try {
			outputStream.write(buffer);
		} catch (IOException e) {
			return false;
		}
		return true;
	}
}
