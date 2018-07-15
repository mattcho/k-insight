package seung.java.lib.ex;

import seung.java.arg.SMap;
import seung.java.lib.ex.dart.SExDartU;

public class SExU {

	public SMap extract(SMap sMap) {
		
		SMap resultMap = null;
		
		switch(sMap.getString("orgCd")) {
			case "dart":
				resultMap = new SExDartU().extract(sMap);
				break;
			default:
				break;
		}
		
		return resultMap;
	}
	
}
