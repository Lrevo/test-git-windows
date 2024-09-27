

package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebServlet;

import bean.ReservationBean;

@WebServlet("/ReservationDao")
public class ReservationDao {

	private final String URL = "jdbc:mysql://localhost:3306/meetingroomc?characterEncoding=UTF-8&serverTimezone=JST";
	private final String USER ="user";
	private final String PASS ="pass";


	public List<ReservationBean> findByDate(String date){
		//DB取得結果を格納するリスト
		List<ReservationBean> reservationBean = new ArrayList<ReservationBean>();
		Connection con = null; //コネクション
		PreparedStatement ps = null;//クエリの実行

		try {
			//SQLの作成
			String sql ="SELECT * FROM reservation WHERE date =? ";
			//データベースに接続
			con = DriverManager.getConnection(URL,USER,PASS);
			//プリペアードステートメント作成
			ps = con.prepareStatement(sql);
			ps.setString(1,date);
			try(ResultSet rs = ps.executeQuery();){
				//データの取得
				while(rs.next()) {
					//ユーザー情報を取得
					String roomId = rs.getString("roomId");
					String start = rs.getString("start").substring(0, 5);
					String end = rs.getString("end").substring(0, 5);
					String userId = rs.getString("userId");
					reservationBean.add(new ReservationBean(roomId,date,start,end,userId));
				}
			}
		} catch(Exception ex) {
			ex.printStackTrace(); // エラーが発生した場合はエラーを出力
		} finally {
			// リソースの解放はfinallyブロックで行います
			try {
				if(ps != null) {
					//データベースを切断
					ps.close();
				}
				if(con != null) {
					//データベースを切断
					con.close();
				}
			} catch(Exception e) {
				e.printStackTrace(); // エラーが発生した場合はエラーを出力
			}
		}
		//結果の返却
		return reservationBean;
	}

	public boolean insert(ReservationBean reservation) {
		//DB取得結果を格納するリスト
//		ReservationBean reservationBean = null;
		//DB取得結果を格納するリスト
		Connection con = null; //コネクション
		PreparedStatement ps = null;//クエリの実行
		ResultSet rs = null;

		try {
			//SQLの作成
			String sql = " INSERT INTO reservation (roomId,date,start,end,userId) VALUES (?,?,?,?,?) ";

			//データベースに接続
			con = DriverManager.getConnection(URL, USER, PASS);
			//プリペアードステートメント作成
			ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			//自動的にコミットする機能をオフにする
			con.setAutoCommit(false);

			//コネクションの取得
			try {
//				reservationBean = new ReservationBean();

				ps.setString(1, reservation.getRoomId());
				ps.setString(2, reservation.getDate());
				ps.setString(3, reservation.getStart());
				ps.setString(4, reservation.getEnd());
				ps.setString(5, reservation.getUserId());
				//SQL実行
				int n = ps.executeUpdate();
				if (n == 1) {
					//登録後、予約IDを取得する
					rs = ps.getGeneratedKeys();
					if (rs.next()) {
						reservation.setId(rs.getInt(1));
						//成功したので、コミットする
						con.commit();
						//登録成功
						return true;
					}
				}
			} catch (SQLException ex) {
				System.out.println("登録に失敗しました");
				con.rollback();
			}
		} catch (Exception ex) {
			//例外の状態を出力
			ex.printStackTrace();
			return false;
			
		} finally {
			try {
				if (rs != null) {
					//データベースを切断
					rs.close();
				}
				if (ps != null) {
					//データベースを切断
					ps.close();
				}
				if (con != null) {
					//データベースを切断
					con.close();
				}			
			} catch (SQLException ex) {
//			    System.out.println("SQLエラー: " + ex.getMessage());
//			    ex.printStackTrace();
			} catch (Exception ex) {
			    System.out.println("例外が発生しました: " + ex.getMessage());
			    ex.printStackTrace();
			}
		} //finally
		return false;
	}



	public boolean delete(ReservationBean reservation){
		//	//DB取得結果を格納するリスト
		//	ReservationBean reservationBean = null;
		Connection con = null; //コネクション
		PreparedStatement ps = null;//クエリの実行

		try {
			//SQLの作成
			
			String sql =" DELETE FROM reservation WHERE  "
					  + " roomId =? AND date =? AND start =? ";
			//データベースにatu
			con = DriverManager.getConnection(URL,USER,PASS);
			//プリペアードステートメント作成
			ps = con.prepareStatement(sql);
			//自動的にコミットする機能をオフにする
//			con.setAutoCommit(false);

			try {
				//自動的にコミットする機能をオフにする
				con.setAutoCommit(false);
//				ReservationBean reservationBean = new ReservationBean();

				//予約番号をセットする
				
				ps.setString(1,reservation.getRoomId());
				ps.setString(2,reservation.getDate());
				ps.setString(3,reservation.getStart()+":00");

				//SQL実行
				int n = ps.executeUpdate();
				if(n==1) {
					//登録成功
					//成功したのでコミットする
					con.commit();
					return true;
				}

			}catch(Exception ex) {
				//失敗したのでロールバックする
				con.rollback();
				System.out.println("キャンセルに失敗しました");
			}
		}
		catch(Exception ex) {
			//例外の状態を出力
			ex.printStackTrace();
		}finally {
			try {
				if(ps != null) {
					//データベースを切断
					ps.close();
				}
				if(con != null) {
					//データベースを切断
					con.close();
				}
			}catch(Exception ex) {
//				System.out.println(ex.getMessage());
			}
		}
		return false;
	}

//and検索してゲットで検索してヒットしたものを消す
//	public static void main(String[] args) {
//		ReservationDao reservationDao = new ReservationDao();
//		reservationDao.findByDate("2024-04-20");
//		ReservationBean reservationBean = new ReservationBean("0501","2024-05-13","11:00:00","12:00:00","1111111",6);
//		reservationDao.insert(reservationBean);
//		reservationDao.delete(reservationBean);
//	}
}
