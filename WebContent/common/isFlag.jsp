<%@page import="util.JavascriptUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%-- 파일명 : isFlag.jsp --%>
<%
request.setCharacterEncoding("UTF-8");
//////멀티게시판 구현을 위한 파라미터 처리
String bname = request.getParameter("bname");
if(bname==null || bname.equals("")){
	JavascriptUtil.jsAlertLocation("필수파라미터 누락됨", "../main/main.do", out);
	return;
}

String boardTitle = "";
switch(bname){
case "freeboard" : 
	boardTitle = "자유게시판";
	break;
case "notice" : 
	boardTitle = "공지사항";
	break;
case "qna" : 
	boardTitle = "질문과답변";
	break;
case "faq" : 
	boardTitle = "자주묻는질문";
	break;
}
%>