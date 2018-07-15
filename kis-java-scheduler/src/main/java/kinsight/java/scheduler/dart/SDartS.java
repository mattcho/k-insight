package kinsight.java.scheduler.dart;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import seung.java.arg.SMap;
import seung.java.lib.ex.SExU;
import seung.spring.conf.arg.SAppProperties;
import seung.spring.conf.dao.SD01;
import seung.spring.conf.dao.SD02;
import seung.spring.util.SCommonU;

@Component
public class SDartS {

	private static final Logger logger = LoggerFactory.getLogger(SDartS.class);
	
	@Resource(name = "sAppProperties")
	private SAppProperties sApplicationProperties;
	
	@Resource(name = "sSchedulerProperties")
	private SMap sSchedulerProperties;
	
	@Resource(name = "sCommonU")
	private SCommonU sCommonU;
	
	@Resource(name = "sD01")
	private SD01 sD01;
	
	@Resource(name = "sD02")
	private SD02 sD02;
	
	/**
	 * @desc extract corperation list
	 */
	@Scheduled(fixedRate = 1000)
	public void dartX0100() {
		
		SMap dartX0100VO = new SMap();
		
		// for server logging
		dartX0100VO.put("srvCd", sSchedulerProperties.getString("srvCd"));
		
		// x0100 run configuration
		dartX0100VO.putAll(sD01.db01SR("x0100SR", dartX0100VO));
		
		
		SMap logMap = null;
		// check x0100 should run
		if(dartX0100VO.getInt("isRun") == 1) {
			
			try {
				
				logger.info(dartX0100VO.toString(true));
				
				SMap dartX0100 = new SExU().extract(dartX0100VO);
				
				if("0000".equals(dartX0100.getString("resultCode"))) {
					
					SMap x0100 = dartX0100.getSMap("x0100");
					
					logger.info(x0100.toString(true));
					
					if("0000".equals(x0100.getString("resultCode"))) {
						
						for(SMap x0100SR: x0100.getListSMap("x0100SL")) {
							x0100SR.put("crpCd", sCommonU.getUUID());
							logMap = x0100SR;
							sD01.db01IR("x0100IR", x0100SR);
						}
						
					} else {
						dartX0100VO.put("isRun", 0);
						throw new Exception(x0100.getString("resultMessage"));
					}
					
				} else {
					dartX0100VO.put("isRun", 0);
					throw new Exception(dartX0100.getString("resultMessage"));
				}
				
			} catch (Exception e) {
				
				e.printStackTrace();
				logger.error(logMap.toString(true));
				// error logging to db
				
			}
			
			// update schedule configuration
			sD01.db01UR("x0199UR", dartX0100VO);
			
		}// end of extract
	}
	
	/**
	 * @desc extract document list
	 */
	@Scheduled(fixedRate = 1000)
	public void dartX0200() {
		
//		logger.info("schedule test - " + sCommonU.getDateString("yyyy-MM-dd HH:mm:ss"));
//		logger.info(sD01.db01SR("ds01Info").toString(true));
//		logger.info(sD02.db02SR("ds02Info").toString(true));
		
//		SMap dartX0200VO = new SMap();
//		
//		// for server logging
//		dartX0200VO.put("srvCd", sSchedulerProperties.getString("srvCd"));
//		
//		// x0200 run configuration
//		dartX0200VO.putAll(sD01.db01SR("x0200SR", dartX0200VO));
//		
//		// check x0200 should run
//		if(dartX0200VO.getInt("isRun") == 1) {
//			
//			SMap x0200 = new SExU().extract(dartX0200VO);
//			
//			if("0000".equals(x0200.getString("resultCode"))) {
//				
//			} else {
//				// error logging to db
//			}
//			
//			sD01.db01UR("x0100UR", dartX0200VO);
//		}
	}
}
