<%@page import="kr.co.sist.chipher.DataDecryption"%>
<%@page import="kr.co.sist.chipher.DataEncryption"%>
<%@page import="java.util.Base64"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.security.MessageDigest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include/siteProperty.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String password="1234";

//1. 알고리즘을 설정하여 객체를 얻기
MessageDigest md=MessageDigest.getInstance("SHA-1");
//gdyb21LQTcIANtvYMT7QVQ==
//cRDtpNCeBiql5KOQsKVyrA0sAiA=
//mfsvSMavR2H5BPyF+V61YZDl1AsfROw6nB+jGQ==
//1ARVn2Auq2/WAqx2gNrL+q3RNjAzXpUfCXrzkA6d4Xa22yhRLy4AC50E+6UTPoscbo31nbOoq51gvkuXzJ6B2w==

//2. 평문에 알고리즘을 적용
md.update(password.getBytes());//문자열이 byte[]로 쪼개져서 element당 알고리즘 적용
//3. 알고리즘을 적용 받은 byte[] 얻기
byte[] algorithm=md.digest();
String sha=new String(Base64.getEncoder().encode(algorithm));

%>
plain text : <%= password %> : 일방향 Hash <%= sha %>

<%
	String sha2=DataEncryption.messageDigest("SHA-1", password);
%>
<br>
<%= sha2 %>
<br>
<%
String key="a02345678912345";
DataEncryption de=new DataEncryption(key);
String name="010-1234-5678";
String encryption=de.encrypt(name);

DataDecryption dd=new DataDecryption(key);
%>
원본문자열 <%= name %> , 암호화된 문자열 : <%= de.encrypt(name) %>
<br>
복호화된 문자열 <%= dd.decrypt(encryption) %>