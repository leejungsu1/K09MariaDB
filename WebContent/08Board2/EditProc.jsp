<%@page import="model.BbsDAO"%>
<%@page import="model.BbsDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 파일명 : EditProc.jsp -->
	<!-- ////////////필수파라미터체크 -->
	<!-- 해당파일내에서 bname에 대한 폼값을 받고 있음 -->
<%@include file="../common/isFlag.jsp" %>

<%@include file="../common/isLogin.jsp"%>
<%
request.setCharacterEncoding("UTF-8");
//폼값받기
/////////필수파라미터를 위에 include로 받고 있으므로 또 받을 필요가 없다.
//String bname = request.getParameter("bname"); 

String num = request.getParameter("num"); 
String title = request.getParameter("title"); 
String content = request.getParameter("content");

BbsDTO dto = new BbsDTO();
dto.setNum(num);
dto.setTitle(title);
dto.setContent(content);
//DTO객체를 DAO로 전달하여 게시물 업데이트(수정)
BbsDAO dao = new BbsDAO(application);
int affected = dao.updateEdit(dto);
if(affected==1){
	//정상적으로 수정되었다면 수정된 내용의 확인을 위해 상세보기로 이동
	response.sendRedirect("BoardView.jsp?bname="+bname+"&num="+dto.getNum());
}
else{
	//수정중 문제가 발생하였다면 수정하기 페이지로 돌아간다.
%>
	<script>
		alert("수정실패");
		history.go(-1);
	</script>
<%
}
%>
