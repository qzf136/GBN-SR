package SR;

import java.net.DatagramPacket;

public class SRServerReceiveACK extends Thread {

	@Override
	public void run() {
		try {
			while (true) {
				byte[] receivebytes = new byte[1024];
				DatagramPacket ackPacket = new DatagramPacket(receivebytes, receivebytes.length);
				SRServer.serverSocket.receive(ackPacket);
				byte ackNum = receivebytes[0];
				SRServer.isReceived[ackNum] = true;
				moveBase();
				System.out.print("Server: receive ACK: " + ackNum + 
								", base = " + SRServer.send_base +
								", nextSeq = " + SRServer.nextSeq + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void moveBase() {
		for (byte i = SRServer.send_base; i < SRServer.isReceived.length; i++) {
			if (SRServer.isReceived[i] == false) {
				SRServer.send_base = i;
				break;
			}
		}
	}
	
}
