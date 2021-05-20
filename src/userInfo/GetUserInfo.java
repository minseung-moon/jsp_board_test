package userInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import control.Command;
import control.Ctrl;
import db.UserInfoDAO;

public class GetUserInfo implements Command{
	HttpServletRequest request;
	
	public GetUserInfo(HttpServletRequest request) {
		this.request = request;
	}
	
	@Override
	public int execute() {
		HttpSession session = request.getSession();
		
		// 이미 세션에 userInfo가 담겨 있는 경우
		if(session.getAttribute("userInfo") != null){
			return Ctrl.TRUE;
		}else if(session.getAttribute("userID") != null) { // 로그인된 세션이 맞는지 확인
			UserInfoDAO dao = new UserInfoDAO(request);
			int result = dao.getUserInfo();
			
			return result;
		}
		
		return Ctrl.EXCEPT;
	}
}
