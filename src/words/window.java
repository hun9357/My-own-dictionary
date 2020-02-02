package words;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.sql.*;
import java.awt.event.ActionEvent;

public class window extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTable table;
	String loc = "jdbc:sqlite:C://Users//home//mywords.sqlite";
	Connection con = null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window frame = new window();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public window() {
		setTitle("\uB2E8\uC5B4\uC7A5");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 590, 532);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Word");
		lblNewLabel.setBounds(34, 190, 108, 41);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(129, 199, 116, 22);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Definition");
		lblNewLabel_1.setBounds(34, 257, 56, 43);
		contentPane.add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		textField_1.setBounds(129, 254, 417, 46);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnNewButton = new JButton("Add");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					con = DriverManager.getConnection(loc);
					Statement st = con.createStatement();
					String words = textField.getText();
					String def = textField_1.getText();
					if(words.isEmpty()||def.isEmpty())
					{
						JOptionPane.showMessageDialog(null, "Please fill out all text fields!","Error",JOptionPane.WARNING_MESSAGE);
						con.close();
					}
					else
					{
						int rows = rowCount()+1;
						String sql = "INSERT INTO words VALUES('"+rows+"','"+words+"','"+def+"');";
						st.executeUpdate(sql);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					if (con != null)
					{
						try {
							con.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		});
		btnNewButton.setBounds(34, 385, 97, 25);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Edit");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String id = table.getValueAt(0,0).toString();
					int index = Integer.parseInt(id);
					String word = textField.getText();
					String def = textField_1.getText();
					con = DriverManager.getConnection(loc);
					Statement st = con.createStatement();
					//ResultSet rs = st.executeQuery("SELECT * FROM words WHERE id= '"+index+"'");
					if(index==0)
					{
						//System.out.println("There is no word in dictionary");
						JOptionPane.showMessageDialog(null,"There is no word in dictionary!","Warning",JOptionPane.WARNING_MESSAGE);
						textField.setText("");
						textField_1.setText("");
					}
					else
					{
						System.out.print(id);
						st.executeUpdate("UPDATE words SET word = '"+word+"' WHERE id = '"+index+"';");
						st.executeUpdate("UPDATE words SET definition = '"+def+"' WHERE id = '"+index+"';");
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					if (con != null)
					{
						try {
							con.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		});
		btnNewButton_1.setBounds(206, 385, 97, 25);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Search");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String word = textField.getText();
					con = DriverManager.getConnection(loc);
					Statement st = con.createStatement();
					ResultSet rs = st.executeQuery("SELECT * FROM words WHERE word= '"+word+"'");
					if(rs.next()==false)
					{
						//System.out.println("There is no word in dictionary");
						JOptionPane.showMessageDialog(null,"There is no word in dictionary!","Warning",JOptionPane.WARNING_MESSAGE);
						textField.setText("");
						textField_1.setText("");
					}
					else
					{
					table.setModel(new DefaultTableModel(
							new Object[][] {
								{rs.getString(1),rs.getString(2),rs.getString(3)},
							},
							new String[] {
								"Index","Word","Definition"
							}
						));
					textField.setText(rs.getString(2));
					textField_1.setText(rs.getString(3));
					table.setRowHeight(70);
					TableColumnModel columnModel = table.getColumnModel();
					columnModel.getColumn(2).setPreferredWidth(450);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					if (con != null)
					{
						try {
							con.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		});
		btnNewButton_2.setBounds(383, 385, 97, 25);
		contentPane.add(btnNewButton_2);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(34, 88, 512, 89);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null,null, null},
			},
			new String[] {
				"Index","Word", "Definition"
			}
		));
		
		JLabel lblNewLabel_2 = new JLabel("My Vocab");
		lblNewLabel_2.setBounds(238, 36, 108, 22);
		contentPane.add(lblNewLabel_2);
	}
	
	public int rowCount() throws SQLException {
		int count=0;
		Connection con = DriverManager.getConnection(loc);
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM words");
		while(rs.next())
		{
			count++;
		}
		con.close();
		return count;
	}
}
