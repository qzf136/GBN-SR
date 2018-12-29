package SR1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SR1 {

	protected static DatagramSocket receiveSocket;
	protected static List<DatagramPacket> cache = new ArrayList<>();
	protected static byte receive_base = 1;
	
	protected static DatagramSocket sendSocket;
	protected static InetAddress address2;
	protected static int port2;
	protected static byte N = 4;
	protected static byte send_base = 1;
	protected static byte nextSeq = 1;
	protected static Timer1[] timers = new Timer1[11];
	protected static boolean[] isReceived = new boolean[11];
	
	public static void main(String[] args) {
		
		try {
			Thread.sleep(3000);
			receiveSocket = new DatagramSocket(11111);
			address2 = InetAddress.getLocalHost();
			port2 = 22222;
			sendSocket = new DatagramSocket();
			Arrays.fill(isReceived, 0, isReceived.length, false);
			for (int i = 1; i <= 10; i++) {
				Timer1 timer = new Timer1();
				timers[i] = timer;
				timer.start();
			}
			new SR1_ReceiveData().start();
			new SR1_SendData().start();
			new SR1_ReceiveACK().start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	public static void startTimer(byte b) {
		timers[b].setTime(b, 3);
	}
	
	public static void timeOut(byte index) {
		try {
			if (isReceived[index] == false) {
				byte[] data = new byte[1500];
				data[0] = index;
				DatagramPacket sendPacket = new DatagramPacket(data, data.length, address2, port2);
				isReceived[index] = true;
				System.out.print("SR1: resend packet " + data[0] + ", base = " + send_base + ", nextSeq = " + nextSeq + "\n");
				sendSocket.send(sendPacket);
				startTimer(index);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
