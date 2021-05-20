package login;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import control.Command;
import control.Ctrl;

public class LogoutAction implements Command{
	HttpServletRequest request;
	HttpServletResponse response;
	
	public LogoutAction(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}
	
	@Override
	public int execute() {
		// userID네임의 쿠키 찾아서 무효화
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for(Cookie cookie : cookies) {
				if(cookie.getName().equals("userID")){
					cookie.setMaxAge(0);
					cookie.setPath("/");
					response.addCookie(cookie);
					break;
				}
			}
			request.getSession().invalidate();
			return Ctrl.TRUE;
		}
		return Ctrl.FALSE;
	}
}
