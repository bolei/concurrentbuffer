package com.concurrentbuffer.publicpkg;

public interface ConcurrentBuffer<T> {
	public T popItem();
	
	public void insertItem(T item);
}
