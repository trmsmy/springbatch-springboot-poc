package com.trmsmy.spring.boot.batch.springbootbatch.partition;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class CleanupTasklet implements Tasklet {
	private final String dirPath;

	public CleanupTasklet(final String processedImagePath) {
		this.dirPath = processedImagePath;
	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		FileUtils.cleanDirectory(new File(dirPath));

		return RepeatStatus.FINISHED;
	}
}
