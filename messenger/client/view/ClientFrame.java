package messenger.client.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.DefaultListSelectionModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import messenger._db.vo.MemberVO;
import messenger._db.vo.RoomVO;
import messenger.client.member_table.MemberVOTable;
import messenger.client.member_table.MemberVOTableModel;
import messenger.client.view.dialog.ChatDialog;
import messenger.client.view.dialog.CreateRoomDialog;
import messenger.client.view.dialog.ProfileDialog;
import messenger.client.view.dialog.SearchDialog;
import messenger.client.view.dialog.UpdateDialog;
/*2018-07-03 수정자 김재현 
 *1. 패스워드 1 2 3 4 글자 그대로 보이는거 수정 
 *2. 특수 문자 입력 제한
 */
public class ClientFrame extends JFrame implements ActionListener, FocusListener, KeyListener{
	//선언부
		Joongbok 	jb			= new Joongbok(this);
		ClientData	clientData	= new ClientData(this);
		int			answer		= 0;
		
		//로그인 패널
		CardLayout 		card		= new CardLayout();
		JPanel 			jp_card		= new JPanel();
		JPanel 			jp_login 	= new JPanel();
		JLabel			jp_img 		= new JLabel();
		JLabel			Rweback		= new JLabel();
		JLabel			Lweback		= new JLabel();
		JTextField 		jtf_id   	= new JTextField(20);
		JPasswordField  jtf_pw		= new JPasswordField(20);
		JButton 		jbtn_log 	= new JButton("로그인");
		JMenuItem		jmi_gaip	= new JMenuItem("회원가입");
		JMenuItem		jmi_system	= new JMenuItem("나가기");
		
		//가입창 패널
		JPanel 				jp_gaip		= new JPanel();
		JLabel 				jl_gid  	= new JLabel("아이디 :");
		JLabel 				jl_gpw 		= new JLabel("비밀번호           :");
		JLabel 				jl_gpw_re 	= new JLabel("비밀번호 확인 :");
		JLabel 				jl_gname 	= new JLabel("이름 :");
		JLabel 				jl_ggender  = new JLabel("성별 :");
		JLabel 				jl_ghp		= new JLabel("핸드폰번호 :");
		JTextField  		jtf_gid		= new JTextField(20);
		JPasswordField 		jtf_gpw		= new JPasswordField(20);
		JPasswordField 		jtf_gpw_re	= new JPasswordField(20);
		JTextField  		jtf_gname 	= new JTextField(20);
		JTextField			jtf_ghp 	= new JTextField(20);
		String[]			genderList  = {"남","여"};
		JComboBox<String>	jtf_ggender = new JComboBox<String>(genderList);
		JMenuItem			jmi_get		= new JMenuItem("   회     원     가     입");
		JMenuItem			jmi_back	= new JMenuItem("   뒤     로     가     기");
		JButton				jbtn_bok	= new JButton("중복검사");
		
		// 메뉴바
		JMenuBar  jmb_menu 		= new JMenuBar();
		JMenuItem jmi_fri 		= new JMenuItem("친구목록");
		JMenuItem jmi_chat 		= new JMenuItem("대화목록");
		JMenu 	  jm_add 		= new JMenu("메뉴");
		JMenuItem jmi_addfri 	= new JMenuItem("새로운 친구");
		JMenuItem jmi_addchat   = new JMenuItem("새로운 채팅");
		JMenuItem jmi_upd 		= new JMenuItem("회원정보수정");
		JMenuItem jmi_exit 		= new JMenuItem("종료");
		
		//이스터에그 다이얼로그창
		
		
		//친구목록 패널
		JPanel				jp_List			= new JPanel();
		MemberVOTableModel	myTableModel 	= new MemberVOTableModel("나");
		MemberVOTableModel	friendTableModel= new MemberVOTableModel("친구 리스트");
		MemberVOTable		myTable			= new MemberVOTable();
		MemberVOTable		friendTable		= new MemberVOTable();		
		JScrollPane			jsp_friendTable = new JScrollPane(friendTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
																	 	 , JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		//대화방목록 패널
		JPanel				jp_chat 	= new JPanel();
		DefaultTableModel 	dtm_room  	= new DefaultTableModel(new String[] {"번호", "제목"}, 0) {
													//방 목록 수정
												 	@Override
												 	public boolean isCellEditable(int row, int column) {
												 		return false;
												 	}
				 						};
		JTable				jt_room 	= new JTable(dtm_room);
		JScrollPane 		jsp_room 	= new JScrollPane(jt_room, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
																 , JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); 
		
		//마우스 팝업 메뉴
		JPopupMenu popup 	   = new JPopupMenu();
		JMenuItem  jmi_popprof = new JMenuItem("프로필보기");
		JMenuItem  jmi_popdel  = new JMenuItem("삭제하기");
		JMenuItem  jmi_popfile = new JMenuItem("파일첨부");
		JMenuItem  jmi_popcall = new JMenuItem("통화하기");
		
		// 임시 이미지 소스
		String noname = "/messenger/client/images/";
		
	//화면부
		public void initDisplay() {
		//메인 액션
			jmi_gaip.addActionListener(this);
			jbtn_log.addActionListener(this);
			jmi_system.addActionListener(this);
			jmi_get.addActionListener(this);
			jmi_back.addActionListener(this);
			jbtn_bok.addActionListener(this);
			jmi_fri.addActionListener(this);
			jmi_chat.addActionListener(this);
			jmi_addfri.addActionListener(this);
			jmi_addchat.addActionListener(this);
			jmi_upd.addActionListener(this);
			jmi_exit.addActionListener(this);
			
			////////////키 리스너 추가//////////////
			//글자수 제한(아이디,비밀번호, 이름, 상태메세지, 전화번호)
			//엔터시 기능 실행(로그인에서 비밀번호입력 후 엔터, 채팅방 다이얼로그에서 엔터시 전달)
			jtf_id.addKeyListener(this);
			jtf_gid.addKeyListener(this);
			jtf_pw.addKeyListener(this);
			jtf_gpw.addKeyListener(this);
			jtf_gpw_re.addKeyListener(this);
			jtf_gname.addKeyListener(this);
			jtf_ghp.addKeyListener(this);
			
			////////////키 리스너 추가 끝////////////
			
			//포커스 리스너 추가(로그인 창 - 아이디, 비밀번호)
			jtf_id.addFocusListener(this);
			jtf_pw.addFocusListener(this);
			
			//카드 패널
			jp_card.setLayout(card);
			jp_card.add(jp_login,"로그인창");
			jp_card.add(jp_gaip,"가입창");
			jp_card.add(jp_List, "친구목록");
			jp_card.add(jp_chat, "대화목록");
			//프레임
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setTitle("보노보노톡");
			this.setSize(380, 550);
			this.setVisible(true);
			this.add(jp_card, BorderLayout.CENTER);
			char c = '*';
			
		//로그인창패널
			//로그인창
			jp_login.add(jp_img);
			jp_login.add(Rweback);
			jp_login.add(Lweback);
			jp_login.add(jtf_id);
			jp_login.add(jtf_pw);
			jp_login.add(jbtn_log);
			jp_login.add(jmi_gaip);
			jp_login.add(jmi_system);
			jp_login.setLayout(null);
			jp_login.setBackground(new Color(126, 195, 237));
			jp_img.setBounds(70, 10, 250, 260);
			jp_img.setIcon(new ImageIcon(ClientFrame.class.getResource(noname+"bonobono2.jpg")));
			//Rweback.setBounds(0,0,380,550);
			//Lweback.setBounds(50,0,10,10);
			//Rweback.setIcon(new ImageIcon(ClientFrame.class.getResource(noname+"훈태(우).png")));
			//Lweback.setIcon(new ImageIcon(ClientFrame.class.getResource(noname+"Lweback.jpg")));
			jtf_id.setBounds(60, 260, 250, 30);
			jtf_id.setText("");
			jtf_pw.setBounds(60, 290, 250, 30);
			jtf_pw.setEchoChar(c);
			jtf_pw.setText("비밀번호");
			jbtn_log.setBounds(60, 340, 250, 30);
			jbtn_log.setBackground(new Color(126, 195, 237));
			jmi_gaip.setBounds(90, 460, 100, 20);
			jmi_gaip.setHorizontalTextPosition(0);
			jmi_gaip.setBackground(new Color(126, 195, 237));
			jmi_system.setBounds(210, 460, 100, 20);
			jmi_system.setHorizontalTextPosition(0);
			jmi_system.setBackground(new Color(126, 195, 237));
			
			
		//가입창 패널
			jp_gaip.setLayout(null);
			jp_gaip.add(jmi_get);
			jp_gaip.add(jmi_back);
			jp_gaip.add(jbtn_bok);
			jp_gaip.add(jl_gid);
			jp_gaip.add(jl_gpw);
			jp_gaip.add(jl_gpw_re);
			jp_gaip.add(jl_gname);
			jp_gaip.add(jl_ghp);
			jp_gaip.add(jl_ggender);
			jp_gaip.add(jtf_gid);
			jp_gaip.add(jtf_gpw);
			jp_gaip.add(jtf_gpw_re);
			jp_gaip.add(jtf_gname);
			jp_gaip.add(jtf_ggender); 
			jp_gaip.add(jtf_ghp);
			jp_gaip.setBackground(new Color(126, 195, 237));
			jl_gid.setBounds(40, 70, 180, 20);
			jbtn_bok.setBounds(160, 110, 160, 20);
			jbtn_bok.setBackground(new Color(126, 195, 237));
			jl_gpw.setBounds(40, 150, 100, 20);
			jl_gpw_re.setBounds(40, 190, 100, 20);
			jl_gname.setBounds(40, 230, 80, 20);
			jl_ggender.setBounds(40, 270, 80, 20);
			jl_ghp.setBounds(40, 310, 80, 20);
			jtf_gid.setBounds(160, 70, 160, 20);
			jtf_gpw.setBounds(160, 150, 160, 20);
			jtf_gpw.setEchoChar(c);
			jtf_gpw_re.setBounds(160, 190, 160, 20);
			jtf_gname.setBounds(160, 230, 160, 20);
			jtf_ggender.setBounds(160, 270, 160, 20);
			jtf_ghp.setBounds(160, 310, 160, 20);
			jmi_get.setBounds(40, 430, 140, 30);
			jmi_get.setBackground(new Color(126, 195, 237));
			jmi_back.setBounds(180, 430, 140, 30);
			jmi_back.setBackground(new Color(126, 195, 237));
		
			//친구 목록 패널 생성//////////
			jp_List.setLayout(new BorderLayout());
			myTable.setModel(myTableModel);
			friendTable.setModel(friendTableModel);
			jp_List.add("North", myTable);
			jp_List.add("Center", jsp_friendTable);
			//친구 목록 패널 생성 끝/////
			
			//방 목록 패널 생성////////////////////
			jp_chat.setLayout(new BorderLayout());
			jt_room.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
			//방 목록에서 방을 클릭했을때 채팅방 다이얼로그를 화면에 띄운다.
			jt_room.addMouseListener(new MouseAdapter() {
				//대화방 클릭시 이벤트
				@Override
				public void mouseClicked(MouseEvent e) {
					if(e.getClickCount() == 2) { //방을 우클릭했을 때 - 우클릭 대신 좋은거 찾을 필요가 있음.
						int row = jt_room.getSelectedRow();
						int room_no = Integer.parseInt((String)jt_room.getValueAt(row, 0));
						ChatDialog dialog = clientData.getChatDialog(room_no);
						if(dialog != null) 
							dialog.setVisible(true);
					}
				}
			});
			//자기 프로필 보기
			myTable.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(e.getClickCount() == 2) {
						System.out.println("내 프로필 보기");
						int row = myTable.getSelectedRow();
						MemberVO memVO = myTableModel.getValueAt(row, 0);
						new ProfileDialog(memVO);
						
					}
				}
			});
			jp_chat.add("Center", jsp_room);
			////방 목록 패널 생성 끝////////////////////////////////
		// 메인화면
			this.add("North", jmb_menu);
			jmb_menu.setVisible(false);
			jmb_menu.setBackground(new Color(126, 195, 237));
			// 사이드 메뉴바
			jmb_menu.setLayout(new GridLayout(1, 5));
			jmb_menu.add(jmi_fri);
			jmb_menu.add(jmi_chat);
			jmb_menu.add(jm_add);
			jm_add.add(jmi_addfri);
			jm_add.add(jmi_addchat);
			jm_add.add(jmi_upd);
			jm_add.add(jmi_exit);
			jm_add.setBackground(new Color(126, 195, 237));
			jmi_fri.setBackground(new Color(126, 195, 237));
			jmi_chat.setBackground(new Color(126, 195, 237));
			jmi_addfri.setBackground(new Color(126, 195, 237));
			jmi_addchat.setBackground(new Color(126, 195, 237));
			jmi_upd.setBackground(new Color(126, 195, 237));
			jmi_exit.setBackground(new Color(126, 195, 237));
			
			//마우스 팝업 레이아웃
			popup = new JPopupMenu();
			popup.add(jmi_popprof);
			popup.add(jmi_popdel);
			popup.add(jmi_popfile);
			popup.add(jmi_popcall);
			friendTable.add(popup);
			//팝업마우스 액션리스너
			friendTable.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					// 오른쪽 버튼 클릭 시 ...
					if(e.getModifiers() == MouseEvent.BUTTON3_MASK) {
					    popup.show(friendTable, e.getX(), e.getY());
					}
				}
			});
			jmi_popprof.addActionListener(this);
			jmi_popdel.addActionListener(this);
			jmi_popcall.addActionListener(this);
			jmi_popfile.addActionListener(this);
			
		}///////////// end initDisplay
		
	//친구목록 패널창 관련 메소드//////////////
		/**
		 * 내 정보를 보여주는 테이블을 갱신한다.
		 * @param mVO : 내 정보
		 */
		//내정보 테이블 새로고침
		public synchronized void refreshMyTable(MemberVO mVO) {
			if(myTableModel.getRowCount() > 0)
				myTableModel.removeRow(0);
			myTableModel.addRow(mVO);
		}
		/**
		 * 친구 목록 테이블을 갱신한다.
		 * @param mVOs : 친구 리스트
		 */
		//친구정보 테이블 새로고침
		public synchronized void refreshFriendTable(ArrayList<MemberVO> mVOs) {
			while(friendTableModel.getRowCount() > 0)
				friendTableModel.removeRow(0);
			for(MemberVO mVO : mVOs)
				friendTableModel.addRow(mVO);
		}
		/**
		 * 친구 목록 테이블을 리턴
		 * @return 친구목록 테이블
		 */
		public MemberVOTableModel getFriendTableModel() {
			return friendTableModel;
		}
	//친구목록 패널창 관련 메소드 끝//////////////
		
	//대화방 목록 패널창 관련 메소드//////////////
		//대화방 목록 새로고침
		public synchronized void refreshRoomList(ArrayList<RoomVO> rVOList) {
			clientData.refreshRoomList(rVOList);
			
			dtm_room.setRowCount(0);
			for(RoomVO rVO : rVOList) {
				dtm_room.addRow(new String[] {Integer.toString(rVO.getRoom_no()), rVO.getRoom_title()});
			}
			
		}
	//대화방 목록 패널창 관련 메소드 끝/////////////		
		
		//성별 박스
		public String getGender() {
			if("남".equals(jtf_ggender.getSelectedItem())) return "1";
			else return "2";
		}
		public void setGender(String gender) {
		if("남".equals(gender)) jtf_ggender.setSelectedItem("남");
		else jtf_ggender.setSelectedItem("여");
		}////////////// end Gender

		public ClientData getClientData() {
			return clientData;
		}
		
	//메인메소드
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClientFrame ui = new ClientFrame();
		ui.initDisplay();
	}////////////// end main
	
		
		//버튼 액션부
		@Override
		public void actionPerformed(ActionEvent e) {
			//로그인창 이벤트
			if(e.getActionCommand().equals("회원가입")) {
				card.show(jp_card,"가입창");
			} else if(e.getActionCommand().equals("로그인")) {
				if(clientData.login(jtf_id.getText(), jtf_pw.getText())) {
					//로그인 성공시 멤버서버에 연결하여 친구리스트가져오기, 채팅서버에 연결하여 방 리스트 가져오기, 이모티콘서버에 연결하여 이모티콘 받아오기 수행
					clientData.getEmoticonFromServer();
					refreshMyTable(clientData.getMyData());
					clientData.actionFriendList();
					clientData.initChat();
					card.show(jp_card,"친구목록");
					jmb_menu.setVisible(true);
				}
				else {
					//로그인 실패시 메세지 다이얼로그를 띄운다.
					JOptionPane.showMessageDialog(null, "에러", "로그인 실패", JOptionPane.ERROR_MESSAGE);
				}
			} else if(e.getActionCommand().equals("나가기")) {
				System.exit(0);
			}
			
			// 회원가입 이벤트
			//중복검사
			else if(e.getActionCommand().equals("중복검사")) {
				answer = jb.Gumsa();
			}
			if(e.getSource()==jmi_get) {
				if(jb.answer != 1){
					JOptionPane.showMessageDialog(jp_gaip, "아이디를 중복검사를 해주세요.", "Error", JOptionPane.ERROR_MESSAGE);
				}else if(jb.answer == 1) {
					if(!jtf_gpw.getText().equals(jtf_gpw_re.getText())) {
						JOptionPane.showMessageDialog(jp_gaip, "비밀번호가 같지 않습니다.", "Error", JOptionPane.ERROR_MESSAGE);
					}else {
						ClientJoin cj = new ClientJoin();
						cj.setID(jtf_gid.getText());
						cj.setPW(jtf_gpw.getText());
						cj.setName(jtf_gname.getText());
						cj.setGender((String)jtf_ggender.getSelectedItem());
						cj.setHP(jtf_ghp.getText());
						cj.result =	cj.Join(cj);
						System.out.println("액션리스너 cj.result"+cj.result);
						if(cj.result == 1) {
							System.out.println("가입성공");
							JOptionPane.showMessageDialog(jp_gaip, "가입되었습니다.", "가입성공", JOptionPane.DEFAULT_OPTION);
						}
						else {
							JOptionPane.showMessageDialog(jp_gaip, "가입에 실패하였습니다.", "가입실패", JOptionPane.DEFAULT_OPTION);
						}
					}	
				}
			} else if(e.getSource()==jmi_back) {
				card.show(jp_card,"로그인창"); 
			}
			
			// 메인화면 이벤트
			if (e.getActionCommand().equals("친구목록")) {
				card.show(jp_card, "친구목록");
			} else if (e.getActionCommand().equals("대화목록")) {
				card.show(jp_card, "대화목록");
			} 
			
			// 메뉴바 이벤트
			if (e.getActionCommand().equals("새로운 친구")) {
				new SearchDialog(this);
			} else if (e.getActionCommand().equals("새로운 채팅")) {
				new CreateRoomDialog(this);
			} else if (e.getActionCommand().equals("회원정보수정")) {
				new UpdateDialog(this);
			} else if (e.getActionCommand().equals("종료")) {
				System.exit(0);
			}
			
			//마우스 팝업 이벤트
			if(e.getActionCommand().equals("프로필보기")) {
				int row = friendTable.getSelectedRow();
				if(row == -1)
					return;
				MemberVO memVO = friendTableModel.getValueAt(row, 0);
				new ProfileDialog(memVO);
			} else if(e.getActionCommand().equals("삭제하기")) {
				int row = friendTable.getSelectedRow();
				if(row == -1)
					return;
				MemberVO f_memVO = friendTableModel.getValueAt(row, 0);
				String f_id = f_memVO.getMem_id();
				System.out.println("삭제하기. 친구 아이디 : " + f_id);
				clientData.actionDeleteFriend(f_id);
			}
			else if(e.getActionCommand().equals("통화하기")) {
				JOptionPane.showMessageDialog(null, "그냥... 카톡쓰세요 왜 이걸써요...", "Easter Egg", 1);
				System.out.println("그냥.. 카톡쓰세요...");
				JOptionPane.showMessageDialog(null, "힝!! 속았지!!? 전화 연결중 입니다.", "Easter Egg", 2);
				JOptionPane.showMessageDialog(null, "는~ 또속았지렁~ 이거 쓰지말고 카톡써요^_^", "Easter Egg", 3);
				
			}
			else if(e.getActionCommand().equals("파일첨부")) {
				JOptionPane.showInputDialog(null, "파일경로를 입력해주세요!!", "Easter Egg", 1);
				System.out.println("그냥.. 카톡쓰세요...");
				JOptionPane.showMessageDialog(null, "그냥... 카톡쓰세요 왜 이걸써요...", "Easter Egg", 1);
				
			}
		}

		@Override
		public void focusGained(FocusEvent e) {
			if(e.getSource() == jtf_id) {
				if(jtf_id.getText().equals("아이디"))
					jtf_id.setText("");
			}
			else if(e.getSource() == jtf_pw) {
				if(jtf_pw.getText().equals("비밀번호"))
					jtf_pw.setText("");
			}
			
		}


		@Override
		public void focusLost(FocusEvent e) {
			if(e.getSource() == jtf_id) {
				if(jtf_id.getText().length() == 0)
					jtf_id.setText("아이디");
			}
			else if(e.getSource() == jtf_pw) {
				if(jtf_pw.getText().length() == 0)
					jtf_pw.setText("비밀번호");
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {}

		@Override
		public void keyReleased(KeyEvent e) {}

		@Override
		public void keyTyped(KeyEvent e) {
			if(e.getSource() == jtf_id) {
				if(Character.isLetterOrDigit(e.getKeyChar()) == false)
					e.consume();
				else if(jtf_id.getText().length()>=20)
					e.consume();
			}
			else if(e.getSource() == jtf_gid) {
				if(Character.isLetterOrDigit(e.getKeyChar()) == false)
					e.consume();
				else if(jtf_gid.getText().length()>=20)
					e.consume();
			}
			else if(e.getSource() == jtf_pw) {
				if(e.getKeyChar()=='\n') {
					if(clientData.login(jtf_id.getText(), jtf_pw.getText())) {
						//로그인 성공시 멤버서버에 연결하여 친구리스트가져오기, 채팅서버에 연결하여 방 리스트 가져오기, 이모티콘서버에 연결하여 이모티콘 받아오기 수행
						clientData.getEmoticonFromServer();
						refreshMyTable(clientData.getMyData());
						clientData.actionFriendList();
						clientData.initChat();
						card.show(jp_card,"친구목록");
						jmb_menu.setVisible(true);
					}
					else {
						//로그인 실패시 메세지 다이얼로그를 띄운다.
						JOptionPane.showMessageDialog(null, "에러", "로그인 실패", JOptionPane.ERROR_MESSAGE);
					}
				}
				else if(Character.isLetterOrDigit(e.getKeyChar()) == false)
					e.consume();
				else if(jtf_pw.getText().length()>=20)
					e.consume();
			}
			else if(e.getSource() == jtf_gpw) {
				if(Character.isLetterOrDigit(e.getKeyChar()) == false)
					e.consume();
				else if(jtf_gpw.getText().length()>=20)
					e.consume();
			}
			else if(e.getSource() == jtf_gpw_re) {
				if(Character.isLetterOrDigit(e.getKeyChar()) == false)
					e.consume();
				else if(jtf_gpw_re.getText().length()>=20)
					e.consume();
			}
			else if(e.getSource() == jtf_gname) {
				if(Character.isLetter(e.getKeyChar()) == false)
					e.consume();
				else if(jtf_gname.getText().length()>=20)
					e.consume();
			}
			else if(e.getSource() == jtf_ghp) {
				if(jtf_ghp.getText().length()>=20)
					e.consume();
			}
		}
}

