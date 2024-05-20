package gomoku3.components;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CountdownTimer extends JLabel implements Runnable{

    private String[] count = { "20.png","19.png","18.png","17.png","16.png","15.png","14.png","13.png","12.png","11.png","10.png",
    		"9.png","8.png","7.png", "6.png", "5.png", "4.png", "3.png","2.png","1.png","0.png", };
    private int index = 0;
    private Background mconText;
    public CountdownTimer(Background mconText) {
       this.mconText = mconText;
    }
    private void initData() {
        // 초기 이미지 설정
        setIcon(new ImageIcon("images/" + count[0]));
        setSize(300, 300);
        setLocation(100, 200);
        setVisible(true);
        mconText.add(this);
    }

    private void setInitLayout() {
        // Timer 설정: 1000ms(1초)마다 actionPerformed 메소드 호출
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // count 배열 내 이미지 반복
                index++;
                if (index < count.length) {
                    setIcon(new ImageIcon("images/" + count[index]));
                } else {
                    // 타이머 중지
                    ((Timer)e.getSource()).stop();
                }
            }
        });
        timer.setInitialDelay(0); // 처음에 즉시 시작
        timer.start();
    }
    @Override
	public void run() {
		 initData();
	     setInitLayout();
	}


	
}