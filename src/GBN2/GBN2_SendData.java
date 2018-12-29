package GBN2;

import java.net.DatagramPacket;

public class GBN2_SendData extends Thread {

	public void run(){
		try {
			while (true) {
				while (GBN2.nextSeq <= GBN2.base + GBN2.N - 1
						&& GBN2.nextSeq <= 10 && GBN2.istimeout == false) {
					if (GBN2.nextSeq == 3) {
						GBN2.nextSeq++;
						continue;
					}
					byte[] data = new byte[1500];
					data[0] = GBN2.nextSeq;
					DatagramPacket sendPacket = new DatagramPacket(data, data.length, 
							GBN2.address1, GBN2.port1);
					GBN2.nextSeq++;
					System.err.print("2: send packet " + data[0] + ", base = " 
							+ GBN2.base + ", nextSeq = " + GBN2.nextSeq + "\n");
					GBN2.sendSocket.send(sendPacket);
					Thread.sleep(500);
				}
				Thread.sleep(500);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
