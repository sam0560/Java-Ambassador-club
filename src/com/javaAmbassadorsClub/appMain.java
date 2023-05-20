package com.javaAmbassadorsClub;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class appMain {
    private JPanel framePanel;
    private JPanel eastPanel;
    private JLabel javaLogo;
    private JLabel titleLabel;
    private JButton createAccountButton;
    private JLabel newMemberLabel;
    private JPanel westPanel;
    private JTextField validateUsernameTF;
    private JPasswordField validatePasswordTF;
    private JButton loginButton;
    private JLabel oldMemberLabel;
    private JLabel validateUsernameLabel;
    private JLabel validatePasswordLabel;

    public appMain() {
//        Make create account button bigger
        Dimension dim = createAccountButton.getPreferredSize();
        dim.height = 70;
        createAccountButton.setPreferredSize(dim);
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                copy content of main method in dialog definition here
                AccountDialogue dialogue = new AccountDialogue();
                dialogue.setTitle("Create Account");
                dialogue.setLocationRelativeTo(null);
                dialogue.setBounds(300, 300, 700, 350); // set frame position and size
                dialogue.action = "create";
                dialogue.pack();
                dialogue.setVisible(true);
//                System.exit(0); //Do not exit program by default
            }
        });

        // Establish a link to the database
        Controller controller = new Controller();
        Connection logLink = controller.getMyConn();
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String sQL = "SELECT * FROM registration where userName = ? and password = ?";
                    PreparedStatement statement = logLink.prepareStatement(sQL);

                    statement.setString(1, validateUsernameTF.getText());
                    statement.setString(2, validatePasswordTF.getText());

                    ResultSet resultSet = statement.executeQuery(); // to retrieve data use result set

                    if (resultSet.next()) {
                        JOptionPane.showMessageDialog(null, "Login successful");

//                        Clear entries
                        validateUsernameTF.setText("");
                        validatePasswordTF.setText("");

//                        Display membersRegister Form
                        JFrame frame = new JFrame("Members Register");
                        frame.setContentPane(new MemberRegister().registerPanel);//Make register public
                        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//Dispose frame without exiting program
                        frame.setBounds(200, 400, 650, 300);
                        //frame.pack();
                        frame.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid username or password");
                    }
                } catch (Exception el) {
                    el.printStackTrace();
                }
            }
        }); //login action ends here
    } //constructor ends here
    private static JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu helpMenu = new JMenu("Help");

        JMenuItem saveDataItem = new JMenuItem("Save As ...");
        JMenuItem openDataItem = new JMenuItem("Open ...");
        JMenuItem exitItem = new JMenuItem("Exit");
        JMenuItem aboutItem = new JMenuItem("About");

        fileMenu.add(saveDataItem);
        fileMenu.add(openDataItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        helpMenu.add(aboutItem);

        // Panel menu sub menus
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

        // set Mnemonics
        fileMenu.setMnemonic(KeyEvent.VK_F);
        helpMenu.setMnemonic(KeyEvent.VK_H);

        // add accelerator
        saveDataItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        openDataItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));

        openDataItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Members Register");
                frame.setContentPane(new MemberRegister().registerPanel);//Make register public
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//Dispose frame without exiting program
                frame.setBounds(200, 400, 650, 300);
                //frame.pack();
                frame.setVisible(true);
            }
        });

        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int exit = JOptionPane.showConfirmDialog(null, "Do you want to exit?", "confirm exit",
                JOptionPane.OK_CANCEL_OPTION);
                if(exit == JOptionPane.OK_OPTION){
                    System.exit(0);
                }
            }
        });

        aboutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                CopyrightInformation copyrightInformation = new CopyrightInformation();
                JWindow window = new JWindow();
                window.setContentPane(new CopyrightInformation().CpframePanel);//Make register public
                window.setBounds(200, 300, 650, 300);
                window.setVisible(true);
            }
        });
        return menuBar;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Java Ambassadors");
        frame.setContentPane(new appMain().framePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(150, 100, 700, 350); // set frame position and size
        frame.setJMenuBar(createMenuBar());
        frame.pack();
        frame.setVisible(true);
    }
}
