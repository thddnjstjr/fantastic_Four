
package gomoku.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import gomoku.service.Rule33;
import gomoku.service.Timer;
import gomoku.service.WinRule;

public class Background extends JFrame implements ActionListener {

	final int LINE_WIDTH = 1900; // 바둑판 x축 크기
	final int LINE_HEIGHT = 1900; // 바둑판 y축 크기

	private final int[][] MAP = new int[LINE_WIDTH][LINE_HEIGHT]; // 바둑판 맵 좌표

	private int color; // 턴이 흑돌인지 백돌인지 구별
	private int blackcount; // 흑돌의 갯수
	private int whitecount; // 백돌의 갯수
	private int choicecount; // 캐릭터 선택 턴 짝수 : 흑돌 턴 홀수 : 백돌 턴
	private int blackwin; // 흑돌 승리 횟수
	private int whitewin; // 백돌 승리 횟수
	private int total; // 흑돌 백돌 총 합 갯수
	private boolean replay; // 다시보기 여부
	private boolean blackWinner; // 흑이 이겼으면 true 기본은 false
	private boolean whiteWinner; // 백이 이겼으면 true 기본은 false
	private boolean game; // 게임이 시작했으면 true 게임이 종료되거나 시작하지않았으면 false
	private boolean history; // 무르기 에서 돌이 최소1개놓았을때 발동되거나 돌을 1번만 무를수있게 해주는 역할
	private boolean result; // 지금 화면이 결과 화면이면 true 그 외에는 false
	private boolean time; // true 일때부터 false 일때까지 시간을 잰다
	private Background mContext = this; // 다른 클래스들에게 이 클래스의 정보를 넘겨주기위해 만든 멤버변수
	public Target cursor; // 커서 객체 생성을 위해 만든 멤버변수
	private JLabel backgroundMAP; // 오목판
	private JButton button1; // 다시하기 버튼
	private JButton button2; // 종료하기 버튼
	private JButton button3; // 시작하기 버튼
	private JButton button4; // 무르기 버튼
	private JButton button5; // 캐릭터 선택창 버튼
	private JButton button6; // 나가기 버튼
	private JButton button7; // 메인메뉴로 가기 버튼
	private JButton button8; // 결과창가기 버튼
	private JButton button9; // 다시보기 버튼
	private JButton button10; // 다시보기 종료 버튼
	private JButton terran1; // 마린
	private JButton terran2; // 발키리
	private JButton terran3; // 탱크
	private JButton zerg1; // 인페스트 테란
	private JButton zerg2; // 저글링
	private JButton zerg3; // 케리건
	private JButton protoss1; // 질럿
	private JButton protoss2; // 프로브
	private JButton protoss3; // 아칸
	private JLabel resultbackground; // 결과창 배경
	private JLabel mainmenu; // 메인메뉴 배경
	private JLabel turn; // 현재 턴 돌 색깔
	private JLabel tag; // 현재 턴 돌 색깔 표지판
	private JLabel whitePlayer; // 백돌 플레이어 캐릭터
	private JLabel blackPlayer; // 흑돌 플레이어 캐릭터
	private JLabel player; // 현재 플레이어 캐릭터
	private JLabel playerlabel; // 현재 플레이어 캐릭터 테두리
	private JLabel backgroundLeft; // 게임화면 왼쪽배경
	private JLabel backgroundRight; // 게임화면 오른쪽 배경
	private JLabel selectBackground; // 캐릭터 선택창 배경
	private JLabel board; // 놓인 돌 갯수 배경
	private JLabel white; // 캐릭터 선택창 화이트 플레이어
	private JLabel black; // 캐릭터 선택창 블랙 플레이어
	private JLabel blank; // 캐릭터 선택창 테두리
	private JLabel blank2;// 캐릭터 선택창 테두리
	private JLabel win; // 승리했을때 나오는 화면
	private JLabel gamename; // 게임 타이틀 제목
	private JLabel timerBackground; // 타이머 백그라운드
	private Timer timer; // 타이머 객체 생성하기 위해 만든 멤버변수
	private boolean[] races = new boolean[6]; // 종족 선택 넣기 0:블랙 테란 1: 블랙 토스 2: 블랙 저그 3: 화이트 테란 4: 화이트 토스 5: 화이트 저그
	private CountdownTimer countdownTimer; // 타이머 클래스
	private WinRule winRule; // 승리조건 클래스
	private JLabel rule33board;
	private Rule33 rule33;
	
	

	public Background() {
		initData();
		setInitLayout();
		addEventListener();
		character();
		addKeyListener();
		addMouseEvent();
		races[0] = true; // 흑돌의 기본 종족은 테란 캐릭터는 마린으로 고정
		races[4] = true; // 백돌의 기본 종족은 프로토스 캐릭터는 질럿으로 고정
	}

	public int[][] getMAP() {
		return MAP;
	}

	public int getColor() {
		return color;
	}

	public boolean isBlackWinner() {
		return blackWinner;
	}

	public boolean isWhiteWinner() {
		return whiteWinner;
	}

	public boolean[] getRaces() {
		return races;
	}

	public boolean isTime() {
		return time;
	}

	public boolean isGame() {
		return game;
	}

	private void initData() {
		gamename = new JLabel(new ImageIcon("images/gamename.png"));
		white = new JLabel(new ImageIcon("images/white.png"));
		black = new JLabel(new ImageIcon("images/black.png"));
		blank = new JLabel(new ImageIcon("images/blank.png"));
		blank2 = new JLabel(new ImageIcon("images/blank.png"));
		backgroundMAP = new JLabel(new ImageIcon("images/omokbackground.png"));
		selectBackground = new JLabel(new ImageIcon("images/background4.jpg"));
		mainmenu = new JLabel(new ImageIcon("images/mainmenu.jpg"));
		turn = new JLabel(new ImageIcon("images/blackStone.png"));
		whitePlayer = new JLabel(new ImageIcon("images/protoss.gif"));
		blackPlayer = new JLabel(new ImageIcon("images/terran.gif"));
		backgroundLeft = new JLabel(new ImageIcon("images/backgroundLeft.jpg"));
		backgroundRight = new JLabel(new ImageIcon("images/backgroundRight.jpg"));
		playerlabel = new JLabel(new ImageIcon("images/player.png"));
		player = new JLabel(blackPlayer.getIcon());
		board = new JLabel(new ImageIcon("images/scoreboard.jpg"));
		tag = new JLabel(new ImageIcon("images/tag.png"));
		win = new JLabel(new ImageIcon("images/win.jpg"));
		button1 = new JButton(new ImageIcon("images/restart.png"));
		button2 = new JButton(new ImageIcon("images/exitbutton.png"));
		button3 = new JButton(new ImageIcon("images/start.png"));
		button4 = new JButton(new ImageIcon("images/back.png"));
		button5 = new JButton(new ImageIcon("images/character.png"));
		button6 = new JButton(new ImageIcon("images/quit.png"));
		button7 = new JButton(new ImageIcon("images/mainmenu.png"));
		button8 = new JButton(new ImageIcon("images/result.png"));
		button9 = new JButton(new ImageIcon("images/replay.png"));
		button10 = new JButton(new ImageIcon("images/replayexit.png"));
		terran1 = new JButton(new ImageIcon("images/terran.gif"));
		terran2 = new JButton(new ImageIcon("images/terran2.gif"));
		terran3 = new JButton(new ImageIcon("images/terran3.gif"));
		protoss1 = new JButton(new ImageIcon("images/protoss.gif"));
		protoss2 = new JButton(new ImageIcon("images/protoss2.gif"));
		protoss3 = new JButton(new ImageIcon("images/protoss3.gif"));
		zerg1 = new JButton(new ImageIcon("images/zerg1.gif"));
		zerg2 = new JButton(new ImageIcon("images/zerg2.gif"));
		zerg3 = new JButton(new ImageIcon("images/zerg3.gif"));
		timerBackground = new JLabel(new ImageIcon("images/timebackground1.png"));
		resultbackground = new JLabel(new ImageIcon("images/blackwin.gif"));
		cursor = new Target(mContext);
		rule33board = new JLabel(new ImageIcon("images/rule33background.png"));
		setContentPane(mainmenu); // 처음에는 메인메뉴를 배경으로 설정
		setTitle("오목크래프트");
		setSize(1900, 1000);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	private void setInitLayout() {
		setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		add(button3);
		add(button5);
		add(button6);
		add(gamename);
		button3.setBounds(420, 650, 150, 50);
		button4.setBounds(100, 800, 300, 100);
		button5.setBounds(310, 730, 400, 50);
		button6.setBounds(410, 810, 180, 50);
		button8.setBounds(580, 435, 620, 70);
		button10.setBounds(1470, 600, 250, 95);
		button1.setBorderPainted(false);
		button1.setContentAreaFilled(false);
		button2.setBorderPainted(false);
		button2.setContentAreaFilled(false);
		button3.setBorderPainted(false);
		button3.setContentAreaFilled(false);
		button4.setBorderPainted(false);
		button4.setContentAreaFilled(false);
		button5.setBorderPainted(false);
		button5.setContentAreaFilled(false);
		button6.setBorderPainted(false);
		button6.setContentAreaFilled(false);
		button7.setBorderPainted(false);
		button7.setContentAreaFilled(false);
		button8.setBorderPainted(false);
		button8.setContentAreaFilled(false);
		button9.setBorderPainted(false);
		button9.setContentAreaFilled(false);
		button10.setBorderPainted(false);
		button10.setContentAreaFilled(false);
		turn.setLocation(1600, 400);
		turn.setSize(100, 100);
		whitePlayer.setLocation(1460, 100);
		whitePlayer.setSize(180, 180);
		blackPlayer.setLocation(1650, 100);
		blackPlayer.setSize(180, 180);
		player.setLocation(1550, 80);
		player.setSize(200, 200);
		backgroundLeft.setLocation(0, 0);
		backgroundRight.setLocation(1440, 0);
		backgroundLeft.setSize(443, 1000);
		backgroundRight.setSize(470, 1000);
		tag.setLocation(1500, 280);
		tag.setSize(300, 300);
		playerlabel.setLocation(1525, 20);
		playerlabel.setSize(250, 280);
		board.setLocation(1450, 600);
		board.setSize(400, 350);
		black.setLocation(1080, 100);
		white.setLocation(1430, 100);
		blank.setLocation(1100, 180);
		blank2.setLocation(1450, 180);
		white.setSize(300, 100);
		black.setSize(300, 100);
		blank.setSize(250, 250);
		blank2.setSize(250, 250);
		win.setSize(800, 300);
		win.setLocation(500, 300);
		gamename.setLocation(350, 80);
		gamename.setSize(1300, 150);
		rule33board.setSize(700, 200);
		rule33board.setLocation(600, 300);
		timerBackground.setSize(370,370);
		timerBackground.setLocation(40,70);
	}

	private void addEventListener() {
		button1.addActionListener(this);
		button2.addActionListener(this);
		button3.addActionListener(this);
		button4.addActionListener(this);
		button5.addActionListener(this);
		button6.addActionListener(this);
		button7.addActionListener(this);
		button8.addActionListener(this);
		button9.addActionListener(this);
		button10.addActionListener(this);
		terran1.addActionListener(this);
		terran2.addActionListener(this);
		terran3.addActionListener(this);
		protoss1.addActionListener(this);
		protoss2.addActionListener(this);
		protoss3.addActionListener(this);
		zerg1.addActionListener(this);
		zerg2.addActionListener(this);
		zerg3.addActionListener(this);
	}

	private void addMouseEvent() { // 마우스 올렸을때 이벤트
		button1.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				button1.setIcon(new ImageIcon("images/yellowrestart.png"));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				button1.setIcon(new ImageIcon("images/restart.png"));
			}
		});
		button2.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				button2.setIcon(new ImageIcon("images/yellowexitbutton.png"));
				if (result == true) {
					button2.setSize(180, 55);
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				button2.setIcon(new ImageIcon("images/exitbutton.png"));
				if (result == true) {
					button2.setSize(200, 70);
				}
			}
		});
		button3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				button3.setIcon(new ImageIcon("images/yellowstart.png"));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				button3.setIcon(new ImageIcon("images/start.png"));
			}
		});
		button4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				button4.setIcon(new ImageIcon("images/yellowback.png"));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				button4.setIcon(new ImageIcon("images/back.png"));
			}
		});
		button5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				button5.setIcon(new ImageIcon("images/yellowcharacter.png"));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				button5.setIcon(new ImageIcon("images/character.png"));
			}
		});
		button6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				button6.setIcon(new ImageIcon("images/yellowquit.png"));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				button6.setIcon(new ImageIcon("images/quit.png"));
			}
		});
		button7.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				button7.setIcon(new ImageIcon("images/yellowmain.png"));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				button7.setIcon(new ImageIcon("images/mainmenu.png"));
				super.mouseExited(e);
			}
		});
		button8.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				button8.setIcon(new ImageIcon("images/yellowresult.png"));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				button8.setIcon(new ImageIcon("images/result.png"));
				super.mouseExited(e);
			}
		});
		button9.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				button9.setIcon(new ImageIcon("images/yellowreplay.png"));
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				button9.setIcon(new ImageIcon("images/replay.png"));
				super.mouseExited(e);
			}
		});
		button10.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				button10.setIcon(new ImageIcon("images/yellowreplayexit.png"));
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				button10.setIcon(new ImageIcon("images/replayexit.png"));
				super.mouseExited(e);
			}
		});
	}

	private void addKeyListener() {
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					cursor.left();
					repaint();
					break;
				case KeyEvent.VK_RIGHT:
					cursor.right();
					repaint();
					break;
				case KeyEvent.VK_UP:
					cursor.up();
					repaint();
					break;
				case KeyEvent.VK_DOWN:
					cursor.down();
					repaint();
					break;
				case KeyEvent.VK_SPACE:
					remove(blackPlayer);
					if (blackWinner == false && whiteWinner == false && game == true) { // 게임이 시작하지 않았거나 게임이 끝났을때는
																						// 작동하지않도록 설계
						if (MAP[cursor.getX()][cursor.getY()] == 0) { // 해당 좌표에 돌이 놓인게 없으면 작동하도록 설계
							history = true; // 돌이 처음 놓일때 최소1개가 있기 때문에 무르기가 작동이되도록 설계
							if ((color % 2) == 0) { // color의 기본값은 0으로 초기화되어있으므로 처음 턴은 흑돌이 되도록 설계
								cursor.BlackStone(); // 흑돌 객체가 생성됨
								MAP[cursor.getX()][cursor.getY()] = 1; // 해당 좌표에 흑돌이 생성되었음으로 1을 넣어줌
								turn.setIcon(new ImageIcon("images/whiteStone.png")); // 흑돌의 차례가 넘어감으로 현재 턴의 색깔을 백돌로 바꿔줌
								new Thread(rule33 = new Rule33(mContext, cursor.getBlackStone().getRealx(), // 33룰은 흑돌에게만 적용 흑돌이
																									// 놓일때마다 쓰레드 생성
										cursor.getBlackStone().getRealy())).start();
								player.setIcon(whitePlayer.getIcon()); // 마찬가지로 현재 플레이어 캐릭터를 백돌 캐릭터로 바꿔줌
								blackcount++; // 흑돌의 갯수가 1개 증가
							} else { // 홀수일때는 백돌의 턴이 됨
								cursor.WhiteStone(); // 백돌 객체 생성
								MAP[cursor.getX()][cursor.getY()] = 2; // 해당 좌표에 백돌(2)를 넣어줌
								turn.setIcon(new ImageIcon("images/blackStone.png")); // 현재 턴 돌 색깔을 흑돌로 바꿔줌
								player.setIcon(blackPlayer.getIcon()); // 현재 플레이어 캐릭터를 흑돌 캐릭터로 바꿔줌
								whitecount++; // 백돌의 갯수가 1개 증가
							}
							System.out.println(cursor.getX() + " , " +cursor.getY());
							color++; // 턴 바꾸기
							if(rule33.checkRule33() == false) {
								countdownTimer.reset();
							}
							repaint();
							break;
						} else { // 해당 자리에 이미 돌이 있으면 경고 문구 표시하고 return
							System.out.println(MAP[cursor.getX()][cursor.getY()]);
							System.out.println("같은자리에는 놓을수없습니다.");
							return;
						}
					}
				}
			}
		});
	}

	public void character() { // 캐릭터 초상화 리셋
		terran1.setBounds(200, 100, 200, 200);
		terran2.setBounds(200, 350, 200, 200);
		terran3.setBounds(200, 600, 200, 200);
		protoss1.setBounds(500, 100, 200, 200);
		protoss2.setBounds(500, 350, 200, 200);
		protoss3.setBounds(500, 600, 200, 200);
		zerg1.setBounds(800, 100, 200, 200);
		zerg2.setBounds(800, 350, 200, 200);
		zerg3.setBounds(800, 600, 200, 200);
	}

	public void mainMenu() { // 메인메뉴
		getContentPane().removeAll(); // 모든 컴포넌트 삭제
		setContentPane(mainmenu); // 배경화면을 메인메뉴로 설정
		setSize(1900, 1000);
		setLocationRelativeTo(null);
		add(gamename);
		add(button3);
		add(button5);
		add(button6);
		button3.setLocation(420, 650);
		repaint();
		this.requestFocus();
	}

	public void start() { // 게임화면(오목)
		time = true; // true 일때부터 시간 측정
		game = true; // true 일때부터 게임 시작
		new Thread(winRule = new WinRule(mContext)).start(); // 게임 승리룰 쓰레드 작동
		new Thread(timer = new Timer(mContext)).start(); // 타이머 쓰레드 작동
		new Thread(countdownTimer = new CountdownTimer(mContext)).start();
		getContentPane().removeAll(); // 모든 컴포넌트 초기화
		setContentPane(backgroundMAP); // 오목판을 배경으로 설정
		setSize(1901, 1000);
		setLocationRelativeTo(null);
		add(cursor); // 커서 추가
		cursor.setX(926); // 다시하기 했을때 커서 좌표 기본으로 재수정
		cursor.setY(477);
		add(turn);
		add(tag);
		add(blackPlayer);
		blackPlayer.setLocation(1550, 80);
		blackPlayer.setSize(200, 200);
		add(player);
		add(playerlabel);
		add(board);
		add(button4);
		add(timerBackground);
		add(backgroundLeft);
		add(backgroundRight);
		this.requestFocus(); // 키액션 리스너를 다시 작동하게 만듬 (중요!!!!)
		repaint();
	}

	public void characterSelect() { // 캐릭터 선택창
		getContentPane().removeAll();
		setContentPane(selectBackground); // 캐릭터 선택창 배경화면으로 설정
		setSize(1899, 1000);
		setLocationRelativeTo(null);
		add(button7);
		add(terran1);
		add(terran2);
		add(terran3);
		add(protoss1);
		add(protoss2);
		add(protoss3);
		add(zerg1);
		add(zerg2);
		add(zerg3);
		add(button3);
		add(white);
		add(black);
		add(blank);
		add(blank2);
		button3.setBounds(1330, 500, 150, 50);
		button7.setBounds(1265, 570, 300, 100);
		repaint();
	}

	public void blackWin() { // 흑돌이 이겼을때 흑 승리 적립
		add(win, 0);
		add(button8, 0);
		add(button2, 0);
		button2.setBounds(580, 525, 620, 70);
		repaint();
		time = false; // 시간 측정 종료
		blackWinner = true;
		blackwin++;
		game = false; // 게임이 끝났음을 알림
	}

	public void whiteWin() { // 백돌이 이겼을때 백 승리 적립
		add(win, 0);
		add(button8, 0);
		add(button2, 0);
		button2.setBounds(580, 525, 620, 70);
		repaint();
		time = false;
		whiteWinner = true;
		whitewin++;
		game = false; // 게임이 끝났음을 알림
	}

	public void result() { // 결과 화면
		timer.getrTime(); // 게임 시간 출력
		result = true; // 현재 화면이 결과창임을 알림
		total = blackcount + whitecount; // 흑돌 + 백돌 총 갯수
		getContentPane().removeAll();
		setContentPane(resultbackground);
		if (blackWinner == true) { // 만약 테란이 이겼으면 테란 승리화면 저그가 이겼으면 저그화면 토스가 이겼으면 토스화면
			if (races[0] == true) {
				resultbackground.setIcon(new ImageIcon("images/terranwin.jpg"));
			} else if (races[1] == true) {
				resultbackground.setIcon(new ImageIcon("images/protosswin.jpg"));
			} else if (races[2] == true) {
				resultbackground.setIcon(new ImageIcon("images/zergwin.jpg"));
			}
		} else if (whiteWinner == true) {
			if (races[3] == true) {
				resultbackground.setIcon(new ImageIcon("images/terranwin.jpg"));
			} else if (races[4] == true) {
				resultbackground.setIcon(new ImageIcon("images/protosswin.jpg"));
			} else if (races[5] == true) {
				resultbackground.setIcon(new ImageIcon("images/zergwin.jpg"));
			}
		}
		add(button1);
		add(button2);
		add(button9);
		setSize(1900, 1000);
		setLocationRelativeTo(null);
		button9.setBounds(950, 788, 250, 95);
		button1.setBounds(1240, 806, 210, 80);
		button2.setBounds(1490, 808, 200, 70);
		repaint();
	}

	public void reset() { // 다시하기를 선택시 승리횟수 제외 모든것을 초기화
		for (int i = 0; i < MAP.length; i++) { // 모든 좌표값에서 돌을 제거
			for (int j = 0; j < MAP.length; j++) {
				MAP[i][j] = 0;
			}
		}
		color = 0;
		total = 0;
		blackcount = 0;
		whitecount = 0;
		blackWinner = false;
		whiteWinner = false;
		result = false;
		turn.setIcon(new ImageIcon("images/blackStone.png"));
		mainMenu(); // 메인메뉴로 돌아감
	}

	@Override
	public void actionPerformed(ActionEvent e) { // 버튼 클릭 이벤트
		JButton selectedButton = (JButton) e.getSource();
		if (selectedButton == button1) {
			reset();
		} else if (selectedButton == button2) {
			System.exit(0);
		} else if (selectedButton == button3) {
			start();
		} else if (selectedButton == button8) {
			result();
		} else if (selectedButton == button9) { // 다시보기 구현
			replay = true;
			add(backgroundMAP,0);
			for(int i = 0; i < MAP.length; i++) {
				for(int j = 0; j< MAP.length; j++) {
					if(MAP[j][i] != 0) {
					if(MAP[j][i] == 1) {
						cursor.setX(j);
						cursor.setY(i);
						cursor.BlackStone(0);
					}
					if(MAP[j][i] == 2) {
						cursor.setX(j);
						cursor.setY(i);
						cursor.WhiteStone(0);
					}
					}
				}
			}
			button1.setLocation(1485, 710);
			add(button10);
			repaint();
		} else if (selectedButton == button10) {
			replay = false;
			result();
		} else if (selectedButton == button4) {
			if (history) {
				if (color % 2 == 1) {
					MAP[cursor.getBlackStone().getRealx()][cursor.getBlackStone().getRealy()] = 0;
					remove(cursor.getBlackStone()); // 무르기가 작동될때 바로 전에 놓인 돌이 삭제됨
					blackcount--;
					turn.setIcon(new ImageIcon("images/blackStone.png")); // 다시 흑돌 턴으로 변경
					player.setIcon(blackPlayer.getIcon());
					color++;
				} else if (color % 2 == 0) {
					MAP[cursor.getWhiteStone().getRealx()][cursor.getWhiteStone().getRealy()] = 0;
					remove(cursor.getWhiteStone());
					whitecount--;
					turn.setIcon(new ImageIcon("images/whiteStone.png"));
					player.setIcon(whitePlayer.getIcon());
					color++;
				}
			} else {

			}
			repaint();
			this.requestFocus();
			history = false; // 무르기가 한번만 작동되도록 false로 변경
		} else if (selectedButton == button5) {
			characterSelect();
		} else if (selectedButton == button6) {
			System.exit(0);
		} else if (selectedButton == button7) {
			mainMenu();
		} else if (selectedButton == terran1) {
			if ((choicecount % 2) == 0) {
				character();
				blackPlayer.setIcon(new ImageIcon("images/terran.gif"));
				terran1.setLocation(1125, 205);
				repaint();
				for (int i = 0; i < races.length / 2; i++) { // 테란 선택시 흑돌의 종족을 테란으로 변경
					if (i == 0) {
						races[i] = true;
					} else {
						races[i] = false;
					}
				}
			} else {
				whitePlayer.setIcon(new ImageIcon("images/terran.gif"));
				terran1.setLocation(1475, 205);
				repaint();
				for (int i = 3; i < races.length; i++) {
					if (i == 3) {
						races[i] = true;
					} else {
						races[i] = false;
					}
				}
			}
			choicecount++;
		} else if (selectedButton == terran2) {
			if ((choicecount % 2) == 0) {
				character();
				blackPlayer.setIcon(new ImageIcon("images/terran2.gif"));
				terran2.setLocation(1125, 205);
				repaint();
				for (int i = 0; i < races.length / 2; i++) {
					if (i == 0) {
						races[i] = true;
					} else {
						races[i] = false;
					}
				}
			} else {
				whitePlayer.setIcon(new ImageIcon("images/terran2.gif"));
				terran2.setLocation(1475, 205);
				repaint();
				for (int i = 3; i < races.length; i++) {
					if (i == 3) {
						races[i] = true;
					} else {
						races[i] = false;
					}
				}
			}
			choicecount++;
		} else if (selectedButton == terran3) {
			if ((choicecount % 2) == 0) {
				character();
				blackPlayer.setIcon(new ImageIcon("images/terran3.gif"));
				terran3.setLocation(1125, 205);
				repaint();
				for (int i = 0; i < races.length / 2; i++) {
					if (i == 0) {
						races[i] = true;
					} else {
						races[i] = false;
					}
				}
			} else {
				whitePlayer.setIcon(new ImageIcon("images/terran3.gif"));
				terran3.setLocation(1475, 205);
				repaint();
				for (int i = 3; i < races.length; i++) {
					if (i == 3) {
						races[i] = true;
					} else {
						races[i] = false;
					}
				}
			}
			choicecount++;
		} else if (selectedButton == protoss1) {
			if ((choicecount % 2) == 0) {
				character();
				blackPlayer.setIcon(new ImageIcon("images/protoss.gif"));
				protoss1.setLocation(1125, 205);
				repaint();
				for (int i = 0; i < races.length / 2; i++) {
					if (i == 1) {
						races[i] = true;
					} else {
						races[i] = false;
					}
				}
			} else {
				whitePlayer.setIcon(new ImageIcon("images/protoss.gif"));
				protoss1.setLocation(1475, 205);
				repaint();
				for (int i = 3; i < races.length; i++) {
					if (i == 4) {
						races[i] = true;
					} else {
						races[i] = false;
					}
				}
			}
			choicecount++;
		} else if (selectedButton == protoss2) {
			if ((choicecount % 2) == 0) {
				character();
				blackPlayer.setIcon(new ImageIcon("images/protoss2.gif"));
				protoss2.setLocation(1125, 205);
				repaint();
				for (int i = 0; i < races.length / 2; i++) {
					if (i == 1) {
						races[i] = true;
					} else {
						races[i] = false;
					}
				}
			} else {
				whitePlayer.setIcon(new ImageIcon("images/protoss2.gif"));
				protoss2.setLocation(1475, 205);
				repaint();
				for (int i = 3; i < races.length; i++) {
					if (i == 4) {
						races[i] = true;
					} else {
						races[i] = false;
					}
				}
			}
			choicecount++;
		} else if (selectedButton == protoss3) {
			if ((choicecount % 2) == 0) {
				character();
				blackPlayer.setIcon(new ImageIcon("images/protoss3.gif"));
				protoss3.setLocation(1125, 205);
				repaint();
				for (int i = 0; i < races.length / 2; i++) {
					if (i == 1) {
						races[i] = true;
					} else {
						races[i] = false;
					}
				}
			} else {
				whitePlayer.setIcon(new ImageIcon("images/protoss3.gif"));
				protoss3.setLocation(1475, 205);
				repaint();
				for (int i = 3; i < races.length; i++) {
					if (i == 4) {
						races[i] = true;
					} else {
						races[i] = false;
					}
				}
			}
			choicecount++;
		} else if (selectedButton == zerg1) {
			if ((choicecount % 2) == 0) {
				character();
				blackPlayer.setIcon(new ImageIcon("images/zerg1.gif"));
				zerg1.setLocation(1125, 205);
				repaint();
				for (int i = 0; i < races.length / 2; i++) {
					if (i == 2) {
						races[i] = true;
					} else {
						races[i] = false;
					}
				}
			} else {
				whitePlayer.setIcon(new ImageIcon("images/zerg1.gif"));
				zerg1.setLocation(1475, 205);
				repaint();
				for (int i = 3; i < races.length; i++) {
					if (i == 5) {
						races[i] = true;
					} else {
						races[i] = false;
					}
				}
			}
			choicecount++;
		} else if (selectedButton == zerg2) {
			if ((choicecount % 2) == 0) {
				character();
				blackPlayer.setIcon(new ImageIcon("images/zerg2.gif"));
				zerg2.setLocation(1125, 205);
				repaint();
				for (int i = 0; i < races.length / 2; i++) {
					if (i == 2) {
						races[i] = true;
					} else {
						races[i] = false;
					}
				}
			} else {
				whitePlayer.setIcon(new ImageIcon("images/zerg2.gif"));
				zerg2.setLocation(1475, 205);
				repaint();
				for (int i = 3; i < races.length; i++) {
					if (i == 5) {
						races[i] = true;
					} else {
						races[i] = false;
					}
				}
			}
			choicecount++;
		} else if (selectedButton == zerg3) {
			if ((choicecount % 2) == 0) {
				character();
				blackPlayer.setIcon(new ImageIcon("images/zerg3.gif"));
				zerg3.setLocation(1125, 205);
				repaint();
				for (int i = 0; i < races.length / 2; i++) {
					if (i == 2) {
						races[i] = true;
					} else {
						races[i] = false;
					}
				}
			} else {
				whitePlayer.setIcon(new ImageIcon("images/zerg3.gif"));
				zerg3.setLocation(1475, 205);
				repaint();
				for (int i = 3; i < races.length; i++) {
					if (i == 5) {
						races[i] = true;
					} else {
						races[i] = false;
					}
				}
			}
			choicecount++;
		}
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Font font = new Font("Kostar", Font.BOLD, 35);
		Font font2 = new Font("Kostar", Font.BOLD, 45);
		g.setFont(font);
		if (game == true) { // 게임 시작했을때 문구 표시 종료시 없어짐
			g.drawString("이번 턴은", 1590, 440);
			g.drawString("플레이어", 1590, 90);
			g.drawString("총 돌의 갯수", 1570, 730);
			g.drawString("흑", 1570, 790);
			g.drawString("백", 1720, 790);
			g.drawString("" + blackcount, 1575, 830);
			g.drawString("" + whitecount, 1725, 830);
		}
		if (result == true) { // 결과창에서 뜨는 문구 마찬가지로 종료시 없어짐
			g.setColor(Color.white);
			g.drawString("흑돌 승리 횟수 : " + blackwin, 40, 400);
			g.drawString("백돌 승리 횟수 : " + whitewin, 40, 500);
			g.drawString("돌 놓인 횟수 : " + total, 40, 600);
			g.setFont(font2);
			g.drawString("경기 시간 : " + (timer.getrTime() / 1000) / 60 + "분 " + timer.getrTime() / 1000 % 60 + "초", 40,
					300);
		}
		if (replay == true) { // 다시보기를 켜면 뜨는 문구
			g.setColor(Color.RED);
			if(blackWinner == true) {
				System.out.println(winRule.getWinBlackX());
				g.drawLine(winRule.getWinBlackX() + 23,winRule.getWinBlackY() + 35, 
				winRule.getWinBlackXEnd() + 23, winRule.getWinBlackYEnd() + 35);
			}
			if(whiteWinner == true) {
				g.drawLine(winRule.getWinWhiteX() + 23,winRule.getWinWhiteY() + 35, 
						winRule.getWinWhiteXEnd() + 23, winRule.getWinWhiteYEnd() + 35);
			}
		}
	}

	public void RullOfThreeThree() { // 33룰이 작동될경우 돌을 지우고 경고문구 출력
		MAP[cursor.getBlackStone().getRealx()][cursor.getBlackStone().getRealy()] = 0;
		remove(cursor.getBlackStone());
		add(rule33board, 0);
		blackcount--;
		color++;
		turn.setIcon(new ImageIcon("images/blackStone.png"));
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		remove(rule33board);
		repaint();
	}

}