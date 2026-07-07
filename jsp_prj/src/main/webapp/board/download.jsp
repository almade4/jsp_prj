<%@page import="java.io.OutputStream"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="application/octet-stream"
	pageEncoding="UTF-8"%>
<%
	String fileName=request.getParameter("file");
	System.out.println(fileName);
//2. 응답 헤더 변경
	response.setHeader("Content-Disposition","attachement;fileName="+
	URLEncoder.encode(fileName,"UTF-8"));
//3. download할 파일에 InputStream 연결
	FileInputStream fis=null;
	OutputStream os=null;
	try{
		fis=new FileInputStream(
				"C:/Users/user/git/jsp_prj/jsp_prj/src/main/webapp/upload/"+fileName);
//4. 응답스트림을 얻기
		os=response.getOutputStream();

		byte[] readData=new byte[512];
		int readSize=0;
		
//5. 읽기스트림에서 파일의 내용을 읽어 들여서, 출력 스트림으로 내보낸다.		
		while((readSize=fis.read(readData)) != -1){
			os.write(readData, 0, readSize);
		}//end while
//6. 출력 스트림을 초기화 : File을 출력하는 스트림으로 변경된 상태
		out.clear();//현재 출력 스트림을 모두 비우고
		out=pageContext.pushBody();//응답 헤더를 초기화
	}finally{
		if(fis != null){fis.close();};
	}//end finally
	
%>