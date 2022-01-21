package com.spring.cjs2108;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MessageController {
	
	@RequestMapping(value="/msg/{msgFlag}", method = RequestMethod.GET)
	public String msgGet(@PathVariable String msgFlag, Model model, HttpSession session) {
		String nickName = session.getAttribute("sNickName")==null ? "" : (String) session.getAttribute("sNickName");
		String strLevel = session.getAttribute("sStrLevel")==null ? "" : (String) session.getAttribute("sStrLevel");
		
		if(msgFlag.equals("guestInputOk")) {
			model.addAttribute("msg", "방명록에 글이 등록되었습니다.");
			model.addAttribute("url", "guest/guestList");
		}
		else if(msgFlag.equals("memLoginOk")) {
			model.addAttribute("msg", nickName+"님("+strLevel+") 로그인 되었습니다.");
			model.addAttribute("url", "member/memMain");
		}
		else if(msgFlag.equals("memLoginNo")) {
			model.addAttribute("msg", "로그인 실패~~~");
			model.addAttribute("url", "member/memLogin");
		}
		else if(msgFlag.equals("memLogout")) {
			session.invalidate();
			model.addAttribute("msg", nickName + "님 로그아웃 되었습니다.");
			model.addAttribute("url", "member/memLogin");
		}
		else if(msgFlag.equals("memberNo")) {
			model.addAttribute("msg", nickName + "님 사용불가입니다.\\n 세션이 끈어졌거나 등급확인후 사용하세요.");
			model.addAttribute("url", "/");
		}
		else if(msgFlag.equals("fileUploadOk")) {
			model.addAttribute("msg", "파일이 업로드 되었습니다.");
			model.addAttribute("url", "study/fileUpload");
		}
		else if(msgFlag.equals("fileUploadNo")) {
			model.addAttribute("msg", "파일 업로드 실패~~~");
			model.addAttribute("url", "study/fileUpload");
		}
		else if(msgFlag.equals("memIdCheckNo")) {
			model.addAttribute("msg", "아이디가 중복되었습니다.");
			model.addAttribute("url", "member/memInput");
		}
		else if(msgFlag.equals("memNickNameCheckNo")) {
			model.addAttribute("msg", "닉네임이 중복되었습니다.");
			model.addAttribute("url", "member/memInput");
		}
		else if(msgFlag.equals("memInputOk")) {
			model.addAttribute("msg", "회원 가입되었습니다.");
			model.addAttribute("url", "member/memLogin");
		}
		else if(msgFlag.equals("memInputNo")) {
			model.addAttribute("msg", "회원 가입실패~~~");
			model.addAttribute("url", "member/memInput");
		}
		else if(msgFlag.equals("pwdCheckNo")) {
			model.addAttribute("msg", "비밀번호를 확인하세요.");
			model.addAttribute("url", "member/memPwdCheck");
		}
		else if(msgFlag.equals("memUpdateOk")) {
			model.addAttribute("msg", "회원정보가 수정되었습니다.");
			model.addAttribute("url", "member/memUpdate");
		}
		else if(msgFlag.equals("memUpdateNo")) {
			model.addAttribute("msg", "회원정보가 수정 실패~~~");
			model.addAttribute("url", "member/memUpdate");
		}
		else if(msgFlag.equals("memDeleteOk")) {
			session.invalidate();
			model.addAttribute("msg", nickName + "회원님 탈퇴 되셨습니다.\\n1개월간 같은 아이디로 가입불가입니다.");
			model.addAttribute("url", "/");
		}
		else if(msgFlag.equals("mailSendOk")) {
			model.addAttribute("msg", "메일이 전송되었습니다.");
			model.addAttribute("url", "mail/mailForm");
		}
		else if(msgFlag.equals("pwdConfirmOk")) {
			model.addAttribute("msg", "임시비밀번호가 메일로 전송되었습니다.\\n메일을 확인하세요.");
			model.addAttribute("url", "member/memLogin");
		}
		else if(msgFlag.equals("pwdConfirmNo")) {
			model.addAttribute("msg", "사용자 정보를 확인하세요.");
			model.addAttribute("url", "member/memLogin");
		}
		else if(msgFlag.equals("idConfirmOk")) {
			model.addAttribute("msg", "아이디를 메일로 전송하였습니다.\\n메일을 확인하세요.");
			model.addAttribute("url", "member/memLogin");
		}
		else if(msgFlag.equals("boardInputOk")) {
			model.addAttribute("msg", "게시글이 저장되었습니다.");
			model.addAttribute("url", "board/boardList");
		}
		else if(msgFlag.equals("pdsInputOk")) {
			model.addAttribute("msg", "자료파일이 업로드 되었습니다.");
			model.addAttribute("url", "pds/pdsList");
		}
		else if(msgFlag.equals("dbProductInputOk")) {
			model.addAttribute("msg", "상품이 등록 되었습니다.");
			model.addAttribute("url", "dbShop/dbProduct");
		}
		else if(msgFlag.equals("dbOptionInputOk")) {
			model.addAttribute("msg", "옵션이 등록되었습니다.");
			model.addAttribute("url", "dbShop/dbOption");
		}
		else if(msgFlag.equals("orderInputOk")) {
			model.addAttribute("msg", "주문이 완료되었습니다.");
			model.addAttribute("url", "dbShop/dbOrderConfirm");
		}
		else if(msgFlag.equals("sessionProductNo")) {
			model.addAttribute("msg", "장바구니가 비어있습니다.");
			model.addAttribute("url", "sessionShop/shopList");
		}
		else if(msgFlag.equals("mainImageInputOk")) {
			model.addAttribute("msg", "메인이미지가 업로드 되었습니다.");
			model.addAttribute("url", "study/mainImageList");
		}
		else if(msgFlag.equals("thumbnailCreateNo")) {
			model.addAttribute("msg", "썸네일 이미지 업로드 실패~~~");
			model.addAttribute("url", "study2/thumbnail");
		}
		else if(msgFlag.equals("photoInputOk")) {
			model.addAttribute("msg", "포토갤러리에 사진이 등록 되었습니다.");
			model.addAttribute("url", "photo/photo");
		}
		else if(msgFlag.equals("photoDeleteOk")) {
			model.addAttribute("msg", "포토갤러리에 선택하신 내역이 삭제 되었습니다.");
			model.addAttribute("url", "photo/photo");
		}
		
		
		
		
		
		
		
		else if(msgFlag.substring(0,13).equals("boardUpdateOk")) {
			model.addAttribute("msg", "게시물의 정보가 변경되었습니다.");
			model.addAttribute("url", "board/boardContent?"+msgFlag.substring(14));
		}
		else if(msgFlag.substring(0,13).equals("boardDeleteOk")) {
			model.addAttribute("msg", "게시물의 정보가 삭제되었습니다.");
			model.addAttribute("url", "board/boardList?"+msgFlag.substring(14));
		}
		else if(msgFlag.substring(0, 11).equals("imgDeleteOk")) {
			model.addAttribute("msg", "임시 그림파일("+msgFlag.substring(12)+"개)이 모두 삭제되었습니다.");
			model.addAttribute("url", "admin/imsiFileDelete");
		}
		else if(msgFlag.substring(0, 17).equals("thumbnailCreateOk")) {
			model.addAttribute("msg", "썸네일 이미지가 성공적으로 업로드 되었습니다.");
			model.addAttribute("url", "study2/thumbnailView?"+msgFlag.substring(18));
		}
		
		
		
		return "include/message";
	}
	
}
