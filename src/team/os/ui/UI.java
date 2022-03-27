package team.os.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class UI extends JFrame {

	private JPanel contentPane;
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_3;
	private JPanel panel_4;
	private JPanel panel_5;
	private JLabel lblNewLabel;
	private JPanel panel_6;
	private JLabel lblPowerCon;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JPanel panel_7;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;
	private JButton btnNewButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FlatDarkLaf.setup();
					UI frame = new UI();
					frame.setTitle("Process Scheduling Simulator (Koreatech OS Team4)");
					frame.setResizable(false);
					frame.setLocationRelativeTo(null);
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
	public UI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1280, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new JPanel();
		panel.setBorder(new LineBorder(Color.GRAY));
		panel.setBounds(10, 10, 312, 181);
		contentPane.add(panel);
		
		panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(Color.GRAY));
		panel_1.setBounds(10, 203, 312, 181);
		contentPane.add(panel_1);
		
		panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(Color.GRAY));
		panel_2.setBounds(10, 396, 312, 75);
		contentPane.add(panel_2);
		
		panel_3 = new JPanel();
		panel_3.setBorder(new LineBorder(Color.GRAY));
		panel_3.setBounds(334, 491, 920, 181);
		contentPane.add(panel_3);
		
		panel_4 = new JPanel();
		panel_4.setBorder(new LineBorder(Color.GRAY));
		panel_4.setBounds(334, 193, 920, 67);
		contentPane.add(panel_4);
		
		panel_5 = new JPanel();
		panel_5.setBorder(new LineBorder(Color.GRAY));
		panel_5.setBounds(746, 42, 508, 107);
		contentPane.add(panel_5);
		
		lblNewLabel = new JLabel("Time Table");
		lblNewLabel.setBounds(334, 10, 244, 20);
		contentPane.add(lblNewLabel);
		
		panel_6 = new JPanel();
		panel_6.setBorder(new LineBorder(Color.GRAY));
		panel_6.setBounds(334, 42, 400, 107);
		contentPane.add(panel_6);
		
		lblPowerCon = new JLabel("Power Consumption");
		lblPowerCon.setBounds(746, 10, 508, 20);
		contentPane.add(lblPowerCon);
		
		lblNewLabel_1 = new JLabel("Ready Queue");
		lblNewLabel_1.setBounds(334, 161, 920, 20);
		contentPane.add(lblNewLabel_1);
		
		lblNewLabel_2 = new JLabel("Gantt Chart");
		lblNewLabel_2.setBounds(334, 272, 920, 20);
		contentPane.add(lblNewLabel_2);
		
		panel_7 = new JPanel();
		panel_7.setBorder(new LineBorder(Color.GRAY));
		panel_7.setBounds(334, 304, 920, 143);
		contentPane.add(panel_7);
		
		lblNewLabel_3 = new JLabel("Process State");
		lblNewLabel_3.setBounds(334, 459, 920, 20);
		contentPane.add(lblNewLabel_3);
		
		lblNewLabel_4 = new JLabel(new ImageIcon("resource/koreatech.png"));
		lblNewLabel_4.setBounds(10, 483, 312, 67);
		contentPane.add(lblNewLabel_4);
		
		btnNewButton = new JButton("New button");
		btnNewButton.setBounds(10, 646, 106, 26);
		contentPane.add(btnNewButton);
	}
}
