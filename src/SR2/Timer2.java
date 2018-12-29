package SR2;


public class Timer2 extends Thread{

	private int time;
	private byte index;
	
	public Timer2() {
		time = -1;
	}
	
	public int getTime() {
		return time;
	}
	
	public void setTime(byte index, int time) {
		this.time = time;
		this.index = index;
	}
	
	@Override
	public void run() {
		try {
			while (time != 0) {
				Thread.sleep(1000);
				time--;
			}
			SR2.timeOut(index);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
