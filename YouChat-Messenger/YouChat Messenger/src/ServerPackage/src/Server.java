/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerPackage.src;
import java.io.*; 
import java.awt.*; 
import java.awt.event.ActionEvent; 
import javax.swing.*; 
import java.net.*; 
/**
 *
 * @author Sagar
 */
public class Server extends JFrame 
{
        private JButton b1; 
        private JTextField userMessage;
	private final JTextArea textAreaPanel;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private ServerSocket server;
	private Socket connection=null; 
        private final JLabel topImage; 
	public Server() {
		super("YouChat Messaging");
		userMessage = new JTextField();
		userMessage.setEditable(false);
		userMessage.addActionListener((ActionEvent event) -> {
                sendMessage(event.getActionCommand());
                userMessage.setText("");
                });
                topImage= new JLabel(new ImageIcon(
                 ".//ServerPackage//src//topLayoutServer.png"));
               
                add(topImage, BorderLayout.NORTH);
		add(userMessage, BorderLayout.SOUTH);
		textAreaPanel = new JTextArea();
                textAreaPanel.setFont(textAreaPanel.getFont().deriveFont(18f));
                add(new JScrollPane(textAreaPanel), BorderLayout.CENTER);
		setSize(600,500); 
		setVisible(true);

	}

	public void initiateConnection() {
		try {
			server = new ServerSocket(55555, 100);
			while (true) {
				try {
					awaitingConnection();
					checkStreams();
					whileMessaging();
				} catch (EOFException eofException) {
					showMessage("\n Connection Terminated by device 2");
				} finally {
					chatTermination();
				}
			}
		} catch (IOException ioException) {
			ioException.printStackTrace();

		}
	}
	public void awaitingConnection() throws IOException {
		showMessage("Awaiting Connection.. ");
		connection = server.accept();
		showMessage("\nConnection established with client !"); 
	}

	public void checkStreams() throws IOException {
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		showMessage("\nStreams running fine \n");
	}

	public void whileMessaging() throws IOException {
		String message = "\nYou are now connected!";
		sendMessage(message);
		interruptTyping(true);
		do {
			try {
				message = (String) input.readObject();
				showMessage("\n" + message);
			} catch (ClassNotFoundException classNotFoundException) {
				showMessage("\n I don't know what user send!");
			}
		} while (!message.equals("\nUSER-END"));
	}

	public void sendMessage(String message) {
		try {
			output.writeObject("ADMIN- " + message);
			output.flush();
			showMessage("\nADMIN- " + message);

		} catch (IOException ioException) {
			textAreaPanel.append("\nERROR: Can't send that message");
		}
	}

	public void chatTermination() {
		showMessage("\n Closing connections \n");
		interruptTyping(false);
		try {
			output.close();
			input.close();
			connection.close();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	public void showMessage(final String text) {
		SwingUtilities.invokeLater(() -> {
                    textAreaPanel.append(text);
                });
	}

	public void interruptTyping(final boolean tof) {
		SwingUtilities.invokeLater(() -> {
                    userMessage.setEditable(tof);
                });
	}
    
}