package seung.spring.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import seung.java.arg.SMap;

@Component("sCommonU")
public class SCommonU {

	private static final Logger logger = LoggerFactory.getLogger(SCommonU.class);
	
	public String getUUID() {
		return UUID.randomUUID().toString();
	}
	public String getDateString(String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern.length() > 0 ? pattern : "yyyy-MM-dd HH:mm:sssss");
		return sdf.format(new Date());
	}
	public int getDateInteger(String pattern) {
		return Integer.parseInt(getDateString(pattern).replaceAll("[^0-9]", ""));
	}
	
	public SMap getSystemInfo() {
		
		SMap systemInfo = new SMap();
		
		try {
			
			InetAddress inetAddress = InetAddress.getLocalHost();
			
			Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
			NetworkInterface networkInterface = null;
			byte[] macBytes = null;
			StringBuilder stringBuilder = new StringBuilder();
			while(networkInterfaces.hasMoreElements()) {
				networkInterface = networkInterfaces.nextElement();
				macBytes = networkInterface.getHardwareAddress();
				if(macBytes != null) {
					stringBuilder.append(",");
					for(int i = 0; i < macBytes.length; i++) {
						stringBuilder.append(String.format("%02X%s", macBytes[i], (i < macBytes.length - 1) ? "-" : ""));
					}
				}
			}
			
			systemInfo.put("osName", System.getProperty("os.name"));
			systemInfo.put("hostName", inetAddress.getHostName());
			systemInfo.put("ip", inetAddress.getHostAddress());
			systemInfo.put("mac", stringBuilder.length() > 0 ? stringBuilder.toString().substring(1) : "");
			
		} catch (UnknownHostException e) {
			logger.error(e.getMessage());
		} catch (SocketException e) {
			logger.error(e.getMessage());
		}
		
		return systemInfo;
	}
	
	/**
	 * @desc get mac address
	 */
	@SuppressWarnings("unused")
	private String getMacAddress(InetAddress inetAddress) {
		
		StringBuilder stringBuilder = new StringBuilder();
		
		try {
			
			NetworkInterface networkInterface = NetworkInterface.getByInetAddress(inetAddress);
			byte[] macBytes = networkInterface.getHardwareAddress();
			
			for(int i = 0; i < macBytes.length; i++) {
				stringBuilder.append(String.format("%02X%s", macBytes[i], (i < macBytes.length - 1) ? "-" : ""));
			}
			
		} catch (SocketException e) {
			logger.error(e.getMessage());
		}
		
		return stringBuilder.toString();
	}
	
	/**
	 * @desc check is vm or not
	 */
	@SuppressWarnings("unused")
	private boolean isVMMac(byte[] mac) {
		
		if(null == mac) return false;
		
		byte invalidMacs[][] = {
			//VMWare
			{0x00, 0x05, 0x69}
			, {0x00, 0x1C, 0x14}
			, {0x00, 0x0C, 0x29}
			, {0x00, 0x50, 0x56}
			//Virtualbox
			, {0x08, 0x00, 0x27}
			, {0x0A, 0x00, 0x27}
			//Virtual-PC
			, {0x00, 0x03, (byte)0xFF}
			//Hyper-V
			, {0x00, 0x15, 0x5D}
		};
		
		for(byte[] invalid: invalidMacs){
			if(invalid[0] == mac[0] && invalid[1] == mac[1] && invalid[2] == mac[2]) return true;
		}
		
		return false;
	}
}
