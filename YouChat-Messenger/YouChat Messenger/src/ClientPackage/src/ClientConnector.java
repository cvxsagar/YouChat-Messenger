/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientPackage.src;

import java.awt.*;
import javax.swing.*;
/**
 *
 * @author Sagar
 */
public class ClientConnector extends JFrame{
     public static void main(String args[])
    {
       SwingUtilities.invokeLater(new Runnable()
        {
        @Override   public void run()
                    {
                        clientGUI();
                    }
        });
    }  

    private static final ClientConnector hostCheck = new ClientConnector();
    static
    {
        JFrame authorize= new JFrame(); 
        JPanel panelData= new JPanel(new BorderLayout(5, 5)); 
      //panelData.add( new JLabel("Hosts Connection Check."), BorderLayout.NORTH); 
        panelData.add( new JLabel("Client Login"), BorderLayout.NORTH);
        
        JPanel showLabel= new JPanel(new GridLayout(0, 1, 2, 2)); 
        showLabel.add(new JLabel("Username", SwingConstants.RIGHT)); 
        showLabel.add(new JLabel("Password", SwingConstants.RIGHT));
        panelData.add(showLabel, BorderLayout.WEST);
        
        JPanel showTextField= new JPanel(new GridLayout(0, 1, 2, 2));
        JTextField username= new JTextField(); 
        showTextField.add(username); 
        JTextField password= new JPasswordField(); 
        showTextField.add(password); 
        panelData.add(showTextField, BorderLayout.CENTER);
        
        JOptionPane.showMessageDialog(
                authorize, panelData, "Login Check", JOptionPane.QUESTION_MESSAGE); 
        if ((!username.getText().equals("user"))&&(!password.getText().equals("user123456")))
        {       
            System.exit(0);
            JOptionPane.showMessageDialog(
                        null, "Incorrect Username or Password", "alert", JOptionPane.ERROR_MESSAGE); 
             
        }
        else {
            //mere system ki ip
            ClientPackage.src.Client clientUser= new ClientPackage.src.Client("192.168.1.5");
            clientUser.setSize(600,400);
            clientUser.setDefaultCloseOperation(EXIT_ON_CLOSE); 
            clientUser.initiateConnection(); 
            clientUser.setVisible(true);
        }
    }
 
    private static void clientGUI()
    {
        hostCheck.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        hostCheck.setLocationRelativeTo(null);     
        hostCheck.setSize(250, 200);
        hostCheck.setVisible(true);
    }
}
