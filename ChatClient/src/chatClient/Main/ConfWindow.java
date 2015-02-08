package chatClient.Main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import chatClient.Client.Client;

public class ConfWindow extends JFrame {
	private JTextField ipTextField;
	private JTextField usernameField;
	
	public static Client client;
	
	public ConfWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setSize(190, 145);
		setTitle("Config");
		getContentPane().setLayout(null);
		
		JButton okButton = new JButton("Ok");
		okButton.setBounds(106, 70, 56, 23);
		final GUI gui = new GUI(this);
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				client = new Client(getIP(), 10000, getUsername());
				setVisible(false);
				gui.setVisible(true);
			}
		});
		getContentPane().add(okButton);
		
		JLabel ipLabel = new JLabel("IP Address:");
		ipLabel.setBounds(10, 11, 68, 14);
		getContentPane().add(ipLabel);
		
		ipTextField = new JTextField();
		ipTextField.setText("108.73.89.75");
		ipTextField.setBounds(78, 8, 86, 20);
		getContentPane().add(ipTextField);
		ipTextField.setColumns(10);
		
		JLabel usernameLabel = new JLabel("Username:");
		usernameLabel.setBounds(10, 42, 68, 14);
		getContentPane().add(usernameLabel);
		
		usernameField = new JTextField();
		usernameField.setText("John Doe");
		usernameField.setBounds(78, 39, 86, 20);
		getContentPane().add(usernameField);
		usernameField.setColumns(10);
	}
	public String getIP() {
		return ipTextField.getText();
	}
	public String getUsername() {
		return usernameField.getText();
	}
	public static void main(String[] args) {
		ConfWindow conf = new ConfWindow();
		conf.setVisible(true);
	}
}
