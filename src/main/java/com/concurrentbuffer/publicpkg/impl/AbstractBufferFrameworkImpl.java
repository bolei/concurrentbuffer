package com.concurrentbuffer.publicpkg.impl;

import java.util.LinkedList;
import java.util.List;

import com.concurrentbuffer.exception.BufferFullException;
import com.concurrentbuffer.publicpkg.ConcurrentBufferFramework;
import com.concurrentbuffer.publicpkg.ItemHandler;

public abstract class AbstractBufferFrameworkImpl<T> implements
		ConcurrentBufferFramework<T> {

	protected Class<T> type;
	protected BufferModel<T> buffer;
	protected List<Thread> threadPool = new LinkedList<Thread>();
	protected int scaleupThreshold = 0;

	public AbstractBufferFrameworkImpl(Class<T> type) {
		this.type = type;
	}

	@Override
	public void startFramework() {
		// TODO Auto-generated method stub

	}

	@Override
	public void startFramework(int scaleupThreshold) {
		startFramework();
		this.scaleupThreshold = scaleupThreshold;
	}

	@Override
	public void startFramework(ItemHandler<T> handler, int numberHandlers) {
		buffer = createBufferModel();
		for (int i = 0; i < numberHandlers; i++) {
			Thread consumerThread = new Thread(new ConsumerTask<T>(
					handler.makeCopy(), buffer));
			consumerThread.start();
			threadPool.add(consumerThread);
		}
	}

	@Override
	public void startFramework(ItemHandler<T> handler, int numberHandlers,
			int scaleupThreshold) {
		startFramework(handler, numberHandlers);
		this.scaleupThreshold = scaleupThreshold;
	}

	@Override
	public void bufferItem(T item) {
		synchronized (buffer) {
			try {
				while (buffer.isFull()) {
					buffer.wait();
				}
				buffer.pushItem(item);
			} catch (BufferFullException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				buffer.notifyAll();
			}
		}

	}

	@Override
	public void bufferItem(List<T> items) {
		synchronized (buffer) {
			try {
				while (!buffer.hasEnoughSpace(items.size())) {
					buffer.wait();
				}
				for (T item : items) {
					buffer.pushItem(item);
				}
			} catch (BufferFullException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				buffer.notifyAll();
			}
		}

	}

	@Override
	public Class<T> getItemClass() {
		return type;
	}

	protected abstract BufferModel<T> createBufferModel();
}
