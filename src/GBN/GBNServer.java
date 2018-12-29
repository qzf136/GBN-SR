package GBN;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class GBNServer {
	
	protected static DatagramSocket serverSocket;
	protected static InetAddress clientAddress;
	protected static int clientPort;
	protected static byte N = 4;
	protected static byte base = 1;
	protected static byte nextSeq = 1;
	protected static GBNTimer timer = new GBNTimer();
	protected static boolean isOpen = false;
	protected static boolean istimeout = false;
	
	public static void main(String[] args) {
		try {
			clientAddress = InetAddress.getLocalHost();
			clientPort = 12345;
			serverSocket = new DatagramSocket();
			timer.start();
			new GBNServerSendData().start();
			new GBNServerReceiveACK().start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void startTimer() {
		timer.setTime(3);
	}
	
	public static void finishTimer() {
		timer.setTime(-1);
	}
	
	public static void timeOut() {
		try {
			istimeout = true;
			System.err.println("Timeout");
			for (byte i = base; i < nextSeq; i++) {
				byte[] data = new byte[1500];
				data[0] = i;
				DatagramPacket sendPacket = new DatagramPacket(data, data.length, clientAddress, clientPort);
				System.err.print("Server: resend packet " + i + ", base = " + base + ", nextSeq = " + nextSeq + "\n");
				GBNServer.serverSocket.send(sendPacket);
				Thread.sleep(500);
			}
			istimeout = false;
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
