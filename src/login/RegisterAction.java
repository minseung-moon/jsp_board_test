package login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import control.Command;
import control.Ctrl;
import db.UserInfoDAO;
import db.UserInfoDTO;

public class RegisterAction implements Command{
	HttpServletRequest request;
	UserInfoDTO dto;
	
	public RegisterAction(HttpServletRequest request) {
		this.request = request;
		
		dto = new UserInfoDTO();
		dto.setUserID(request.getParameter("userID"));
		dto.setUserPW(request.getParameter("userPW"));
		dto.setUserPW2(request.getParameter("userPW2"));
		dto.setUserName(request.getParameter("userName"));
		dto.setUserEmail(request.getParameter("userEmail"));
		dto.setUserPhone(request.getParameter("userPhone"));
		dto.setUserGender(request.getParameter("userGender"));
	}
	
	@Override
	public int execute() {
		HttpSession session = request.getSession();
		
		// ��й�ȣ Ȯ���� ��ġ���� ���� ���
		if(!dto.getUserPW().equals(dto.getUserPW2())){
			session.setAttribute("forMsg", "��й�ȣ�� Ȯ�����ּ���.");
			return Ctrl.FALSE;
		}else{
			UserInfoDAO dao = new UserInfoDAO(request);
			int result = dao.registerUserInfo(dto);
			
			// ����
			if(result == Ctrl.TRUE) {
				session.setAttribute("loginMsg", "ȸ�����Կ� �����߽��ϴ�. �α������ּ���");
				return Ctrl.TRUE;
			}else if(result == Ctrl.FALSE){ // ����
				// �α��� ȭ���ؼ� ������� �޼��� �߰�
				session.setAttribute("formMsg", "�̹� �����ϴ� ID �Դϴ�.");
				return Ctrl.FALSE;
			} else { // ����
				return Ctrl.EXCEPT;
			}
		}
	}
}
