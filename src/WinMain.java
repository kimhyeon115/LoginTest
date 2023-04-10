import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JTable;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class WinMain extends JDialog {
   private JTable table;   
   private DefaultTableModel dtm;

   /**
    * Launch the application.
    */
   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            try {
               WinMain dialog = new WinMain();
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
   public WinMain() {
      setTitle("회원 대화상자");
      setBounds(100, 100, 839, 545);
      
      JScrollPane scrollPane = new JScrollPane();
      getContentPane().add(scrollPane, BorderLayout.CENTER);
      
      String columnNames[] = {"아이디","패스워드","이름","이메일","전화번호"};
      dtm = new DefaultTableModel(columnNames, 0);
      
      table = new JTable(dtm);
      table.addMouseListener(new MouseAdapter() {
      	  @Override
	      public void mouseClicked(MouseEvent e) {
	      	if(JOptionPane.showConfirmDialog(null, "삭제 할까요?") == JOptionPane.YES_OPTION) {
	      		String sID = table.getValueAt(table.getSelectedRow(), 0).toString();
	          	dtm.removeRow(table.getSelectedRow());
	          	deleteRecord(sID);
	      	}
	      }
      });      
      scrollPane.setViewportView(table);      
      showRecords();
   }

	public WinMain(String sId) {
		this();
		setTitle("로그인 계정 : " + sId);
}

	protected void deleteRecord(String sID) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB","root","1234");
			System.out.println("DB 연결 성공");
			Statement stmt = con.createStatement();
			String sql = "delete from membertbl where id='" + sID + "'";
			
			if(stmt.executeUpdate(sql) <= 0) {
				System.out.println("삭제 오류");
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("DB 문제(테이블 이름확인, 쿼리확인, 데이터베이스 확인)");
		}
	}
	private void showRecords() {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB","root","1234");
			System.out.println("DB 연결 성공");
			
			String sql = "select * from membertbl";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);		
			
			while(rs.next()) {
				Vector<String> vec = new Vector<>();					// 벡터는 길이가 유연성이 좋다  
				for(int i=1; i<=5; i++) {								// 배열은 길이를 정해놓아햐한다
					vec.add(rs.getString(i));
				}
				dtm.addRow(vec);
				
//				String sArr[] = new String[5];							// 배열 사용시
//				for(int i=0; i<sArr.length; i++) {
//					sArr[i] = rs.getString(i+1);
//				}
//				dtm.addRow(sArr);				
			}		
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("DB 문제(테이블 이름확인, 쿼리확인, 데이터베이스 확인)");
		}	
	}
}