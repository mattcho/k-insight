package seung.java.lib.http;

public enum SHttpMethod {

	GET("GET")
	, POST("POST")
	;
	
	private String method;
	
	SHttpMethod(String method) {
		this.method = method;
	}
	
	public String getMethod() {
		return method;
	}
	
}
