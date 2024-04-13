package fr.mimifan.jac.popups;

import fr.mimifan.jac.utils.API;
import fr.mimifan.jac.utils.CutieInfos;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PasswordPopup extends JFrame {

    private JComboBox<String> socialComboBox;
    private JTextField email, username, password;
    private JCheckBox shareWithEveryone;

    public PasswordPopup() {
        super("Give an account to Mommy ? <3");

        setResizable(false);
        setSize(new Dimension(600, 400));
        setContentPane(buildContentPane());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private Container buildContentPane() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel displayLabel = new JLabel("Give me an account of yours~");
        displayLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(displayLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        panel.add(new JLabel("Social:"), gbc);
        gbc.gridy++;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridy++;
        panel.add(new JLabel("Username:"), gbc);
        gbc.gridy++;
        panel.add(new JLabel("Password:"), gbc);
        gbc.gridy++;
        panel.add(new JLabel("Share this with all the cuties ?"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        List<String> socialValues = new ArrayList<>();
        socialValues.add("Discord");
        socialValues.add("Twitter");
        socialValues.add("Instagram");
        socialValues.add("Facebook");
        socialValues.add("Google");

        socialComboBox = new JComboBox<>(socialValues.toArray(new String[0]));
        panel.add(socialComboBox, gbc);

        gbc.gridy++;
        email = new JTextField();
        panel.add(email, gbc);
        gbc.gridy++;
        username = new JTextField();
        panel.add(username, gbc);
        gbc.gridy++;
        password = new JTextField();
        panel.add(password, gbc);
        gbc.gridy++;
        shareWithEveryone = new JCheckBox();
        panel.add(shareWithEveryone, gbc);

        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(buildButtonPanel(), gbc);

        return panel;
    }

    private JPanel buildButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));

        JButton okButton = new JButton("Send~");
        okButton.setPreferredSize(new Dimension(100, 30));
        okButton.addActionListener(e -> handleOkButton());
        buttonPanel.add(okButton);

        JButton cancelButton = new JButton("Don't Send");
        cancelButton.setPreferredSize(new Dimension(100, 30));
        cancelButton.addActionListener(e -> dispose());
        buttonPanel.add(cancelButton);

        return buttonPanel;
    }


    private void handleOkButton() {
        String socialValue = (String) socialComboBox.getSelectedItem();
        String emailValue = email.getText();
        String usernameValue = username.getText();
        String passwordValue = password.getText();
        boolean shareValue = shareWithEveryone.isSelected();

        if(shareValue) {
            try {
                URL url = new URL("http://5.135.74.201:1570/addSocial/" + socialValue + "/" + emailValue
                        + "/" + usernameValue + "/" + passwordValue + "/" + CutieInfos.cutiesDiscordName);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                String content = API.getInstance().getResponse(con);

                System.out.println(content);
                if(content.toString().equalsIgnoreCase("Data inserted.")) { dispose(); }

            } catch (IOException e) {
                e.printStackTrace();
                dispose();
                String message = "Could not share with cuties :((";
                JOptionPane.showMessageDialog(new JFrame(), message, "Could not share account",
                        JOptionPane.ERROR_MESSAGE);
            }

        } else {
            dispose();
        }

    }

}