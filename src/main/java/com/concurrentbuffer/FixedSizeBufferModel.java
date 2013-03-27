package com.concurrentbuffer;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.concurrentbuffer.exception.BufferEmptyException;
import com.concurrentbuffer.exception.BufferFullException;

public class FixedSizeBufferModel<T> implements BufferModel<T> {

	private List<T> buffer;

	private int capacity;

	private int begin = 0;
	private int end = 0; // points to the next cell of the last element

	private List<ConsumerTask<T>> consumers = new LinkedList<ConsumerTask<T>>();

	@SuppressWarnings("unchecked")
	public FixedSizeBufferModel(int size) {
		// no element is put into the cell before start position
		this.capacity = size;
		buffer = Arrays.asList((T[]) new Object[capacity + 1]);
	}

	public void startModel() {
		for (ConsumerTask<T> consumer : consumers) {
			new Thread(consumer).start();
		}
	}

	@Override
	public T getItem() throws BufferEmptyException {
		if (isEmpty()) {
			throw new BufferEmptyException();
		}
		return buffer.get(begin++);
	}

	@Override
	public void pushItem(T item) throws BufferFullException {
		if (isFull()) {
			throw new BufferFullException();
		}
		buffer.set(end++, item);
	}

	@Override
	public boolean isEmpty() {
		return getElementCount() == 0 ? true : false;
	}

	@Override
	public boolean isFull() {
		return getElementCount() == capacity ? true : false;
	}

	@Override
	public boolean hasEnoughSpace(int requiredSpace) {
		return getElementCount() + requiredSpace <= capacity ? true : false;

	}

	private int getElementCount() {
		return (end - begin) % buffer.size();
	}

	@Override
	public void registerConsumer(ConsumerTask<T> consumer) {
		consumer.setBufferModel(this);
		consumers.add(consumer);
	}

	@Override
	public void registerConsumers(List<ConsumerTask<T>> listConsumers) {
		for (ConsumerTask<T> consumer : listConsumers) {
			consumer.setBufferModel(this);
			consumers.add(consumer);
		}
	}

}
