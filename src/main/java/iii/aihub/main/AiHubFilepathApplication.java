package iii.aihub.main;

public class AiHubFilepathApplication {

	
	public static void main(String[] args) throws Exception{
		org.apache.camel.spring.Main main = new org.apache.camel.spring.Main();
		main.setFileApplicationContextUri("aihub-context.xml");
		main.run();
	}

}
