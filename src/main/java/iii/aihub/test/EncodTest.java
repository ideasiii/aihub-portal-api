package iii.aihub.test;

import iii.aihub.helper.MemberHelper;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Duration;

public class EncodTest {

	public static void main(String[] args) throws Exception {
		MemberHelper memberHelper = new MemberHelper();
		String encryptString = "ZQndvZ/65Iy09KG8pCZ6C8Zu0B/+ouQskE3PtxSIHQM5NIB+NFwGNN93BGDXZoTo";
		String decryptString = memberHelper.decrypt(encryptString);
		System.out.println("decrypt string: "+decryptString);
		String[] pair = StringUtils.split(decryptString, ';');
		String ts = pair[0];
		String memberId = pair[1];
		System.out.println("member id: "+memberId);

		Long beforeLong = new Long(ts);
		DateTime dateTime = new DateTime(beforeLong);
		System.out.println("date time: "+dateTime);
		Duration duration = new Duration(dateTime, DateTime.now());
		System.out.println("duration: "+ duration.getStandardMinutes() +" minutes");
	}

	private static void test1() throws Exception {
		MemberHelper memberHelper = new MemberHelper();
		String nowString = String.valueOf(DateTime.now().getMillis());
		String ts = memberHelper.encrypt(nowString);
		System.out.println("encrypt ts: "+ts);
		String nowString2 = memberHelper.decrypt(ts);
		System.out.println("decrypt ts: "+nowString2);
		Long nowLong = new Long(nowString2);
		DateTime dateTime = new DateTime(nowLong);
		System.out.println("date time: "+dateTime);
		Duration duration = new Duration(dateTime, DateTime.now());
		System.out.println("duration: "+ duration.getStandardMinutes());
	}

}
