package chatClient.Client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientThread extends Thread{
	private Socket socket = null;
	private Client client = null;
	private BufferedReader streamIn = null;
	
	public ClientThread(Client client){
		this.client = client;
		socket = client.getSocket();
		begin();
		start();
	}
	
	public void begin(){
		try{
			streamIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));;
		} catch(IOException error){
			System.out.println("ERROR : Error in getting Input Stream! Please report to SiliconIncorporatedOrganization!");
			System.out.println(error.getMessage());
			client.end();
		}
	}
	
	public void end(){
		try{
			if(streamIn != null) streamIn.close();
		} catch(IOException error){
			System.out.println("ERROR : Error in closing! Please report to SiliconIncorporatedOrganization!");
			System.out.println(error.getMessage());
		}
	}
	
	public void run(){
			while(true){
				try{
					client.handle(streamIn.readLine());
			} catch(IOException error){
				System.out.println("ERROR : Error in listening! Please report to SiliconIncorporatedOrganization!");
				System.out.println(error.getMessage());
			}
		}
	}
}