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
		
		// 비밀번호 확인이 일치하지 않을 경우
		if(!dto.getUserPW().equals(dto.getUserPW2())){
			session.setAttribute("forMsg", "비밀번호를 확인해주세요.");
			return Ctrl.FALSE;
		}else{
			UserInfoDAO dao = new UserInfoDAO(request);
			int result = dao.registerUserInfo(dto);
			
			// 성공
			if(result == Ctrl.TRUE) {
				session.setAttribute("loginMsg", "회원가입에 성공했습니다. 로그인해주세요");
				return Ctrl.TRUE;
			}else if(result == Ctrl.FALSE){ // 실패
				// 로그인 화면해서 출력해줄 메세지 추가
				session.setAttribute("formMsg", "이미 존재하는 ID 입니다.");
				return Ctrl.FALSE;
			} else { // 예외
				return Ctrl.EXCEPT;
			}
		}
	}
}
