package GBN1;

public class Timer1 extends Thread{

	private int time;
	
	public Timer1() {
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
		while (time != 0) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			time--;
		}
		GBN1.timeOut();
	}
	
}
