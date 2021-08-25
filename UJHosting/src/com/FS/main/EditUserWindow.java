package com.FS.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditUserWindow extends JPanel
{
    private Window window;
    private int user, owner;
    private PostgresManager psql;

    private JTextField regFirstName, regSecondName, regNickname, regEmail, regCountry, regCity, regOrg;
    private JPasswordField regPassword, regPasswordRepeat;
    private JComboBox regGender;
    private JButton regRegister, regBack;
    private JLabel regError;

    public EditUserWindow(Window window, int user, int owner)
    {
        this.window = window;
        this.user = user;
        this.owner = owner;
        psql = new PostgresManager(window);
        psql.Connect();

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel firstName = new JLabel("first name");
        JLabel secondName = new JLabel("second name");
        JLabel nickname = new JLabel("nickname");
        JLabel gender = new JLabel("gender");
        JLabel country = new JLabel("Country");
        JLabel city = new JLabel("City");
        JLabel email = new JLabel("e-mail");
        JLabel password = new JLabel("password");
        JLabel repeatPassword = new JLabel("repeat password");

        User us = psql.GetUserInfo(owner);

        regFirstName = new JTextField(us.firstName);
        regSecondName = new JTextField(us.secondName);
        regNickname = new JTextField(us.nickname);
        regGender = new JComboBox(new Object[]{"Unspecified", "Male", "Female", "Other"});
        regGender.setSelectedItem(us.gender);
        regCountry = new JTextField(us.country);
        regCity = new JTextField(us.city);
        regOrg = new JTextField(us.organisation);
        regEmail = new JTextField(us.email);
        regPassword = new JPasswordField(us.password);
        regPasswordRepeat = new JPasswordField("");

        JPanel registerFields = new JPanel();
        registerFields.setLayout(new GridLayout(9, 2, 5, 5));

        registerFields.add(firstName); registerFields.add(regFirstName);
        registerFields.add(secondName); registerFields.add(regSecondName);
        registerFields.add(gender); registerFields.add(regGender);
        registerFields.add(country); registerFields.add(regCountry);
        registerFields.add(city); registerFields.add(regCity);
        registerFields.add(new JLabel("organisation")); registerFields.add(regOrg);
        registerFields.add(email); registerFields.add(regEmail);
        registerFields.add(password); registerFields.add(regPassword);
        registerFields.add(repeatPassword); registerFields.add(regPasswordRepeat);

        regError = new JLabel("");
        regError.setForeground(Color.RED);

        this.add(new JLabel("edit " + us.nickname + " info"), gbc);
        gbc.gridy = 1;
        this.add(registerFields, gbc);
        gbc.gridy = 2;
        this.add(regError, gbc);
        gbc.gridy = 3;
        JButton backButton = new JButton("back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                window.OpenProfile(owner);
            }
        });
        this.add(backButton, gbc);
        gbc.gridy = 4;
        JButton applyButton = new JButton("apply changes");
        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Apply();
            }
        });
        this.add(applyButton, gbc);
    }

    private void ShowError(String text)
    {
        regError.setForeground(Color.red);
        regError.setText(text);
    }

    private void ShowSuccess(String text)
    {
        regError.setForeground(Color.green);
        regError.setText(text);
    }

    private Integer GetLocationId()
    {
        if(regCountry.getText() == "")
            return null;
        int rs = psql.GetLocation(regCountry.getText(), regCity.getText(), regOrg.getText());
        if(rs == -1)
            return null;
        return rs;
    }

    private void Apply()
    {
        if(!String.valueOf(regPassword.getPassword()).equals(String.valueOf(regPasswordRepeat.getPassword())))
        {
            ShowError("passwords doesn't match");
            return;
        }
        boolean result = psql.EditUser(owner, regFirstName.getText(), regSecondName.getText(),
                (String)regGender.getSelectedItem(), GetLocationId(), regPassword.getPassword(), regEmail.getText());
        if(!result)
            ShowError("some your register data is incorrect");
        else
            ShowSuccess("successfully registered!");
    }
}
