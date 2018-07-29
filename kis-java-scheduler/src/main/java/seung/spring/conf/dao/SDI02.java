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

@Repository("sD02")
public class SDI02 implements SD02 {

	private Logger logger = LoggerFactory.getLogger(SDI02.class);
	
	@Resource(name="sst02")
	private SqlSession sst02;
	
	public List<SMap> db02SL(String statement) {
		logger.debug(String.format("[%s] - %s", statement, ""));
		return sst02.selectList(statement);
	}
	public List<SMap> db02SL(String statement, String value) {
		logger.debug(String.format("[%s] - %s", statement, value));
		return sst02.selectList(statement, value);
	}
	public List<SMap> db02SL(String statement, SMap sMap) {
		sMap.put("date", getDateString("yyyyMMdd"));
		sMap.put("time", getDateString("HHmmss"));
		logger.debug(String.format("[%s] - %s", statement, sMap.toString(true)));
		return sst02.selectList(statement, sMap);
	}
	
	public SMap db02SR(String statement) {
		logger.debug(String.format("[%s] - %s", statement, ""));
		return sst02.selectOne(statement);
	}
	public SMap db02SR(String statement, SMap sMap) {
		sMap.put("date", getDateString("yyyyMMdd"));
		sMap.put("time", getDateString("HHmmss"));
		logger.debug(String.format("[%s] - %s", statement, sMap.toString(true)));
		return sst02.selectOne(statement, sMap);
	}
	
	public SMap db02SR(String statement, String string) {
		logger.debug(String.format("[%s] - %s", statement, string));
		return sst02.selectOne(statement, string);
	}
	
	public int db02IR(String statement, SMap sMap) {
		sMap.put("date", getDateString("yyyyMMdd"));
		sMap.put("time", getDateString("HHmmss"));
		logger.debug(String.format("[%s] - %s", statement, sMap.toString(true)));
		return sst02.insert(statement, sMap);
	}
	
	public int db02UR(String statement, SMap sMap) {
		sMap.put("date", getDateString("yyyyMMdd"));
		sMap.put("time", getDateString("HHmmss"));
		logger.debug(String.format("[%s] - %s", statement, sMap.toString(true)));
		return sst02.update(statement, sMap);
	}
	
	public int db02UL(String statement, SMap sMap) {
		sMap.put("date", getDateString("yyyyMMdd"));
		sMap.put("time", getDateString("HHmmss"));
		logger.debug(String.format("[%s] - %s", statement, sMap.toString(true)));
		return sst02.update(statement, sMap);
	}
	
	public int db02DR(String statement, SMap sMap) {
		sMap.put("date", getDateString("yyyyMMdd"));
		sMap.put("time", getDateString("HHmmss"));
		logger.debug(String.format("[%s] - %s", statement, sMap.toString(true)));
		return sst02.delete(statement, sMap);
	}
	
	public int db02DL(String statement, SMap sMap) {
		sMap.put("date", getDateString("yyyyMMdd"));
		sMap.put("time", getDateString("HHmmss"));
		logger.debug(String.format("[%s] - %s", statement, sMap.toString(true)));
		return sst02.delete(statement, sMap);
	}
	
	public String getDateString(String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern.length() > 0 ? pattern : "yyyy-MM-dd HH:mm:sssss");
		return sdf.format(new Date());
	}
}
