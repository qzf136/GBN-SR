package SR;

import java.net.DatagramPacket;

public class SRServerSendData extends Thread {

	@Override
	public void run() {
		try {
			while (true) {
				while (SRServer.send_base + SRServer.N - 1 >= SRServer.nextSeq && SRServer.nextSeq <= 10) {
					byte[] data = new byte[1500];
					data[0] = SRServer.nextSeq;
					if (data[0] == 3) {
						SRServer.nextSeq++;
						continue;
					}
					DatagramPacket sendPacket = new DatagramPacket(data, data.length, SRServer.clientAddress, SRServer.clientPort);
					if (data[0] >= SRServer.send_base && data[0] <= SRServer.send_base + SRServer.N - 1) {
						SRServer.nextSeq++;
						System.err.print("Server: send packet " + data[0] + 
									", base = " + SRServer.send_base + 
									", nextSeq = " + SRServer.nextSeq +  "\n");
						SRServer.serverSocket.send(sendPacket);
						Thread.sleep(500);
					}
					SRServer.startTimer(data[0]);
				}
				Thread.sleep(500);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
