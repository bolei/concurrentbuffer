package com.concurrentbuffer.publicpkg;

public interface ItemHandler<T> {
	public void handleItem(T item);
}
