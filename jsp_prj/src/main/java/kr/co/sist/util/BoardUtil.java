package kr.co.sist.util;

import java.io.File;
import java.util.UUID;

import org.apache.catalina.tribes.util.UUIDGenerator;

public class BoardUtil {
	
	private BoardUtil() {
	}
	
	/**
	 * pagination을 제작하는 일
	 * @param currentPage 현재페이지
	 * @param totalPage 총 페이지 수
	 * @param url 이동할 URL
	 * @param fieldNum 검색 필드명 0-title, 1-content, 2-id
	 * @param keyword 검색 키워드
	 * @return
	 */
	public static  String pagination(int currentPage,int totalPage,String url,
			String fieldNum, String keyword) {
		//1. 한 화면에 보여줄 pagination 수
		int pageNumber=3;
		//2. 화면에 보여줄 시작 번호([1][2][3] =>1, [4][5][6] =>4, [7][8][9] =>7,)
		int startPage=((currentPage-1)/pageNumber)*pageNumber+1;
		//3. 화면에 보여줄 마지막 번호( 1->3, 4->6, 7->9)
		int endPage= (((startPage-1)+pageNumber)/pageNumber)*pageNumber;
		//4. 총페이지 수가 연산된 마지막 페이지 수 보다 작다면, 총페이지수가 마지막 페이지 수로 설정되어야한
		if(totalPage <= endPage){
			endPage=totalPage;				
		}//end if
		
		StringBuilder pagination=new StringBuilder();
		pagination.append("<ul class='pagination' style='width:250px;margin: 0px auto'>");
		//5.이전 페이지로 가기위한 [<<] 기호
		StringBuilder prevMark=new StringBuilder();
		prevMark.append("<li class='page-item page-link'>Previous</li>");
		int movePage=0;
		if( currentPage > pageNumber ){//4 >3 
			movePage=startPage-1; //3->1, 4,5,6->3->1,  7,8,9->6->4
			prevMark.delete(0, prevMark.length());//[ &lt;&lt; ] 삭제
			prevMark.append("<li class='page-item'><a class='page-link' href='")
			.append(url)
			.append("?currentPage=").append(movePage);
			if(keyword != null && !keyword.isEmpty()) {
				prevMark.append("&fieldNum=").append(fieldNum)
				.append("&keyword=").append(keyword);
			}//end if
			prevMark.append("'>Previous</a></li>");
		}//end if
		
		pagination.append(prevMark.toString());
		
		//6.시작 페이지 번호부터 끝 페이지 번호까지 화면에 출력하기
		movePage=startPage;
		StringBuilder pageLink=new StringBuilder();
		
		while( movePage <= endPage){
			if( movePage == currentPage ){//현재 페이지는 링크를 설정하지 않는다.
				pageLink.append("<li class='page-item'><span class='page-link active'>").append(movePage)
				.append("</span>");
			}else{
				pageLink.append("<li class='page-item'><a class='page-link' href='")
				.append(url).append("?currentPage=").append(movePage);
				
				if(keyword != null && !keyword.isEmpty()) {
					prevMark.append("&fieldNum=").append(fieldNum)
					.append("&keyword=").append(keyword);
				}//end if
				
				pageLink.append("'>").append(movePage).append("</a></li>");		
				
			}//end else
			
			movePage++;
		}//end while
			
		
		if(pageLink.toString().isEmpty()){
			pageLink.append("<li class='page-item'><span class='page-link'>1<span></li>");
		}//end if
		
		pagination.append(pageLink.toString());
		
		//7. 뒤에 페이지가 더 있는 경우
		StringBuilder nextMark=new StringBuilder("<li class='page-item page-link'>Next</li>");
		
		if(totalPage > endPage){
			movePage=endPage+1;
			nextMark.delete(0, nextMark.length());
			nextMark.append("<li class='page-item'><a class='page-link' href='")
			.append(url)
			.append("?currentPage=").append(movePage);
			
			if(keyword != null && !keyword.isEmpty()) {
				prevMark.append("&fieldNum=").append(fieldNum)
				.append("&keyword=").append(keyword);
			}//end if
			
			nextMark.append("'>Next</a></li>");
			
		}//end if
		pagination.append(nextMark.toString());
		
		return pagination.toString();
	}
	
	public static String uuidFile(File file) {
		//파일명을 UUID를 사용하여 변경된 이름으로 반환
		String fileName=file.getName();
		String ext=fileName.substring(fileName.lastIndexOf("."));
		String uuidName=UUID.randomUUID().toString().replaceAll("-", "")+ext;
		
		return uuidName;
	}
}
