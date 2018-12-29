package SR;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;


public class SRServer {

	protected static DatagramSocket serverSocket;
	protected static InetAddress clientAddress;
	protected static int clientPort;
	protected static byte N = 4;
	protected static byte send_base = 1;
	protected static byte nextSeq = 1;
	protected static SRTimer[] timers = new SRTimer[11];
	protected static boolean[] isReceived = new boolean[11];
	
	public static void main(String[] args) {
		try {
			clientAddress = InetAddress.getLocalHost();
			clientPort = 12345;
			serverSocket = new DatagramSocket();
			Arrays.fill(isReceived, 0, isReceived.length, false);
			for (int i = 1; i <= 10; i++) {
				SRTimer timer = new SRTimer();
				timers[i] = timer;
				timer.start();
			}
			new SRServerSendData().start();
			new SRServerReceiveACK().start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void startTimer(byte b) {
		timers[b].setTime(3);
	}
	
	public static void timeOut() {
		try {
			byte index = 0;
			for (byte i = send_base; i < nextSeq; i++) {
				if (isReceived[i] == false) {
					index = i;
					break;
				}
			}
			if (index == 0)	return;
			if (isReceived[index] == false) {
				byte[] data = new byte[1500];
				data[0] = index;
				DatagramPacket sendPacket = new DatagramPacket(data, data.length, SRServer.clientAddress, SRServer.clientPort);
				serverSocket.send(sendPacket);
				isReceived[index] = true;
				System.err.print("Server: resend packet " + data[0] + ", base = " + SRServer.send_base + ", nextSeq = " + SRServer.nextSeq + "\n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
