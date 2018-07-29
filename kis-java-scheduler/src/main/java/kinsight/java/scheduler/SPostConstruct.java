package kinsight.java.scheduler;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import seung.java.lib.arg.SMap;
import seung.spring.conf.dao.SD01;
import seung.spring.util.SCommonU;

@Component
public class SPostConstruct {

	private static final Logger logger = LoggerFactory.getLogger(SPostConstruct.class);
	
	@Resource(name = "sSchedulerProperties")
	private SMap sSchedulerProperties;
	
	@Resource(name = "sCommonU")
	private SCommonU sCommonU;
	
	@Resource(name = "sD01")
	private SD01 sD01;
	
	@PostConstruct
	public void initSPostConstruct() {
		
		try {
			
			sSchedulerProperties.putAll(sCommonU.getSystemInfo());
			
			SMap serverSR = sD01.db01SR("serverSR", sSchedulerProperties);
			
			if(serverSR == null) {
				sSchedulerProperties.put("srvCd", sCommonU.getUUID());
				sD01.db01IR("serverIR", sSchedulerProperties);
				serverSR = sD01.db01SR("serverSR", sSchedulerProperties);
			} else {
				sD01.db01IR("serverUR", serverSR);
			}
			sSchedulerProperties.putAll(serverSR);
			
			logger.info(sSchedulerProperties.toString(true));
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
		}
	}
}
