package bean;

import java.io.Serializable;

public class RoomBean implements Serializable{
	
	//フィールド
	private static final long serialVersionUID = 1L;  //直列化用バージョン番号
      private String id;     // 会議室ID
      private String name;   // 会議室名
	//コンストラクタ
     public RoomBean(){} 
     public RoomBean(String id,String name) { 
    	 this.id = id;
    	 this.name = name;
     }
	//メソッド
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return "RoomBean [id=" + id + ", name=" + name + "]";
	}

}
