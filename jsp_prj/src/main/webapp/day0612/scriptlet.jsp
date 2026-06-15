<%@page import="java.time.LocalDate"%>
<%@page import="java.util.Calendar"%>
<%@page import="jdk.internal.org.jline.terminal.TerminalBuilder.SystemOutput"%>
<%@page import="jdk.internal.misc.FileSystemOption"%>
<%@page import="day0612.TestDTO"%>
<%@page import="day0612.TestService"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Random"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    info="scriptlet의 사용"
    %>
<%
//method 내 정의하는 Java 코드 작성. 변수선언, 연산자사용, 제어문, 객체 생성, 
String name="홍길동"; %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><%= name %>님 안녕하세요?</title>
<link rel="shortcut icon" href="http://192.168.10.69/jsp_prj/common/images/favicon.ico"/>
<!-- Bootstrap CDN -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.min.js" integrity="sha384-G/EV+4j2dNv+tEPo3++6LCgdCROaejBqfUeNjuKAiuXbjrxilcCdDz6ZAVfHWe1Y" crossorigin="anonymous"></script>


<style type="text/css">
#wrap{ width: 1000px; height: 900px; margin: 0px auto;  }
#header{  height: 200px; }
#container{  height: 600px;}
#footer{  height: 100px; }
.age{font-weight:bold;}
.age2{font-weight:bold; color:#FF0000;}

</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

<script type="text/javascript">
$(function(){

});//ready
</script>
</head>
<body>
<div id="wrap">
<div id="header">
</div>
<div id="container">
<%
// _jspService() 안쪽으로 코드가 생성된다.
int age=25; // 지역 변수 => 초기화를 하지 않고 사용하면 error

int total=0;
int j=0;

for(j=0; j<101; j++){
	total=total+j;
	
	%>
<%}

String css="age";
if(new Random().nextBoolean()){
	css="age2";
}
%>
나이는 <span class="<%= css %>"><%= age %></span>살입니다.<br>
1에서 100까지의 총합은 <%= total %>

<table class="table table-dark">
<thead>
<tr>
<th>번호</th>
<th>이름</th>
<th>나이</th>
<th>비고</th>
</tr>
</thead>
<tbody>
<%
TestService ts=new TestService();
List<TestDTO> list=ts.serchMember();

TestDTO tDTO=null;
for(int i=0; i<list.size(); i++){
	tDTO=list.get(i);
%>
<tr>
<td><input type="text" value="<%= i+1 %>" readonly="readonly" style="width:80px"></td>
<td><input type="text" value="<%= tDTO.getName() %>"></td>
<td>
<select>
<% for(int k=1; k < 101; k++){ %>
<option<%= k == tDTO.getAge()?" selected='selected'":"" %>><%= k %></option>
<% } %>
</select>
</td>
<td><button class="btn btn-warning btn-sm">삭제</button></td>
</tr>
<%}

%>
</tbody>
</table>

<!-- 올해를 기준으로 (2026) 이전 2년 이후 2년을 옵션으로 보여주는 select을
	Calendar를 사용하여 만들어보세요.
 -->
<%
LocalDate ld=LocalDate.now();
int nowYear=ld.getYear();
int nowMonth=ld.getMonthValue();
int lastDay=ld.lengthOfMonth();
int nowDay=ld.getDayOfMonth();
%>
<select>
<% for(int i= -2; i<3; i++){ %>
<option <%= (nowYear+i==nowYear) ? "selected='selected'":"" %>><%= nowYear+i %></option>
<%}%>
</select>
<select>
<% for(int i = 1; i<13; i++){ %>
<option <%= i==nowMonth?"selected='selected'":"" %>><%= i %></option>
<%}%>
</select>
<select>
<% for(int i = 1; i<lastDay+1; i++){ %>
<option <%= i==nowDay?"selected='selected'":"" %>><%= i %></option>
<%}%>
</select>

<!-- 0점~10점까지 선택할 수 있는 라디오버튼 11개 생성 -->
<div>
<% for(int i=0; i<11; i++){ %>
	<input type="radio" name="score" <%= i==5?" checked='checked'":"" %>/><%= i %>점&nbsp;<br>
<%}%>

<!-- 아래 이미지가 강아지 또는 google.png, naver.png, daum.png가 보여지도록 -->
<% String[] imgs="default_img2,google,naver,daum".split(","); %>
<img src="../common/images/<%= imgs[new Random().nextInt(imgs.length)] %>.png">
<%
//method안에 method는 중첩 정의할 수 없다. : error
//	public void test(){
//}
%>
</div>
</div>
<div id="footer">
</div>
</div>
</body>
</html>