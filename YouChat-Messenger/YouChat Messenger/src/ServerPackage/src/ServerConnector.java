/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerPackage.src;
import java.awt.*;
import javax.swing.*; 

/**
 *
 * @author Sagar
 */
public class ServerConnector extends JFrame
{
   
    private static ServerConnector valid = new ServerConnector();
    
    static
    {
        JFrame authorize= new JFrame(); 
        JPanel panelData= new JPanel(new BorderLayout(5, 5)); 
        panelData.add( new JLabel("Admin authorization"), BorderLayout.NORTH); 
      //panelData.add( new JLabel("Please enter your username and password"), BorderLayout.NORTH);
        
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
                authorize, panelData, "Authorization", JOptionPane.QUESTION_MESSAGE); 
        if ((!username.getText().equals("admin"))&&(!password.getText().equals("admin123456")))
        {       
            JOptionPane.showMessageDialog(
                        null, "Incorrect Username or Password", "alert", JOptionPane.ERROR_MESSAGE); 
            System.exit(0); 
        }
        else {
            ServerPackage.src.Server admin= new ServerPackage.src.Server();
            admin.setDefaultCloseOperation(EXIT_ON_CLOSE); 
            admin.setSize(600, 400);
            admin.setVisible(true); 
            admin.initiateConnection(); 
        }
    }
 
    private static void serverGUI()
    {
        valid.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        valid.setLocationRelativeTo(null);
        valid.setSize(250, 200);
        valid.setVisible(true);
    }
    
     public static void main(String args[])
    {
       SwingUtilities.invokeLater(new Runnable()
        {
        @Override   public void run()
                    {
                        serverGUI();
                    }
        });
    }
}