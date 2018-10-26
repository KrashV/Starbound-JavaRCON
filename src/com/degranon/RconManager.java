package com.degranon;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static javax.swing.GroupLayout.Alignment.*;

public class RconManager extends JFrame {

    private static JTextArea display;

    public RconManager() {
        final JButton btnLogin = new JButton("Click to login");
        final JLabel lbCommand = new JLabel("Enter command:");
        final JTextField tfCommand = new JTextField(30);
        tfCommand.setEnabled (false);
        JPanel middlePanel = new JPanel ();
        middlePanel.setBorder ( new TitledBorder( new EtchedBorder(), "Log" ) );

        display = new JTextArea (30, 60);
        display.setEditable ( false ); // set textArea non-editable
        JScrollPane scroll = new JScrollPane ( display );
        scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
        middlePanel.add ( scroll );


        btnLogin.addActionListener(
                e -> {
                    LoginDialog loginDlg = new LoginDialog(this);
                    loginDlg.setVisible(true);
                    if(loginDlg.isSucceeded()){
                        LogMessage("Connected to " + loginDlg.getHost());
                        tfCommand.setEnabled(true);
                    }
                });
        tfCommand.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = tfCommand.getText();
                LogMessage(RconClient.send(command));
                tfCommand.setText("");
            }
        });
        JPanel pane = new JPanel();
        GroupLayout layout = new GroupLayout(pane);
        pane.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(LEADING)
                        .addComponent(btnLogin)
                        .addComponent(lbCommand))
                .addComponent(tfCommand)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(btnLogin)
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(lbCommand)
                        .addComponent(tfCommand))
        );

        setTitle("Rcon client");
        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        add(pane);
        add(middlePanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo ( null );

        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("icon.png")));
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(
                            "javax.swing.plaf.metal.MetalLookAndFeel");
                    //  "com.sun.java.swing.plaf.motif.MotifLookAndFeel");
                    //UIManager.getCrossPlatformLookAndFeelClassName());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                new RconManager().setVisible(true);
            }
        });
    }

    private static void LogMessage(String message) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("[hh:mm:ss] ");
        Calendar calendar = Calendar.getInstance();

        display.append(dateFormat.format(calendar.getTime()) + message + "\n");
    }
}
