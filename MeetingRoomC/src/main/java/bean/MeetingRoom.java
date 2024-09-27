package bean;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import dao.ReservationDao;
import dao.RoomDao;
import dao.UserDao;

public class MeetingRoom implements Serializable{

	//フィールド
	private static final long serialVersionUID = 1L;

	private String date;//利用日
	private final int INTERVAL = 60;//利用時間60分固定
	private String[]  PERIOD = {"09:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00"};//予約できる時間帯固定
	private RoomBean[] rooms;//会議室たち
	private UserBean user;//利用者

	//コンストラクタ
	//インスタンス化した際にはＤＢ登録済みルーム一覧、本日の日付だけが登録される
	public MeetingRoom() {
		Calendar today = Calendar.getInstance();
		Date date = today.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sdf.format(date);
		this.date = dateStr;
		RoomDao rd = new RoomDao();
		this.rooms = rd.findAll();

	}

	//メソッド


	/**roomIdから会議室の配列の添え字取得
	 * 
	 * @param roomId
	 * 予約したい会議室のID:String
	 * @return
	 * 添字表の縦
	 * @throws IndexOutOfBoundsException
	 * 存在しない配列を参照時
	 */
	public int roomIndex(String roomId) throws IndexOutOfBoundsException {
		int soeji =-1;
		for(int i =0;i<rooms.length;i++) {//DB登録済み会議室数の配列数
			if(roomId.equals(rooms[i].getId())) {//DB会議室IDと入力されたIDが同一か
				soeji = i;
			}
		}
		if(soeji==-1) {//存在してない時
			throw new IndexOutOfBoundsException();
		}
		return soeji;
	}


	/**開始時間に対する添え字取得
	 * 
	 * @param start
	 * 開始時間：String
	 * @return
	 * 添字表の横
	 * @throws IndexOutOfBoundsException
	 * 存在しない配列を参照時
	 */
	public int startPeriod(String start) throws IndexOutOfBoundsException{
		int soeji =-1;//ありえない値
		for(int i = 0 ; i<PERIOD.length;i++) {//PIRIOD配列
			if(start.equals(PERIOD[i])) {//開始時間と同一か
				soeji = i;
			}
		}if(soeji==-1) {//存在してない時
			throw new IndexOutOfBoundsException();
		}
		return soeji;

	}
	/**
	 * 利用可能時間帯一覧の配列
	 * @return 
	 * 開始時間一覧(String)登録された配列
	 */
	public String[] getPeriod() {
		return this.PERIOD;
	}


	/**利用可能会議室の一覧
	 * 
	 * @return
	 * DB登録済み会議室（RoomBean）配列
	 */
	public RoomBean[] getRooms() {
		return this.rooms;
	}


	/**会議室配列からIDを利用して会議室の情報を返す
	 * 
	 * @param roomId
	 * ルームID
	 * @return
	 * 会議室の情報（RoomBean）
	 * なければnull
	 */
	public RoomBean getRoom(String roomId) {
		RoomBean room = null;
		for(RoomBean list:rooms) {//会議室一覧の配列を拡張for文で確認
			if(roomId.equals(list.getId())){//イコールズで同一の値検索
				room = list;//見つけた場合roomに代入
			}
		}
		return room;

	}
	//ユーザービーン(利用者ID、パスワード、氏名、住所)
	public UserBean getUser() {
		return user;
	}
	//利用日
	public String getDate() {
		return date;
	}

	//inputから飛んできた予約日、キャンセル日を会議室へ登録
	public void setDate(String date) {
		this.date = date;

	}
	/**今ログインしている人誰だ存在している？
	 * 
	 * @param id
	 * ログインID
	 * @param password
	 * ログインパスワード
	 * @return
	 * true/false
	 */
	public boolean login(String id,String password) {
		UserDao ud = new UserDao();
		UserBean user= ud.certificate(id, password);
		if( user != null) {
			this.user = user;
			return true;
		}
		return false;
	}

	//会議室予約システムの利用日における予約状況
	public ReservationBean[][] getReservation(){
		//予約だお↓
		ReservationDao rd = new ReservationDao();//予約だおインスタンス化
		//だおからArrayListで予約一覧を受け取る↓
		List<ReservationBean> list=new ArrayList<ReservationBean>(rd.findByDate(date));//予約済みあればList<ReservationBean>で返る
		//二次元配列の形で予約リストを作成↓
		ReservationBean[][] rb =new ReservationBean[rooms.length][PERIOD.length];
		//すべてnullで生成
		for(int i=0;i<rooms.length;i++) {
			//会議室一覧の繰り返し
			for(int j=0;j<PERIOD.length;j++) {
				//時間帯の繰り返し
				rb[i][j]=null;
			}	
		}	
		
		for(int i=0;i<rooms.length;i++) {
			//会議室一覧の繰り返し
			for(int j=0;j<PERIOD.length;j++) {
				//時間帯の繰り返し
				
				for(ReservationBean lists:list) {
					if(this.roomIndex(lists.getRoomId()) ==i && this.startPeriod(lists.getStart())==j) {
						rb[i][j]= lists; 
												
					}
				}
			}
		}
		return rb;

	}
	/**
	 * 
	 * @param roomId
	 * 予約会議室ID
	 * @param start
	 * 予約開始時間
	 * @return
	 * 予約した情報（ReservationBean）
	 * 無理だったらエラーとnull
	 * @throws ParseException 
	 */
	public ReservationBean createReservation(String roomId,String start) {

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");//フォーマット用
		Date sta = null;
		try {
			sta = sdf.parse(start);//Stringからデータへ
			Calendar calendar = Calendar.getInstance();//カレンダーをインスタンス化
			calendar.setTime(sta);//時刻操作のために終了時刻（予定）をカレンダーに
			calendar.add(Calendar.MINUTE,INTERVAL );//６０分追加
			Date date = calendar.getTime();//進めた値をカレンダー型からデータに
			String end = sdf.format(date);//データからStringに
			String uid =this.user.getId();//ユーザーID取得
			ReservationBean reservation = new ReservationBean(roomId,this.date,start,end,uid);
			return reservation;//追加した値を返す

		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}//スタート時間文字列をDate型に
	}

	/**予約登録機能
	 * 現時刻と比較し利用時間チェック
	 * 
	 * @param reservation
	 * 予約するよデータ
	 * @throws Exception
	 * 選んだ時間が【予約済み】or【時間が超過】している場合はエラーを投げる
	 */
	public void reserve(ReservationBean reservation) throws Exception{

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-ddHH:mm");//揃え
		
		Calendar now = Calendar.getInstance();//現時刻
		Date ima = now.getTime();//現時刻をデータ変換
		
		Date resrve = sdf.parse(reservation.getDate()+reservation.getStart());//これでリザベーションと今が同じ形になるはず

		if(resrve.compareTo(ima)>0) { //現時刻と予約時刻比較大丈夫なら１
			ReservationDao rd = new ReservationDao();//リザベーションだお
			if(rd.insert(reservation)==false) {//trueかえらんかったら予約済み
				throw new Exception("予約済みです");
			}
		}else {
			throw new Exception("予約可能時刻が過ぎています");
		}
	}
	
/**キャンセル機能
 * 
 * @param reservation
 * キャンセルしたいリザベーション
 * @throws Exception
 * 時間過ぎてるかキャンセル済み
 */
	public void cancel(ReservationBean reservation) throws Exception{

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-ddHH:mm");//揃え
	
		Calendar now = Calendar.getInstance();//現時刻
		Date ima = now.getTime();//現時刻データ化
	
		Date cancel = sdf.parse(reservation.getDate()+reservation.getStart());//キャンセルしたい時間をデータ型に
		
		if(cancel.compareTo(ima)>0) { //現時刻と予約時刻比較大丈夫なら１
			ReservationDao rd = new ReservationDao();//リザベーションだお
			if(rd.delete(reservation)==false){
				throw new Exception("キャンセル済みです");}
		}else {//もうその時間なってる時、過ぎた時
			throw new Exception("キャンセル可能時刻がすぎています");
		}

	}

	@Override
	public String toString() {
		String room = "";
		for(RoomBean list:rooms) {
			room += list.toString()+",";
		}
		return "利用者:"+user.getName()+" 会議室:"+ room +" 日付:" + date  ;
	}





}
