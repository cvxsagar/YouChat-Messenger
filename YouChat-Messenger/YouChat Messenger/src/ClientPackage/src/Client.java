/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientPackage.src;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.*; 
import java.io.*; 
import javax.swing.*; 
import javax.imageio.ImageIO; 
/**
 *
 * @author Sagar
 */
public class Client extends JFrame {
    //member variables 

        private JLabel topLayout; 
        private JTextArea displayText;
        private final String targetIP;
	private ObjectOutputStream outStream;
	private JTextField msg;
	private ObjectInputStream inStream;
	private String message = "";
	private Socket connect;
    //constructor
	public Client(String host) {
		super("YouChat Messaging");
		targetIP = host;
		msg = new JTextField();
		msg.setEditable(false);
		msg.addActionListener(event -> {
                                sendMessage(event.getActionCommand());
                                msg.setText("");
                            }); 
                topLayout= new JLabel(new ImageIcon(
                        "C:\\Users\\Sagar\\Documents\\NetBeansProjects\\"
                + "CollegeProject\\src\\ClientPackage\\src\\topLayoutClient.png"));
                add(topLayout, BorderLayout.NORTH);
		add(msg, BorderLayout.SOUTH);
		displayText = new JTextArea();
                displayText.setFont(displayText.getFont().deriveFont(18f));
                displayText.setBounds(0,200,600,300);
                add(new JScrollPane(displayText));
		setVisible(true);
                
        }
	public void initiateConnection() {

		try {
			connectToServer();
			setupStreams();
			whileChatting();
		} catch (EOFException eofException) {
			showMessage("\n Connection Terminated by Device1");

		} catch (IOException ioException) {
			ioException.printStackTrace();
		} finally {
			chatTermination();
		}
	}
        
        private void sendMessage(String message){
		try{
			outStream.writeObject("USER - "+ message);
			outStream.flush();
			showMessage("\nUSER - "+message);
		}catch(IOException ioException){
			displayText.append("\nSomething is messed up!");
		}
		
	}
	
	private void showMessage(final String m){
		SwingUtilities.invokeLater(new Runnable(){
	@Override	public void run(){
				displayText.append(m);
			}
		});
	}
        
	private void connectToServer() throws IOException {
		showMessage("Sending Connection Request.. Please wait.. ");
		connect = new Socket(InetAddress.getByName(
                        
                        targetIP), 55555);
		showMessage("\nYour'e connected !");

	}

	private void setupStreams() throws IOException {
		outStream = new ObjectOutputStream(connect.getOutputStream());
		outStream.flush();
		inStream = new ObjectInputStream(connect.getInputStream());
		showMessage("\nStreams working fine.");

	}

	private void whileChatting() throws IOException {
		interruptTyping(true);
		do {
			try {
				message = (String) inStream.readObject();
				showMessage("\n" + message);

			} catch (ClassNotFoundException classNotFoundException) {
				showMessage("\nDont know ObjectType!");
			}
		} while (!message.equals("\nADMIN - END"));
	}
        
        private void interruptTyping(final boolean random){
		SwingUtilities.invokeLater(new Runnable(){
	@Override	public void run(){
				msg.setEditable(random);
			}
		});
	}

	private void chatTermination() {
		showMessage("\nClosing down!");
		interruptTyping(false);
		try {
			outStream.close();
			inStream.close();
			connect.close();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}
	
}
