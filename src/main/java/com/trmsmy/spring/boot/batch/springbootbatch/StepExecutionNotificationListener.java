package com.trmsmy.spring.boot.batch.springbootbatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.stereotype.Component;

@Component
public class StepExecutionNotificationListener extends StepExecutionListenerSupport {

	private static final Logger log = LoggerFactory.getLogger(StepExecutionNotificationListener.class);

	@Override
	public void beforeStep(StepExecution stepExecution) {
		log.info("Step Execution Notification listner " + stepExecution.getId());
	}

/*	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		// TODO Auto-generated method stub
		return null;
	}
*/
}
