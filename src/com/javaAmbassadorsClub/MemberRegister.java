package com.javaAmbassadorsClub;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Locale;

public class MemberRegister {
    public JPanel registerPanel;
    private JPanel southPanel;
    private JScrollPane centerPanel;
    private JPanel northPanel;
    private JTextField searchNameTF;
    private JButton searchButton;
    private JLabel searchNameLabel;
    private JButton updateButton;
    private JButton deleteButton;
    public JTable registeredTable;

    DefaultTableModel model = new DefaultTableModel(); // Define a global table model

    AccountDialogue dialog = new AccountDialogue();

    public MemberRegister() {
        createRegisterTable();

        Dimension dimension = updateButton.getPreferredSize();

        deleteButton.setPreferredSize(dimension);

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Copy content to main method in the dialog definition here
                 fillUpdateDialog();

                dialog.setTitle("Update Account");
                dialog.action = "update";
                //dialog.setLocationRelativeTo(null);
                dialog.setBounds(200, 200, 310, 210);
                //dialog.pack();
                dialog.setVisible(true);
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteFromDatabase();
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String lastName = searchNameTF.getText();
                filter(lastName);
            }
        });
    }

    public void createRegisterTable() {
        Object[] columnName = new Object[7];

        columnName[0] = "Serial number";
        columnName[1] = "First name";
        columnName[2] = "Last name";
        columnName[3] = "User name";
        columnName[4] = "Mobile";
        columnName[5] = "Email";
        columnName[6] = "Date registered";

        model.setColumnIdentifiers(columnName);

        Object[] rowData = new Object[7];

        Controller contact = new Controller();
        ArrayList<Members> list = contact.getMembers();

        for (int i = 0; i < list.size(); i++) {
            rowData[0] = list.get(i).getSerialNumber();
            rowData[1] = list.get(i).getFirstName();
            rowData[2] = list.get(i).getLastName();
            rowData[3] = list.get(i).getUserName();
            rowData[4] = list.get(i).getMobile();
            rowData[5] = list.get(i).getEmail();
            rowData[6] = list.get(i).getDate_created();

            model.addRow(rowData);
        }
        registeredTable.setModel(model);
    }

    // Create a method to filter the row containing las name
    private void filter(String lastName) {
        TableRowSorter<DefaultTableModel> searchItem = new TableRowSorter<>(model);
        registeredTable.setRowSorter(searchItem);
        searchItem.setRowFilter(RowFilter.regexFilter(lastName));
    }

    //Create method to update the database
    public void fillUpdateDialog() {
        int i = registeredTable.getSelectedRow();

        if (i > 0) {
            dialog.serialNumberField.setText(registeredTable.getValueAt(i, 0).toString());
            dialog.firstNameTF.setText(registeredTable.getValueAt(i, 1).toString());
            dialog.lastNameTF.setText(registeredTable.getValueAt(i, 2).toString());
            dialog.userNameTF.setText(registeredTable.getValueAt(i, 3).toString());
            dialog.mobileTF.setText(registeredTable.getValueAt(i, 4).toString());
            dialog.emailTF.setText(registeredTable.getValueAt(i, 5).toString());

            dialog.createPasswordTF.setText("**********");
        }
    }

    // Create a method to delete records from database
    public void deleteFromDatabase() {
        int j = registeredTable.getSelectedRow();
        if (j >= 0) {
            int serialNumber = Integer.parseInt(registeredTable.getValueAt(j, 0).toString());
            try {
                Connection link = new Controller().getMyConn();
                String sQL = "DELETE FROM registration WHERE serialNumber = '" + serialNumber + "'";

                PreparedStatement statement = link.prepareStatement(sQL);
                int update = statement.executeUpdate();

                if (update > 0) {
                    JOptionPane.showMessageDialog(null, "Member deleted successfully");
                } else {
                    JOptionPane.showMessageDialog(null, "Update failed");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}


