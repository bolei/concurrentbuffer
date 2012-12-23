package com.concurrentbuffer.publicpkg.impl;

import com.concurrentbuffer.exception.BufferEmptyException;
import com.concurrentbuffer.exception.BufferFullException;

public interface BufferModel<T> {

	public T getItem() throws BufferEmptyException;

	public void pushItem(T item) throws BufferFullException;

	public boolean isEmpty();

	public boolean isFull();

	public boolean hasEnoughSpace(int requiredSpace);

}
