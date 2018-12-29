package SR2;

import java.net.DatagramPacket;


public class SR2_SendData extends Thread{

	
	public void run() {
		try {
			while (true) {
				while (SR2.send_base + SR2.N - 1 >= SR2.nextSeq && SR2.nextSeq <= 10) {
					byte[] data = new byte[1500];
					data[0] = SR2.nextSeq;
					if (data[0] == 3) {
						SR2.startTimer(data[0]);
						SR2.nextSeq++;
						continue;
					}
					DatagramPacket sendPacket = new DatagramPacket(data, data.length, SR2.address1, SR2.port1);
					if (data[0] >= SR2.send_base && data[0] <= SR2.send_base + SR2.N - 1) {
						SR2.nextSeq++;
						System.err.print("SR2: send packet " + data[0] + 
									", base = " + SR2.send_base + 
									", nextSeq = " + SR2.nextSeq +  "\n");
						SR2.sendSocket.send(sendPacket);
						SR2.startTimer(data[0]);
					}
					Thread.sleep(500);
				}
				Thread.sleep(500);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
}
