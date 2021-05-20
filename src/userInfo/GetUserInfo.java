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
		
		// �̹� ���ǿ� userInfo�� ��� �ִ� ���
		if(session.getAttribute("userInfo") != null){
			return Ctrl.TRUE;
		}else if(session.getAttribute("userID") != null) { // �α��ε� ������ �´��� Ȯ��
			UserInfoDAO dao = new UserInfoDAO(request);
			int result = dao.getUserInfo();
			
			return result;
		}
		
		return Ctrl.EXCEPT;
	}
}
