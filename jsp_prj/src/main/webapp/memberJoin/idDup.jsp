<%@page import="kr.co.sist.user.member.MemberService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/siteProperty.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="shortcut icon" href="${CommonURL }/common/images/favicon.ico"/>
<!-- Bootstrap CDN -->
<c:import url="${CommonURL }/fragments/external_file.jsp"/>

<style type="text/css">
#wrap{ width: 470px; height: 370px; margin: 0px auto; }
#idDivFrm{position:relative; width:465; height:366px;
	background:#FFF url(images/id_background.png) no-repeat;}
#inputWrap{position:absolute; top:190px; left:60px;}
#viewResult{ }

</style>
<script type="text/javascript">
$(function(){
	$("#btnChkNull").click(function(){
		chkNull();
	});
	$("#id").keyup(function(evt){
		if(evt.which == 13){
			chkNull();
		}
	});//end if
});//ready

function chkNull(){
	var id = $("#id").val();
	
 	if(id.replace(/ /g,"") == ""){
		alert("아이디는 필수 입력입니다.");
		id=id.replace(/ /g,"");
		$("#id").val(id);
		
		
	return;
	}//end if
		$("#dupFrm").submit();
}//chkNull

function sendId(id){
	opener.window.document.joinForm.id.value = id;
	self.close();
}//sendId
</script>
</head>
<body>
<div id="wrap">
    <div id="idDivFrm">
    <div id="inputWrap">
        <form name="dupFrm" id="dupFrm" action="${CommonURL }/memberJoin/idDup.jsp">
		    <label>아이디</label><input type="text" name="id" id="id" value="${param.id}">
		    <input type="text" style="display: none"/>
		    <input type="button" value="중복확인" class="btn btn-outline-secondary btn-sm" id="btnChkNull"/>
        </form>
    </div>
    <div id="viewResult">
    <%
    String id=request.getParameter("id");
    if(id != null && !"".equals(id)){
    	//중복확인 시작
    	MemberService ms = new MemberService();
    	boolean idFlag = ms.searchDupId(id);
    	pageContext.setAttribute("idFlag", !idFlag);
    	%>
    	입력한 아이디인 <strong style="font-size:25px">
    		<c:out value="${param.id }"/></strong>
    	<c:choose>
    	<c:when test="${idFlag }">
    	<span style="font-size:25px;font-weight:bold;color:#2A5893">
    	사용 가능</span>합니다.<a href="javascript:sendId('${param.id }')">사용</a>
    	</c:when>
    	<c:otherwise>
    	<span style="font-size:25px;font-weight:bold;color:#D94754">이미 사용중 입니다.</span>
    	</c:otherwise>
    	</c:choose>
    	<%
    }//end if
    %>
    </div>
</div>
</div>
</body>
</html>