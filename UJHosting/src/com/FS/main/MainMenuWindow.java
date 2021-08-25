package com.FS.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainMenuWindow extends JPanel
{
    private JPanel signUp, login, lgButtons, registerFields;
    private JLabel loginL, passwordL, titleL, registerTitle, logError;
    private JTextField lg_user;
    private JPasswordField lg_pass;
    private JButton back, logBut, showPassword, register, forgotPassword;
    private Window wind;

    private JTextField regFirstName, regSecondName, regNickname, regEmail, regCountry, regCity, regOrg;
    private JPasswordField regPassword, regPasswordRepeat;
    private JComboBox regGender;
    private JButton regRegister, regBack;
    private JLabel regError;

    private JTextField forEmail;
    private JLabel forTitle, forEmailTitle, forPassTitle, forPassword, forError;
    private JButton forSend, forBack;
    private JPanel forgotPasswordPanel;

    private PostgresManager postgres;

    private Component currentWindow;

    public MainMenuWindow(Window window)
    {
        wind = window;

        {
            signUp = new JPanel();
            signUp.setSize(400, 500);

            registerTitle = new JLabel("Register");
            registerFields = new JPanel();
            registerFields.setLayout(new GridLayout(10, 2, 10, 10));
            registerFields.setSize(350, 400);
            JLabel firstName = new JLabel("first name");
            JLabel secondName = new JLabel("second name");
            JLabel nickname = new JLabel("nickname");
            JLabel gender = new JLabel("gender");
            JLabel country = new JLabel("Country");
            JLabel city = new JLabel("City");
            JLabel email = new JLabel("e-mail");
            JLabel password = new JLabel("password");
            JLabel repeatPassword = new JLabel("repeat password");

            regFirstName = new JTextField("");
            regSecondName = new JTextField("");
            regNickname = new JTextField("");
            regGender = new JComboBox(new Object[]{"Unspecified", "Male", "Female", "Other"});
            regCountry = new JTextField("");
            regCity = new JTextField("");
            regOrg = new JTextField("");
            regEmail = new JTextField("");
            regPassword = new JPasswordField("");
            regPasswordRepeat = new JPasswordField("");

            registerFields.add(firstName); registerFields.add(regFirstName);
            registerFields.add(secondName); registerFields.add(regSecondName);
            registerFields.add(nickname); registerFields.add(regNickname);
            registerFields.add(gender); registerFields.add(regGender);
            registerFields.add(country); registerFields.add(regCountry);
            registerFields.add(city); registerFields.add(regCity);
            registerFields.add(new JLabel("organisation")); registerFields.add(regOrg);
            registerFields.add(email); registerFields.add(regEmail);
            registerFields.add(password); registerFields.add(regPassword);
            registerFields.add(repeatPassword); registerFields.add(regPasswordRepeat);

            regError = new JLabel("");
            regError.setForeground(Color.RED);

            regRegister = new JButton("register");
            regRegister.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    Register();
                }
            });
            regBack = new JButton("back to login");
            regBack.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    LoginWindow();
                }
            });

            signUp.add(registerTitle);
            signUp.add(registerFields);
            signUp.add(regRegister);
            signUp.add(regBack);
            signUp.add(regError);

            signUp.setLayout(new BoxLayout(signUp, BoxLayout.Y_AXIS));
        }

        {
            forgotPasswordPanel = new JPanel();
            forgotPasswordPanel.setLayout(new GridLayout(8, 1, 10, 10));
            forgotPasswordPanel.setSize(400, 500);

            forTitle = new JLabel("forgot password");
            forEmailTitle = new JLabel("email or nickname");
            forEmail = new JTextField();
            forError = new JLabel("");
            forError.setForeground(Color.red);
            forSend = new JButton("send");
            forPassTitle = new JLabel("");
            forPassword = new JLabel("");
            forBack = new JButton("back to login");

            forBack.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    LoginWindow();
                }
            });

            forSend.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    GetPassword();
                }
            });

            forgotPasswordPanel.add(forTitle);
            forgotPasswordPanel.add(forEmailTitle);
            forgotPasswordPanel.add(forEmail);
            forgotPasswordPanel.add(forError);
            forgotPasswordPanel.add(forSend);
            forgotPasswordPanel.add(forPassTitle);
            forgotPasswordPanel.add(forPassword);
            forgotPasswordPanel.add(forBack);
        }

        login = new JPanel();
        login.setLayout(new GridLayout(10, 1, 10, 20));
        login.setSize(400, 500);
        lgButtons = new JPanel();
        lgButtons.setLayout(new GridLayout(1, 2, 20, 20));

        titleL = new JLabel("Login or Signup");
        loginL = new JLabel("Enter email or nickname");
        lg_user = new JTextField("", 1);
        passwordL = new JLabel("Enter password");
        lg_pass = new JPasswordField("", 1);
        lg_pass.setEchoChar('*');

        back = new JButton("back");
        logBut = new JButton("login");
        showPassword = new JButton("show password");
        register = new JButton("register");
        forgotPassword = new JButton("forgot password?");
        logError = new JLabel("");
        logError.setForeground(Color.RED);

        logBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Login();
            }
        });

        lgButtons.add(logBut);
        lgButtons.add(showPassword);

        showPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                TogglePassword();
            }
        });

        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                RegisterWindow();
            }
        });

        forgotPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ForgetPasswordWindow();
            }
        });

        JButton postgresButton = new JButton("connection settings");
        postgresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                window.PostgresSetting();
            }
        });

        login.add(titleL);
        login.add(loginL);
        login.add(lg_user);
        login.add(passwordL);
        login.add(lg_pass);
        login.add(lgButtons);
        login.add(register);
        login.add(forgotPassword);
        login.add(logError);
        login.add(postgresButton);

        this.add(login);
        currentWindow = login;
        this.setSize(410, 510);

        postgres = new PostgresManager(window);
        postgres.Connect();
    }

    boolean passShown = false;

    private void TogglePassword()
    {
        passShown = !passShown;
        if(passShown)
        {
            lg_pass.setEchoChar((char)0);
        }
        else
        {
            lg_pass.setEchoChar('*');
        }
    }

    boolean haveBut = true;

    private void RemoveButton()
    {
        haveBut = !haveBut;
        if(haveBut)
        {
            login.add(forgotPassword);
        }
        else
        {
            login.remove(forgotPassword);
        }
      //  login.invalidate();
        wind.frame.repaint();
        wind.Pack();

    }

    private void ShowErrorReg(String message)
    {
        regError.setForeground(Color.red);
        regError.setText(message);
        wind.frame.repaint();
        wind.Pack();
    }

    private void ShowMessageReg(String message)
    {
        regError.setForeground(Color.green);
        regError.setText(message);
        wind.frame.repaint();
        wind.Pack();
    }

    private void ShowErrorFor(String message)
    {
        forError.setText(message);
        forPassword.setText("");
        forPassTitle.setText("");
        wind.frame.repaint();
        wind.Pack();
    }

    private void ShowErrorLog(String message)
    {
        logError.setText(message);
        wind.frame.repaint();
        wind.Pack();
    }

    private void ShowPasswordFor(String password)
    {
        if(password.equals(""))
        {
            forPassword.setText("");
            forPassTitle.setText("");
        }
        else {
            forPassword.setText(password);
            forPassTitle.setText("your password is:");
        }
        forError.setText("");
        wind.frame.repaint();
        wind.Pack();
    }

    private void GetPassword()
    {
        System.out.println(forEmail.getText());
        if(!postgres.CheckUserLogin(forEmail.getText()))
            ShowErrorFor("No such user");
        else
            ShowPasswordFor(postgres.GetPassword(forEmail.getText()));
    }

    private void LoginWindow()
    {
        this.remove(currentWindow);
        currentWindow = login;
        this.add(currentWindow);
        wind.frame.repaint();
        wind.Pack();
    }

    private void Login()
    {
        int id = postgres.ConnectAsUser(lg_user.getText(), lg_pass.getPassword());
        if(id == -1)
        {
            ShowErrorLog("email, nickname or password are incorrect");
        }
        else
        {
            System.out.println("Connected as " + id);
            wind.HomeWindow(id);
        }
    }

    private Integer GetLocationId()
    {
        if(regCountry.getText() == "")
            return null;
        int rs = postgres.GetLocation(regCountry.getText(), regCity.getText(), regOrg.getText());
        if(rs == -1)
            return null;
        return rs;
    }

    private void Register()
    {
        if(!String.valueOf(regPassword.getPassword()).equals(String.valueOf(regPasswordRepeat.getPassword())))
        {
            ShowErrorReg("passwords doesn't match");
            return;
        }
        boolean result = postgres.RegisterUser(regFirstName.getText(), regSecondName.getText(), regNickname.getText(),
                (String)regGender.getSelectedItem(), GetLocationId(), regPassword.getPassword(), regEmail.getText());
        if(!result)
            ShowErrorReg("some your register data is incorrect");
        else
            ShowMessageReg("successfully registered!");
    }

    private void RegisterWindow()
    {
        this.remove(currentWindow);
        currentWindow = signUp;
        this.add(currentWindow);
        wind.frame.repaint();
        wind.Pack();
    }

    private void ForgetPasswordWindow()
    {
        this.remove(currentWindow);
        currentWindow = forgotPasswordPanel;
        this.add(currentWindow);
        wind.frame.repaint();
        wind.Pack();
    }
}
