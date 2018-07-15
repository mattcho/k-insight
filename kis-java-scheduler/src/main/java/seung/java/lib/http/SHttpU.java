package seung.java.lib.http;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class SHttpU {
	
	private HttpURLConnection   httpURLConnection = null;
	private String              requestUrl;
	private String              requestMethod;
	private ArrayList<String[]> requestHeaders;
	private int                 contentLength;
	private String              requestEncoding;
	private ArrayList<String[]> requestParameters;
	private String              queryString;
	private String              responseEncoding;
	private int                 connectionTimeout;
	private int                 readTimeout;
	private int                 responseCode;
	private String              responseText;
	
	public SHttpU() {
		init();
	}
	
	public SHttpU(HttpURLConnection httpURLConnection) {
		this.httpURLConnection = httpURLConnection;
		init();
	}
	
	public void init() {
		this.requestUrl        = "http://";
		this.requestHeaders    = new ArrayList<String[]>();
		this.requestMethod     = "GET";
		this.requestEncoding   = "UTF-8";
		this.requestParameters = new ArrayList<String[]>();
		this.queryString       = "";
		this.responseEncoding  = "UTF-8";
		this.connectionTimeout = 1000 * 3;
		this.readTimeout       = 1000 * 3;
		this.responseCode      = -1;
	}
	
	public void httpRequest() {
		
		OutputStream outputStream = null;
		BufferedReader bufferedReader = null;
		StringBuilder stringBuilder = new StringBuilder();
		
		try {
			
			setQueryString();
			
			httpURLConnection = (HttpURLConnection) new URL(requestUrl + ("GET".equals(requestMethod) ? "?" + requestParameters : "")).openConnection();
			for(String[] header : requestHeaders) {
				httpURLConnection.setRequestProperty(header[0], header[1]);
			}
			setContentLength(queryString.getBytes(requestEncoding).length);
			httpURLConnection.setRequestProperty("Content-Length", "" + contentLength);
			
			httpURLConnection.setRequestMethod(requestMethod);
			httpURLConnection.setConnectTimeout(connectionTimeout);
			httpURLConnection.setReadTimeout(readTimeout);
			httpURLConnection.setDoInput(true);
			httpURLConnection.setDoOutput(true);
			
			outputStream = new BufferedOutputStream(httpURLConnection.getOutputStream());
			
			outputStream.write(queryString.getBytes(requestEncoding));
			outputStream.flush();
			
			setResponseCode(httpURLConnection.getResponseCode());
			
			bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), responseEncoding));
			
			String line;
			while((line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line);
			}
			
		} catch (IOException e) {
			setResponseCode(9999);
			stringBuilder.append(e.getMessage());
		} finally {
			try {
				if(bufferedReader != null) bufferedReader.close();
				if(outputStream != null) outputStream.close();
				httpURLConnection.disconnect();
			} catch (IOException e) {
				stringBuilder.append(e.getMessage());
			}
		}
		
		setResponseText(stringBuilder.toString());
	}
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[requestUrl] " + requestUrl);
		stringBuilder.append(System.getProperty("line.separator"));
		stringBuilder.append("[requestMethod] " + requestMethod);
		stringBuilder.append(System.getProperty("line.separator"));
		for(String[] header : requestHeaders) {
			stringBuilder.append("[requestHeader] " + header[0] + ": " + header[1]);
			stringBuilder.append(System.getProperty("line.separator"));
		}
		stringBuilder.append("[requestEncoding] " + requestEncoding);
		stringBuilder.append(System.getProperty("line.separator"));
		for(String[] parameter : requestParameters) {
			stringBuilder.append("[requestParameter] " + parameter[0] + ": " + parameter[1]);
			stringBuilder.append(System.getProperty("line.separator"));
		}
		stringBuilder.append("[queryString] " + queryString);
		stringBuilder.append(System.getProperty("line.separator"));
		stringBuilder.append("[responseEncoding] " + responseEncoding);
		stringBuilder.append(System.getProperty("line.separator"));
		stringBuilder.append("[responseCode] " + responseCode);
		stringBuilder.append(System.getProperty("line.separator"));
		stringBuilder.append("[responseText] " + responseText);
		stringBuilder.append(System.getProperty("line.separator"));
		return stringBuilder.toString();
	}
	
	public void setRequestUrl(String requestUrl) {
		this.requestUrl += requestUrl;
	}
	
	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}
	
	public void addRequestHeader(String key, String val) {
		requestHeaders.add(new String[] {key, val});
	}
	
	public void setContentLength(int contentLength) {
		this.contentLength = contentLength;
	}
	
	public String getRequestEncoding() {
		return requestEncoding == null ? "UTF-8" : requestEncoding;
	}
	
	public void setRequestEncoding(String requestEncoding) {
		this.requestEncoding = requestEncoding;
	}
	
	public void addRequestParameter(String key, String val) {
		requestParameters.add(new String[] {key, val});
	}
	
	public void setQueryString() throws UnsupportedEncodingException {
		StringBuilder stringBuilder = new StringBuilder();
		for(String[] parameter : requestParameters) {
			stringBuilder.append("&");
			stringBuilder.append(String.format("%s=%s", URLEncoder.encode(parameter[0], getRequestEncoding()), URLEncoder.encode(parameter[1], getRequestEncoding())));
		}
		this.queryString = stringBuilder.length() > 0 ? stringBuilder.toString().substring(1) : "";
	}
	
	public String getQueryString() {
		return queryString;
	}
	
	public void setResponseEncoding(String responseEncoding) {
		this.responseEncoding = responseEncoding;
	}
	
	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}
	
	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}
	
	public int getResponseCode() {
		return responseCode;
	}
	
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
	
	public String getResponseText() {
		return responseText;
	}
	
	public void setResponseText(String responseText) {
		this.responseText = responseText;
	}
	
	public void disconnect() {
		httpURLConnection.disconnect();
	}
	
}
