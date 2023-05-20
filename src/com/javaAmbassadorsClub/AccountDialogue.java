package com.javaAmbassadorsClub;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.lang.Integer;
import java.util.ArrayList;

public class AccountDialogue extends JDialog {
    private JPanel contentPane;
    public String action;
    private JLabel firstNameLabel;
    private JLabel lastNameLabel;
    public JTextField firstNameTF;
    public JTextField lastNameTF;
    private JLabel userNameLabel;
    public JTextField userNameTF;
    private JLabel createPasswordLabel;
    public JPasswordField createPasswordTF;
    private JLabel mobileLabel;
    public JTextField mobileTF;
    public JTextField emailTF;
    private JLabel emailLabel;
    private JButton cancelButton;
    private JButton saveButton;
    private JPanel formPanel;

    public JTextField serialNumberField; // temporal field to store serial number

    MemberRegister forRefresh;

    Controller link = new Controller();
    Connection myLink = link.getMyConn();


//    public void setContentPane(JPanel contentPane) {
//        this.contentPane = formPanel;
//    }

    public AccountDialogue() {
//        updateRegister();
        serialNumberField = new JTextField();

        setContentPane(formPanel);
        setModal(true);
        getRootPane().setDefaultButton(saveButton);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (action.equals("create")) {
                    insertData();
                } else {
                    updateDatabase();
//                    implement table fresh
                }
                onSave();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() on when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        formPanel.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 onCancel();
             }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
            JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onSave() {
        // change instance of onOK to onSave
        dispose();
    }

    private void onCancel() {
        // add your code here if neccesary
        dispose();
    }

    // Create a method to insert value into ambassadorsRegister in database
    public void insertData() {
        // Format today's date and add to ambassadorsRegister
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        String dateString = format.format(today);

        try {
            DatabaseMetaData dMD = myLink.getMetaData();
            ResultSet resultSet = dMD.getTables(null, null, "registration", null);

            String sQL = "INSERT INTO registration VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement statement = myLink.prepareStatement(sQL);

            int dataInserted = 0;
            if (resultSet.next()) {
                statement.setInt(1, 0);
                statement.setString(2, firstNameTF.getText());
                statement.setString(3, lastNameTF.getText());
                statement.setString(4, userNameTF.getText());
                statement.setString(5, createPasswordTF.getText());
                statement.setString(6, mobileTF.getText());
                statement.setString(7, emailTF.getText());
                statement.setString(8, dateString);
                dataInserted = statement.executeUpdate();
            }
            if (dataInserted > 0) {
                JOptionPane.showMessageDialog(null, "Account created successfully");
            } else {
                JOptionPane.showMessageDialog(null, "Error! Data insertion failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Update database
    public void updateDatabase() {
        String sQL = "UPDATE registration SET firstName = '" + firstNameTF.getText() + "',"
                + "lastName = '" + lastNameTF.getText() + "',"
                + "userName = '" + userNameTF.getText() + "',"
                + "mobile = '" + mobileTF.getText() + "',"
                + "email = '" + emailTF.getText() + "',"
                + "password = '" + createPasswordTF.getText() + "',"
                + "WHERE serialNumber = '" + Integer.parseInt(serialNumberField.getText()) + "'";
        try {
            PreparedStatement statement = myLink.prepareStatement(sQL);

            int update = statement.executeUpdate();

            if (update > 0) {
                JOptionPane.showMessageDialog(null, "Member updated successfully");
            } else {
                JOptionPane.showMessageDialog(null, "Update failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
