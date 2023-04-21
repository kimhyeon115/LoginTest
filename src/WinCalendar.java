import java.awt.EventQueue;

import javax.swing.JDialog;
import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Calendar;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Color;
import java.awt.Font;

public class WinCalendar extends JDialog {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WinCalendar dialog = new WinCalendar();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private JPanel panelCalender;
	private JComboBox comboMonth;
	private JComboBox comboYear;
	private JButton btnNext;
	private JButton btnBefore;
	private JButton btnNext2;
	private JButton btnBofore2;
	private int tyear;
	private int tmonth;
	private Calendar today;
	private int tday;
	private String selectedDate;
	
	public String getDate() {
		return selectedDate;
	}
	/**
	 * Create the dialog.
	 */
	public WinCalendar() {
		setTitle("\uB2EC\uB825(1923~2123\uB144)");
		setBounds(100, 100, 450, 431);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		
		panelCalender = new JPanel();
		getContentPane().add(panelCalender, BorderLayout.CENTER);
		panelCalender.setLayout(new GridLayout(0, 7, 5, 5));
		
		JButton btnRun = new JButton("\uB2EC\uB825 \uBCF4\uAE30");		
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				showCalendar();					
			}
		});
		
		comboMonth = new JComboBox();
		comboMonth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showCalendar();
			}
		});
		
		btnBefore = new JButton("<");
		btnBefore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int year = Integer.parseInt(comboYear.getSelectedItem().toString());
				int month = Integer.parseInt(comboMonth.getSelectedItem().toString());
				month--;
				if(month==0) {
					year--;
					month=12;					
				}
				comboYear.setSelectedItem(year);
				comboMonth.setSelectedIndex(month-1);
				showCalendar();
			}
		});
		
		btnBofore2 = new JButton("<<");
		btnBofore2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int year = Integer.parseInt(comboYear.getSelectedItem().toString());
				year--;
				if(year == 1923)
					year = 1923;
				comboYear.setSelectedItem(year);				
				showCalendar();
			}
		});
		panel.add(btnBofore2);
		panel.add(btnBefore);
		
		comboYear = new JComboBox();
		comboYear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showCalendar();
			}			
		});
		panel.add(comboYear);
		comboMonth.setModel(new DefaultComboBoxModel(new String[] 
				{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"}));
		panel.add(comboMonth);
		panel.add(btnRun);
		
		btnNext = new JButton(">");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int year = Integer.parseInt(comboYear.getSelectedItem().toString());
				int month = Integer.parseInt(comboMonth.getSelectedItem().toString());
				month++;
				if(month==13) {
					year++;
					month=1;
					
				}
				comboYear.setSelectedItem(year);
				comboMonth.setSelectedIndex(month-1);
				
				showCalendar();
			}
		});
		panel.add(btnNext);
		
		btnNext2 = new JButton(">>");
		btnNext2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int year = Integer.parseInt(comboYear.getSelectedItem().toString());
				year++;
				if(year == 2123)
					year = 2123;
				comboYear.setSelectedItem(year);				
				showCalendar();
			}
		});
		panel.add(btnNext2);

		for(int year=1923;year<=2123;year++)
			comboYear.addItem(year);
		
		//현재 연도와 월을 알아오자
		today = Calendar.getInstance();
		tyear = today.get(Calendar.YEAR);
		tmonth = today.get(Calendar.MONTH)+1;
		tday = today.get(Calendar.DATE);
		comboYear.setSelectedItem(tyear);
		comboMonth.setSelectedIndex(tmonth-1);		
	}

	protected void showCalendar() {
		// 버튼 전부 제거
				
		Component[] componentList = panelCalender.getComponents();
		for(Component c: componentList) {
			if(c instanceof JButton) {
				panelCalender.remove(c);
			}
		}
		panelCalender.revalidate();			// 잔상 지우고
		panelCalender.repaint();			// 새로 칠한다
		
//		String[] week = {"일","월","화","수","목","금","토"};
//		for(int i=0; i<week.length; i++) {
//			JButton btn = new JButton(week[i]);
//			panelCalender.add(btn);
//		}
		
		String week = "일월화수목금토";
		for(int i=0; i<week.length(); i++) {
			JButton btn = new JButton(week.substring(i,i+1));
			btn.setBackground(Color.WHITE);
			btn.setForeground(new Color(0,0,255));
//			btn.setEnabled(false);
			btn.setFont(new Font("굴림", Font.BOLD, 16));
			panelCalender.add(btn);
		}
		int Month[] = {31,28,31,30,31,30,31,31,30,31,30,31};
		int year = Integer.parseInt(comboYear.getSelectedItem().toString());
		int month = Integer.parseInt(comboMonth.getSelectedItem().toString());
		int sum = 0;
		
		// 해당하는 전연도까지의 합을 구하시오(1923.1.1~2022.12.31)
		for(int i=1923; i<year; i++) {
			if(i%4==0 && i%100 != 0 || i%400==0) {
				sum = sum + 366;
			}else {
				sum = sum + 365;
			}
		}
		// 해당하는 전월까지의 날짜 수의 합을 구하시오
		for(int i=0; i<month-1; i++) {
			if(i==1 && (year%4==0 && year%100!=0 || year%400==0)) {
				sum = sum + ++Month[i];
			}else {
				sum = sum + Month[i];
			}
		}				
		
		// 1923년도 1월 1일의 시작은 토요일(1)부터 시작
		int start = 1;
		start = (start + sum) % 7;
		for(int i=1; i<=start; i++) {
			JButton btn = new JButton("");
			panelCalender.add(btn);
			btn.setVisible(false);
		}	
		
		// 해당하는 달의 마지막 날까지 버튼 생성
		int last = Month[month-1];
		if(month==2 && (year%4==0 && year%100!=0 || year%400==0))
			last++;
		for(int i=1; i<=last; i++) {
			JButton btn = new JButton(i+"");
			btn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					JButton btn1 = (JButton)e.getSource();
					selectedDate = year + "-" + month + "-" + btn1.getText();
					dispose();
				}
			});
			if(year == tyear && month == tmonth && i == tday)
				btn.setBackground(Color.lightGray);
			panelCalender.add(btn);
			panelCalender.revalidate();
		btn.setForeground(new Color(0,0,255));
		}	
	}
}
