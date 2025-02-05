package services;

public class UserService  {
    private String userId;
    private String name;
    private String password;

    public UserService (String userId, String name, String password) {
        this.userId = userId;
        this.name = name;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public boolean checkPassword(String passwordToCheck) {
        return this.password.equals(passwordToCheck);
    }
}
