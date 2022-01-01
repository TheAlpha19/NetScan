package Gui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import Scan.FullScan;
import Scan.HostAlive;
import Scan.OSDetect;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.net.InetAddress;
import javax.swing.JTextPane;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.Toolkit;


/*
 * Start Scan : initiates Scanning Process
 * Reset : Resets The textPane After Scan
 * Terminate : Terminates ongoing scan process
 */

public class Init extends JFrame {

	public String Banner = "\r\n\n\n\n\n\n\n\n\n\n\n\n\n"
			+ "███╗   ██╗███████╗████████╗███████╗ ██████╗ █████╗ ███╗   ██╗\r\n"
			+ "████╗  ██║██╔════╝╚══██╔══╝██╔════╝██╔════╝██╔══██╗████╗  ██║\r\n"
			+ "██╔██╗ ██║█████╗     ██║   ███████╗██║     ███████║██╔██╗ ██║\r\n"
			+ "██║╚██╗██║██╔══╝     ██║   ╚════██║██║     ██╔══██║██║╚██╗██║\r\n"
			+ "██║ ╚████║███████╗   ██║   ███████║╚██████╗██║  ██║██║ ╚████║\r\n"
			+ "╚═╝  ╚═══╝╚══════╝   ╚═╝   ╚══════╝ ╚═════╝╚═╝  ╚═╝╚═╝  ╚═══╝\r\n"
			+ "                                                             \r\n"
			+ "";
	private JPanel contentPane;
	private JTextField txtIp;
	private JComboBox comboBox;
	private String[] Mode_arr;
	private JTextPane lblNewLabel_2;
	private StyledDocument doc;
	private SimpleAttributeSet center;
	private JLabel nsba_title;
	private JButton reset, st_scan, terminate;
	private static Thread t[];
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Init frame = new Init();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void Terminator(Thread[] arr) {
		for (Thread t : arr) {
			try {
				if(t.isAlive()) {
					t.stop();
				}
			}catch(Exception e) {}
		}
	}

	private void Reset() {
		doc = lblNewLabel_2.getStyledDocument();
		center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		lblNewLabel_2.setText(Banner);
		nsba_title.setText("");
		st_scan.setEnabled(true);
		terminate.setEnabled(false);
		reset.setEnabled(false);
		txtIp.setEditable(true);
	}	

	private boolean checkIP(String IP) {
		String regex = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(IP);

		if(m.matches()) {
			return true;
		}else {
			return false;
		}
	}

	private boolean checkIP_CIDR(String IP) {
		String regex = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])(\\/(3[0-2]|[1-2][0-9]|[0-9]))$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(IP);

		if(m.matches()) {
			return true;
		}else {
			return false;
		}
	}

	/**
	 * Create the frame.
	 */
	public Init() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("logo.png"));
		setTitle("NetScan");
		setBackground(Color.LIGHT_GRAY);
		setResizable(false);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 893, 558);

		Mode_arr = new String[] {"Simple Port Scan", "Full Port Scan", "TTL OS Detection", "Check Alive Hosts"};

		contentPane = new JPanel();

		contentPane.setBackground(new Color(24, 25, 26));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 343, 519);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("NetScan");
		lblNewLabel.setFont(new Font("Consolas", Font.BOLD, 40));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(76, 49, 165, 57);
		panel.add(lblNewLabel);

		txtIp = new JTextField();
		//txtIp.
		txtIp.setFont(new Font("Consolas", Font.PLAIN, 12));
		txtIp.setHorizontalAlignment(SwingConstants.CENTER);
		txtIp.setBounds(127, 166, 157, 26);
		panel.add(txtIp);
		txtIp.setColumns(10);


		JLabel lblNewLabel_1 = new JLabel("IP Address");
		lblNewLabel_1.setFont(new Font("Consolas", Font.PLAIN, 12));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(41, 166, 76, 26);
		panel.add(lblNewLabel_1);

		JSeparator separator = new JSeparator();
		separator.setBounds(41, 117, 243, 2);
		panel.add(separator);


		lblNewLabel_2 = new JTextPane();
		nsba_title = new JLabel();
		reset = new JButton("Reset");
		st_scan = new JButton("Start Scan");
		terminate = new JButton("Terminate");
		terminate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				terminate.setEnabled(false);
				reset.setEnabled(true);
				Terminator(t);
			}
		});

		Reset();

		lblNewLabel_2.setEditable(false);
		lblNewLabel_2.setFont(new Font("Liberation Mono", Font.PLAIN, 12));
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setBackground(new Color(24, 25, 26));
		lblNewLabel_2.setBounds(353, 35, 514, 473);

		st_scan.setForeground(Color.WHITE);
		st_scan.setBackground(new Color(24, 25, 26));
		st_scan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String IP = txtIp.getText();

				int mode = comboBox.getSelectedIndex();
				nsba_title.setText("NetScan By Alpha19 [A19]");
				lblNewLabel_2.setText("");
				StyleConstants.setAlignment(center, StyleConstants.ALIGN_LEFT);
				doc.setParagraphAttributes(0, doc.getLength(), center, false);

				st_scan.setEnabled(false);
				terminate.setEnabled(true);
				txtIp.setEditable(false);

				if(mode >= 0 && mode <= 2) {
					if(checkIP(IP)) {
						try {
							if(InetAddress.getByName(IP).isReachable(5000)) {
								if(mode == 0) {
									lblNewLabel_2.setText(lblNewLabel_2.getText() + "\n[+] Starting Simple Scan [+]");
		
									t = new Thread[4];
		
									int cnt = 0, rng = 9001-2250;
									for(int i = 1; i <= rng; i += 2250) {
										t[cnt] = new Thread(new FullScan(IP, i, (i + 2250), lblNewLabel_2));
										t[cnt].start();
										cnt++;
									}
								}else if(mode == 1) {
									lblNewLabel_2.setText(lblNewLabel_2.getText() + "\n[+] Starting Full Scan [+]");
		
									int cnt = 0, rng = 65535-3276;
									t = new Thread[21];
									for(int i = 1; i <= rng; i += 3276) {
										t[cnt] = new Thread(new FullScan(IP, i, (i + 3276), lblNewLabel_2));
										t[cnt].start();
										cnt++;
										if(i == 20) { //Closing gap to the last bit
											t[cnt] = new Thread(new FullScan(IP, 65521, 65535, lblNewLabel_2));
											t[cnt].start();
										}
									}
								}else {
									OSDetect o = new OSDetect(IP, lblNewLabel_2);
								}
							}else {
								lblNewLabel_2.setText("[!] Please Provide an Active/Discoverable IP Address [!]");
							}
						}catch(Exception e3) {}
					}else {
						lblNewLabel_2.setText("[!] Please Provide a Valid IP Address [!]");
					}
				}else {
					if(checkIP_CIDR(IP)) {
						t = new Thread[1];
						t[0] = new Thread(new HostAlive(IP, lblNewLabel_2));
						t[0].start();
					}else {
						lblNewLabel_2.setText("[!] Please Provide a Valid IP Address with a valid CIDR block [x.x.x.x/{0-32}] [!]");
					}
				}
			}
		});
		st_scan.setFont(new Font("Consolas", Font.PLAIN, 14));
		st_scan.setBounds(41, 415, 243, 38);
		panel.add(st_scan);

		comboBox = new JComboBox();
		comboBox.setBackground(Color.WHITE);
		comboBox.setForeground(Color.BLACK);
		comboBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		comboBox.setModel(new DefaultComboBoxModel(Mode_arr));
		comboBox.setBounds(127, 292, 157, 26);
		panel.add(comboBox);

		JLabel lblNewLabel_1_1 = new JLabel("Scan Mode");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1.setFont(new Font("Consolas", Font.PLAIN, 12));
		lblNewLabel_1_1.setBounds(41, 292, 76, 26);
		panel.add(lblNewLabel_1_1);

		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Reset();
			}
		});
		reset.setForeground(Color.WHITE);
		reset.setFont(new Font("Consolas", Font.PLAIN, 14));
		reset.setBackground(new Color(24, 25, 26));
		reset.setBounds(170, 470, 119, 38);
		panel.add(reset);

		terminate.setForeground(Color.WHITE);
		terminate.setFont(new Font("Consolas", Font.PLAIN, 14));
		terminate.setBackground(new Color(24, 25, 26));
		terminate.setBounds(41, 470, 119, 38);
		panel.add(terminate);

		contentPane.add(lblNewLabel_2);

		nsba_title.setFont(new Font("Liberation Mono", Font.PLAIN, 14));
		nsba_title.setBounds(353, 11, 397, 14);
		nsba_title.setForeground(Color.WHITE);
		contentPane.add(nsba_title);
	}
}
