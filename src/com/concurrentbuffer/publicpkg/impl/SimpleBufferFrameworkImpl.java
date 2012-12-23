package com.concurrentbuffer.publicpkg.impl;

/**
 * This is a simple implementation of the buffer framework. Its buffer size is
 * fixed, number of consumers is fixed once initialized.
 * 
 * @author bolei
 * 
 * @param <T>
 */
public class SimpleBufferFrameworkImpl<T> extends
		AbstractBufferFrameworkImpl<T> {

	private int bufferSize;

	public SimpleBufferFrameworkImpl(Class<T> type, int bufferSize) {
		super(type);
		this.bufferSize = bufferSize;
	}

	@Override
	protected BufferModel<T> createBufferModel() {
		return new FixedSizeBufferModel<T>(bufferSize);
	}

}
