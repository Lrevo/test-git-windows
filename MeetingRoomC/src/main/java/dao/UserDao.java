
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.annotation.WebServlet;

import bean.UserBean;

@WebServlet("/UserDao")
public class UserDao {

	private final String URL = "jdbc:mysql://localhost:3306/meetingroomc?characterEncoding=UTF-8&serverTimezone=JST";
	private final String USER = "user";
	private final String PASS ="pass";


//	private final String RESOURCE = "java:comp/env/jdbc/meetingroomc";

	public UserBean certificate(String id,String password){


		//DB取得結果を格納するリスト
		UserBean ub = null;
		Connection con = null; //コネクション
		PreparedStatement ps = null;//クエリの実行
		ResultSet rs = null;//返却される値


		try {

			con = DriverManager.getConnection(URL,USER,PASS);
			//sql
			String sql = "select * from user WHERE id =? and password =? ";

			ps= con.prepareStatement(sql);

			ps.setString(1,id);
			ps.setString(2,password);

			rs=ps.executeQuery();

			while(rs.next()) {
				String uid =rs.getString("id");
				String pass = rs.getString("password");
				String name = rs.getString("name");
				String add = rs.getString("address");

				ub = new UserBean(uid,pass,name,add);
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


		return ub;



	}
}
