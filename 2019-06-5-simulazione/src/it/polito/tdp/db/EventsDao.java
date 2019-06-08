package it.polito.tdp.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import com.javadocmd.simplelatlng.LatLng;

import it.polito.tdp.model.District;
import it.polito.tdp.model.Crimine;


public class EventsDao {
	
	public List<Crimine> listAllEvents(){
		String sql = "SELECT * FROM events" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Crimine> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Crimine(res.getLong("incident_id"),
							res.getInt("offense_code"),
							res.getInt("offense_code_extension"), 
							res.getString("offense_type_id"), 
							res.getString("offense_category_id"),
							res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"),
							res.getDouble("geo_lon"),
							res.getDouble("geo_lat"),
							res.getInt("district_id"),
							res.getInt("precinct_id"), 
							res.getString("neighborhood_id"),
							res.getInt("is_crime"),
							res.getInt("is_traffic")));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Integer> anniEventi(){
		String sql = "SELECT DISTINCT reported_date " + 
				"FROM EVENTS "+
				"ORDER BY reported_date ASC";
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Integer> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					if(!list.contains(res.getTimestamp("reported_date").toLocalDateTime().getYear())) {
					list.add((
							res.getTimestamp("reported_date").toLocalDateTime().getYear()));
					}
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
		
	}
	
	
	
	public void calcolaCentro(District d, int anno) {
		String sql = "SELECT geo_lat, geo_lon , reported_date " + 
				"FROM EVENTS " + 
				"WHERE district_id =?";
		try {
			Connection conn = DBConnect.getConnection() ;
			
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, d.getId());
			
			//List<Integer> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			double sommaLat = 0 ;
			double sommaLon = 0 ;
			double count = 0 ;
			while(res.next()) {
				try {
					if(res.getTimestamp("reported_date").toLocalDateTime().getYear()==anno) {
						sommaLat+=res.getDouble("geo_lat");
						sommaLon+=res.getDouble("geo_lon") ;
						count++;
						double cLat = sommaLat/count ;
						double cLon = sommaLon/count ;
						
						d.setCentro(new LatLng(cLat, cLon));
					}
					
					
				} catch (Throwable t) {
					t.printStackTrace();
					//System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
	}
	
	public int trovaCentrale(int anno) {
		String sql = "SELECT id" + 
				" FROM (SELECT	district_id	AS id, COUNT(is_crime) AS cnt" + 
				" FROM EVENTS e1" + 
				" WHERE YEAR(reported_date) = ? " + 
				" GROUP BY district_id) AS t1" + 
				" WHERE cnt = (SELECT MIN(cnt) FROM(SELECT	district_id	AS id, COUNT(is_crime) AS cnt" + 
				" FROM EVENTS e1" + 
				" WHERE YEAR(reported_date) = ? " + 
				" GROUP BY district_id) AS t2)"  	;
		try {
			Connection conn = DBConnect.getConnection() ;
			
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, anno);
			st.setInt(2, anno);
			
			//List<Integer> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			int idCentrale = 0;
			while(res.next()) {
				try {
					
						idCentrale = res.getInt("id");
					
					
					
				} catch (Throwable t) {
					t.printStackTrace();
					//System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return idCentrale;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
			
		}
		
		
	}
	public List<Crimine> criminiGiorno(int giorno, int mese, int anno){
		String sql = "SELECT * "+
				"FROM EVENTS "+
				"WHERE YEAR(reported_date) = ? AND MONTH(reported_date) = ? AND DAY(reported_date)=?" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Crimine> list = new ArrayList<>() ;
			st.setInt(1, anno);
			st.setInt(2, mese);
			st.setInt(3, giorno);
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Crimine(res.getLong("incident_id"),
							res.getInt("offense_code"),
							res.getInt("offense_code_extension"), 
							res.getString("offense_type_id"), 
							res.getString("offense_category_id"),
							res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"),
							res.getDouble("geo_lon"),
							res.getDouble("geo_lat"),
							res.getInt("district_id"),
							res.getInt("precinct_id"), 
							res.getString("neighborhood_id"),
							res.getInt("is_crime"),
							res.getInt("is_traffic")));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}

}
