public abstract class User {
    private String name;
    private String username;
    private String password;

    public User(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public abstract String getWelcomeMessage();

    public boolean authenticate(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public String getName() { return this.name; }

    public String getUsername() { return this.username; }

    @Override
    public String toString() { return this.name; }
}