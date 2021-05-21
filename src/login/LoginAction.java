package login;

import java.util.Enumeration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import control.Command;
import control.Ctrl;
import db.UserInfoDAO;

public class LoginAction implements Command {
	HttpServletRequest request;
	HttpServletResponse response;
	public LoginAction(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}
	
	@Override
	public int execute() {
		HttpSession session = request.getSession();
		
		// 이미 로그인 되어 있을 경우
		if(session.getAttribute("userID") != null) {
			return Ctrl.TRUE;
		}
		
		UserInfoDAO dao = new UserInfoDAO(request);
		int result = dao.loginAccept();	
		
		// 로그인 성공 시 세션 및 쿠키 설정
		if(result == Ctrl.TRUE){
			String userID = request.getParameter("userID");
			session.setAttribute("userID", userID);
			
			// 자동로그인 체크됐다면 쿠키 추가
			String isAuto = request.getParameter("isAutoLogin");
			if(isAuto != null && isAuto.equals("true")) {
				Cookie cookie = new Cookie("userID", userID);
				cookie.setMaxAge(60*2);
				cookie.setPath("/");
				response.addCookie(cookie);
			}
			
			return Ctrl.TRUE;
		}else if(result == Ctrl.FALSE) { // 로그인 실패시
			
			session.setAttribute("loginMsg", "로그인에 실패했습니다. 계정을 확인해주세요.");
			return Ctrl.FALSE;
		}else{
			return Ctrl.EXCEPT;
		}
	}
}
