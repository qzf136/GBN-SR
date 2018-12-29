package SR1;

public class Timer1 extends Thread {

	private int time;
	private byte index;
	
	public Timer1() {
		time = -1;
	}
	
	public void setTime(Byte b, int time) {
		this.time = time;
		this.index = b;
	}
	
	@Override
	public void run() {
		try {
			while (time != 0) {
				Thread.sleep(1000);
				time--;
			}
			SR1.timeOut(index);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
