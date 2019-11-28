package client.gui;

import client.Client;
import client.gui.screens.*;
import java.awt.CardLayout;
import java.awt.Toolkit;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class ClientFrame extends JFrame {

    private JPanel content;
    private JPanel logInScreen, signUpScreen;

    private Client client = new Client();
    private HomeScreen homeScreen;

    public ClientFrame() {
        super("Chat");

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            SwingUtilities.updateComponentTreeUI(this);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        setSize(400, 550);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("res/logo.png")));

        addComponents();
        addListeners();

        setVisible(true);
        pack();
    }

    public void establishConnection() {
        try {
            client.establishConnection();
            setActiveScreen(ScreenType.SIGNIN);
        } catch (IOException e) {
            e.printStackTrace();
            setActiveScreen(ScreenType.LOADING_ERROR);
        }
    }

    private void addComponents() {
        content = new JPanel();
        content.setLayout(new CardLayout());
        add(content);

        logInScreen = new SignInScreen(this, client);
        content.add(logInScreen, ScreenType.SIGNIN.name());

        signUpScreen = new SignUpScreen(this, client);
        content.add(signUpScreen, ScreenType.SIGNUP.name());

        content.add(new LoadingScreen(this, client), ScreenType.LOADING.name());
        content.add(new LoadingErrorScreen(this), ScreenType.LOADING_ERROR.name());

        homeScreen = new HomeScreen(this, client);
        content.add(homeScreen, ScreenType.HOME.name());

        setActiveScreen(ScreenType.LOADING);
    }

    private void addListeners() {

    }

    public void setActiveScreen(ScreenType type) {
        if (type == ScreenType.HOME) {
            homeScreen.setIcon();
        }

        CardLayout layout = (CardLayout) content.getLayout();
        layout.show(content, type.name());
    }

    public static void main(String[] args) {
        ClientFrame client = new ClientFrame();
        client.establishConnection();
    }
}
