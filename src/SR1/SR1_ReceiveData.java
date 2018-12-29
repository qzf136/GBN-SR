package SR1;

import java.net.DatagramPacket;
import java.net.InetAddress;

public class SR1_ReceiveData extends Thread {

	@Override
	public void run() {
		try {
			while(true) {
				byte[] receiveData = new byte[1500];
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				SR1.receiveSocket.receive(receivePacket);
				InetAddress address2 = receivePacket.getAddress();
				int port2 = receivePacket.getPort();
				byte[] ackData = new byte[1024];
				ackData[0] = receiveData[0];
				DatagramPacket ackPacket = new DatagramPacket(ackData, ackData.length, address2, port2);
				if (ackData[0] == SR1.receive_base) {
					String str = "" + SR1.receive_base;
					for (byte i = 0; i < SR1.cache.size(); i++) 
						str += SR1.cache.get(i).getData()[0];
					System.err.println("SR1: receive packet " + ackData[0] + ", send ACK " + ackData[0] + ", ½»¸¶" + str);
					SR1.receiveSocket.send(ackPacket);
					SR1.receive_base = (byte) (SR1.receive_base + SR1.cache.size() + 1);
					SR1.cache.clear();
				} else if (ackData[0] > SR1.receive_base && ackData[0] <= SR1.receive_base+SR1.N-1) {
					if (!SR1.cache.contains(receivePacket))
						SR1.cache.add(receivePacket);
					System.err.println("SR1: receive packet " + ackData[0] + ", send ACK " + ackData[0] + ", »º´æ");
					SR1.receiveSocket.send(ackPacket);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
}
