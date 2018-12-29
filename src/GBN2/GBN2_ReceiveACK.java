package GBN2;

import java.net.DatagramPacket;

public class GBN2_ReceiveACK extends Thread {

	
	@Override
	public void run() {
		try {
			while (true) {
				byte[] receivebytes = new byte[1024];
				DatagramPacket ackPacket = new DatagramPacket(receivebytes, receivebytes.length);
				GBN2.sendSocket.receive(ackPacket);
				byte ackNum = receivebytes[0];
				GBN2.base = (byte) (ackNum + 1);
				System.err.print("2: receive ACK: " + ackNum + ", base = " 
				+ GBN2.base + ", nextSeq = " + GBN2.nextSeq + "\n");
				if (GBN2.base != GBN2.nextSeq && GBN2.isOpen == false) {
					GBN2.startTimer();
				}
				else if (GBN2.base == GBN2.nextSeq && GBN2.isOpen == true) {
					GBN2.finishTimer();	
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
}
