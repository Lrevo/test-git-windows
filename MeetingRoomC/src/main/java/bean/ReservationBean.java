package bean;

import java.io.Serializable;

// 予約情報を表すBeanクラス
public class ReservationBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String roomId; // 予約された部屋のID
	private String date;   // 予約日
	private String start;  // 予約開始時刻
	private String end;    // 予約終了時刻
	private String userId; // 予約したユーザーのID
	private int id;        // 予約ID

	// デフォルトコンストラクタ
	public ReservationBean() { }

	// 全てのフィールドを指定して予約情報を初期化するコンストラクタ
	public ReservationBean(String roomId, String date, String start, String end, String userId, int id) {
		this.roomId = roomId;
		this.date = date;
		this.start = start;
		this.end = end;
		this.userId = userId;
		this.id = id;
	}

	// IDを指定せずに予約情報を初期化するコンストラクタ
	public ReservationBean(String roomId, String date, String start, String end, String userId) {
		this.roomId = roomId;
		this.date = date;
		this.start = start;
		this.end = end;
		this.userId = userId;
	}

	// 以下は各フィールドのゲッターとセッター

	// 予約された部屋のIDを取得
	public String getRoomId() {
		return roomId;
	}

	// 予約された部屋のIDを設定
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	// 予約日を取得
	public String getDate() {
		return date;
	}

	// 予約日を設定
	public void setDate(String date) {
		this.date = date;
	}

	// 予約開始時刻を取得
	public String getStart() {
		return start;
	}

	// 予約開始時刻を設定
	public void setStart(String start) {
		this.start = start;
	}

	// 予約終了時刻を取得
	public String getEnd() {
		return end;
	}

	// 予約終了時刻を設定
	public void setEnd(String end) {
		this.end = end;
	}

	// 予約したユーザーのIDを取得
	public String getUserId() {
		return userId;
	}

	// 予約したユーザーのIDを設定
	public void setUserId(String userId) {
		this.userId = userId;
	}

	// 予約IDを取得
	public int getId() {
		return id;
	}

	// 予約IDを設定
	public void setId(int id) {
		this.id = id;
	}

	// toStringメソッドのオーバーライド（デバッグやテスト用）
	//@Override
	public String toString() {
		return 
				"予約会議室'" + roomId + '\'' +
				",日付'" + date + '\'' +
				", 開始'" + start + '\'' +
				"終了"+end+
				", 予約ユーザーID'" + userId + '\'' +
				", 予約id" + id +
				'}';
	}

}
