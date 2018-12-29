package GBN1;

import java.net.DatagramPacket;
import java.net.InetAddress;

public class GBN1_ReceiveData extends Thread {

	@Override
	public void run() {
		while(true) {
			try {
				byte[] receiveData = new byte[1500];
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				GBN1.receiveSocket.receive(receivePacket);
				int seq = receiveData[0];
				if (seq == GBN1.hasReceived+1)
					GBN1.hasReceived++;
				
				InetAddress address = receivePacket.getAddress();
				int port = receivePacket.getPort();
				byte[] ackData = new byte[1024];
				ackData[0] = GBN1.hasReceived;
				DatagramPacket ackPacket = new DatagramPacket(ackData, ackData.length, address, port);
				System.err.print("1: receive packet " + seq + ", send ACK " + GBN1.hasReceived + "\n");
				GBN1.receiveSocket.send(ackPacket);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
