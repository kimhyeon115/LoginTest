import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPasswordField;

public class WinLogin extends JDialog {
	private JTextField tfID;
	private JPasswordField tfPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WinLogin dialog = new WinLogin();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public WinLogin() {
		setTitle("로그인 창");
		setBounds(100, 100, 450, 264);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblID = new JLabel("ID:");
		lblID.setBounds(56, 41, 57, 15);
		panel.add(lblID);
		
		tfID = new JTextField();
		tfID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				tfPassword.requestFocus();
				}
			}
		});
		tfID.setBounds(153, 38, 116, 21);
		panel.add(tfID);
		tfID.setColumns(10);
		
		JLabel lblPW = new JLabel("Password:");
		lblPW.setBounds(56, 85, 72, 15);
		panel.add(lblPW);
		
		JButton btLogin = new JButton("로그인...");
		btLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//데이터베이스 연결
				connectDB();
			}
		});
		btLogin.setBounds(301, 38, 81, 65);
		panel.add(btLogin);
		
		JLabel lblJoinmember = new JLabel("회원가입...");
		lblJoinmember.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				WinMemberJoin winMemberJoin = new WinMemberJoin();
				winMemberJoin.setModal(true);
				winMemberJoin.setVisible(true);
			}
		});
		lblJoinmember.setBounds(162, 152, 107, 26);
		panel.add(lblJoinmember);
		
		tfPassword = new JPasswordField();
		tfPassword.setBounds(153, 82, 116, 21);
		panel.add(tfPassword);

	}

	protected void connectDB() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB","root","1234");
			System.out.println("DB 연결 성공");
			
			String sql = "select * from membertbl where id='" + tfID.getText() + "'"; // "'" 중요성
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()) {
				String pw = rs.getString("password");
				String sId = rs.getString("id");						
				if(pw.equals(new String(tfPassword.getPassword()))) {
//					setVisible(false);										// 보이지는 않지만 존재함
					dispose();												// 실제 닫아버림
					WinMain winMain = new WinMain(sId);
					winMain.setModal(true);									// 이전 창 안잡히게함(현재창 강제)
					winMain.setVisible(true);							// 지명한 창 출력
				} else {
					JOptionPane.showMessageDialog(null, "password를 확인하세요");
					tfPassword.setText("");
					tfPassword.requestFocus();
				}
			} else {
				JOptionPane.showMessageDialog(null, "Id를 확인하세요");
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("DB 문제");
		}
	}
}
