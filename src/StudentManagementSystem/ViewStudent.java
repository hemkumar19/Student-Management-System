package StudentManagement;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.GroupLayout.Alignment;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewStudent extends JFrame {	
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTable table;
    Connection con = null ;
    PreparedStatement pst = null;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ViewStudent frame = new ViewStudent();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public ViewStudent() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(650, 140, 782, 611);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setBackground(Color.cyan);
        setContentPane(contentPane);

        JDesktopPane desktopPane = new JDesktopPane();
        desktopPane.setBackground(Color.lightGray);
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addComponent(desktopPane, GroupLayout.PREFERRED_SIZE, 753, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addComponent(desktopPane, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(423, Short.MAX_VALUE))
        );

        JLabel lblNewLabel = new JLabel("Student Details");
        lblNewLabel.setForeground(Color.BLACK);
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 28));
        lblNewLabel.setBounds(255, 27, 225, 52);
        desktopPane.add(lblNewLabel);

        JButton btnNewButton = new JButton("Go Back");
        btnNewButton.setForeground(Color.BLACK);
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Menu menu = new Menu();
                menu.show();
                dispose();
            }
        });

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10,150 , 750, 400);
		contentPane.add(scrollPane);
		table = new JTable();
		scrollPane.setViewportView(table);

        JButton submit = new JButton("Refresh to view");
        submit.setForeground(Color.BLACK);
        submit.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    try{
                        String query = "SELECT * FROM `student`";
                        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentmanagementsystem", "root", "");
                        Statement st = con.createStatement();
                        ResultSet rs = st.executeQuery(query);
                        ResultSetMetaData rsmd = rs.getMetaData();
                        DefaultTableModel model = (DefaultTableModel) table.getModel();

                        int cols = rsmd.getColumnCount();
                        String[] colName = new String[cols];
                        for(int i = 0 ; i <cols ; i++){
                                colName[i] = rsmd.getColumnName(i+1);
                        model.setColumnIdentifiers(colName);
                        String name, entrynumber , email , contentnumber , homecity ;
                        while (rs.next()){
                            name = rs.getString(1);
                            entrynumber = rs.getString(2);
                            email = rs.getString(3);
                            contentnumber = rs.getString(4);
                            homecity = rs.getString(5);
                            String[] row = {name , entrynumber , email, contentnumber, homecity};
                            model.addRow(row);
                        }

                        }
                    }
                    catch ( SQLException ex) {
                        JOptionPane.showMessageDialog(null, ex);
                    }
                }
        });
        submit.setFont(new Font("Tahoma", Font.BOLD, 14));
        submit.setBounds(200, 98, 226, 32);
        contentPane.add(submit);
        btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnNewButton.setBounds(10, 96, 113, 32);
        desktopPane.add(btnNewButton);
        contentPane.setLayout(gl_contentPane);
    }
}
