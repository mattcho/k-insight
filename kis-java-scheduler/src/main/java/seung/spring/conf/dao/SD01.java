package seung.spring.conf.dao;

import java.util.List;

import seung.java.arg.SMap;

public interface SD01 {

	List<SMap> db01SL(String statement);
	List<SMap> db01SL(String statement, String value);
	List<SMap> db01SL(String statement, SMap sMap);
	
	SMap db01SR(String statement);
	SMap db01SR(String statement, SMap sMap);
	
	int db01IR(String statement, SMap sMap);
	
	int db01UR(String statement, SMap sMap);
	
	int db01UL(String statement, SMap sMap);
	
	int db01DR(String statement, SMap sMap);
	
	int db01DL(String statement, SMap sMap);
}
