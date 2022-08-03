package domeniu;

public class Jucator extends Entity{
    private String username;
    private String parola;

    public Jucator() {
    }

    public Jucator(String username, String parola) {
        this.username = username;
        this.parola = parola;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }
}
