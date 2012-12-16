package com.concurrentbuffer.publicpkg.impl;

import java.util.LinkedList;
import java.util.List;

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
			buffer.pushItem(item);
			buffer.notify();
		}
	}

	@Override
	public void bufferItem(List<T> items) {
		synchronized (buffer) {
			for (T item : items) {
				buffer.pushItem(item);
			}
			buffer.notify();
		}
	}

	@Override
	public Class<T> getItemClass() {
		return type;
	}

	protected abstract BufferModel<T> createBufferModel();
}
