package SR1;

import java.net.DatagramPacket;


public class SR1_ReceiveACK extends Thread {

	@Override
	public void run() {
		try {
			while (true) {
				byte[] receivebytes = new byte[1024];
				DatagramPacket ackPacket = new DatagramPacket(receivebytes, receivebytes.length);
				SR1.sendSocket.receive(ackPacket);
				byte ackNum = receivebytes[0];
				SR1.isReceived[ackNum] = true;
				moveBase();
				System.out.print("SR1: receive ACK: " + ackNum + 
								", base = " + SR1.send_base +
								", nextSeq = " + SR1.nextSeq + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void moveBase() {
		for (byte i = SR1.send_base; i < SR1.isReceived.length; i++) {
			if (SR1.isReceived[i] == false) {
				SR1.send_base = i;
				break;
			}
		}
	}
	
}
