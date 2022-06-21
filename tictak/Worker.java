
public class Worker extends Thread {

	private int id;
	private Data data;

	public Worker(int id, Data d) {
		this.id = id;
		data = d;
		this.start();
	}

	@Override
	public void run() {
		super.run();
		for (int i = 0; i < 5; i++) {
			synchronized (data) {
				while (this.id != data.getState()) {
					try {
						data.wait();
					} catch (InterruptedException e) {
						return;
					}
				}
				if (this.id == 1) {
					data.Tic();
				} else if (this.id == 2) {
					data.Tak();
				} else {
					data.Toy();
				}
				data.notifyAll();
			}
		}
	}

}
