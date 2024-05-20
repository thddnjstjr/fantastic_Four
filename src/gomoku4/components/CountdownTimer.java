package gomoku4.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

public class CountdownTimer extends JLabel implements Runnable{

	private String[] count = { "black.png", "1.png", "2.png", "black.png" };
	private int index = 0;

	public CountdownTimer() {
		initData();
		setInitLayout();
	}
	
	@Override
	public void run() {
		
	}

	private void initData() {
		// 초기 이미지 설정
		setIcon(new ImageIcon("images/" + count[0]));
		setSize(500, 500);
		setLocation(100, 300);
		setVisible(true);
	}

	private void setInitLayout() {
		// Timer 설정: 5000ms(5초)마다 actionPerformed 메소드 호출
		Timer timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// count 배열 내 이미지 반복
				index++;
				if (index < count.length) {
					setIcon(new ImageIcon("images/" + count[index]));
				} else {
					// 타이머 중지
					((Timer) e.getSource()).stop();
				}
			}
		});
		timer.setInitialDelay(0); // 처음에 즉시 시작
		timer.start();
	}


}