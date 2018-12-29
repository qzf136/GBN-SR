package GBN;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class GBNClient {

private byte hasReceived = 0;
	
	public static void main(String[] args) {
		GBNClient client = new GBNClient();
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
			int seq = receiveData[0];
			if (seq == hasReceived+1)
				hasReceived++;
			
			InetAddress serverAddress = receivePacket.getAddress();
			int serverPort = receivePacket.getPort();
			byte[] ackData = new byte[1024];
			ackData[0] = hasReceived;
			DatagramPacket ackPacket = new DatagramPacket(ackData, ackData.length, serverAddress, serverPort);
			if (hasReceived == 4) {
				System.err.println("Client: receive packet " + seq);
			}
			else {
				System.err.println("Client: receive packet " + seq + ", send ACK " + hasReceived);
				clientSocket.send(ackPacket);
			}
			
		}
		
	}
	
}
