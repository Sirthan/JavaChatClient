package chatClient.Client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client implements Runnable{  
	private Socket socket = null;
	private Thread thread = null;
	private BufferedReader console = null;
	private DataOutputStream streamOut = null;
	private String username = null;
	private ClientThread client = null;
	
	
	public Client(String serverName, int serverPort, String username){
		System.out.println("Attempting to establish connection");
		try{
			this.username = username;
			socket = new Socket(serverName, serverPort);
			System.out.println("Connection Established!");
			begin();
		} catch(UnknownHostException error){
			System.out.println("ERROR : Host of that Name or Port unknown!");
			System.out.println(error.getMessage());
		} catch(IOException error){
			System.out.println("ERROR : Unknown Exception, Please report to SiliconIncorporatedOrganization!");
			System.out.println(error.getMessage());
		}
		
	}
	
	@Override
	public void run() {
		while(thread != null){
			try{
				streamOut.writeUTF(username + " says: " + console.readLine());
				streamOut.flush();
			} catch(IOException error){
				System.out.println("ERROR : Unknown sending exception, Please report to SiliconIncorporatedOrganization!");
				System.out.println(error.getMessage());
				end();
			}
		}
		
	}
	
	public void handle(String mes){
		if(mes.equalsIgnoreCase(".bye")){
			System.out.println("Logging off...");
			end();
		} else {
			System.out.println(mes);
		}
	}
	
	public void begin() throws IOException{
		console = new BufferedReader(new InputStreamReader(System.in));
		streamOut = new DataOutputStream(socket.getOutputStream());
		if(thread == null){
			client = new ClientThread(this);
			thread = new Thread(this);
			thread.start();
		}
	}
	
	public void end(){
		try{
			if(thread != null){
				try{
					thread.join();
				} catch(InterruptedException error){
					error.printStackTrace();
				}
				thread = null;
			}
			if(console != null) console.close();
			if(streamOut != null) streamOut.close();
			if(socket != null) socket.close();
		} catch(IOException error){
			System.out.println("ERROR : Closing error, Please report to SiliconIncorporatedOrganization");
			System.out.println(error.getMessage());
		}
		try{
			client.join();
		} catch(InterruptedException error){
			error.printStackTrace();
		}
		client.end();
	}
	
	public Socket getSocket(){
		return socket;
	}
}