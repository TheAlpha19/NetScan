package Scan;
import java.net.InetAddress;

import javax.swing.JTextPane;
import org.apache.commons.net.util.*;

public class HostAlive implements Runnable{	
	private String IP;
	private JTextPane j;
	
	public HostAlive(String IP, JTextPane j) {
		this.IP = IP;
		this.j = j;
	}
	
	@Override
	public void run() {
		SubnetUtils utils = new SubnetUtils(IP);
		String[] allIps = utils.getInfo().getAllAddresses();

		j.setText("[!] Finding All Online Hosts on " + IP + " Network [!]");
		for(String ip : allIps) {
			try {
				if (InetAddress.getByName(ip).isReachable(500)){
				      j.setText(j.getText() + "\n[+]" + ip + " is Online [+]");
				}
			} catch (Exception e) {}
		}
	}
}
