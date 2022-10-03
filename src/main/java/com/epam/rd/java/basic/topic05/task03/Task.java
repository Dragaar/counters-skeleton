package com.epam.rd.java.basic.topic05.task03;

public class Task {

	private int numberOfThreads;
	private int numberOfIterations;
	private int pause;
	private int  c1; private int c2;

	Thread[] threads;
	public Task(int numberOfThreads, int numberOfIterations, int pause) {
		this.numberOfThreads = numberOfThreads;
		this.numberOfIterations = numberOfIterations;
		this.pause = pause;
		threads = new Thread[numberOfThreads];
	}


	public void compare() {
		c1 = 0; c2 = 0;
		threadCreator(logic);
		threadsStarter();
		threadsJoiner();
	}

	public void compareSync() {
		c1 = 0; c2 = 0;
		threadCreator(syncLogic);
		threadsStarter();
		threadsJoiner();
	}

	private void threadCreator(Runnable r)
	{
		for(int i = 0; i < numberOfThreads; i++)
		{
			threads[i] = new Thread(r, "Thread - " + i);
		}

	}

	private void threadsStarter(){
		for(Thread t : threads) {
			t.start();
		}
	}
	private void threadsJoiner()
	{
		for(Thread t : threads) {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	Runnable logic = new Runnable() {
		@Override
		public void run() {
			for (int i = 0; i < numberOfIterations; i++) {
				boolean compare = c1 == c2;
				System.out.println(compare + " " + c1 + " " + c2);
				c1++;
				try {
					Thread.sleep(pause);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				c2++;
			}
		}
	};

	Runnable syncLogic = new Runnable()   {
		@Override
		public void run()  {
			for (int i = 0; i < numberOfIterations; i++) {
				synchronized (this) {
					boolean compare = c1 == c2;
					System.out.println(compare + " " + c1 + " " + c2);

					c1++;
					try {
						Thread.sleep(pause);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					c2++;
				}
			}
		}
	};

	public static void main(String[] args) {
		Task t = new Task(2, 5, 10);
		t.compare();
		System.out.println("~~~");
		t.compareSync();
	}

}
