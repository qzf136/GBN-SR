package GBN2;

public class Timer2 extends Thread {

	private int time;
	
	public Timer2() {
		time = -1;
	}
	
	public int getTime() {
		return time;
	}
	
	public void setTime(int time) {
		this.time = time;
	}
	
	@Override
	public void run() {
		try {
			while (time != 0) {
				Thread.sleep(1000);
				time--;
			}
			GBN2.timeOut();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
