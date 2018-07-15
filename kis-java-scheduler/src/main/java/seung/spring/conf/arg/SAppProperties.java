package seung.spring.conf.arg;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author seung
 * @desc custom application properties
 */
@Component("sAppProperties")
@ConfigurationProperties(prefix="seung",ignoreUnknownFields=true)
public class SAppProperties {

	private String appName;
	private String appVersion;
	
	private String driverClassNameDs01;
	private String urlDs01;
	private String userNameDs01;
	private String passwordDs01;
	
	private String driverClassNameDs02;
	private String urlDs02;
	private String userNameDs02;
	private String passwordDs02;
	
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getAppVersion() {
		return appVersion;
	}
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	public String getDriverClassNameDs01() {
		return driverClassNameDs01;
	}
	public void setDriverClassNameDs01(String driverClassNameDs01) {
		this.driverClassNameDs01 = driverClassNameDs01;
	}
	public String getUrlDs01() {
		return urlDs01;
	}
	public void setUrlDs01(String urlDs01) {
		this.urlDs01 = urlDs01;
	}
	public String getUserNameDs01() {
		return userNameDs01;
	}
	public void setUserNameDs01(String userNameDs01) {
		this.userNameDs01 = userNameDs01;
	}
	public String getPasswordDs01() {
		return passwordDs01;
	}
	public void setPasswordDs01(String passwordDs01) {
		this.passwordDs01 = passwordDs01;
	}
	public String getDriverClassNameDs02() {
		return driverClassNameDs02;
	}
	public void setDriverClassNameDs02(String driverClassNameDs02) {
		this.driverClassNameDs02 = driverClassNameDs02;
	}
	public String getUrlDs02() {
		return urlDs02;
	}
	public void setUrlDs02(String urlDs02) {
		this.urlDs02 = urlDs02;
	}
	public String getUserNameDs02() {
		return userNameDs02;
	}
	public void setUserNameDs02(String userNameDs02) {
		this.userNameDs02 = userNameDs02;
	}
	public String getPasswordDs02() {
		return passwordDs02;
	}
	public void setPasswordDs02(String passwordDs02) {
		this.passwordDs02 = passwordDs02;
	}
}
