package day0624;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ProductService {
	public List<ProductDTO> searchProduct(){
		List<ProductDTO> list=new ArrayList<ProductDTO>();
		list.add(new ProductDTO(0, "소고기", "국내산 한우++ 등급으로 최상의 맛을 제공합니다.", "ox.png",
				new Date(System.currentTimeMillis()), 3500000));
		list.add(new ProductDTO(0, "돼지고기", "국내산 양돈가에서 생산한 한돈으로 맛있음.", "pig.png",
				new Date(System.currentTimeMillis()-(1000*60*60*24*1)), 21000));
		list.add(new ProductDTO(0, "닭고기", "동물복지 환경에서 자유롭게 뛰어놀면서 자란 닭고기.", "rooster.png",
				new Date(System.currentTimeMillis()), 8500));
		list.add(new ProductDTO(0, "토끼고기", "호주에서 직접 잡아온 토끼로 만든 고기.", "rabbit.png",
				new Date(System.currentTimeMillis()-(1000L*60*60*24*31*1)), 40000));
		return list;
	}
}

/*
private int prdNum;
private String item;
private String shortInfo;
private String itemImg;
private Date inputDate;
private int price ;
*/