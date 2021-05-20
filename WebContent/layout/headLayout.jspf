<%@page import="login.SessionCheck" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
	form {
		display: inline;
	}
</style>
</head>
<body>
	<%
		Cookie[] cookies = request.getCookies();
		boolean check = SessionCheck.loginCheck(session,cookies);
		if(check) {
	%>
	<p>
		<form action="/Board/etc" method="post">
			&nbsp;<input type="submit" value="홈으로가기" />	
		</form>
		<form action="/Board/logout" method="post">
			&nbsp;<input type="submit" value="로그아웃" />	
		</form>
		<form action="/Board/getUserInfo" method="post">
			&nbsp;<input type="submit" value="회원정보보기" /><br><br>
				&nbsp;<%=session.getAttribute("userID") %>님 안녕하세요. 로그인 되셨습니다.
		</form>
	</p>
	<hr/>
	<%
		}else{
	%>
	<p>
		<form action="/Board/etc" method="post">
			&nbsp;<input type="submit" value="홈으로가기" />	
		</form>
		<form action="/Board/login" method="post">
			&nbsp;<input type="submit" value="로그인" />	
		</form>
		<form action="/Board/register" method="post">
			&nbsp;<input type="submit" value="회원가입" /><br><br>
				&nbsp;로그인 또는 회원가입을 해주세요.
		</form>
	</p>
	<hr/>
	<%
		}
	%>
</body>
</html>