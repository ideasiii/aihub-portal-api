package iii.aihub.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NetworkUtils {
	
	static Logger logger = LoggerFactory.getLogger("network utils");

	public static void main(String[] args) {
		NetworkUtils nu = new NetworkUtils();
		System.out.println("hostname: "+NetworkUtils.getHostname());
		System.out.println("interface ip address: "+NetworkUtils.getInterfaceIpAddress());

	}

	public static String execCmd(String execCommand) throws IOException {
		InputStream is = Runtime.getRuntime().exec(execCommand).getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		return br.readLine();
	}
	
	public static String getHostname(){
		String osName = SystemUtils.OS_NAME;
		String hostname = "";
		if(osName.startsWith("Win") || osName.startsWith("win")){
			hostname = System.getenv("COMPUTERNAME");
		}else{
			try {
				hostname = execCmd("hostname");
			} catch (IOException e) {
				hostname = "localhost";
				logger.error(e.getMessage());
			}
		}
		return hostname;
	}
	
	public static String getInterfaceIpAddress(String ifname){
		String ip = "";
		NetworkInterface nif = null;
		try {
			nif = NetworkInterface.getByName(ifname);
			Enumeration<InetAddress> addresses =  nif.getInetAddresses();
			if(nif.isUp()){
				ip = addresses.nextElement().getHostAddress();
			}
		} catch (SocketException e) {
			logger.error(e.getLocalizedMessage());
		}
		return ip;
	}
	
	public static String getInterfaceIpAddress(){
		String ip = "127.0.0.1";
		NetworkInterface nif = null;
		List<String> ifNameList = new ArrayList<String>();
		ifNameList.add("eth0");
		ifNameList.add("eth1");
		ifNameList.add("eth2");
		ifNameList.add("eth3");
		for(String ifname : ifNameList){
			try {
				nif = NetworkInterface.getByName(ifname);
				Enumeration<InetAddress> addresses =  nif.getInetAddresses();
				while(addresses.hasMoreElements()){
					InetAddress addr = addresses.nextElement();
					if(!(addr instanceof Inet6Address)){
						ip = addr.getHostAddress();
						continue;
					}
				}
			} catch (Exception e) {
				//logger.error("interface:["+ifname+"] get ip error.");
				//logger.error(e.getLocalizedMessage());
			}
		}
		return ip;
	}
}
