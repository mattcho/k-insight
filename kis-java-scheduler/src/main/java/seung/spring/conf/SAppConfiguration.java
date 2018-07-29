package seung.spring.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import seung.java.lib.arg.SMap;

@Configuration
@EnableScheduling
@ComponentScan("seung,kinsight")
public class SAppConfiguration {

	private static final Logger logger = LoggerFactory.getLogger(SAppConfiguration.class);
	
	/**
	 * @desc app information
	 */
	@Bean(name="sAppInfo")
	public SMap createSAppInfo() {
		SMap sAppInfo = new SMap();
		return sAppInfo;
	}
	
	/**
	 * @desc scheduler properties
	 */
	@Bean(name="sSchedulerProperties")
	public SMap createSSchedulerProperties() {
		SMap sSchedulerProperties = new SMap();
		return sSchedulerProperties;
	}
	
//	/**
//	 * @desc single scheduler
//	 */
//	@Bean
//	public TaskScheduler buildTaskScheduler() {
//		return new ConcurrentTaskScheduler();
//	}
	
	/**
	 * @desc thread pool scheduler
	 */
	@Bean(destroyMethod="shutdown")
	public TaskScheduler buildTaskScheduler() {
		int poolSize = 10;
		logger.debug("thread pool task scheduler pool size: " + poolSize);
		ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
		threadPoolTaskScheduler.setPoolSize(poolSize);
		return threadPoolTaskScheduler;
	}
	
//	@Bean(destroyMethod = "shutdown")
//	public Executor taskScheduler() {
//		return Executors
//			.newScheduledThreadPool(30)
//			;
//	}
}
