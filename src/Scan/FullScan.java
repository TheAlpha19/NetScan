package Scan;

import java.net.Socket;
import javax.swing.JTextPane;


public class FullScan implements Runnable{
	protected String IP;
	protected int port_st;
	protected int port_end;
	protected JTextPane j;
	
	public FullScan(String IP, int port_st, int port_end, JTextPane j){
		this.IP = IP;
		this.port_end = port_end;
		this.port_st = port_st;
		this.j = j;
	}
	
	@Override
	public void run() {
		for(int i = port_st; i <= port_end; i++) {
			try {
				Socket s = new Socket(IP, i);
				s.close();
				j.setText(j.getText() + "\nOpen Port @ " + IP + ":" + i + "/tcp");
			}catch(Exception e) {
			}
		}
	}
}