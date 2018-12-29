package GBN1;

import java.net.DatagramPacket;

public class GBN1_ReceiveACK extends Thread {

	@Override
	public void run() {
		try {
			while (true) {
				byte[] receivebytes = new byte[1024];
				DatagramPacket ackPacket = new DatagramPacket(receivebytes, receivebytes.length);
				GBN1.sendSocket.receive(ackPacket);
				byte ackNum = receivebytes[0];
				GBN1.base = (byte) (ackNum + 1);
				System.out.print("1: receive ACK: " + ackNum + ", base = " 
				+ GBN1.base + ", nextSeq = " + GBN1.nextSeq + "\n");
				if (GBN1.base != GBN1.nextSeq && GBN1.isOpen == false) {
					GBN1.startTimer();
					
				}
				else if (GBN1.base == GBN1.nextSeq && GBN1.isOpen == true) {
					GBN1.finishTimer();
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
