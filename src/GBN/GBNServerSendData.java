package GBN;

import java.net.DatagramPacket;

public class GBNServerSendData extends Thread {

	public void run(){
		try {
			while (true) {
				while (GBNServer.nextSeq <= GBNServer.base + GBNServer.N - 1
						&& GBNServer.nextSeq <= 10 && GBNServer.istimeout == false) {
					if (GBNServer.nextSeq == 3) {
						GBNServer.nextSeq++;
						continue;
					}
					byte[] data = new byte[1500];
					data[0] = GBNServer.nextSeq;
					DatagramPacket sendPacket = new DatagramPacket(data, data.length, 
							GBNServer.clientAddress, GBNServer.clientPort);
					GBNServer.nextSeq++;
					System.err.print("Server: send packet " + data[0] + ", base = " 
							+ GBNServer.base + ", nextSeq = " + GBNServer.nextSeq + "\n");
					GBNServer.serverSocket.send(sendPacket);
					Thread.sleep(500);
				}
				Thread.sleep(500);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
