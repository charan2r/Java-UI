import java.awt.EventQueue;
import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;


public class StudentApplication {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField txtID;
	private JTable StudentTable;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentApplication window = new StudentApplication();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public StudentApplication() {
		initialize();
		Connect();
		table();
	}
	
	Connection connection;
	PreparedStatement preparedStatement;
	ResultSet resultSet;
	
	
	public void Connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost/student", "root","");
			
		} 
		catch (ClassNotFoundException ex) 
		{
			ex.printStackTrace();
		}
		catch (SQLException ex) 
		{
			ex.printStackTrace();
		}
	}
	
	public void table()
	{
		try 
		{
			preparedStatement = connection.prepareStatement("select * from students");
			resultSet = preparedStatement.executeQuery();
			StudentTable.setModel(DbUtils.resultSetToTableModel(resultSet));
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		} 
		
		
	}
	
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 582, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Student Registration ");
		lblNewLabel.setForeground(new Color(255, 0, 0));
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(52, 25, 166, 23);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Registration Border", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(28, 77, 189, 129);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Reg. number:");
		lblNewLabel_1.setBounds(10, 21, 79, 14);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Name:");
		lblNewLabel_1_1.setBounds(10, 58, 79, 14);
		panel.add(lblNewLabel_1_1);
		
		textField = new JTextField();
		textField.setBounds(93, 18, 86, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(93, 55, 86, 20);
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Email:");
		lblNewLabel_1_1_1.setBounds(10, 93, 79, 14);
		panel.add(lblNewLabel_1_1_1);
		
		textField_2 = new JTextField();
		textField_2.setBounds(93, 90, 86, 20);
		panel.add(textField_2);
		textField_2.setColumns(10);
		
		JButton btnNewButton = new JButton("Save");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String regno, name, email;
				regno = textField.getText();
				name = textField_1.getText();
				email = textField_2.getText();
				
				try {
					preparedStatement = connection.prepareStatement("insert into students(regNo,name,email)values(?,?,?)");
					preparedStatement.setString(1, regno);
					preparedStatement.setString(2, name);
					preparedStatement.setString(3, email);
					preparedStatement.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Added!!!!!");
					table();
					
					textField.setText("");
					textField_1.setText("");
					textField_2.setText("");
					textField.requestFocus();
					
				} catch (Exception e2) {
					e2.printStackTrace();
					
				}
			}

		});
		btnNewButton.setBounds(28, 217, 89, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Clear");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				textField.setText("");
				textField_1.setText("");
				textField_2.setText("");
				textField.requestFocus();
			}
		});
		btnNewButton_1.setBounds(127, 217, 89, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_1_1 = new JButton("Exit");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnNewButton_1_1.setBounds(227, 217, 89, 23);
		frame.getContentPane().add(btnNewButton_1_1);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(341, 92, 189, 114);
		frame.getContentPane().add(scrollPane_1);
		
		StudentTable = new JTable();
		scrollPane_1.setViewportView(StudentTable);
		
		JButton btnNewButton_1_1_1 = new JButton("Update");
		btnNewButton_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String regno, name, email,id;
				regno = textField.getText();
				name = textField_1.getText();
				email = textField_2.getText();
				id = txtID.getText();
				
				try {
					preparedStatement = connection.prepareStatement("update students set regNo=?, name=?, email=? where id=? ");
					preparedStatement.setString(1, regno);
					preparedStatement.setString(2, name);
					preparedStatement.setString(3, email);
					preparedStatement.setString(4, id);
					preparedStatement.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Updated!!");
					table();
					
					textField.setText("");
					textField_1.setText("");
					textField_2.setText("");
					textField.requestFocus();
					
				} catch (Exception e2) {
					e2.printStackTrace();
					
				}
			}
		});
		btnNewButton_1_1_1.setBounds(346, 217, 89, 23);
		frame.getContentPane().add(btnNewButton_1_1_1);
		
		JButton btnNewButton_1_1_1_1 = new JButton("Delete");
		btnNewButton_1_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String id;
				id= txtID.getText();
				try {
					preparedStatement = connection.prepareStatement("delete from students where id=? ");
					preparedStatement.setString(1, id);
					preparedStatement.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record deleted!!");
					table();
					
					textField.setText("");
					textField_1.setText("");
					textField_2.setText("");
					textField.requestFocus();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		btnNewButton_1_1_1_1.setBounds(446, 217, 89, 23);
		frame.getContentPane().add(btnNewButton_1_1_1_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(245, 25, 280, 49);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("Student ID:");
		lblNewLabel_2.setBounds(10, 24, 73, 14);
		panel_1.add(lblNewLabel_2);
		
		txtID = new JTextField();
		txtID.setBounds(93, 21, 86, 20);
		panel_1.add(txtID);
		txtID.setColumns(10);
		
		JButton btnNewButton_2 = new JButton("Search");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					String id = txtID.getText();
					preparedStatement = connection.prepareStatement("Select regNo, name, email from students where id=?");
					preparedStatement.setString(1, id);
					ResultSet rs=preparedStatement.executeQuery();
					
					if (rs.next()==true) {
						String regNo=rs.getString(1);
						String name=rs.getString(2);
						String email=rs.getString(3);
						
						textField.setText(regNo);
						textField_1.setText(name);
						textField_2.setText(email);
						
					}
					else {
						textField.setText("");
						textField_1.setText("");
						textField_2.setText("");
					}
				} catch(SQLException ex)  {
					
				}
			}
		});
		btnNewButton_2.setBounds(191, 20, 73, 23);
		panel_1.add(btnNewButton_2);
	}
}
