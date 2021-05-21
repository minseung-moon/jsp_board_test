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
		
		// �̹� �α��� �Ǿ� ���� ���
		if(session.getAttribute("userID") != null) {
			return Ctrl.TRUE;
		}
		
		UserInfoDAO dao = new UserInfoDAO(request);
		int result = dao.loginAccept();	
		
		// �α��� ���� �� ���� �� ��Ű ����
		if(result == Ctrl.TRUE){
			String userID = request.getParameter("userID");
			session.setAttribute("userID", userID);
			
			// �ڵ��α��� üũ�ƴٸ� ��Ű �߰�
			String isAuto = request.getParameter("isAutoLogin");
			if(isAuto != null && isAuto.equals("true")) {
				Cookie cookie = new Cookie("userID", userID);
				cookie.setMaxAge(60*2);
				cookie.setPath("/");
				response.addCookie(cookie);
			}
			
			return Ctrl.TRUE;
		}else if(result == Ctrl.FALSE) { // �α��� ���н�
			
			session.setAttribute("loginMsg", "�α��ο� �����߽��ϴ�. ������ Ȯ�����ּ���.");
			return Ctrl.FALSE;
		}else{
			return Ctrl.EXCEPT;
		}
	}
}
