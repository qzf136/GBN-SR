package GBN;

import java.net.DatagramPacket;

public class GBNServerReceiveACK extends Thread {

	
	@Override
	public void run() {
		try {
			while (true) {
				byte[] receivebytes = new byte[1024];
				DatagramPacket ackPacket = new DatagramPacket(receivebytes, receivebytes.length);
				GBNServer.serverSocket.receive(ackPacket);
				byte ackNum = receivebytes[0];
				GBNServer.base = (byte) (ackNum + 1);
				System.out.print("Server: receive ACK: " + ackNum + ", base = " 
				+ GBNServer.base + ", nextSeq = " + GBNServer.nextSeq + "\n");
				if (GBNServer.base != GBNServer.nextSeq && GBNServer.isOpen == false) {
					GBNServer.startTimer();
					GBNServer.isOpen = true;
				}
				else if (GBNServer.base == GBNServer.nextSeq && GBNServer.isOpen == true) {
					GBNServer.finishTimer();
					GBNServer.isOpen = false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
}
