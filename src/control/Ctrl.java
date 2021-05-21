package control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import login.LoginAction;
import login.LogoutAction;
import login.RegisterAction;
import userInfo.GetUserInfo;

@WebServlet("/")
public class Ctrl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// command���� ���Ϲ��� ����
	public static final int TRUE = 0;
	public static final int FALSE = 1;
	public static final int EXCEPT = 2;

	public Ctrl() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		doAction(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	protected void doAction(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		// ������Ʈ �̸� ���� URI�� ���⵵�� �߶��� (������Ʈ/������ - ������Ʈ = /������)
		String uri = request.getRequestURI().substring(
				request.getContextPath().length());

		String page = null; // �����̷�Ʈ�� ������ ���� ���ڿ�
		Command command = null; // ������ Ŀ��� Ŭ������ ����� �������̽� ��ü

		/* ���ο��� �α��� ��ư ������ �� */
		if (uri.equals("/login")) {
			page = "/join/login.jsp";

			/* ���ο��� ȸ������ ��ư ������ �� */
		} else if (uri.equals("/register")) {
			page = "/join/register.jsp";

			/* join/login.jsp���� ���� �ְ� �α��� ��û ���� �� */
		} else if (uri.equals("/join/loginAsk")) {
			command = new LoginAction(request, response);
			int result = command.execute();

			// TRUE�� ��� ���� ȭ������
			if (result == Ctrl.TRUE) {
				page = "/main.jsp";
				// FALSE�� ��� �ٽ� �α��� ��������
			} else if (result == Ctrl.FALSE) {
				page = "/join/login.jsp";
				// EXCEPT�� ��� ���� ��������
			} else {
				page = "/exception/exception.jsp";
			}

			/* join/register.jsp���� ȸ������ �� �ۼ��ϰ� ���� ������ �� */
		} else if (uri.equals("/join/registerAsk")) {
			command = new RegisterAction(request);
			int result = command.execute();

			// TRUE�� ��� �α��� ��������
			if (result == Ctrl.TRUE) {
				page = "/join/login.jsp";
				// FALSE�� ��� ȸ������ �������� (session�� �߰��� �޼��� ���)
			} else if (result == Ctrl.FALSE) {
				page = "/join/register.jsp";
				// EXCEPT�� ��� ���� ��������
			} else {
				page = "/exception/exception.jsp";
			}

			/* ���ο��� �α׾ƿ� ��ư ������ �� ���� �� ��Ű ���� */
		} else if (uri.equals("/logout")) {
			command = new LogoutAction(request, response);
			command.execute();
			page = "/main.jsp";

			/* ���ο��� ȸ��������ȸ ��ư ������ �� */
		} else if (uri.equals("/getUserInfo")) {
			command = new GetUserInfo(request);
			int result = command.execute();

			if (result == Ctrl.TRUE) {
				page = "/userInfo/getUserInfo.jsp";
				// FALSE�� ��� �α��� ��������
			} else if (result == Ctrl.FALSE) {
				page = "/join/login.jsp";
			} else {
				page = "/exception/exception.jsp";
			}

			/* �۾� �� ���� �߻��� ��� */
		} else if (uri.equals("/exception")) {
			page = "/exception/exception.jsp";

			/* �� �� URI�� ��� ���� ùȭ������ ������ */
		} else {
			page = "/main.jsp";
		}

		response.sendRedirect("/Board" + page);
	}
}