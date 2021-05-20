package control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import userInfo.GetUserInfo;
import login.LoginAction;
import login.LogoutAction;
import login.RegisterAction;

@WebServlet("/")
public class Ctrl  extends HttpServlet{
	private static final long serialVersionUID = 1L;

	// command에서 리턴받을 종류
	public static final int TRUE = 0;
	public static final int FALSE = 1;
	public static final int EXCEPT = 2;
	
	public Ctrl() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request,response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request,response);
	}
	
	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		// 프로젝트 이름 뒤의 URI만 남기도록 잘라줌(프로젝트/페이지 - 프로젝트 = /페이지)
		String uri = request.getRequestURI().substring(request.getContextPath().length());
		
		// 리다이렉트할 페이지 넣읆 문자열
		String page = null;
		// 실행한 커맨드 클래스를 담아줄 인터페이스 객체
		Command command = null;
		
		if(uri.equals("/login")){// 메인에서 로그인 버튼 눌렀을 때
			page = "/join/login.jsp";
		}else if(uri.equals("/register")){// 메인에서 회원가입 버튼 눌렀을 때
			page = "/join/register.jsp";
		}else if(uri.equals("/join/loginAsk")){// login/login.jsp에서 계정넣고 로그인 요청 했을 때
			command = new LoginAction(request, response);
			int result = command.execute();
			
			if(result == Ctrl.TRUE){
				page = "/main.jsp";
			}else if(result == Ctrl.FALSE){
				page = "/join/login.jsp";
			}else{
				page = "/exception/exception.jsp";
			}
		}else if(uri.equals("/join/registerAsk")) { // join/register.jsp에서 회원 가입 폼 작성하고 가입 눌렀을 때
			command = new RegisterAction(request);
			int result = command.execute();
			
			if(result == Ctrl.TRUE){
				page = "/join/login.jsp";
			}else if(result == Ctrl.FALSE){
				page = "/join/register.jsp";
			}else{
				page = "/exception/exception.jsp";
			}
		}else if(uri.equals("/logout")) { // 메인에서 로그아웃 버튼  눌렀을 때 세션 및 쿠키 제거
			command = new LogoutAction(request, response);
			command.execute();
			page = "/main.jsp";
		}else if(uri.equals("/getUserInfo")) { // 메인에서 회원벙조 조회버튼 눌렀을 때
			command = new GetUserInfo(request);
			int result = command.execute();
			
			if(result == Ctrl.TRUE){
				page = "/userInfo/getUserInfo.jsp";
			}else if(result == Ctrl.FALSE){
				page = "/join/login.jsp";
			}else{
				page = "/exception/exception.jsp";
			}
		}else if(uri.equals("/exception")){ // 작업 시 예외 발생할 경우
			page = "/exception/exception.jsp";
		}else{ // 그 외 URI일 경우 메인 첫 회면으로 돌려줌
			page = "/main.jsp";
		}
		response.sendRedirect("/Board"+page);
		
	}
}
