package client.gui.screens;

import client.ChatInformation;
import client.Client;
import client.gui.ClientFrame;
import client.gui.components.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.ScrollPaneConstants;

/**
 *
 * @author uross
 */
public class HomeScreen extends javax.swing.JPanel {

    /**
     * Creates new form HomeScreen
     *
     * @param _clientFrame
     * @param _client
     */
    public HomeScreen(ClientFrame _clientFrame, Client _client) {
        initComponents();

        clientFrame = _clientFrame;
        client = _client;

        chatListPanel.setLayout(new BoxLayout(chatListPanel, BoxLayout.Y_AXIS));

        chatListScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        chatListScrollPane.getVerticalScrollBar().setUnitIncrement(10);

        revalidate();
        repaint();
    }

    public void setIcon() {
        ProfilePicturePanel ppp = (ProfilePicturePanel) profilePicturePanel;
        ppp.setImage(client.getProfilePicture());
        ppp.repaint();
    }

    public void addChatInfo(ChatInformation chatInfo) {
        ChatInformationPanel chatInfoPanel = new ChatInformationPanel(chatInfo, clientFrame);
        chatListPanel.add(chatInfoPanel, 0);
        chatInfoPanels.put(chatInfo.getChatId(), chatInfoPanel);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        profilePicturePanel = new ProfilePicturePanel(1);
        jLabel1 = new javax.swing.JLabel();
        chatListScrollPane = new javax.swing.JScrollPane();
        chatListPanel = new javax.swing.JPanel();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(400, 550));
        setMinimumSize(new java.awt.Dimension(400, 550));
        setPreferredSize(new java.awt.Dimension(400, 550));
        setRequestFocusEnabled(false);

        profilePicturePanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        profilePicturePanel.setMaximumSize(new java.awt.Dimension(50, 50));
        profilePicturePanel.setMinimumSize(new java.awt.Dimension(50, 50));
        profilePicturePanel.setPreferredSize(new java.awt.Dimension(50, 50));
        profilePicturePanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                profilePicturePanelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout profilePicturePanelLayout = new javax.swing.GroupLayout(profilePicturePanel);
        profilePicturePanel.setLayout(profilePicturePanelLayout);
        profilePicturePanelLayout.setHorizontalGroup(
            profilePicturePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        profilePicturePanelLayout.setVerticalGroup(
            profilePicturePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        jLabel1.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        jLabel1.setText("Chats");

        chatListScrollPane.setHorizontalScrollBar(null);

        chatListPanel.setBackground(new java.awt.Color(255, 255, 255));
        chatListPanel.setMaximumSize(new java.awt.Dimension(398, 483));
        chatListPanel.setMinimumSize(new java.awt.Dimension(398, 483));

        javax.swing.GroupLayout chatListPanelLayout = new javax.swing.GroupLayout(chatListPanel);
        chatListPanel.setLayout(chatListPanelLayout);
        chatListPanelLayout.setHorizontalGroup(
            chatListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 398, Short.MAX_VALUE)
        );
        chatListPanelLayout.setVerticalGroup(
            chatListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 484, Short.MAX_VALUE)
        );

        chatListScrollPane.setViewportView(chatListPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(chatListScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(5, 5, 5))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(profilePicturePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(219, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(profilePicturePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addComponent(chatListScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 485, Short.MAX_VALUE)
                .addGap(5, 5, 5))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void profilePicturePanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_profilePicturePanelMouseClicked
        clientFrame.getProfileScreen().setInfo(
                client.getUser().getFirstName() + " " + client.getUser().getLastName(),
                client.getUser().getProfilePicture(),
                ScreenType.HOME);
        clientFrame.setActiveScreen(ScreenType.PROFILE);
    }//GEN-LAST:event_profilePicturePanelMouseClicked

    private ClientFrame clientFrame;
    private Client client;

    private Map<Integer, ChatInformationPanel> chatInfoPanels = new HashMap<>();

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel chatListPanel;
    private javax.swing.JScrollPane chatListScrollPane;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel profilePicturePanel;
    // End of variables declaration//GEN-END:variables
}
