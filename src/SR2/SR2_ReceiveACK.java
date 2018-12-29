package SR2;

import java.net.DatagramPacket;

public class SR2_ReceiveACK extends Thread{

	@Override
	public void run() {
		try {
			while (true) {
				byte[] receivebytes = new byte[1024];
				DatagramPacket ackPacket = new DatagramPacket(receivebytes, receivebytes.length);
				SR2.sendSocket.receive(ackPacket);
				byte ackNum = receivebytes[0];
				SR2.isReceived[ackNum] = true;
				moveBase();
				System.err.print("SR2: receive ACK: " + ackNum + 
								", base = " + SR2.send_base +
								", nextSeq = " + SR2.nextSeq + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void moveBase() {
		for (byte i = SR2.send_base; i < SR2.isReceived.length; i++) {
			if (SR2.isReceived[i] == false) {
				SR2.send_base = i;
				break;
			}
		}
	}
	
	
}
