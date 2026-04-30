public class Admin extends User {

    public Admin() {
        super("Burhan", "burhan", "burunghantu123");
    }

    public Admin(String name, String username, String password) {
        super(name, username, password);
    }

    @Override
    public String getWelcomeMessage() {
        return "Login berhasil! Selamat datang, " + getName() + ".";
    }

    @Override
    public String toString() {
        return "Admin: " + getName() + " (" + getUsername() + ")";
    }
}