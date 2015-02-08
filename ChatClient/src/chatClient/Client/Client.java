package chatClient.Client;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import chatClient.Main.GUI;


public class Client implements Runnable {  
	private Socket socket = null;
	private Thread thread = null;
	private BufferedReader console = null;
	private DataOutputStream streamOut = null;
	private String username = null;
	private ClientThread client = null;
	

	public Client(String serverName, int serverPort, String username) {
		System.out.println("Attempting to establish connection");
		try {
			this.username = username;
			socket = new Socket(serverName, serverPort);
			System.out.println("Connection Established!");
			begin();
		} catch (UnknownHostException error) {
			System.out.println("ERROR : Host of that Name or Port unknown!");
			System.out.println(error.getMessage());
			System.exit(1);
		} catch (IOException error) {
			System.out.println("ERROR : Unknown Exception, Please report to SiliconIncorporatedOrganization!");
			System.out.println(error.getMessage());
			System.exit(1);
		}
	}
	@Override
	public void run() {
		// while(thread != null){
		//
		// }
	}
	public void sendData(String mes) {
		try {
			if(mes.charAt(0) == '*'){
				System.out.println(username + " " + mes.substring(1));
				streamOut.writeUTF(username + " " + mes.substring(1));
				streamOut.flush();
			} 
			else if (mes.trim().startsWith(".")) {
				if (mes.split(" ")[0].equals(".color")) {
					String color = mes.split(" ")[1];
					if (color.equals("RED")) GUI.color = Color.RED;
					else if (color.equalsIgnoreCase("GREEN"))  GUI.color = Color.GREEN;
					else if (color.equalsIgnoreCase("BLUE"))  GUI.color = Color.BLUE;
					else if (color.equalsIgnoreCase("PINK")) GUI.color = Color.PINK;
					else if (color.equalsIgnoreCase("CYAN")) GUI.color = Color.CYAN;
					else if (color.equalsIgnoreCase("BROWN")) GUI.color = new Color(158, 84, 32);
					else if (color.equalsIgnoreCase("SILVER")) GUI.color = new Color(163, 168, 168);
					else if (color.equalsIgnoreCase("YELLOW")) GUI.color = Color.YELLOW;
					else if (color.equalsIgnoreCase("ORANGE")) GUI.color = Color.ORANGE;
					else if (color.equalsIgnoreCase("SMEXY")) GUI.color = new Color(255, 179, 171);
					else if (color.equalsIgnoreCase("DGREEN")) GUI.color = new Color(4, 130, 0);
					
				}
			} else {
				if(mes.trim().length() != 0){
					System.out.println(username + ": " + mes);
					streamOut.writeUTF(username + ": " + mes);
					streamOut.flush();
				}
			}
		} catch (IOException error) {
			System.out.println("ERROR : Unknown sending exception, Please report to SiliconIncorporatedOrganization!");
			System.out.println(error.getMessage());
			end();
		}
	}
	public void handle(String mes) {
		GUI.appendMessage(mes);
	}
	public void begin() throws IOException {
		console = new BufferedReader(new InputStreamReader(System.in));
		streamOut = new DataOutputStream(socket.getOutputStream());
		if (thread == null) {
			client = new ClientThread(this);
			thread = new Thread(this);
			thread.start();
		}
		
	}
	public void end() {
		try {
			if (thread != null) {
				try {
					thread.join();
				} catch (InterruptedException error) {
					error.printStackTrace();
				}
				thread = null;
			}
			if (console != null)
				console.close();
			if (streamOut != null)
				streamOut.close();
			if (socket != null)
				socket.close();
		} catch (IOException error) {
			System.out.println("ERROR : Closing error, Please report to SiliconIncorporatedOrganization");
			System.out.println(error.getMessage());
		}
		client.end();
		try {
			client.join();
		} catch (InterruptedException error) {
			error.printStackTrace();
		}
	}
	public Socket getSocket() {
		return socket;
	}
}