package kh.web.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import kh.web.dto.KkanbuDTO;

public class KkanbuDAO {

	private static KkanbuDAO instance = null;

	public static KkanbuDAO getInstance() {
		if(instance==null) {
			instance = new KkanbuDAO();
		}
		return instance;
	}
	public KkanbuDAO() {

	}
	private Connection getConnection() throws Exception{
		Context ctx = new InitialContext();
		DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/oracle");
		return ds.getConnection();
	}
	public int insertKkanbu(int companySeq, int influencerSeq) throws SQLException, Exception {
		String sql = "insert into kkanbu values(kkanbu_seq.nextval,?,?)";
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){
			pstat.setInt(1, influencerSeq);
			pstat.setInt(2, companySeq);
			int result = pstat.executeUpdate();
			
			return result;
		}
	}
	public ArrayList<KkanbuDTO> getInfKkanbu(int loggedInSeq) throws SQLException, Exception {
		
		String sql = "select * from kkanbu where influencer_Seq = ?";
		
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){
			pstat.setInt(1, loggedInSeq);
			try(ResultSet rs = pstat.executeQuery();){
				ArrayList<KkanbuDTO> list = new ArrayList<>();
				
				while(rs.next()) {
					int kkanbu_seq = rs.getInt("kkanbu_seq");
					int influencer_seq = rs.getInt("influencer_seq");
					int company_seq = rs.getInt("company_seq");
					
					KkanbuDTO kkanbuDTO = new KkanbuDTO(kkanbu_seq,influencer_seq,company_seq);
					list.add(kkanbuDTO);
				}
				return list;
					}
				}
			}
	
public ArrayList<KkanbuDTO> getCompanyKkanbu(int loggedInSeq) throws SQLException, Exception {
		
		String sql = "select * from kkanbu where company_Seq = ?";
		
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){
			pstat.setInt(1, loggedInSeq);
			try(ResultSet rs = pstat.executeQuery();){
				ArrayList<KkanbuDTO> list = new ArrayList<>();
				
				while(rs.next()) {
					int kkanbu_seq = rs.getInt("kkanbu_seq");
					int influencer_seq = rs.getInt("influencer_seq");
					int company_seq = rs.getInt("company_seq");
					
					KkanbuDTO kkanbuDTO = new KkanbuDTO(kkanbu_seq,influencer_seq,company_seq);
					list.add(kkanbuDTO);
				}
				return list;
					}
				}
			}
	}