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

	// command���� ���Ϲ��� ����
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
		// ������Ʈ �̸� ���� URI�� ���⵵�� �߶���(������Ʈ/������ - ������Ʈ = /������)
		String uri = request.getRequestURI().substring(request.getContextPath().length());
		
		// �����̷�Ʈ�� ������ �֟� ���ڿ�
		String page = null;
		// ������ Ŀ�ǵ� Ŭ������ ����� �������̽� ��ü
		Command command = null;
		
		if(uri.equals("/login")){// ���ο��� �α��� ��ư ������ ��
			page = "/join/login.jsp";
		}else if(uri.equals("/register")){// ���ο��� ȸ������ ��ư ������ ��
			page = "/join/register.jsp";
		}else if(uri.equals("/join/loginAsk")){// login/login.jsp���� �����ְ� �α��� ��û ���� ��
			command = new LoginAction(request, response);
			int result = command.execute();
			
			if(result == Ctrl.TRUE){
				page = "/main.jsp";
			}else if(result == Ctrl.FALSE){
				page = "/join/login.jsp";
			}else{
				page = "/exception/exception.jsp";
			}
		}else if(uri.equals("/join/registerAsk")) { // join/register.jsp���� ȸ�� ���� �� �ۼ��ϰ� ���� ������ ��
			command = new RegisterAction(request);
			int result = command.execute();
			
			if(result == Ctrl.TRUE){
				page = "/join/login.jsp";
			}else if(result == Ctrl.FALSE){
				page = "/join/register.jsp";
			}else{
				page = "/exception/exception.jsp";
			}
		}else if(uri.equals("/logout")) { // ���ο��� �α׾ƿ� ��ư  ������ �� ���� �� ��Ű ����
			command = new LogoutAction(request, response);
			command.execute();
			page = "/main.jsp";
		}else if(uri.equals("/getUserInfo")) { // ���ο��� ȸ������ ��ȸ��ư ������ ��
			command = new GetUserInfo(request);
			int result = command.execute();
			
			if(result == Ctrl.TRUE){
				page = "/userInfo/getUserInfo.jsp";
			}else if(result == Ctrl.FALSE){
				page = "/join/login.jsp";
			}else{
				page = "/exception/exception.jsp";
			}
		}else if(uri.equals("/exception")){ // �۾� �� ���� �߻��� ���
			page = "/exception/exception.jsp";
		}else{ // �� �� URI�� ��� ���� ù ȸ������ ������
			page = "/main.jsp";
		}
		response.sendRedirect("/Board"+page);
		
	}
}
