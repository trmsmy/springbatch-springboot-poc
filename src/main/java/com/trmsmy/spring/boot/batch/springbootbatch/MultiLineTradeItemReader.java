package com.trmsmy.spring.boot.batch.springbootbatch;


import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.support.SingleItemPeekableItemReader;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * @author Dan Garrette
 * @since 2.0
 */
public class MultiLineTradeItemReader implements ItemReader<String>, ItemStream {
	private SingleItemPeekableItemReader<String> delegate;

	/**
	 * @see org.springframework.batch.item.ItemReader#read()
	 */
	@Override
	public String read() throws Exception {
		List<String> record = null;

		for (String line; (line = this.delegate.read()) != null;) {
			
			if (isRecordStart(line)) {
				record = new ArrayList<>();
				record.add(line);
			}
			else {
				record.add(line);
				String nextLine = this.delegate.peek();
				
				if (nextLine == null || isRecordStart(nextLine)) {
					return StringUtils.collectionToDelimitedString(record, "\n");				
				}
				
			}
		}
		
		Assert.isNull(record, "No 'END' was found.");
		return null;
	}

	private boolean isRecordStart(String line) {
		return line != null && line.equals("START");
	}

	public void setDelegate(FlatFileItemReader<String> delegate) {
		this.delegate = new SingleItemPeekableItemReader<>();
		this.delegate.setDelegate(delegate);
	}

	@Override
	public void close() throws ItemStreamException {
		this.delegate.close();
	}

	@Override
	public void open(ExecutionContext executionContext) throws ItemStreamException {
		this.delegate.open(executionContext);
	}

	@Override
	public void update(ExecutionContext executionContext) throws ItemStreamException {
		this.delegate.update(executionContext);
	}
}