
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.annotation.WebServlet;

import bean.RoomBean;

@WebServlet("/RoomDao")
public class RoomDao {
	//フィールド
	//コンストラクタ
	//メソッド

	public RoomBean[] findAll() {

		//DB取得結果を格納するリスト
		Connection con = null; //コネクション
		PreparedStatement ps = null;//クエリの実行
		ResultSet rs = null;//返却される値
		int i=0;
		RoomBean[] rb=null;

		try {		
			String url ="jdbc:mysql://localhost:3306/meetingroomc?characterEncoding=utf-8&serverTimezone=JST";
			String user = "user";
			String pass="pass";

			//データベース接続			
			con = DriverManager.getConnection(url,user,pass);

			//SQL作成
			String sql = "select * from room ";

			ps= con.prepareStatement(sql);
			rs=ps.executeQuery();

			rs.last();//最終行
			int row = rs.getRow();//今どこ
			rs.beforeFirst();   //最初に戻る

			 rb = new RoomBean[row]; //配列数獲得

			while(rs.next()) {
				String id =rs.getString("id");
				String name = rs.getString("name");
				rb[i] = new RoomBean(id,name);
				i++;
			}

		}catch(Exception e) {
			System.out.println(e.getMessage());
			System.out.println(456);
		}finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(ps != null) {
					ps.close();
				}
				if(con != null) {
					con.close();
				}
			}catch(Exception e) {
				System.out.println(e.getMessage());
				System.out.println(123);
			}
			
		}
		return rb;


		



	}









}

