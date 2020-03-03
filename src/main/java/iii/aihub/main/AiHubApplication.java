package iii.aihub.main;

public class AiHubApplication {

	
	public static void main(String[] args) throws Exception{
		org.apache.camel.spring.Main main = new org.apache.camel.spring.Main();
		main.setApplicationContextUri("aihub-context.xml");
		main.run();
	}

}
