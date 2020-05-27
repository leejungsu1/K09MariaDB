package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class MemberDAO {
	
	//멤버변수(클래스전체 멤버메소드에서 접근가능)
	Connection con;
	PreparedStatement psmt;
	ResultSet rs;

	/*
	 rs.next();
		: 반환되는 값이 무조건 1개가 반환되는 쿼리문일 경우 사용가능
		         
		if(rs.next()) {}
		: 반환되는 값이 없거나 1개가 반환되는 쿼리문일 경우 사용가능
		         
		while(rs.next()) {}
		: 반환되는값이 없거나 n개가 반환되는 쿼리문일 경우 사용가능
		         
		※ rs.next()는 반환되는 값이 없으면 false값이 나온다
		따라서 if문과 while문에 사용이 가능하다. 
	 */
	//기본생성자
	public MemberDAO() {
		System.out.println("MemberDAO생성자 호출");
	}
	public MemberDAO(String driver, String url) {
		try {
			Class.forName(driver);
			String id = "kosmo61_user";
			String pw = "1234";
			
			con = DriverManager.getConnection(url, id, pw);
			System.out.println("DB연결성공");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	//방법1 : 회원의 존재 유무만 판단한다.
	public boolean isMember(String id, String pass) {
		String sql = "SELECT COUNT(*) FROM member WHERE id=? AND pass=?";
		int isMember = 0;
		boolean isFlag = false;
		
		try {
			//prepare 객체로 쿼리문 전송
			psmt = con.prepareStatement(sql);
			//인파라미터 설정
			psmt.setString(1, id);
			psmt.setString(2, pass);
			//쿼리실행
			rs = psmt.executeQuery();
			//실행결과를 가져오기 위해 next() 호출
			rs.next();
			
			isMember = rs.getInt(1);
			System.out.println("affected:"+isMember);
			
			if(isMember==0) {
				isFlag = false;
			}
			else {
				isFlag = true;
			}
			
		}
		catch (Exception e) {
			isFlag = false;
			e.printStackTrace();
		}
		return isFlag;
	}
	
	//방법2 : 회원인증후 MemberDTO객체로 회원정보를 반환한다.
	public MemberDTO getMemberDTO(String uid, String upass) {
		//DTO객체를 생성한다.
		MemberDTO memberDTO = new MemberDTO();
		//쿼리문을 작성
		String query = "SELECT id, pass, name FROM member WHERE id=? AND pass=?";
		try {
			//prepared객체생성
			psmt = con.prepareStatement(query);
			//쿼리문의 인파라미터 설정
			psmt.setString(1, uid);
			psmt.setString(2, upass);
			//오라클로 쿼리문 전송 및 결과셋 (ResultSet)반환받음
			rs = psmt.executeQuery();
			//오라클이 반환해준 ResultSet이 있는지 확인
			if(rs.next()) {
				//true를 반환했다면 결과셋 있음
				//DTO객체에 회원레코드의 값을 저장한다.
				memberDTO.setId(rs.getString("id"));
				memberDTO.setPass(rs.getString("pass"));
				memberDTO.setName(rs.getString(3));
			}
			else {
				//false를 반환했다면 결과셋 없음
				System.out.println("결과셋이 없습니다.");
			}
		}
		catch (Exception e) {
			System.out.println("getMemberDTO오류");
			e.printStackTrace();
		}
		//DTO객체를 반환한다.
		return memberDTO;
		/*
		String sql = "SELECT * FROM member WHERE id=? and pass=?";
				
		try {
			//prepare 객체로 쿼리문 전송
			psmt = con.prepareStatement(sql);
			
			psmt.setString(1, uid);
			psmt.setString(2, upass);
			
			rs = psmt.executeQuery();
			rs.next();
			
			String id = rs.getString("id");
			String pw = rs.getString("pw");
			String name = rs.getString("name");
			Date regidate = rs.getDate("regidate");
			memberDTO.setId(id);
			memberDTO.setPass(pw);
			memberDTO.setName(name);
			memberDTO.setRegidate(regidate);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		*/
	}
	public Map<String,String> getMemberMap(String id, String pw) {
		Map<String, String> maps = new HashMap<String, String>();
		String sql = "SELECT id, pass, name FROM member WHERE id=? AND pass=?";
		try {
			//prepared객체생성
			psmt = con.prepareStatement(sql);
			//쿼리문의 인파라미터 설정
			psmt.setString(1, id);
			psmt.setString(2, pw);
			//오라클로 쿼리문 전송 및 결과셋 (ResultSet)반환받음
			rs = psmt.executeQuery();
			if(rs.next()) {
				maps.put("id", rs.getString(1));
				maps.put("pass", rs.getString(2));
				maps.put("name", rs.getString("name"));
			}
			else {
				System.out.println("결과셋이 없습니다.");
			}
		}
		catch (Exception e) {
			System.out.println("getMemberDTO오류");
			e.printStackTrace();
		}
			return maps;
	}
}