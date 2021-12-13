package kh.web.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import kh.web.dto.Board_CpDTO;
import kh.web.dto.InfluencerDTO;
import kh.web.dto.Profile_IfDTO;
import kh.web.statics.IFCPStatics;

public class InfluencerDAO  {

	private static InfluencerDAO instance = null;

	public static InfluencerDAO getInstance() {
		if(instance==null) {
			instance = new InfluencerDAO();
		}
		return instance;
	}
	public InfluencerDAO() {

	}
	private Connection getConnection() throws Exception{
		Context ctx = new InitialContext();
		DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/oracle");
		return ds.getConnection();
	}



	//====================================================================================================================================
	//로그인..
	public boolean login(String id, String pw) throws SQLException, Exception {
		String sql = "SELECT * FROM influencer WHERE id_if =? AND pw_if=?";
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){
			pstat.setString(1, id);
			pstat.setString(2, pw);
			try(ResultSet rs = pstat.executeQuery();){

				return rs.next();

			}
		}
	}
	//====================================================================================================================================
	//페이징...
	//개인 총 갯수..
	private int getRecordCount() throws SQLException, Exception {
		String sql = "select count(*) from profile_if";

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
			pageNavi += "<li class='page-item'><a class='page-link rounded-0 mr-3 shadow-sm border-top-0 border-left-0 text-dark' href='/influencerList.ifcp?cpage="+(startNavi-1)+"'>◀</a></li>";
		}
		for(int i = startNavi; i <=endNavi; i++) {
			pageNavi+="<li class='page-item'><a class='page-link rounded-0 mr-3 shadow-sm border-top-0 border-left-0 text-dark' href='/influencerList.ifcp?cpage="+i+"'>"+i+"</a></li>";
		}
		if(needNext) {
			pageNavi += "<li class='page-item'><a class='page-link rounded-0 mr-3 shadow-sm border-top-0 border-left-0 text-dark' href='/influencerList.ifcp?cpage="+(endNavi+1)+"'>▶</a></li>";
		}
		return pageNavi;
	}

	public LinkedHashMap<Profile_IfDTO, InfluencerDTO> selectByBound(int start, int end) throws Exception {
		//			String sql ="select * from (select influencer.*, row_number() over(order by seq_if desc) rn from influencer) where rn between ? and ?";
		String sql = "select * from (\n"
				+ " select \n"
				+ "    row_number() over(order by decode(grade,'gold','A','silver','B','bronze','C')) rn, \n"
				+ "    temp.*\n"
				+ "from \n"
				+ "    (select \n"
				+ "        i.*,\n"
				+ "        p.seq_if p_seq_if,\n"
				+ "        member_seq,\n"
				+ "        condition_if,\n"
				+ "        career_if,\n"
				+ "        intro_if,\n"
				+ "        slike_if,\n"
				+ "        rlike_if from influencer i, profile_if p where i.seq_if = member_seq) temp)\n"
				+ "        where rn between ? and ? order by 2 desc";

		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql)){;
				pstat.setInt(1, start);
				pstat.setInt(2, end);
				try(ResultSet rs = pstat.executeQuery()){

				LinkedHashMap<Profile_IfDTO, InfluencerDTO> list = new LinkedHashMap<Profile_IfDTO, InfluencerDTO>();

					while(rs.next()) {
						int seq = rs.getInt("seq_if");
						String id = rs.getString("id_if");
						String pw = rs.getString("pw_if");
						String photo = rs.getString("photo_if");
						String name = rs.getString("name_if");
						String nickname = rs.getString("nickname_if");
						String zipcode = rs.getString("zipcode_if");
						String address1 = rs.getString("address1_if");
						String address2 = rs.getString("address2_if");
						String sns = rs.getString("sns_if");
						String phone = rs.getString("phone_if");
						String email= rs.getString("email_if");
						String grade = rs.getString("grade");
						String pwAsk = rs.getString("pwAsk_if");
						String pwAnswer= rs.getString("pwAnswer_if");
						String favorite = rs.getString("favorite_if");
						
						int seq_if = rs.getInt("seq_if");
						int member_seq = rs.getInt("member_seq");
						String condition_if = rs.getString("condition_if");
						String career_if = rs.getString("condition_if");
						String intro_if = rs.getString("intro_if");
						int sLike_if = rs.getInt("sLike_if");
						int rLike_if = rs.getInt("rLike_if");
						


						InfluencerDTO influencerDTO = new InfluencerDTO(seq,id,pw,photo,name,nickname,zipcode,address1,address2,sns,phone,email,grade,pwAsk,pwAnswer,favorite);
						Profile_IfDTO profile_IfDTO = new Profile_IfDTO(seq_if,member_seq,condition_if,career_if,intro_if,sLike_if,rLike_if);

						list.put(profile_IfDTO,influencerDTO);
					}
					return list;
				}
		}
	}
	//====================================================================================================================================

	public LinkedHashMap<Profile_IfDTO, InfluencerDTO> getIfProfile(int seq) throws Exception { // 인플루언서 카드 작성자로 검색
		String sql = "select i.*, p.* from influencer i\n"
				+ "   join profile_if p ON i.seq_if = p.member_seq\n"
				+ "   where p.seq_if = ?";
		
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){
			pstat.setInt(1, seq);
			try(ResultSet rs = pstat.executeQuery();){
				LinkedHashMap<Profile_IfDTO, InfluencerDTO> list = new LinkedHashMap<Profile_IfDTO, InfluencerDTO>();

				while(rs.next()) {
					seq = rs.getInt("seq_if");
					String id = rs.getString("id_if");
					String pw = rs.getString("pw_if");
					String photo = rs.getString("photo_if");
					String name = rs.getString("name_if");
					String nickname = rs.getString("nickname_if");
					String zipcode = rs.getString("zipcode_if");
					String address1 = rs.getString("address1_if");
					String address2 = rs.getString("address2_if");
					String sns = rs.getString("sns_if");
					String phone = rs.getString("phone_if");
					String email= rs.getString("email_if");
					String grade = rs.getString("grade");
					String pwAsk = rs.getString("pwAsk_if");
					String pwAnswer= rs.getString("pwAnswer_if");
					String favorite = rs.getString("favorite_if");
					
					int seq_if = rs.getInt("seq_if");
					int member_seq = rs.getInt("member_seq");
					String condition_if = rs.getString("condition_if");
					String career_if = rs.getString("condition_if");
					String intro_if = rs.getString("intro_if");
					int sLike_if = rs.getInt("sLike_if");
					int rLike_if = rs.getInt("rLike_if");
					


					InfluencerDTO influencerDTO = new InfluencerDTO(seq,id,pw,photo,name,nickname,zipcode,address1,address2,sns,phone,email,grade,pwAsk,pwAnswer,favorite);
					Profile_IfDTO profile_IfDTO = new Profile_IfDTO(seq_if,member_seq,condition_if,career_if,intro_if,sLike_if,rLike_if);
					
					list.put(profile_IfDTO,influencerDTO);
				}
				return list;
			}
		}
	}
}
