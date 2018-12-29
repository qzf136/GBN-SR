package SR;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class SRClient {

	private List<DatagramPacket> cache = new ArrayList<>();
	private byte N = 4;
	private byte receive_base = 1;
	
	public static void main(String[] args) {
		SRClient client = new SRClient();
		try {
			DatagramSocket clientSocket = new DatagramSocket(12345);
			client.receive(clientSocket);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void receive(DatagramSocket clientSocket) throws Exception {
		while(true) {
			byte[] receiveData = new byte[1500];
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			clientSocket.receive(receivePacket);
			InetAddress serverAddress = receivePacket.getAddress();
			int serverPort = receivePacket.getPort();
			byte[] ackData = new byte[1024];
			ackData[0] = receiveData[0];
			DatagramPacket ackPacket = new DatagramPacket(ackData, ackData.length, serverAddress, serverPort);
			if (ackData[0] == receive_base) {
				String str = "" + receive_base;
				for (byte i = 0; i < cache.size(); i++) 
					str += cache.get(i).getData()[0];
				System.err.println("Client: receive packet " + ackData[0] + ", send ACK " + ackData[0] + ", ½»¸¶" + str);
				clientSocket.send(ackPacket);
				receive_base = (byte) (receive_base + cache.size() + 1);
				cache.clear();
			} else if (ackData[0] > receive_base && ackData[0] <= receive_base+N-1) {
				if (!cache.contains(receivePacket))
					cache.add(receivePacket);
				System.err.println("Client: receive packet " + ackData[0] + ", send ACK " + ackData[0] + ", »º´æ");
				clientSocket.send(ackPacket);
			}
		}
		
	}
	
}
