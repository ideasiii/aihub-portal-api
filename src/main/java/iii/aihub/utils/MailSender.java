package iii.aihub.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

public class MailSender {

	Logger logger = LoggerFactory.getLogger(getClass());

	final static String charset = "UTF8";
	final static String port = "587";
	
	public static void main(String[] args) throws Exception {
		MailSender msender = new MailSender();
		msender.testSendMail();
	}



	private void _sendMail(List<String> listMailTo, String from, String fromName, String host, String mailUser, String mailPwd,
			String mailTitle, String mailBody) throws NoSuchProviderException, MessagingException {
		logger.info("recipient size: ["+listMailTo.size()+"]");
		InternetAddress[] addressList = new InternetAddress[listMailTo.size()];
		for(int i=0;i<listMailTo.size();i++){
			addressList[i] = new InternetAddress(listMailTo.get(i));
		}
		Properties properties = System.getProperties();
		
		properties.setProperty("mail.smtp.host", host);
		properties.setProperty("mail.smtp.port", port);
		properties.setProperty("mail.user", mailUser);
		properties.setProperty("mail.password", mailPwd);
		properties.put("mail.smtp.auth", "true"); //enable authentication
		properties.put("mail.smtp.starttls.enable", "true");//enable STARTTLS

		Authenticator auth = new Authenticator() {
			//override the getPasswordAuthentication method
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, mailPwd);
			}
		};

		Session session = Session.getDefaultInstance(properties, auth);
		Transport transport = session.getTransport();
		try{
			logger.info("start send mail to "+listMailTo.toString());
			
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(from, fromName));
			msg.setRecipients(Message.RecipientType.TO, addressList);
			msg.setSubject(MimeUtility.encodeText(mailTitle));
			//msg.setSubject(mailTitle, charset);
			msg.setContent(mailBody, "text/html;charset=UTF8");
			
			transport.connect(host, mailUser, mailPwd);
			transport.sendMessage(msg, msg.getAllRecipients());
			logger.info("Email sent!");
		}catch(Exception e){
			e.printStackTrace();
			//throw e;
		}finally{
			transport.close();
		}
	}

	public void sendMail(String mailTo, String mailTitle, String mailBody) throws Exception{
		ArrayList<String> mailToList = new ArrayList<String>();
		mailToList.add(mailTo);
		String mailFrom = "aihub-service@iii.org.tw";
		String fromName = "aihub-service";
		String host = "smtp.office365.com";
		String mailUser = "aihub-service";
		String mailPwd = "Summer@20191101";

		_sendMail(mailToList, mailFrom, fromName, host, mailUser, mailPwd, mailTitle, mailBody);
	}

	public void testSendMail() throws Exception{
		ArrayList<String> mailToList = new ArrayList<String>(); 
		mailToList.add("summerchang@iii.org.tw");
		String from = "aihub-service@iii.org.tw";
		String fromName = "aihub-service";
		String host = "smtp.office365.com";
		String mailUser = "aihub-service";
		String mailPwd = "Summer@20191101";
		String mailTitle = "[AIHub] 你被選重了!";
		String mailBody = "這封信要很多字所以再來一次，這封信要很多字所以再來一次。";
		
		_sendMail(mailToList, from, fromName, host, mailUser, mailPwd, mailTitle, mailBody);
	}
	
	/*
	public void sendXPathExtractFailMail(ScrapePageEntity re, String errorMsg){
		ArrayList<String> mailToList = new ArrayList<String>(); 
		mailToList.add("service@contentparty.org");
		mailToList.add("madoka2000@gmail.com");

		String siteName = re.getSitename();
		String mailTitle = "[Ted1] 網站名稱["+siteName+"] 網站內容擷取發生異常.";
		String from = "service@contentparty.org";
		String fromName = "cpservice";
		String host = "smtp01.smartdove.net";
		String mailUser = "cool3c";
		String mailPwd = "N3r3MnJ5JGUKZfzz";
		
		String mailBody = "<p>XPath extract fail：</p>"
				+ "<ul>"
				+"<li>網站來源名稱:"+ siteName+"< /li>"
				+ "<li>列表網址："+re.getUrl()+"</li>"
				+ "<li>title xpath: "+re.getTitleXpath()+"</li>"
				+ "<li>author xpath: "+re.getAuthorXpath()+"</li>"
				+ "<li>content xpath: "+re.getBodyXpath()+"</li>"
				+ "<li>爬行時間: "+re.getSpentTime()+"</li>"
				+ "<li>ERROR info: "+errorMsg+"</li>"
						+ "</ul>"
				;
		String to = "madoka2000@gmail.com";//service@contentparty.org
		try{
			_sendMail(mailToList, from, fromName, host, mailUser, mailPwd, mailTitle, mailBody);
		}catch(Exception e){
			System.err.println("sned error mail fail.");
		}
		
	}
	*/

}
