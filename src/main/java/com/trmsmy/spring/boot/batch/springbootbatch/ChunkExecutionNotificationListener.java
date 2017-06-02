package com.trmsmy.spring.boot.batch.springbootbatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.listener.ChunkListenerSupport;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.stereotype.Component;


@Component
public class ChunkExecutionNotificationListener  extends ChunkListenerSupport {
	private static final Logger log = LoggerFactory.getLogger(ChunkExecutionNotificationListener.class);

	@Override
	public void beforeChunk(ChunkContext context) {
		for (String attrName : context.attributeNames()) {
			log.info("Attribute Name in Chunk Listener " + attrName );
		}
	}
	
}
