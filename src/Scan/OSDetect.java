package Scan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JTextPane;

public class OSDetect {
	public OSDetect(String IP, JTextPane j) {
		try {
			Process p = Runtime.getRuntime().exec("ping " + IP);

			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));

			br.readLine();
			br.readLine();

			String[] spl = br.readLine().split("=");
			br.close();

			int ttl = Integer.parseInt(spl[spl.length - 1]);

			j.setText("OS TTL: " + ttl);

			if(ttl >= 49 && ttl <= 79) {
				j.setText(j.getText() + "\nOS Type Based on TTL: *nix OS (Unix/Linux)");
			}else if(ttl >= 113 && ttl <= 143) {
				j.setText(j.getText() + "\nOS Type Based on TTL: Windows (Windows Server 2003/XP/Vista/7/Server 2008/10)");
			}else if(ttl >= 239 && ttl <= 269) {
				j.setText(j.getText() + "\nOS Type Based on TTL: Solaris (Solarix/AIX Based OS)");
			}else {
				j.setText(j.getText() + "\nUnable to detect the OS from TTL values :(");
			}
		} catch (IOException e) {}
	}
}
