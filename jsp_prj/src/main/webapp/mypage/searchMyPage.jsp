<%@page import="kr.co.sist.mypage.MyPageService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%
String id=request.getParameter("id");
MyPageService mps=new MyPageService();
%>
<%= mps.searchMyPage(id) %>