package iii.aihub.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class NetworkUtils {
	
	public static void main(String[] args) {
		System.out.println("Hello world");
		/*
		NetworkUtils nu = new NetworkUtils();
		System.out.println("hostname: "+nu.getHostname());
		System.out.println("interface ip address: "+nu.getInterfaceIpAddress());
		*/
	}

	public String execCmd(String execCommand) throws IOException {
		InputStream is = Runtime.getRuntime().exec(execCommand).getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		return br.readLine();
	}
	
	public String getHostname(){
		String osName = System.getProperty("os.name");
		String hostname = "";
		if(osName.startsWith("Win") || osName.startsWith("win")){
			hostname = System.getenv("COMPUTERNAME");
		}else{
			try {
				hostname = execCmd("hostname");
			} catch (IOException e) {
				hostname = "localhost";
				e.printStackTrace();
			}
		}
		return hostname;
	}
	
	public String getInterfaceIpAddress(String ifname){
		String ip = "";
		NetworkInterface nif = null;
		try {
			nif = NetworkInterface.getByName(ifname);
			Enumeration<InetAddress> addresses =  nif.getInetAddresses();
			if(nif.isUp()){
				ip = addresses.nextElement().getHostAddress();
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return ip;
	}
	
	public String getInterfaceIpAddress(){
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
				if(nif.isUp()){
					ip = addresses.nextElement().getHostAddress();
					continue;
				}
			} catch (Exception e) {
				System.err.println("interface:["+ifname+"] get ip error.");
				System.err.println(e.getLocalizedMessage());
			}
		}
		return ip;
	}
}
