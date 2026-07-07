package kr.co.sist.user.member;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import kr.co.sist.chipher.DataEncryption;

public class MemberService {
	
	/**
	 * 아이디 중복 확인
	 * @param id
	 * @return
	 */
	public boolean searchDupId(String id) {
		boolean idFlag = false;
		
		MemberDAO mDAO = MemberDAO.getInstance();
		try {
			idFlag = mDAO.selectID(id);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return idFlag;
	}
	
	public boolean addMember(MemberDTO mDTO) {
		
		boolean flag=false;
		//DAO클래스를 사용하여 DB 추가 작업 수행
		MemberDAO mDAO=MemberDAO.getInstance();
		try {
			//일방향해시 : 비밀번호
			mDTO.setPassword(DataEncryption.messageDigest("SHA-1", mDTO.getPassword()));
			//암호화 : 이름, 이메일, 전화번호
			String key="a012345678912345";
			DataEncryption de=new DataEncryption(key);
			mDTO.setName(de.encrypt(mDTO.getName()));
			mDTO.setEmail(de.encrypt(mDTO.getEmail()));
			mDTO.setPhone(de.encrypt(mDTO.getPhone1()+"-"+mDTO.getPhone2()+"-"+mDTO.getPhone3()));
			
			mDAO.insertWebMember(mDTO);
			if(mDTO.getHobby() != null) {//취미가 있다면
				mDAO.insertWebMemberHobby(mDTO);
			}//end if
			flag=true;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return flag;
		
	}
}
