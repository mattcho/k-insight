package kinsight.java.scheduler.dart;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import seung.java.lib.arg.SMap;
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
//	@Scheduled(fixedRate = 1000)
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
				
				logger.info(dartX0100.toString(true));
				
				if("0000".equals(dartX0100.getString("rslCd"))) {
					
					SMap x0100 = dartX0100.getSMap("x0100");
					
					logger.info(x0100.toString(true));
					
					if("0000".equals(x0100.getString("rslCd"))) {
						
						for(SMap x0100SR: x0100.getListSMap("x0100SL")) {
							x0100SR.put("crpCd", sCommonU.getUUID());
							x0100SR.put("exSrvCd", sSchedulerProperties.getString("srvCd"));
							logMap = x0100SR;
							sD01.db01IR("x0100IR", x0100SR);
						}
						
					} else {
						dartX0100VO.put("isRun", 0);
						throw new Exception(x0100.getString("rslMsg"));
					}
					
				} else {
					dartX0100VO.put("isRun", 0);
					throw new Exception(dartX0100.getString("rslMsg"));
				}
				
			} catch (Exception e) {
				
				logger.error(e.getMessage());
				// x0100 run false
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
		
		SMap dartX0200VO = new SMap();
		
		// for server logging
		dartX0200VO.put("srvCd", sSchedulerProperties.getString("srvCd"));
		
		// x0200 run configuration
		dartX0200VO.putAll(sD01.db01SR("x0200SR", dartX0200VO));
		
		SMap logMap = null;
		// check x0100 should run
		if(dartX0200VO.getInt("isRun") == 1) {
			
			try {
				
				SMap dartX0101 = null;
				SMap x0101 = null;
				SMap dartX0200 = new SExU().extract(dartX0200VO);
				
				if("0000".equals(dartX0200.getString("rslCd"))) {
					
					SMap x0200 = dartX0200.getSMap("x0200");
					
					if("0000".equals(x0200.getString("rslCd"))) {
						
						for(SMap x0200SR: x0200.getListSMap("x0200SL")) {
							
							x0200SR.put("srvCd", sSchedulerProperties.getString("srvCd"));
							
							// case when corporation not exist
							if(0 == sD01.db01IR("x0200IR", x0200SR)) {
								
								x0200SR.put("orgCd", "dart");
								x0200SR.put("jobCd", "x0101");
								dartX0101 = new SExU().extract(x0200SR);
								
								if("0000".equals(dartX0101.getString("rslCd"))) {
									
									x0101 = dartX0101.getSMap("x0101");
									x0101.put("srvCd", sSchedulerProperties.getString("srvCd"));
									
									if(1 == sD01.db01IR("x0100IR", x0101)) {
										sD01.db01IR("x0200IR", x0200SR);
									}
									
								}
								
							}// end of x0101
						}
						
					} else {
						throw new Exception(x0200.getString("rslMsg"));
					}
					
				} else {
					throw new Exception(dartX0200.getString("rslMsg"));
				}
				
			} catch (Exception e) {
				
				logger.error(e.getMessage());
				// x0200 run false
				dartX0200VO.put("isRun", 0);
				// error logging to db
				
			}
			
			// update schedule configuration
			sD01.db01UR("x0299UR", dartX0200VO);
			
		}// end of extract
		
	}
	
	/**
	 * @desc extract document list
	 */
	@Scheduled(fixedRate = 1000)
	public void dartX0300() {
		
		SMap dartX0300VO = new SMap();
		
		// for server logging
		dartX0300VO.put("srvCd", sSchedulerProperties.getString("srvCd"));
		
		// x0200 run configuration
		dartX0300VO.putAll(sD01.db01SR("x0300SR", dartX0300VO));
		
		// check x0300 should run
		if(dartX0300VO.getInt("isRun") == 1) {
			
			try {
				
				for(SMap x0300SR : sD01.db01SL("x0300SL", dartX0300VO)) {
					
					logger.info(x0300SR.toString(true));
					
					SMap dartX0300 = new SExU().extract(x0300SR);
					
					if("0000".equals(dartX0300.getString("rslCd"))) {
						
						SMap x0300 = dartX0300.getSMap("x0300");
						
						if("0000".equals(x0300.getString("rslCd"))) {
							
							x0300SR.put("dcmNo", x0300.getString("dcmNo"));
							x0300.put("srvCd", sSchedulerProperties.getString("srvCd"));
							x0300.put("crpCd", x0300SR.getString("crpCd"));
							
							// balance sheet
							if(x0300.containsKey("x0301")) {
								
								for(SMap x0301 : x0300.getListSMap("x0301")) {
									x0301.put("crpCd"    , x0300SR.getString("crpCd"));
									x0301.put("rcpNo"    , x0300SR.getString("rcpNo"));
									x0301.put("rptSrtCd" , "BS");
									x0301.put("accValUnt", x0300.getString("unt_bs"));
									sD01.db01IR("x0301IR", x0301);
								}
								x0300.put("rptSrtCd", "BS");
								sD01.db01IR("x0300IR", x0300);
							}
							
							// income statement
							if(x0300.containsKey("x0302")) {
								
								for(SMap x0302 : x0300.getListSMap("x0302")) {
									x0302.put("crpCd"    , x0300SR.getString("crpCd"));
									x0302.put("rcpNo"    , x0300SR.getString("rcpNo"));
									x0302.put("rptSrtCd" , "IS");
									x0302.put("accValUnt", x0300.getString("unt_is"));
									sD01.db01IR("x0301IR", x0302);
								}
								x0300.put("rptSrtCd", "IS");
								sD01.db01IR("x0300IR", x0300);
							}
							
							// statement of comprehensive income
							if(x0300.containsKey("x0303")) {
								
								for(SMap x0303 : x0300.getListSMap("x0303")) {
									x0303.put("crpCd"    , x0300SR.getString("crpCd"));
									x0303.put("rcpNo"    , x0300SR.getString("rcpNo"));
									x0303.put("rptSrtCd" , "CI");
									x0303.put("accValUnt", x0300.getString("unt_ci"));
									sD01.db01IR("x0301IR", x0303);
								}
								x0300.put("rptSrtCd", "CI");
								sD01.db01IR("x0300IR", x0300);
							}
							
							// statement of cash flows
							if(x0300.containsKey("x0304")) {
								
								for(SMap x0304 : x0300.getListSMap("x0304")) {
									x0304.put("crpCd"    , x0300SR.getString("crpCd"));
									x0304.put("rcpNo"    , x0300SR.getString("rcpNo"));
									x0304.put("rptSrtCd" , "CF");
									x0304.put("accValUnt", x0300.getString("unt_cf"));
									sD01.db01IR("x0301IR", x0304);
								}
								x0300.put("rptSrtCd", "CF");
								sD01.db01IR("x0300IR", x0300);
							}
							
							sD01.db01UR("x0300UR", x0300SR);
							
						} else {
							throw new Exception(x0300.getString("rslMsg"));
						}
						
					} else {
						throw new Exception(dartX0300.getString("rslMsg"));
					}
				}
				
				
				
			} catch (Exception e) {
				
				logger.error(e.getMessage());
				// x0300 run false
				dartX0300VO.put("isRun", 0);
				// error logging to db
				
			}
			
			// update schedule configuration
			sD01.db01UR("x0399UR", dartX0300VO);
			
		}// end of extract
	}
}
