package com.concurrentbuffer;

import com.concurrentbuffer.exception.BufferEmptyException;

public abstract class ConsumerTask<T> implements Runnable {
	private BufferModel<T> bufferModel;

	@Override
	public void run() {
		T item;
		try {
			while (!Thread.interrupted()) {
				item = null;
				synchronized (bufferModel) {
					while (bufferModel.isEmpty()) {
						bufferModel.wait();
					}
					try {
						item = bufferModel.getItem();
					} catch (BufferEmptyException e) {
						e.printStackTrace();
					} finally {
						bufferModel.notifyAll();
					}
				}
				if (item != null) {
					consume(item);
				}
			}
		} catch (InterruptedException e) {
			System.out.println("interupted!");
		}
	}

	protected void setBufferModel(BufferModel<T> buff) {
		bufferModel = buff;
	}

	protected abstract void consume(T item);

}
