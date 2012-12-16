package com.concurrentbuffer.publicpkg.impl;

import com.concurrentbuffer.publicpkg.ItemHandler;

public class ConsumerTask<T> implements Runnable {
	private ItemHandler<T> itemHandler;
	private BufferModel<T> bufferModel;

	public ConsumerTask(ItemHandler<T> handler, BufferModel<T> buffer) {
		itemHandler = handler;
		this.bufferModel = buffer;
	}

	@Override
	public void run() {
		T item;
		try {
			while (!Thread.interrupted()) {
				synchronized (bufferModel) {
					while (bufferModel.isEmpty()) {
						bufferModel.wait();
					}
					item = bufferModel.getItem();
				}
				itemHandler.handleItem(item);
			}
		} catch (InterruptedException e) {
			System.out.println("interupted!");
		}
	}
}
