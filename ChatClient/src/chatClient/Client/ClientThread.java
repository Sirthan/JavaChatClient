package chatClient.Client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientThread extends Thread{
	private Socket socket = null;
	private Client client = null;
	private DataInputStream streamIn = null;
	
	public ClientThread(Client client){
		this.client = client;
		socket = client.getSocket();
		begin();
		start();
	}
	
	public void begin(){
		try{
			streamIn = new DataInputStream(socket.getInputStream());;
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
					client.handle(streamIn.readUTF());
			} catch(IOException error){
				System.out.println("ERROR : Error in listening! Please report to SiliconIncorporatedOrganization!");
				System.out.println(error.getMessage());
				client.end();
			}
		}
	}
}
