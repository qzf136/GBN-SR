package SR2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SR2 {

	protected static DatagramSocket receiveSocket;
	protected static List<DatagramPacket> cache = new ArrayList<>();
	protected static byte receive_base = 1;
	
	protected static DatagramSocket sendSocket;
	protected static InetAddress address1;
	protected static int port1;
	protected static byte N = 4;
	protected static byte send_base = 1;
	protected static byte nextSeq = 1;
	protected static Timer2[] timers = new Timer2[11];
	protected static boolean[] isReceived = new boolean[11];
	
	public static void main(String[] args) {
		
		try {
			Thread.sleep(3000);
			receiveSocket = new DatagramSocket(22222);
			address1 = InetAddress.getLocalHost();
			port1 = 11111;
			sendSocket = new DatagramSocket();
			Arrays.fill(isReceived, 0, isReceived.length, false);
			for (int i = 1; i <= 10; i++) {
				Timer2 timer = new Timer2();
				timers[i] = timer;
				timer.start();
			}
			new SR2_ReceiveData().start();
			new SR2_SendData().start();
			new SR2_ReceiveACK().start();
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
				DatagramPacket sendPacket = new DatagramPacket(data, data.length, address1, port1);
				isReceived[index] = true;
				System.err.print("SR2: resend packet " + data[0] + ", base = " + send_base + ", nextSeq = " + nextSeq + "\n");
				sendSocket.send(sendPacket);
				startTimer(index);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
