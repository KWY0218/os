package team.os.ui;

import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatDarkLaf;

import team.os.simulator.History;
import team.os.simulator.Simulator;

import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class UI extends JFrame {

	private JPanel contentPane;
	private JPanel panel_2;
	private JPanel panel_5;
	private JLabel lblNewLabel;
	private JPanel panel_6;
	private JLabel lblPowerCon;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;
	private JPanel panel_1;
	private JLabel lblNewLabel_5;
	private JScrollPane scrollPane;
	private JLabel lblPCores;
	private JLabel lblPCores_1;
	private JLabel lblPCores_2;
	private JTable table;
	private JLabel lblNewLabel_6;
	private JLabel lblNewLabel_7;
	private JLabel lblNewLabel_8;
	private JLabel lblNewLabel_9;
	private JScrollPane scrollPane_1;
	private JTable table_1;
	private JScrollPane scrollPane_2;
	private JTable table_2;
	private JLabel lblCoreSetting;
	private JLabel lblTimeQuantum;
	private JScrollPane scrollPane_3;
	private JTable table_3;
	private JRadioButton rdbtnNewRadioButton;
	private JRadioButton rdbtnNewRadioButton_1;
	private JRadioButton rdbtnNewRadioButton_2;
	private JRadioButton rdbtnNewRadioButton_3;
	private JRadioButton rdbtnSrtn;
	private JRadioButton rdbtnNewRadioButton_5;
	private JLabel lblW;
	private JLabel lblW_1;
	private JLabel lblW_2;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JLabel lblSeconds;
	private JTextField textField_3;
	private JLabel lblPCore;
	private JTextField textField_4;
	private JButton btnNewButton_3;
	private JButton btnNewButton_4;
	private JLabel lblPCore_1;
	private JTextField textField_5;
	private JButton btnNewButton_5;
	private JButton btnNewButton_6;
	private JLabel lblNewLabel_10;
	private JLabel lblNewLabel_11;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;

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
					frame.setIconImage(Toolkit.getDefaultToolkit().getImage("resource/icon.png"));
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
		
		panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(Color.GRAY));
		panel_2.setBounds(10, 494, 312, 102);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		lblNewLabel_7 = new JLabel("Burst Time (BT) :");
		lblNewLabel_7.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_7.setBounds(6, 70, 111, 20);
		panel_2.add(lblNewLabel_7);
		
		lblNewLabel_8 = new JLabel("Arrival Time (AT) :");
		lblNewLabel_8.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_8.setBounds(6, 38, 111, 20);
		panel_2.add(lblNewLabel_8);
		
		lblNewLabel_9 = new JLabel("Process Name :");
		lblNewLabel_9.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_9.setBounds(6, 6, 111, 20);
		panel_2.add(lblNewLabel_9);
		
		lblNewLabel_10 = new JLabel("second (s)");
		lblNewLabel_10.setBounds(242, 38, 64, 20);
		panel_2.add(lblNewLabel_10);
		
		lblNewLabel_11 = new JLabel("second (s)");
		lblNewLabel_11.setBounds(242, 70, 64, 20);
		panel_2.add(lblNewLabel_11);
		
		textField_6 = new JTextField();
		textField_6.setHorizontalAlignment(SwingConstants.CENTER);
		textField_6.setColumns(10);
		textField_6.setBounds(129, 6, 177, 26);
		panel_2.add(textField_6);
		
		textField_7 = new JTextField();
		textField_7.setText("0");
		textField_7.setHorizontalAlignment(SwingConstants.CENTER);
		textField_7.setColumns(10);
		textField_7.setBounds(129, 38, 101, 26);
		panel_2.add(textField_7);
		
		textField_8 = new JTextField();
		textField_8.setText("0");
		textField_8.setHorizontalAlignment(SwingConstants.CENTER);
		textField_8.setColumns(10);
		textField_8.setBounds(129, 70, 101, 26);
		panel_2.add(textField_8);
		
		panel_5 = new JPanel();
		panel_5.setBorder(new LineBorder(Color.GRAY));
		panel_5.setBounds(915, 42, 339, 142);
		contentPane.add(panel_5);
		panel_5.setLayout(null);
		
		lblPCores = new JLabel("P Core's Power Consumption :");
		lblPCores.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPCores.setBounds(6, 6, 192, 20);
		panel_5.add(lblPCores);
		
		lblPCores_1 = new JLabel("E Core's Power Consumption :");
		lblPCores_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPCores_1.setBounds(6, 38, 192, 20);
		panel_5.add(lblPCores_1);
		
		lblPCores_2 = new JLabel("Total Power Consumption :");
		lblPCores_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPCores_2.setBounds(6, 70, 192, 20);
		panel_5.add(lblPCores_2);
		
		lblW = new JLabel("W");
		lblW.setBounds(313, 6, 20, 20);
		panel_5.add(lblW);
		
		lblW_1 = new JLabel("W");
		lblW_1.setBounds(313, 38, 20, 20);
		panel_5.add(lblW_1);
		
		lblW_2 = new JLabel("W");
		lblW_2.setBounds(313, 70, 20, 20);
		panel_5.add(lblW_2);
		
		textField = new JTextField();
		textField.setText("0");
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setEditable(false);
		textField.setBounds(210, 6, 91, 26);
		panel_5.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setText("0");
		textField_1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_1.setEditable(false);
		textField_1.setColumns(10);
		textField_1.setBounds(210, 38, 91, 26);
		panel_5.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setText("0");
		textField_2.setHorizontalAlignment(SwingConstants.CENTER);
		textField_2.setEditable(false);
		textField_2.setColumns(10);
		textField_2.setBounds(210, 70, 91, 26);
		panel_5.add(textField_2);
		
		lblNewLabel = new JLabel("Scheduling Algorithms");
		lblNewLabel.setBounds(334, 10, 327, 20);
		contentPane.add(lblNewLabel);
		
		panel_6 = new JPanel();
		panel_6.setBorder(new LineBorder(Color.GRAY));
		panel_6.setBounds(334, 42, 327, 142);
		contentPane.add(panel_6);
		panel_6.setLayout(null);
		
		lblTimeQuantum = new JLabel("Time Quantum (\u03B4) :");
		lblTimeQuantum.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTimeQuantum.setBounds(6, 114, 123, 20);
		panel_6.add(lblTimeQuantum);
		
		rdbtnNewRadioButton = new JRadioButton("FCFS");
		rdbtnNewRadioButton.setSelected(true);
		rdbtnNewRadioButton.setBounds(6, 6, 135, 24);
		panel_6.add(rdbtnNewRadioButton);
		
		rdbtnNewRadioButton_1 = new JRadioButton("RR");
		rdbtnNewRadioButton_1.setBounds(6, 42, 135, 24);
		panel_6.add(rdbtnNewRadioButton_1);
		
		rdbtnNewRadioButton_2 = new JRadioButton("SPN");
		rdbtnNewRadioButton_2.setBounds(6, 78, 135, 24);
		panel_6.add(rdbtnNewRadioButton_2);
		
		rdbtnNewRadioButton_3 = new JRadioButton("Own");
		rdbtnNewRadioButton_3.setBounds(153, 78, 135, 24);
		panel_6.add(rdbtnNewRadioButton_3);
		
		rdbtnSrtn = new JRadioButton("SRTN");
		rdbtnSrtn.setBounds(153, 6, 135, 24);
		panel_6.add(rdbtnSrtn);
		
		rdbtnNewRadioButton_5 = new JRadioButton("HRRN");
		rdbtnNewRadioButton_5.setBounds(153, 42, 135, 24);
		panel_6.add(rdbtnNewRadioButton_5);
		
		lblSeconds = new JLabel("second (s)");
		lblSeconds.setBounds(257, 114, 64, 20);
		panel_6.add(lblSeconds);
		
		textField_3 = new JTextField();
		textField_3.setText("0");
		textField_3.setHorizontalAlignment(SwingConstants.CENTER);
		textField_3.setColumns(10);
		textField_3.setBounds(141, 111, 104, 26);
		panel_6.add(textField_3);
		
		lblPowerCon = new JLabel("Power Consumption");
		lblPowerCon.setBounds(915, 10, 339, 20);
		contentPane.add(lblPowerCon);
		
		lblNewLabel_1 = new JLabel("Ready Queue");
		lblNewLabel_1.setBounds(334, 196, 920, 20);
		contentPane.add(lblNewLabel_1);
		
		lblNewLabel_2 = new JLabel("Gantt Chart");
		lblNewLabel_2.setBounds(334, 290, 920, 20);
		contentPane.add(lblNewLabel_2);
		
		lblNewLabel_3 = new JLabel("Process State");
		lblNewLabel_3.setBounds(334, 462, 920, 20);
		contentPane.add(lblNewLabel_3);
		
		lblNewLabel_4 = new JLabel(new ImageIcon("resource/koreatech.png"));
		lblNewLabel_4.setBounds(10, 10, 312, 67);
		contentPane.add(lblNewLabel_4);
		
		btnNewButton = new JButton("Add");
		btnNewButton.setBounds(10, 608, 151, 26);
		contentPane.add(btnNewButton);
		
		btnNewButton_1 = new JButton("Reset");
		btnNewButton_1.setBounds(171, 608, 151, 26);
		contentPane.add(btnNewButton_1);
		
		btnNewButton_2 = new JButton("Run");
		btnNewButton_2.setBounds(10, 646, 312, 26);
		contentPane.add(btnNewButton_2);
		
		panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(Color.GRAY));
		panel_1.setBounds(673, 42, 230, 142);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		lblPCore = new JLabel("P Core");
		lblPCore.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPCore.setBounds(6, 6, 43, 20);
		panel_1.add(lblPCore);
		
		textField_4 = new JTextField();
		textField_4.setText("0");
		textField_4.setHorizontalAlignment(SwingConstants.CENTER);
		textField_4.setEditable(false);
		textField_4.setColumns(10);
		textField_4.setBounds(61, 6, 97, 26);
		panel_1.add(textField_4);
		
		btnNewButton_3 = new JButton("-");
		btnNewButton_3.addActionListener(e -> {
			addCoreSize(textField_4, new JTextField[] {textField_4, textField_5}, -1);
		});
		btnNewButton_3.setBounds(198, 6, 26, 26);
		panel_1.add(btnNewButton_3);
		
		btnNewButton_4 = new JButton("+");
		btnNewButton_4.addActionListener(e -> {
			addCoreSize(textField_4, new JTextField[] {textField_4, textField_5}, 1);
		});
		btnNewButton_4.setBounds(170, 6, 26, 26);
		panel_1.add(btnNewButton_4);
		
		lblPCore_1 = new JLabel("E Core");
		lblPCore_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPCore_1.setBounds(6, 38, 43, 20);
		panel_1.add(lblPCore_1);
		
		textField_5 = new JTextField();
		textField_5.setText("0");
		textField_5.setHorizontalAlignment(SwingConstants.CENTER);
		textField_5.setEditable(false);
		textField_5.setColumns(10);
		textField_5.setBounds(61, 38, 97, 26);
		panel_1.add(textField_5);
		
		btnNewButton_5 = new JButton("+");
		btnNewButton_5.addActionListener(e -> {
			addCoreSize(textField_5, new JTextField[] {textField_4, textField_5}, 1);
		});
		btnNewButton_5.setBounds(170, 38, 26, 26);
		panel_1.add(btnNewButton_5);
		
		btnNewButton_6 = new JButton("-");
		btnNewButton_6.addActionListener(e -> {
			addCoreSize(textField_5, new JTextField[] {textField_4, textField_5}, -1);
		});
		btnNewButton_6.setBounds(198, 38, 26, 26);
		panel_1.add(btnNewButton_6);
		
		lblNewLabel_5 = new JLabel("Time Table");
		lblNewLabel_5.setBounds(10, 89, 312, 20);
		contentPane.add(lblNewLabel_5);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 121, 312, 329);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		lblNewLabel_6 = new JLabel("Create Process");
		lblNewLabel_6.setBounds(10, 462, 312, 20);
		contentPane.add(lblNewLabel_6);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setBounds(334, 494, 920, 178);
		contentPane.add(scrollPane_1);
		
		table_1 = new JTable();
		scrollPane_1.setViewportView(table_1);
		
		scrollPane_2 = new JScrollPane();
		scrollPane_2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane_2.setBounds(334, 322, 920, 128);
		contentPane.add(scrollPane_2);
		
		table_2 = new JTable();
		scrollPane_2.setViewportView(table_2);
		
		lblCoreSetting = new JLabel("Core Setting");
		lblCoreSetting.setBounds(673, 10, 230, 20);
		contentPane.add(lblCoreSetting);
		
		scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(334, 228, 920, 50);
		contentPane.add(scrollPane_3);
		
		table_3 = new JTable();
		scrollPane_3.setViewportView(table_3);
	}

	/**
	 * @param targetField
	 * @param fields
	 * @param value
	 */

	private void addCoreSize(JTextField targetField, JTextField[] fields, int value) {
		
		int now = Integer.parseInt(targetField.getText());
		
		int using = 0;
		
		for(JTextField field : fields)
			
			using += Integer.parseInt(field.getText());
		
		// 타겟 필드의 코어 사용량이 0개 미만이 아니면서, 전체 코어 사용량이 MAX_CORE_SIZE를 초과하지 않을 때
		if(now + value >= 0 && using + value <= Simulator.MAX_CORE_SIZE)
		
			targetField.setText(String.valueOf(now + value));
		
	}
	
	private void addProcess() {
		
	}
	
	private void resetProcess() {
		
	}

	private void runScheduling() {
		
		// 
		drawGanttChart(new History());
		
	}
	
	private void drawGanttChart(History history) {
		
	}
	
}
