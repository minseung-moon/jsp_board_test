package login;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

public class SessionCheck {
	public static boolean loginCheck(HttpSession session, Cookie[] cookies) {
		if(session.getAttribute("userID") != null){
			return true;
		}else if(cookies != null){
			for(Cookie cookie : cookies) {
				if(cookie.getName().equals("userID")){
					System.out.println(cookie.getPath());
					session.setAttribute("userID", cookie.getValue());
					return true;
				}
			}
		}
		return false;
	}
}
