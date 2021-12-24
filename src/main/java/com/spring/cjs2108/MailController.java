package com.spring.cjs2108;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.cjs2108.vo.MailVO;

@Controller
@RequestMapping("/mail")
public class MailController {
	@Autowired
	JavaMailSender mailSender;
	
	@RequestMapping(value="/mailForm", method = RequestMethod.GET)
	public String mailFormGet() {
		return "mail/mailForm";
	}
	
	@RequestMapping(value="/mailForm", method = RequestMethod.POST)
	public String mailFormPost(MailVO vo) {
		try {
			String toMail = vo.getToMail();
			String title = vo.getTitle();
			String content = vo.getContent();
		
			// 메세지를 변환시켜서 보관함(messageHelper)에 저장하기위한 준비를 한다.
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
			
			// 메일보관함에 회원이 보낸 메세지를 모두 저장시켜준다.
			messageHelper.setTo(toMail);
			messageHelper.setSubject(title);
			messageHelper.setText(content);
			
			// 메세지 보관함의 내용을 편집해서 다시 보관함에 담는다.(전송할 메세지를 편집처리후 content변수에 넣은후 보관함에 담아준다)
			content = content.replace("\n", "<br>");
			content += "<br><hr><h3>길동이가 보냅니다.</h3><hr><br>";
			content += "<p><img src=\"cid:dog.jpg\" width='500px'></p><hr>";
			content += "<p><img src=\"cid:dog2.jpg\" width='500px'></p><hr>";
			content += "<p>Have a Good Time!!!</p>";
			content += "<p>방문하기 : <a href='http://218.236.203.146:9090/cjs2108'>cjs2108</a></p>";
			content += "<hr>";
			messageHelper.setText(content, true);
			
			// 본문의 그림(기타파일)을 함께 보내고자 할때는 저장된 파일의 절대경로를 등록시켜준다.
			FileSystemResource file = new FileSystemResource("D:\\JavaCourse\\SpringFramework\\works\\cjs2108\\src\\main\\webapp\\resources\\images\\dog.jpg");
			FileSystemResource file2 = new FileSystemResource("D:\\JavaCourse\\SpringFramework\\works\\cjs2108\\src\\main\\webapp\\resources\\images\\dog2.jpg");
			messageHelper.addInline("dog.jpg", file);
			messageHelper.addInline("dog2.jpg", file2);
			
			// 메일에 첨부파일과 함께 전송처리
			//FileSystemResource mfile = new FileSystemResource("D:\\JavaCourse\\SpringFramework\\works\\cjs2108\\src\\main\\webapp\\resources\\images\\dog2.jpg");
			//messageHelper.addAttachment("dog2.jpg", mfile);
			messageHelper.addAttachment("dog2.jpg", file2);
			
			mailSender.send(message);	// 보관함에 있는 내용들을 모두 변환하여 message에 담아서 mailSender에 의해서 전송한다.
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		return "redirect:/msg/mailSendOk";
	}

	// 임시 비밀번호 발급해서 메일로 보낼 준비처리
	@RequestMapping(value="/pwdConfirmSend/{toMail}/{content}/", method = RequestMethod.GET)
	public String pwdConfirmPost(@PathVariable String toMail, @PathVariable String content) {
		try {
			String fromMail = "ccbbss1126@gmail.com";
			String title = ">> 임시 비밀번호를 발급하였습니다.";
			String pwd = content;
			content = "cjs2108에서 발송한 메일입니다.\n아래 임시 비밀번호를 보내오니 사이트에 접속하셔서 비밀번호를 변경하세요\n";
			
	  	// 메세지를 변환시켜서 보관함(messageHelper)에 저장하기위한 준비를 한다.
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
		
			// 메일보관함에 회원이 보낸 메세지를 모두 저장시켜준다.
			messageHelper.setFrom(fromMail);
			messageHelper.setTo(toMail);
			messageHelper.setSubject(title);
			
			// 메세지 내용 편집후 보관함에 저장처리한다.
			content = content.replace("\n", "<br>");
			content += "<br><hr><h3>임시비밀번호 : <font color='red'>"+pwd+"</font></h3><hr><br>";
			content += "<p><img src='cid:cjs2108.jpg' width='600px'></p><hr>";
			content += "Have a Good Time!!!";
			content += "<p>접속주소 : <a href='http://218.236.203.146:9090/cjs2108'>cjs2108</a></p><hr>";
			messageHelper.setText(content, true);
			FileSystemResource file = new FileSystemResource("D:\\JavaCourse\\SpringFramework\\works\\cjs2108\\src\\main\\webapp\\resources\\images\\cjs2108.jpg");
			messageHelper.addInline("cjs2108.jpg", file);
			
			mailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return "redirect:/msg/pwdConfirmOk";
	}
	
	// 아이디를 찾아서 메일로 보낼 준비처리(여기서는 사용하지 않았음.....)
	@RequestMapping(value="/idConfirmSend/{toMail}/{content}/", method = RequestMethod.GET)
	public String idConfirmPost(@PathVariable String toMail, @PathVariable String content) {
		try {
			String fromMail = "ccbbss1126@gmail.com";
			String title = ">> 회원님 아이디를 전송하였습니다.";
			String mid = content;
			content = "cjs2108에서 발송한 메일입니다.\n아래 회원님의 아이디를 보내오니 사이트에 접속해보세요\n";
			
			// 메세지를 변환시켜서 보관함(messageHelper)에 저장하기위한 준비를 한다.
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
			
			// 메일보관함에 회원이 보낸 메세지를 모두 저장시켜준다.
			messageHelper.setFrom(fromMail);
			messageHelper.setTo(toMail);
			messageHelper.setSubject(title);
			
			// 메세지 내용 편집후 보관함에 저장처리한다.
			content = content.replace("\n", "<br>");
			content += "<br><hr><h3>회원 아이디 : <font color='red'>"+mid+"</font></h3><hr><br>";
			content += "<p><img src='cid:cjs2108.jpg' width='600px'></p><hr>";
			content += "Have a Good Time!!!";
			content += "<p>접속주소 : <a href='http://218.236.203.146:9090/cjs2108'>cjs2108</a></p><hr>";
			messageHelper.setText(content, true);
			FileSystemResource file = new FileSystemResource("D:\\JavaCourse\\SpringFramework\\works\\cjs2108\\src\\main\\webapp\\resources\\images\\cjs2108.jpg");
			messageHelper.addInline("cjs2108.jpg", file);
			
			mailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return "redirect:/msg/idConfirmOk";
	}
	
}
