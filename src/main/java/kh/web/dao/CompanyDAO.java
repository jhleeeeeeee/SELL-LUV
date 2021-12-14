package kh.web.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import kh.web.dto.Board_CpDTO;
import kh.web.dto.CompanyDTO;
import kh.web.dto.Photo_ListDTO;
import kh.web.statics.IFCPStatics;

public class CompanyDAO {

	private static CompanyDAO instance = null;

	public static CompanyDAO getInstance() {
		if(instance==null) {
			instance = new CompanyDAO();
		}
		return instance;
	}
	public CompanyDAO() {

	}
	private Connection getConnection() throws Exception{
		Context ctx = new InitialContext();
		DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/oracle");
		return ds.getConnection();
	}

	//====================================================================================================================================
	//로그인 
	public boolean login(String id, String pw) throws SQLException, Exception {
		String sql = "SELECT * FROM company WHERE id_cp =? AND pw_cp=?";
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){
			pstat.setString(1, id);
			pstat.setString(2, pw);
			try(ResultSet rs = pstat.executeQuery();){

				boolean result = rs.next();

				return result;

			}
		}

	}

	//====================================================================================================================================
	//페이징 목록 출력시작....

	//기업 총 개수..
	private int getRecordCount() throws SQLException, Exception {
		String sql = "select count(*) from board_Cp";

		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				ResultSet rs = pstat.executeQuery()){
			rs.next();
			return rs.getInt(1);
		}

	}

	//목록 출력 페이징 알고리즘..
	public String getPageNavi(int currentPage) throws SQLException, Exception {

		int recordTotalCount = this.getRecordCount();

		int pageTotalCount =0; //총페이지...

		if(recordTotalCount%IFCPStatics.RECORD_COUNT_PER_PAGE == 0) { //페이지수가 나눠떨어지는경우..
			pageTotalCount = recordTotalCount/IFCPStatics.RECORD_COUNT_PER_PAGE;
		}else { //페이지수가 안나눠떨어지는경우..
			pageTotalCount = recordTotalCount/IFCPStatics.RECORD_COUNT_PER_PAGE + 1;
		}

		if(currentPage < 1) { //페이지수가 1보다 작은경우..
			currentPage = 1;
		}else if(currentPage > pageTotalCount) { //총페이지 수가 현재페이지보다작은경우..
			currentPage = pageTotalCount;
		}

		int startNavi = (currentPage -1) / IFCPStatics.NAVI_PER_PAGE * 3 + 1; //10단위씩 끊어서 하단의 첫번째 수..
		int endNavi = startNavi + IFCPStatics.NAVI_PER_PAGE-1; //하단표시된 페이지 수중에마지막수.. 

		if(endNavi > pageTotalCount) {
			endNavi = pageTotalCount; 
		}

		boolean needPrev = true;
		boolean needNext = true;

		if(startNavi == 1) { //첫페이지엔 이전페이지가 필요없
			needPrev = false;
		}

		if(endNavi == pageTotalCount) { //마지막 페이지에선 뒤페이지가 필요없
			needNext = false;
		}
		String pageNavi = ""; 

		if(needPrev) {
			pageNavi += "<li class='page-item'><a class='page-link rounded-0 mr-3 shadow-sm border-top-0 border-left-0 text-dark' href='/companyList.ifcp?cpage="+(startNavi-1)+"'>◀</a></li>";
		}
		for(int i = startNavi; i <=endNavi; i++) {
			pageNavi+="<li class='page-item'><a class='page-link rounded-0 mr-3 shadow-sm border-top-0 border-left-0 text-dark' href='/companyList.ifcp?cpage="+i+"'>"+i+"</a></li>";
		}
		if(needNext) {
			pageNavi += "<li class='page-item'><a class='page-link rounded-0 mr-3 shadow-sm border-top-0 border-left-0 text-dark' href='/companyList.ifcp?cpage="+(endNavi+1)+"'>▶</a></li>";
		}
		System.out.println(endNavi);
		return pageNavi;
	}


	public LinkedHashMap<Board_CpDTO,CompanyDTO> selectByBound(int start, int end) throws Exception {
		//		String sql ="select * from (select company.*, row_number() over(order by seq_cp desc) rn from company) where rn between ? and ?";
		String sql = "select * from (\n"
				+ " select \n"
				+ "    row_number() over(order by decode(grade,'gold','A','silver','B','bronze','C')) rn, \n"
				+ "    temp.*\n"
				+ "from \n"
				+ "    (select \n"
				+ "        c.*,\n"
				+ "        seq_board_cp,\n"
				+ "        member_seq,\n"
				+ "        title_cp,\n"
				+ "        condition_cp,\n"
				+ "        intro_cp,\n"
				+ "        slike_cp,\n"
				+ "        rlike_cp,\n"
				+ "        b.photo_cp b_photo_cp from company c,board_cp b where seq_cp = member_seq) temp)\n"
				+ "        where rn between ? and ? order by 2 desc";

		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql)){;
				pstat.setInt(1, start);
				pstat.setInt(2, end);
				try(ResultSet rs = pstat.executeQuery()){

					//					List<CompanyDTO> list = new ArrayList<>();
					LinkedHashMap<Board_CpDTO,CompanyDTO> list = new LinkedHashMap<>();

					while(rs.next()) {
						int seq = rs.getInt("seq_cp");
						String id = rs.getString("id_cp");
						String pw = rs.getString("pw_cp");
						String photo = rs.getString("photo_cp");
						String name = rs.getString("name_cp");
						String crnumber = rs.getString("crnumber_cp");
						String zipcode = rs.getString("zipcode_cp");
						String address1 = rs.getString("address1_cp");
						String address2 = rs.getString("address2_cp");
						String rpt = rs.getString("rpt_cp");
						String phone = rs.getString("phone_cp");
						String email= rs.getString("email_cp");
						Long sales = rs.getLong("sales_cp");
						String grade = rs.getString("grade");
						String pwAsk = rs.getString("pwAsk_cp");
						String pwAnswer = rs.getString("pwAnswer_cp");


						int seq_board_cp = rs.getInt("seq_board_cp");
						int member_seq = rs.getInt("member_seq");
						String title_cp = rs.getString("title_cp");
						String condition_cp = rs.getString("condition_cp");
						String intro_cp = rs.getString("intro_cp");
						int sLike_cp =  rs.getInt("sLike_cp");
						int rLike_cp =  rs.getInt("rLike_cp");
						String photo_cp = rs.getString("photo_cp");


						CompanyDTO companyDTO = new CompanyDTO(seq,id,pw,photo,name,crnumber,zipcode,address1,address2,rpt,phone,email,sales,grade,pwAsk,pwAnswer);
						Board_CpDTO board_CpDTO = new Board_CpDTO(seq_board_cp,member_seq,title_cp,condition_cp,intro_cp,sLike_cp,rLike_cp,photo_cp);

						list.put(board_CpDTO,companyDTO);
					}
					return list;
				}
		}
	}

	//페이징 목록 출력 끝..
	//====================================================================================================================================

	public LinkedHashMap<Board_CpDTO, CompanyDTO> getCompanyBoardDetail(int seq) throws Exception { 
		String sql = " select c.*, b.* from company c\n"
				+ "   join board_cp b ON c.seq_cp = b.member_seq\n"
				+ "   where seq_board_cp = ?";

		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){
			pstat.setInt(1, seq);
			try(ResultSet rs = pstat.executeQuery();){

				LinkedHashMap<Board_CpDTO,CompanyDTO> list = new LinkedHashMap<>();

				while(rs.next()) {

					seq = rs.getInt("seq_cp");
					String id = rs.getString("id_cp");
					String pw = rs.getString("pw_cp");
					String photo = rs.getString("photo_cp");
					String name = rs.getString("name_cp");
					String crnumber = rs.getString("crnumber_cp");
					String zipcode = rs.getString("zipcode_cp");
					String address1 = rs.getString("address1_cp");
					String address2 = rs.getString("address2_cp");
					String rpt = rs.getString("rpt_cp");
					String phone = rs.getString("phone_cp");
					String email= rs.getString("email_cp");
					Long sales = rs.getLong("sales_cp");
					String grade = rs.getString("grade");
					String pwAsk = rs.getString("pwAsk_cp");
					String pwAnswer = rs.getString("pwAnswer_cp");

					int seq_board_cp = rs.getInt("seq_board_cp");
					int member_seq = rs.getInt("member_seq");
					String title_cp = rs.getString("title_cp");
					String condition_cp = rs.getString("condition_cp");
					String intro_cp = rs.getString("intro_cp");
					int sLike_cp =  rs.getInt("sLike_cp");
					int rLike_cp =  rs.getInt("rLike_cp");
					String photo_cp = rs.getString("photo_cp");

					CompanyDTO companyDTO = new CompanyDTO(seq,id,pw,photo,name,crnumber,zipcode,address1,address2,rpt,phone,email,sales,grade,pwAsk,pwAnswer);
					Board_CpDTO board_CpDTO = new Board_CpDTO(seq_board_cp,member_seq,title_cp,condition_cp,intro_cp,sLike_cp,rLike_cp,photo_cp);

					list.put(board_CpDTO,companyDTO);
					Board_CpDTO dto = new Board_CpDTO();
					dto.setSeq_cp(rs.getInt("seq_board_cp"));
					dto.setMember_seq(rs.getInt("member_seq"));
					dto.setTitle_cp(rs.getString("title_cp"));
					dto.setCondition_cp(rs.getString("condition_cp"));
					dto.setIntro_cp(rs.getString("intro_cp"));
					dto.setsLike_cp(rs.getInt("sLike_cp"));
					dto.setrLike_cp(rs.getInt("rLike_cp"));
					dto.setPhoto_cp(rs.getString("photo_cp"));
					list.put(board_CpDTO,companyDTO);

				}
				return list;
			}
		}
	}	


	// Company 회원가입 -이준협

	// 회원가입 method
	public int insert(String id, String pw, String photo, String name, String crunumber, String zipcode, String address1, 
			String address2, String rpt_cp, String phone, String email, String sales, String grade, String pwAsk, String pwAnswer) throws Exception {

		String sql = "insert into company values(company_seq_cp.nextval,?,?,?,?,?,?,?,?,?,?,?,?,default,?,?)";


		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){

			pstat.setString(1, id);
			pstat.setString(2, pw);			
			pstat.setString(3, photo);
			pstat.setString(4, name);
			pstat.setString(5, crunumber);
			pstat.setString(6, zipcode);
			pstat.setString(7, address1);
			pstat.setString(8, address2);
			pstat.setString(9, rpt_cp);
			pstat.setString(10, phone);
			pstat.setString(11, email);
			pstat.setString(12, sales);
			pstat.setString(13, pwAsk);
			pstat.setString(14, pwAnswer);
			int result  = pstat.executeUpdate();

			return result;
		}
	}

	// 회원가입 중복 ID 체크 method
	public boolean isIdExist(String id) throws Exception{

		String sql = "select * from(select id_cp from company union select id_if from influencer) where id_cp = ?";

		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){
			pstat.setString(1,id);
			try(ResultSet rs = pstat.executeQuery()){
				return rs.next();
			}		
		}
	}

	// 아이디 정보 전달 method
	public CompanyDTO selectById(String paramID) throws Exception {

		String sql = "select * from company where id_cp = ?";

		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){
			pstat.setString(1, paramID);
			try(ResultSet rs = pstat.executeQuery()){

				CompanyDTO dto = new CompanyDTO();
				if(rs.next()) {
					dto.setSeq(rs.getInt("seq_cp"));
					dto.setId(rs.getString("id_cp"));
					dto.setPw(rs.getString("pw_cp"));
					dto.setPhoto(rs.getString("photo_cp"));
					dto.setName(rs.getString("name_cp"));
					dto.setCrnumber(rs.getString("crnumber_cp"));
					dto.setZipcode(rs.getString("zipcode_cp"));
					dto.setAddress1(rs.getString("address1_cp"));
					dto.setAddress2(rs.getString("address2_cp"));
					dto.setRpt(rs.getString("rpt_cp"));
					dto.setPhone(rs.getString("phone_cp"));
					dto.setEmail(rs.getString("email_cp"));
					dto.setSales(rs.getLong("sales_cp"));
					dto.setGrade(rs.getString("grade"));
					dto.setPwAsk(rs.getString("pwAsk_cp"));
					dto.setPwAnswer(rs.getString("pwAnswer_cp"));
					return dto;					
				}
				return null;
			}
		}
	}

	public int update(String pw, String photo, String name, String crunumber, String zipcode, String address1, 
			String address2, String rpt_cp, String phone, String email, Long sales, String pwAsk, String pwAnswer, String id) throws Exception {
		String sql = "update company set pw_cp = ?, photo_cp = ?, name_cp = ?, crnumber_cp = ?, zipcode_cp = ?, address1_cp = ?, "
				+ "address2_cp = ?, rpt_cp = ?, phone_cp = ?, email_cp = ?, sales_cp = ?, pwAsk_cp = ?, pwAnswer_cp =? where id_cp = ?";
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){

			pstat.setString(1, pw);			
			pstat.setString(2, photo);
			pstat.setString(3, name);
			pstat.setString(4, crunumber);
			pstat.setString(5, zipcode);
			pstat.setString(6, address1);
			pstat.setString(7, address2);
			pstat.setString(8, rpt_cp);
			pstat.setString(9, phone);
			pstat.setString(10, email);
			pstat.setLong(11, sales);
			pstat.setString(12, pwAsk);
			pstat.setString(13, pwAnswer);
			pstat.setString(14, id);
			int result  = pstat.executeUpdate();

			return result;
		}
	}




	// 맞는 회원 정보 가져오기
	public CompanyDTO findMember(String id, String name, String text, String answer) throws Exception {
		String sql = "SELECT * FROM company WHERE id_cp =? AND name_cp =? AND pwask_cp =? AND pwanswer_cp=?";
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){
			pstat.setString(1, id);
			pstat.setString(2, name);
			pstat.setString(3, text);
			pstat.setString(4, answer);
			try(ResultSet rs = pstat.executeQuery();){

				CompanyDTO dto = new CompanyDTO();
				if(rs.next()) {
					int seq = rs.getInt("seq_cp");
					String pw = rs.getString("pw_cp");
					String photo = rs.getString("photo_cp");
					String crnumber = rs.getString("crnumber_cp");
					String zipcode = rs.getString("zipcode_cp");
					String address1 = rs.getString("address1_cp");
					String address2 = rs.getString("address2_cp");
					String rpt = rs.getString("rpt_cp");
					String phone = rs.getString("phone_cp");
					String email= rs.getString("email_cp");
					Long sales = rs.getLong("sales_cp");
					String grade = rs.getString("grade");

					dto = new CompanyDTO(seq,id,pw,photo,name,crnumber,zipcode,address1,address2,rpt,phone,email,sales,grade,text,answer);
				}
				return dto;
			}
		}
	}

	// 맞는 회원 정보 가져오기
	public CompanyDTO findId(String email, String name, String text, String answer) throws Exception {
		String sql = "SELECT * FROM company WHERE email_cp =? AND name_cp =? AND pwask_cp =? AND pwanswer_cp=?";
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){
			pstat.setString(1, email);
			pstat.setString(2, name);
			pstat.setString(3, text);
			pstat.setString(4, answer);
			try(ResultSet rs = pstat.executeQuery();){

				CompanyDTO dto = new CompanyDTO();
				if(rs.next()) {
					int seq = rs.getInt("seq_cp");
					String pw = rs.getString("pw_cp");
					String id = rs.getString("id_cp");
					String photo = rs.getString("photo_cp");
					String crnumber = rs.getString("crnumber_cp");
					String zipcode = rs.getString("zipcode_cp");
					String address1 = rs.getString("address1_cp");
					String address2 = rs.getString("address2_cp");
					String rpt = rs.getString("rpt_cp");
					String phone = rs.getString("phone_cp");
					Long sales = rs.getLong("sales_cp");
					String grade = rs.getString("grade");

					dto = new CompanyDTO(seq,id,pw,photo,name,crnumber,zipcode,address1,address2,rpt,phone,email,sales,grade,text,answer);
				}
				return dto;
			}
		}
	}

	// 멤버인지 아닌지 확인하는 메소드
	public boolean isMember(String id, String name, String text, String answer) throws Exception {
		String sql = "SELECT * FROM company WHERE id_cp =? AND name_cp =? AND pwask_cp =? AND pwanswer_cp=?";
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){
			pstat.setString(1, id);
			pstat.setString(2, name);
			pstat.setString(3, text);
			pstat.setString(4, answer);
			try(ResultSet rs = pstat.executeQuery();){

				CompanyDTO dto = new CompanyDTO();
				if(rs.next()) {
					return true;
				}
				return false;
			}
		}
	}

	// 비밀번호 재설정 메소드

	public int updateNewPW(String id, String pw) throws Exception {
		String sql = "UPDATE company SET pw_cp=? WHERE id_cp =?";
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){

			pstat.setString(1, pw);
			pstat.setString(2, id);			
			int result  = pstat.executeUpdate();

			return result;
		}
	}


	// 제품등록시 정보 불러오기
	public ArrayList<CompanyDTO> searchById(String loginID) throws Exception {
		String sql = "select * from company where id_cp=?";

		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql)){;
				pstat.setString(1, loginID);
				try(ResultSet rs = pstat.executeQuery()){

					List<CompanyDTO> list = new ArrayList<>();

					while(rs.next()) {
						int seq1 = rs.getInt("seq_cp");
						String id = rs.getString("id_cp");
						String pw = rs.getString("pw_cp");
						String photo = rs.getString("photo_cp");
						String name = rs.getString("name_cp");
						String crnumber = rs.getString("crnumber_cp");
						String zipcode = rs.getString("zipcode_cp");
						String address1 = rs.getString("address1_cp");
						String address2 = rs.getString("address2_cp");
						String rpt = rs.getString("rpt_cp");
						String phone = rs.getString("phone_cp");
						String email= rs.getString("email_cp");
						Long sales = rs.getLong("sales_cp");
						String grade = rs.getString("grade");
						String pwAsk = rs.getString("pwAsk_cp");
						String pwAnswer = rs.getString("pwAnswer_cp");


						CompanyDTO companyDTO = new CompanyDTO(seq1,id,pw,photo,name,crnumber,zipcode,address1,address2,rpt,phone,email,sales,grade,pwAsk,pwAnswer);


						list.add(companyDTO);
					}
					return (ArrayList<CompanyDTO>) list;
				}
		}
	}

	public int insertPhoto(Photo_ListDTO dto) throws Exception { // 사진 업로드
		String sql = "insert into files values(files_seq.nextval,?,?,?)";
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){
			pstat.setString(1, dto.getOriName());
			pstat.setString(2, dto.getSysName());
			pstat.setInt(3, dto.getParentSeq());
			int result = pstat.executeUpdate();
			con.commit();
			return result;
		}
	}

	public int findSeq(String id, String pw) throws Exception{
		String sql = "SELECT seq_cp FROM company WHERE id_cp =? AND pw_cp =?";
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){
			pstat.setString(1, id);
			pstat.setString(2, pw);
			int result = 0;
			try(ResultSet rs = pstat.executeQuery();){
				if(rs.next()) {
				result = rs.getInt("seq_cp");
				
				}
			}
			return result;
			}
	}
}




