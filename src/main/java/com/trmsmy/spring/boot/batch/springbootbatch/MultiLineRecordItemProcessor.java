package com.trmsmy.spring.boot.batch.springbootbatch;

import org.springframework.batch.item.ItemProcessor;

public class MultiLineRecordItemProcessor implements ItemProcessor<String, String> {

	@Override
	public String process(String item) throws Exception {
		
		System.out.println("In Processor : " + item);
		
		return item.toUpperCase();
	}

}
