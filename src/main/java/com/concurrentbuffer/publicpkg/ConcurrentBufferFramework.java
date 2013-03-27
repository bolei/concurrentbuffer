package com.concurrentbuffer.publicpkg;

import java.util.List;

public interface ConcurrentBufferFramework<T> {
	/**
	 * Initialize a concurrent buffer so that it is ready to be used. This
	 * method reads configuration file buffer.properties from the class path.
	 */
	public void startFramework();

	/**
	 * Initialize a concurrent buffer so that it is ready to be used. This
	 * method reads configuration file buffer.properties from the class path. If
	 * the number of elements in the buffer exceeds the threshold, more consumer
	 * threads will be created. 0 means the framework will not add consumer
	 * thread automatically.
	 * 
	 * @param scaleupThreshold
	 */
	public void startFramework(int scaleupThreshold);

	/**
	 * Initialize a concurrent buffer so that it is ready to be used. For each
	 * of the handlers, a consumer thread is created. The scaleupThreshold is 0.
	 * 
	 * @param handlers
	 * @param numberHandlers
	 */
	public void startFramework(ItemHandler<T> handlers, int numberHandlers);

	public void startFramework(ItemHandler<T> handlers, int numberHandlers,
			int scaleupThreshold);

	/**
	 * Put one item into buffer
	 * 
	 * @param item
	 */
	public void bufferItem(T item);

	public void bufferItem(List<T> items);

	public Class<T> getItemClass();
}
