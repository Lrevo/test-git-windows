package bean;

import java.io.Serializable;

public class UserBean implements Serializable{

	//フィールド
	private static final long serialVersionUID = 1L;
	private String id;
	private String password;
	private String name;
	private String address;
	//コンストラクタ
	public UserBean() {
		super();
	}
	public UserBean(String id, String password, String name, String address) {
		super();
		this.id = id;
		this.password = password;
		this.name = name;
		this.address = address;
	}


	//メソッド
	public String getId() {
		return id;
	}	
	public String getPassword() {
		return password;
	}
	public String getName() {
		return name;
	}
	public String getAddress() {
		return address;
	}

	@Override
	public String toString() {
		return "UserBean [id=" + id + ", password=" + password + ", name=" + name + ", address=" + address + "]";
	};


}


