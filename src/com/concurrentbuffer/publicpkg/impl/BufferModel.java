package com.concurrentbuffer.publicpkg.impl;

public interface BufferModel<T> {

	public T getItem();

	public void pushItem(T item);

	public boolean isEmpty();

}
