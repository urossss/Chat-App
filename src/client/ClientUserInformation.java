package client;

import javax.swing.ImageIcon;

public class ClientUserInformation {

    private int id;
    private String firstName, lastName;
    private ImageIcon profilePicture;

    public ClientUserInformation(int _id, String _firstName, String _lastName) {
        id = _id;
        firstName = _firstName;
        lastName = _lastName;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public ImageIcon getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(ImageIcon profilePicture) {
        this.profilePicture = profilePicture;
    }
    
}
