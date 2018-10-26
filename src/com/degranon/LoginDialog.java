package com.degranon;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class LoginDialog extends JDialog {

    private JTextField tfHost;
    private JTextField tfPort;
    private JPasswordField pfPassword;
    private JLabel lbHost;
    private JLabel lbPassword;
    private JLabel lbPort;
    private JButton btnLogin;
    private JButton btnCancel;
    private boolean succeeded;

    public LoginDialog(Frame parent) {
        super(parent, "Login", true);
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();

        cs.fill = GridBagConstraints.HORIZONTAL;

        lbHost = new JLabel("IP: ");
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(lbHost, cs);

        tfHost = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 2;
        panel.add(tfHost, cs);

        lbPassword = new JLabel("Password: ");
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(lbPassword, cs);

        pfPassword = new JPasswordField(20);
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 2;
        panel.add(pfPassword, cs);

        lbPort = new JLabel("Port: ");
        cs.gridx = 0;
        cs.gridy = 2;
        cs.gridwidth = 1;
        panel.add(lbPort, cs);

        tfPort = new JTextField("21026", 20);
        cs.gridx = 1;
        cs.gridy = 2;
        cs.gridwidth = 2;
        panel.add(tfPort, cs);

        panel.setBorder(new LineBorder(Color.GRAY));

        btnLogin = new JButton("Login");

        btnLogin.addActionListener(e -> {
            try {
                RconClient.authenticate(getHost(), getPort(), getPassword());
                succeeded = true;
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(LoginDialog.this,
                        ex.getMessage(),
                        "Warning",
                        JOptionPane.ERROR_MESSAGE);
                succeeded = false;
            }
        });
        JPanel bp = new JPanel();
        bp.add(btnLogin);

        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.PAGE_END);

        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    public String getHost() {
        return tfHost.getText().trim();
    }

    public String getPassword() {
        return new String(pfPassword.getPassword());
    }

    public Integer getPort() {
        return new Integer(tfPort.getText());
    }

    public boolean isSucceeded() {
        return succeeded;
    }
}