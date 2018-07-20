package seung.spring.conf.dao;

import java.util.List;

import seung.java.arg.SMap;

public interface SD02 {

	List<SMap> db02SL(String statement);
	List<SMap> db02SL(String statement, String value);
	List<SMap> db02SL(String statement, SMap sMap);
	
	SMap db02SR(String statement);
	SMap db02SR(String statement, SMap sMap);
	
	int db02IR(String statement, SMap sMap);
	
	int db02UR(String statement, SMap sMap);
	
	int db02UL(String statement, SMap sMap);
	
	int db02DR(String statement, SMap sMap);
	
	int db02DL(String statement, SMap sMap);
}