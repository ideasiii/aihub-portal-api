package iii.aihub.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import org.apache.commons.lang3.SystemUtils;

public class GetIPAddress {

	public static String execCmd(String execCommand) throws IOException {
		InputStream is = Runtime.getRuntime().exec(execCommand).getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		return br.readLine();
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println("ip address: ["+InetAddress.getLocalHost().getHostAddress()+"]");
		System.out.println("hostname: ["+InetAddress.getLocalHost().getHostName()+"]");
		
		String osName = SystemUtils.OS_NAME;
		//String hostname = "";
		if(osName.startsWith("Win") || osName.startsWith("win")){
			System.out.println("hostname: "+System.getenv("COMPUTERNAME"));
		}else{
			System.out.println("hostname: "+ execCmd("hostname"));
		}
		
		Enumeration<NetworkInterface> nifs = NetworkInterface.getNetworkInterfaces();
		while(nifs.hasMoreElements()){
			NetworkInterface nif =  nifs.nextElement();
			System.out.println("network interface name : "+ nif.getName());
			NetworkInterface ifs = NetworkInterface.getByName(nif.getName());
			Enumeration<InetAddress> addresses =  ifs.getInetAddresses();
			/*
			if(addresses.hasMoreElements()){
				InetAddress ia = addresses.nextElement();
				System.out.println("ip: "+ia.getHostAddress());
				System.out.println("hostname: "+ia.);
			}
			*/
			
			while(addresses.hasMoreElements()){
				InetAddress address = addresses.nextElement();
				System.out.println("hostname: "+address.getHostName());
				System.out.println("address: "+address.getHostAddress());
			}
			
		}
		
		NetworkInterface nif = NetworkInterface.getByName("eth0");
		System.out.println("is up? ["+nif.isUp()+"]");
		Enumeration<InetAddress> addresses =  nif.getInetAddresses();
		while(addresses.hasMoreElements()){
			InetAddress address = addresses.nextElement();
			System.out.println("hostname: "+address.getHostName());
			System.out.println("address: "+address.getHostAddress());
		}
		
	}

}
