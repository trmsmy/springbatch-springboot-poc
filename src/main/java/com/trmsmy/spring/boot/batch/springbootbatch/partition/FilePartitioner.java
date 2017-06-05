package com.trmsmy.spring.boot.batch.springbootbatch.partition;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

public class FilePartitioner implements Partitioner {

    Logger LOG = LoggerFactory.getLogger(FilePartitioner.class);

	
	private String inputDirectoryPath;

	public FilePartitioner(String inputDirPath) {
		this.inputDirectoryPath = inputDirPath;
	}
	
	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {
		Map<String, ExecutionContext> mapContext = new HashMap<>();
		
		LOG.warn("Executing Partitioner with Grid Size " + gridSize  + ", dir path " + inputDirectoryPath);
		
		Collection<File> fileList = FileUtils.listFiles(new File(inputDirectoryPath), new String[]{"csv"}, false);
		
		LOG.warn("List of files to be procssed " + fileList);
		
		for (File file : fileList) {
			ExecutionContext context = new ExecutionContext();
			context.put("filePath", file.getPath());
			context.put("fileName", file.getName());
			mapContext.put(file.getPath(), context);
			
		}
		
		/*
		mapContext.put("file1.csv", new ExecutionContext());
		mapContext.put("file2.csv", new ExecutionContext());
		mapContext.put("file3.csv", new ExecutionContext());
		mapContext.put("file4.csv", new ExecutionContext());
		mapContext.put("file5.csv", new ExecutionContext());*/
		
		return mapContext;
	}

}
