//import com.cmt.common.MailSender;
//import com.cmt.common.enums.MailContentTypeEnum;
//import java.io.UnsupportedEncodingException;
//import java.util.ArrayList;
//import java.util.List;
//import javax.mail.MessagingException;
//
//public class Test1 {
//
//	public static void main(String[]  args) throws UnsupportedEncodingException, MessagingException {
//		List<String> toMailAddress = new ArrayList<>();
//		toMailAddress.add("qp-peter@ying31.com");
//		//toMailAddress.add("hudson@iv66.net");
//		List<String> imgNames = new ArrayList<>();
//		//imgNames.add("test.jpg");
//		List<String> attachNames = new ArrayList<>();
//		//attachNames.add("Java后台排期.xlsx");
//		new MailSender().title("测试")
//		.content("测试成功了吗？")
//		.contentType(MailContentTypeEnum.HTML.getType())
//		.from("qp-roy@ying31.com")
//				.ticket(">7HGH>rQ")
//		.target(toMailAddress)
//		.imgNames(imgNames)
//		.attachNames(attachNames)
//				.host("imap.gmail.com")
//				.port("993")
//		.attachNames(attachNames).send();
//
//	}
//
//}
