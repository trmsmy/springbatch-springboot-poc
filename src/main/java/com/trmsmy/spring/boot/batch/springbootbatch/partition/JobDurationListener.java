package com.trmsmy.spring.boot.batch.springbootbatch.partition;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.util.StopWatch;

public class JobDurationListener implements JobExecutionListener {
	
    Logger LOG = LoggerFactory.getLogger(JobDurationListener.class);
	
	private StopWatch stopWatch;

	@Override
	public void beforeJob(JobExecution jobExecution) {
		stopWatch = new StopWatch();
		stopWatch.start("Processing image submissions");
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		stopWatch.stop();

		long duration = stopWatch.getLastTaskTimeMillis();

		LOG.info(String.format("Job took: %d minutes, %d seconds.", TimeUnit.MILLISECONDS.toMinutes(duration),
				TimeUnit.MILLISECONDS.toSeconds(duration)
						- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration))));
	}

}
