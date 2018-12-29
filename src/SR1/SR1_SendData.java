package SR1;

import java.net.DatagramPacket;


public class SR1_SendData extends Thread{

	public void run() {
		try {
			while (true) {
				while (SR1.send_base + SR1.N - 1 >= SR1.nextSeq && SR1.nextSeq <= 10) {
					byte[] data = new byte[1500];
					data[0] = SR1.nextSeq;
					if (data[0] == 3) {
						SR1.startTimer(data[0]);
						SR1.nextSeq++;
						continue;
					}
					DatagramPacket sendPacket = new DatagramPacket(data, data.length, SR1.address2, SR1.port2);
					if (data[0] >= SR1.send_base && data[0] <= SR1.send_base + SR1.N - 1) {
						SR1.nextSeq++;
						System.out.print("SR1: send packet " + data[0] + 
									", base = " + SR1.send_base + 
									", nextSeq = " + SR1.nextSeq +  "\n");
						SR1.sendSocket.send(sendPacket);
						SR1.startTimer(data[0]);
					}
					Thread.sleep(500);
				}
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
