package com.trmsmy.spring.boot.batch.springbootbatch.partition;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

public class FilePartitioner implements Partitioner {

	private String inputDirectoryPath;

	public FilePartitioner(String inputDirPath) {
		this.inputDirectoryPath = inputDirPath;
	}
	
	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {
		Map<String, ExecutionContext> mapContext = new HashMap<>();
		
		
		
		return mapContext;
	}

}
