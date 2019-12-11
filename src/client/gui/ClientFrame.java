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

    private Client client = new Client(this);

    private SignInScreen signInScreen;
    private SignUpScreen signUpScreen;
    private ProfileScreen profileScreen;
    private HomeScreen homeScreen;
    private ChatScreen chatScreen;

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

        signInScreen = new SignInScreen(this, client);
        content.add(signInScreen, ScreenType.SIGNIN.name());

        signUpScreen = new SignUpScreen(this, client);
        content.add(signUpScreen, ScreenType.SIGNUP.name());

        content.add(new LoadingScreen(this, client), ScreenType.LOADING.name());
        content.add(new LoadingErrorScreen(this), ScreenType.LOADING_ERROR.name());

        homeScreen = new HomeScreen(this, client);
        content.add(homeScreen, ScreenType.HOME.name());

        profileScreen = new ProfileScreen(this);
        content.add(profileScreen, ScreenType.PROFILE.name());

        chatScreen = new ChatScreen(this, client);
        content.add(chatScreen, ScreenType.CHAT.name());

        setActiveScreen(ScreenType.LOADING);
    }

    public void setActiveScreen(ScreenType type) {
        CardLayout layout = (CardLayout) content.getLayout();
        layout.show(content, type.name());
    }

    public SignInScreen getSignInScreen() {
        return signInScreen;
    }

    public SignUpScreen getSignUpScreen() {
        return signUpScreen;
    }

    public ProfileScreen getProfileScreen() {
        return profileScreen;
    }

    public HomeScreen getHomeScreen() {
        return homeScreen;
    }

    public ChatScreen getChatScreen() {
        return chatScreen;
    }

    public static void main(String[] args) {
        ClientFrame client = new ClientFrame();
        client.establishConnection();
    }
}
