<%@page import="model.BbsDAO"%>
<%@page import="model.BbsDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%-- 파일명 : WriteProc.jsp --%>
    <!-- 글작성 완료전에 로그인 체크하기 -->
    <%@ include file = "../common/isLogin.jsp" %>
    <!-- /////필수파라미터 -->
    <%@ include file = "../common/isFlag.jsp" %>
    <%
    request.setCharacterEncoding("UTF-8");
    //폼값 전송받기
    String title = request.getParameter("title");
    String content = request.getParameter("content");
    //폼값을 DTO객체에 저장
    BbsDTO dto = new BbsDTO();
    dto.setTitle(title);
    dto.setContent(content);
    //세션 영역에 저장된 회원인증정보를 가져와서 저장
    dto.setId(session.getAttribute("USER_ID").toString());
    
    ///////게시판 필수파라미터를 DTO에 추가
    dto.setBname(bname);
    
    //DAO객체 생성시 application내장객체를 인자로 전달
    BbsDAO dao = new BbsDAO(application);
    //사용자의 입력값을 DTO객체에 저장후 파라미터로 전달
    int affected = dao.insertWrite(dto);
    if(affected==1){
    	//글쓰기 성공했을때
    	response.sendRedirect("BoardList.jsp?bname="+bname);
    }
    else{
    	//글쓰기 실패했을때
    %>
    <script>
    	alert("글쓰기에 실패했습니다.");
    	history.go(-1);
    </script>
    <%
    }
    %>
<%
//out.print("글쓰기처리 : "+ request.getParameter("title"));

%>
