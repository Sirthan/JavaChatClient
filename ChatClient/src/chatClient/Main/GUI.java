package chatClient.Main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoundedRangeModel;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class GUI extends JFrame {
	public static JTextPane outgoingMessagePane = null;
	public static JTextPane incomingMessagePane = null;
	public static Color color = Color.BLACK;
	
	public GUI(final ConfWindow win) {
		setIconImage(Toolkit.getDefaultToolkit().getImage("dock-icon.png"));
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		setUndecorated(true);
		setTitle("Chat Client");
		setLocationRelativeTo(null);
		getContentPane().setBackground(UIManager.getColor("Button.background"));
		getContentPane().setLayout(null);
		
		JPanel topPanel = new JPanel();
		topPanel.setBounds(0, 0, 500, 17);
		topPanel.setBackground(new Color(240, 240, 240));
		getContentPane().add(topPanel);
		topPanel.setLayout(new BorderLayout(0, 0));
		
		BufferedImage closeImage = null;
		try {
			closeImage = ImageIO.read(new File("close-icon-small.png"));
		} catch (IOException ie) {
			ie.printStackTrace();
		}
		JButton closeButton = new JButton(new ImageIcon(closeImage));
		closeButton.setBorder(null);
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				win.client.sendData(".quit");
				win.client.end();
			}
		});
		closeButton.setToolTipText("Close");
		topPanel.add(closeButton, BorderLayout.EAST);
		
		JLabel titleLabel = new JLabel("JIM Client");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		topPanel.add(titleLabel, BorderLayout.CENTER);
		
		incomingMessagePane = new JTextPane();
		incomingMessagePane.setText("JIM Version 0.0.2\nCopyright 2015, Silicon Incorporated, All rights reserved.");
		incomingMessagePane.setEditable(false);
		
		JScrollPane scrollPane = new JScrollPane(incomingMessagePane);
		scrollPane.setWheelScrollingEnabled(true);
		scrollPane.getVerticalScrollBar();
		scrollPane.setBounds(10, 28, 480, 230);
		getContentPane().add(scrollPane);
		
		outgoingMessagePane = new JTextPane();
		outgoingMessagePane.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		outgoingMessagePane.setBounds(10, 269, 410, 20);
		getContentPane().add(outgoingMessagePane);
		
		JButton sendButton = new JButton("Send");
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConfWindow.client.sendData(outgoingMessagePane.getText());
				outgoingMessagePane.setText(null);
			}
		});
		sendButton.setBounds(430, 266, 60, 23);
		getContentPane().add(sendButton);
		
		InputMap im = outgoingMessagePane.getInputMap();
        im.put(KeyStroke.getKeyStroke("ENTER"), "none");
        im.put(KeyStroke.getKeyStroke("released ENTER"), "released");
        this.outgoingMessagePane.getRootPane().setDefaultButton(sendButton);
		
		setSize(500, 300);
		
		addMouseMotionListener(new MouseMotionListener() {
		    private int mx, my;

		    @Override
		    public void mouseMoved(MouseEvent e) {
		        mx = e.getXOnScreen();
		        my = e.getYOnScreen();
		    }

		    @Override
		    public void mouseDragged(MouseEvent e) {
		        Point p = getLocation();
		        p.x += e.getXOnScreen() - mx;
		        p.y += e.getYOnScreen() - my;
		        mx = e.getXOnScreen();
		        my = e.getYOnScreen();
		        setLocation(p);
		    }
		});
	}
	public static void appendMessage(String mes) {
		StyleContext sc = StyleContext.getDefaultStyleContext();
        
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, color);

//      aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = incomingMessagePane.getDocument().getLength();
        incomingMessagePane.setCaretPosition(len);
        incomingMessagePane.setCharacterAttributes(aset, false);
        
		incomingMessagePane.setText(incomingMessagePane.getText() + "\n" + mes);
		incomingMessagePane.setCaretPosition(incomingMessagePane.getDocument().getLength());
	}
}
