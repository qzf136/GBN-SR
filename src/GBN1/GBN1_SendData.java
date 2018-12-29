package GBN1;

import java.net.DatagramPacket;

public class GBN1_SendData extends Thread{
	@Override
	public void run(){
		try {
			while (true) {
				while (GBN1.nextSeq <= GBN1.base + GBN1.N - 1
						&& GBN1.nextSeq <= 10 && GBN1.istimeout == false) {
					if (GBN1.nextSeq == 3) {
						GBN1.nextSeq++;
						continue;
					}
					byte[] data = new byte[1500];
					data[0] = GBN1.nextSeq;
					DatagramPacket sendPacket = new DatagramPacket(data, data.length, 
							GBN1.address2, GBN1.port2);
					GBN1.nextSeq++;
					System.out.print("1: send packet " + data[0] + ", base = " 
							+ GBN1.base + ", nextSeq = " + GBN1.nextSeq + "\n");
					GBN1.sendSocket.send(sendPacket);
					Thread.sleep(500);
				}
				Thread.sleep(500);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
