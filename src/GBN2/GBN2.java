package GBN2;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class GBN2 {
	
	protected static DatagramSocket receiveSocket;
	protected static byte hasReceived = 0;
	
	protected static DatagramSocket sendSocket;
	protected static InetAddress address1;
	protected static int port1;
	protected static byte N = 4;
	protected static byte base = 1;
	protected static byte nextSeq = 1;
	protected static Timer2 timer = new Timer2();
	protected static boolean isOpen = false;
	protected static boolean istimeout = false;
	
	public static void main(String[] args) {
		try {
			Thread.sleep(5000);
			receiveSocket = new DatagramSocket(22222);
			new GBN2_ReceiveData().start();
			
			address1 = InetAddress.getLocalHost();
			port1 = 11111;
			sendSocket = new DatagramSocket();
			timer.start();
			startTimer();
			new GBN2_SendData().start();
			new GBN2_ReceiveACK().start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void startTimer() {
		timer.setTime(3);
		GBN2.isOpen = true;
	}
	
	public static void finishTimer() {
		timer.setTime(-1);
		GBN2.isOpen = false;
	}
	
	public static void timeOut() {
		try {
			istimeout = true;
			System.err.println("Timeout");
			for (byte i = base; i < nextSeq; i++) {
				byte[] data = new byte[1500];
				data[0] = i;
				DatagramPacket sendPacket = new DatagramPacket(data, data.length, address1, port1);
				System.err.print("2: resend packet " + i + ", base = " + base + ", nextSeq = " + nextSeq + "\n");
				GBN2.sendSocket.send(sendPacket);
				Thread.sleep(500);
			}
			istimeout = false;
			isOpen = false;
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
}
