package GBN2;

import java.net.DatagramPacket;
import java.net.InetAddress;

public class GBN2_ReceiveData extends Thread {

	@Override
	public void run() {
		try {
			while(true) {
				byte[] receiveData = new byte[1500];
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				GBN2.receiveSocket.receive(receivePacket);
				int seq = receiveData[0];
				if (seq == GBN2.hasReceived+1)
					GBN2.hasReceived++;
				
				InetAddress address = receivePacket.getAddress();
				int port = receivePacket.getPort();
				byte[] ackData = new byte[1024];
				ackData[0] = GBN2.hasReceived;
				DatagramPacket ackPacket = new DatagramPacket(ackData, ackData.length, address, port);
				System.out.print("2: receive packet " + seq + ", send ACK " + GBN2.hasReceived + "\n");
				GBN2.receiveSocket.send(ackPacket);	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
