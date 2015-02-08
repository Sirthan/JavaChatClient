package chatClient.Main;

import java.util.Scanner;

import chatClient.Client.Client;

public class Main {
	public static void main(String[] args){
		Client client = null;
		if(args.length == 3){
			client = new Client(args[0], Integer.parseInt(args[1]), args[2]);
		} else if (args.length != 0){
			System.out.println("Useage - java chatClient/Main/Main [String serverName] [int serverPort] [String userName]");
		} else {
			Scanner sc = new Scanner(System.in);
			System.out.println("What is the server Name?");
			String servName = sc.nextLine();
			System.out.println("What is the server Port?");
			int servPort = 0;
			try{
				servPort = Integer.parseInt(sc.nextLine());
			} catch (NumberFormatException error){
				System.out.println("That is not a number!");
				System.exit(1);
			}
			System.out.println("What is your Username?");
			String userName = sc.nextLine();
			client = new Client(servName, servPort, userName);
		}
	}
}
