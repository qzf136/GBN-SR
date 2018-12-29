package SR2;

import java.net.DatagramPacket;
import java.net.InetAddress;


public class SR2_ReceiveData extends Thread {

	@Override
	public void run() {
		try {
			while(true) {
				byte[] receiveData = new byte[1500];
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				SR2.receiveSocket.receive(receivePacket);
				InetAddress address2 = receivePacket.getAddress();
				int port2 = receivePacket.getPort();
				byte[] ackData = new byte[1024];
				ackData[0] = receiveData[0];
				DatagramPacket ackPacket = new DatagramPacket(ackData, ackData.length, address2, port2);
				if (ackData[0] == SR2.receive_base) {
					String str = "" + SR2.receive_base;
					for (byte i = 0; i < SR2.cache.size(); i++) 
						str += SR2.cache.get(i).getData()[0];
					System.out.println("SR2: receive packet " + ackData[0] + ", send ACK " + ackData[0] + ", ½»¸¶" + str);
					SR2.receiveSocket.send(ackPacket);
					SR2.receive_base = (byte) (SR2.receive_base + SR2.cache.size() + 1);
					SR2.cache.clear();
				} else if (ackData[0] > SR2.receive_base && ackData[0] <= SR2.receive_base+SR2.N-1) {
					if (!SR2.cache.contains(receivePacket))
						SR2.cache.add(receivePacket);
					System.out.println("SR2: receive packet " + ackData[0] + ", send ACK " + ackData[0] + ", »º´æ");
					SR2.receiveSocket.send(ackPacket);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
}
