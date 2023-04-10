import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class WinMemberJoin extends JDialog {
	private JTextField tfId;
	private JTextField tfName;
	private JTextField tfEmail;
	private JTextField tfMobile;
	private JPasswordField tfPw;
	private JButton btnJoin;
	private JLabel lblPw;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			WinMemberJoin dialog = new WinMemberJoin();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public WinMemberJoin() {
		setTitle("회원 가입창");
		setBounds(100, 100, 427, 336);
		getContentPane().setLayout(null);
		{
			JLabel lblId = new JLabel("id:");
			lblId.setBounds(52, 30, 57, 15);
			getContentPane().add(lblId);
		}
		{
			tfId = new JTextField();
			tfId.setBounds(154, 27, 152, 21);
			getContentPane().add(tfId);
			tfId.setColumns(10);
		}
		{
			lblPw = new JLabel("pw:");
			lblPw.setBounds(52, 65, 57, 15);
			getContentPane().add(lblPw);
		}
		{
			JLabel lblName = new JLabel("name:");
			lblName.setBounds(52, 101, 57, 15);
			getContentPane().add(lblName);
		}
		{
			JLabel lblEmail = new JLabel("email:");
			lblEmail.setBounds(52, 137, 57, 15);
			getContentPane().add(lblEmail);
		}
		{
			JLabel lblMobile = new JLabel("mobile:");
			lblMobile.setBounds(52, 172, 57, 15);
			getContentPane().add(lblMobile);
		}
		{
			tfName = new JTextField();
			tfName.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode() == KeyEvent.VK_ENTER) {
						tfEmail.setEditable(true);
						tfEmail.requestFocus();
						}
				}
			});
			tfName.setEditable(false);
			tfName.setColumns(10);
			tfName.setBounds(154, 98, 152, 21);
			getContentPane().add(tfName);
		}
		{
			tfEmail = new JTextField();
			tfEmail.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode() == KeyEvent.VK_ENTER) {
						tfMobile.setEditable(true);
						tfMobile.requestFocus();
					}
				}
			});
			tfEmail.setEditable(false);
			tfEmail.setColumns(10);
			tfEmail.setBounds(154, 134, 152, 21);
			getContentPane().add(tfEmail);
		}
		{
			tfMobile = new JTextField();
			tfMobile.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
				}
			});
			tfMobile.setEditable(false);
			tfMobile.setColumns(10);
			tfMobile.setBounds(154, 169, 152, 21);
			getContentPane().add(tfMobile);
			if((new String(tfMobile.getText()).length()) > 10) {
				btnJoin.setEnabled(true);
			}
		}
		{
			btnJoin = new JButton("가입");
			btnJoin.setEnabled(false);
			btnJoin.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB","root","1234");
						System.out.println("DB 연결 성공");
						Statement stmt = con.createStatement();
						
						String temp = tfMobile.getText();		// 01011112222 -> 010-1111-2222
						if(temp.length() == 11)
							temp = temp.substring(0,3) + "-" + temp.substring(3,7) + "-" + temp.substring(7);
						else
							temp = "000-0000-0000";
						
						String sql = "insert into membertbl values('" + tfId.getText() + "','";
						sql = sql + new String(tfPw.getPassword()) + "','" + tfName.getText() + "','";
						sql = sql + tfEmail.getText() + "','" + temp + "')";
						
						if(stmt.executeUpdate(sql) <= 0) {
							System.out.println("삽입 오류 발생");
						}						
					} catch (ClassNotFoundException | SQLException f) {
						System.out.println("DB 문제");
					}
				}
			});
			btnJoin.setBounds(154, 228, 97, 23);
			getContentPane().add(btnJoin);
		}
		
		tfPw = new JPasswordField();
		tfPw.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfName.setEditable(true);
					tfName.requestFocus();
					}
			}
		});
		tfPw.setEditable(false);
		tfPw.setEchoChar('*');
		tfPw.setBounds(154, 62, 152, 21);
		getContentPane().add(tfPw);
		{
			JButton btnDup = new JButton("중복확인");
			btnDup.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB","root","1234");
						System.out.println("DB 연결 성공");
						
						String sql = "select id from membertbl where id='" + tfId.getText() + "'";
						Statement stmt = con.createStatement();
						ResultSet rs = stmt.executeQuery(sql);		
						
						if(rs.next()) {
							JOptionPane.showMessageDialog(null, "이미 가입된 아이디 입니다");
							tfId.setSelectionStart(0);
							tfId.setSelectionEnd(tfId.getText().length());
							tfId.requestFocus();
						} else {
							JOptionPane.showMessageDialog(null, "사용 가능한 아이디 입니다");
							tfPw.setEditable(true);
							tfPw.requestFocus();
						}						
					} catch (ClassNotFoundException | SQLException f) {
						System.out.println("DB 문제(테이블 이름확인, 쿼리확인, 데이터베이스 확인)");
					}
				}
			});
			btnDup.setFont(new Font("굴림", Font.PLAIN, 10));
			btnDup.setBounds(314, 26, 72, 23);
			getContentPane().add(btnDup);
		}
	}
}