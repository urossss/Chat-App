package server;

public class UserInformation {

    private int id;
    private String username, password, firstName, lastName;
    private String rootFolderPath, profilePicturePath;

    public UserInformation(
            int _id,
            String _username,
            String _password,
            String _firstName,
            String _lastName) {
        this(_username, _password, _firstName, _lastName);
        id = _id;
    }
    
    public UserInformation(
            String _username,
            String _password,
            String _firstName,
            String _lastName) {
        username = _username;
        password = _password;
        firstName = _firstName;
        lastName = _lastName;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setRootFolderPath(String rootFolderPath) {
        this.rootFolderPath = rootFolderPath;
    }
    
    public String getRootFolderPath() {
        return rootFolderPath;
    }

    public String getProfilePicturePath() {
        return profilePicturePath;
    }

    public void setProfilePicturePath(String profilePicturePath) {
        this.profilePicturePath = profilePicturePath;
    }
    
    public boolean hasProfilePicture() {
        return profilePicturePath != null;
    }
    
    public String serialize() {
        return id + "#" + username + "#" + password + "#" + firstName + "#" + lastName;
    }
}
