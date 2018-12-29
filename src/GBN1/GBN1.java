package GBN1;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class GBN1 {
	
	protected static byte hasReceived = 0;
	protected static DatagramSocket receiveSocket;
	
	protected static InetAddress address2;
	protected static int port2;
	protected static DatagramSocket sendSocket;
	protected static byte N = 4;
	protected static byte base = 1;
	protected static byte nextSeq = 1;
	protected static Timer1 timer = new Timer1();
	protected static boolean isOpen = false;
	protected static boolean istimeout = false;
	
	public static void main(String[] args) {
		try {
			Thread.sleep(5000);
			receiveSocket = new DatagramSocket(11111);
			new GBN1_ReceiveData().start();
			
			address2 = InetAddress.getLocalHost();
			port2 = 22222;
			sendSocket = new DatagramSocket();
			timer.start();
			startTimer();
			new GBN1_SendData().start();
			new GBN1_ReceiveACK().start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void startTimer() {
		timer.setTime(3);
		GBN1.isOpen = true;
	}
	
	public static void finishTimer() {
		timer.setTime(-1);
		GBN1.isOpen = false;
	}
	
	public static void timeOut() {
		try {
			istimeout = true;
			System.out.println("Timeout");
			for (byte i = base; i < nextSeq; i++) {
				byte[] data = new byte[1500];
				data[0] = i;
				DatagramPacket sendPacket = new DatagramPacket(data, data.length, address2, port2);
				System.out.print("1: resend packet " + i + ", base = " + base + ", nextSeq = " + nextSeq + "\n");
				GBN1.sendSocket.send(sendPacket);
				Thread.sleep(500);
			}
			istimeout = false;
			isOpen = false;
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
}
