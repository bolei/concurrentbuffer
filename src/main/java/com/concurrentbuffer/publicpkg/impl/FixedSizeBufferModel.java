package com.concurrentbuffer.publicpkg.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.list.FixedSizeList;

import com.concurrentbuffer.exception.BufferEmptyException;
import com.concurrentbuffer.exception.BufferFullException;

public class FixedSizeBufferModel<T> implements BufferModel<T> {

	private List<T> buffer;

	private int capacity;

	private int begin = 0;
	private int end = 0; // points to the next cell of the last element

	@SuppressWarnings("unchecked")
	public FixedSizeBufferModel(int size) {
		// no element is put into the cell before start position
		this.capacity = size;
		buffer = FixedSizeList
				.decorate(Arrays.asList(new Object[capacity + 1]));
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

}
