package seung.spring.conf.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import seung.java.lib.arg.SMap;

@Repository("sD01")
public class SDI01 implements SD01 {

	private Logger logger = LoggerFactory.getLogger(SDI01.class);
	
	@Resource(name="sst01")
	private SqlSession sst01;
	
	public List<SMap> db01SL(String statement) {
		logger.debug(String.format("[%s] - %s", statement, ""));
		return sst01.selectList(statement);
	}
	public List<SMap> db01SL(String statement, String value) {
		logger.debug(String.format("[%s] - %s", statement, value));
		return sst01.selectList(statement, value);
	}
	public List<SMap> db01SL(String statement, SMap sMap) {
		sMap.put("date", getDateString("yyyyMMdd"));
		sMap.put("time", getDateString("HHmmss"));
		logger.debug(String.format("[%s] - %s", statement, sMap.toString(true)));
		return sst01.selectList(statement, sMap);
	}
	
	public SMap db01SR(String statement) {
		logger.debug(String.format("[%s] - %s", statement, ""));
		return sst01.selectOne(statement);
	}
	public SMap db01SR(String statement, SMap sMap) {
		sMap.put("date", getDateString("yyyyMMdd"));
		sMap.put("time", getDateString("HHmmss"));
		logger.debug(String.format("[%s] - %s", statement, sMap.toString(true)));
		return sst01.selectOne(statement, sMap);
	}
	
	public SMap db01SR(String statement, String string) {
		logger.debug(String.format("[%s] - %s", statement, string));
		return sst01.selectOne(statement, string);
	}
	
	public int db01IR(String statement, SMap sMap) {
		sMap.put("date", getDateString("yyyyMMdd"));
		sMap.put("time", getDateString("HHmmss"));
		logger.debug(String.format("[%s] - %s", statement, sMap.toString(true)));
		return sst01.insert(statement, sMap);
	}
	
	public int db01UR(String statement, SMap sMap) {
		sMap.put("date", getDateString("yyyyMMdd"));
		sMap.put("time", getDateString("HHmmss"));
		logger.debug(String.format("[%s] - %s", statement, sMap.toString(true)));
		return sst01.update(statement, sMap);
	}
	
	public int db01UL(String statement, SMap sMap) {
		sMap.put("date", getDateString("yyyyMMdd"));
		sMap.put("time", getDateString("HHmmss"));
		logger.debug(String.format("[%s] - %s", statement, sMap.toString(true)));
		return sst01.update(statement, sMap);
	}
	
	public int db01DR(String statement, SMap sMap) {
		sMap.put("date", getDateString("yyyyMMdd"));
		sMap.put("time", getDateString("HHmmss"));
		logger.debug(String.format("[%s] - %s", statement, sMap.toString(true)));
		return sst01.delete(statement, sMap);
	}
	
	public int db01DL(String statement, SMap sMap) {
		sMap.put("date", getDateString("yyyyMMdd"));
		sMap.put("time", getDateString("HHmmss"));
		logger.debug(String.format("[%s] - %s", statement, sMap.toString(true)));
		return sst01.delete(statement, sMap);
	}
	
	public String getDateString(String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern.length() > 0 ? pattern : "yyyy-MM-dd HH:mm:sssss");
		return sdf.format(new Date());
	}
}
