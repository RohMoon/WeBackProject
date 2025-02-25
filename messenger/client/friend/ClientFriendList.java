package messenger.client.friend;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import messenger._db.vo.MemberVO;
import messenger._protocol.Message;
import messenger._protocol.Port;
import messenger._protocol.Server;
import messenger.client.view.ClientFrame;

/**********************************************************************
 * [클러이언트]친구리스트 불러오기
 * 기능 : 본인회원번호만 담아서 서버로 전달. 서버로부터 MemberVO의 리스트를 받은 후 친구목록 갱신하기
 * @author1 이정렬...18/06/26
 * [시나리오]
 * 1. 본인회원정보(userNo)를 초기화하기
 * 2. 소켓생성후 서버로 친구목록을 요청하도록 메소드로 실행시키기(수신되고나면 닫기)
 * 3. 서버에서 넘어온 친구목록을 받는 메소드를 실행시키기(무한반복으로 열어두기)
 * 4. 2번과 3번을 순차적으로 실행하는 메소드를 만들어서, UI에서 호출하면 바로 작동하도록 하기
 * 
 * @author2 이정렬...18/06/27
 * [체크사항]
 * 1. oos를 선언한 이후 write를 먼저하고, ois를 선언할 것
 * 2. 사용자의 회원번호를 넘길때, userNo로 바로넘기는것이 아니라,
 *    MemberVO와 Message클래스, ArrayList를 이용할 것
 * 3. 서버에 요청하는 MemberVO는 request
 * 4. 서버로 부터 받아온 MemberVO는 response : 이 내용을 해체하여 UI에 올리기
 * 5. Table형태로 올려서 열람하기 (JTable)
 * 
 * @author3 이정렬...18/06/27
 * [체크사항]
 * 1. 서버와의 송수신 확인완료 : Vector타입에 찢어담을 필요없음 (주석확인)
 * 
 * @author4 이정렬...18/07/02
 * [체크사항]
 * 1. 서버측과의 데이터송수신 테스트완료 : 친구목록 전체조회/검색/추가/삭제
 * 2. 뷰 단에서 사용할 수 있도록, test파일처럼 진행해보기
 *
 **********************************************************************/
public class ClientFriendList extends Thread {

	int userNo	= 0;
	ClientFrame frame = null;  //화면에 담는 f_Panel 전역변수
	
	Message<MemberVO> mms = null;//Client-Server간 주고받을 메세지와
	List<MemberVO> mli = null;	 //메시지에 담길 자료구조 List
	MemberVO mvo = null;		 //List에 담겨질 클래스자료 MemberVO
	
	//int타입 생성자가 있어서 만들어준 디펄트 생성자
	public ClientFriendList() {}
	
	//userNo 전역변수 초기화
	public ClientFriendList(int userNo) {
		this.userNo = userNo;
	}
	
	//userNo 전역변수 초기화
	//화면에 담는 f_Panel 전역변수 초기화 추가
	public ClientFriendList(int userNo,ClientFrame frame) {
		this.userNo = userNo;
		this.frame = frame;
	}

	//본인회원번호를 서버로 전달하기
	public void getFriendList() {//UI에서 호출할때 사용할 메소드
		mms = new Message<MemberVO>();
		mli = new ArrayList<MemberVO>();
		mvo = new MemberVO();
		
		mvo.setMem_no(userNo);
		
		mli.add(mvo);
		mms.setRequest(mli);//보낼 데이터를 메시지로 묶음
		mms.setType(Message.FRIEND_ALL);//타입설정 -18.06.27 이진 디버깅
//		cf = new ClientFriend(mms, this);//메시지를 넘겨서 start()호출
		
		try(
			Socket socket = new Socket(Server.IP, Port.MEMBER);
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());	
		) {
			oos.writeObject(mms);
			try(
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			) {
				mms = (Message<MemberVO>)ois.readObject();
			} catch(Exception e) {
				e.printStackTrace();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		List<MemberVO> res = mms.getResponse();
		setFriendList(res);
	}
	
	//전달 받은 정보를 UI에 띄우기(run메소드에서 호출)
	public void setFriendList(List<MemberVO> res) {
		frame.refreshMyTable(frame.getClientData().getMyData());
		frame.refreshFriendTable((ArrayList<MemberVO>) res);
	}//////////////end of setFriendList
		
}//////////////////end of class
