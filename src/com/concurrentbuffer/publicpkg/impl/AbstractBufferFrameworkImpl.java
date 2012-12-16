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
		// TODO Auto-generated method stub

	}

	@Override
	public void startFramework(ItemHandler<T> handlers, int numberHandlers) {
		// TODO Auto-generated method stub

	}

	@Override
	public void startFramework(ItemHandler<T> handlers, int numberHandlers,
			int scaleupThreshold) {
		// TODO Auto-generated method stub

	}

	@Override
	public void bufferItem(T item) {
		synchronized (buffer) {
			buffer.pushItem(item);
		}
	}

	@Override
	public void bufferItem(List<T> items) {
		synchronized (buffer) {
			for (T item : items) {
				buffer.pushItem(item);
			}
		}
	}

	@Override
	public Class<T> getItemClass() {
		return type;
	}
}
