package domeniu;

public class Punctaje extends Entity{
    private int id_jucator_trimite;
    private int id_jucator_primeste;
    private int pozitie;
    private int punctaj;
    private int runda;

    public Punctaje() {
    }

    public Punctaje(int id_jucator_trimite, int id_jucator_primeste, int pozitie, int punctaj, int runda) {
        this.id_jucator_trimite = id_jucator_trimite;
        this.id_jucator_primeste = id_jucator_primeste;
        this.pozitie = pozitie;
        this.punctaj = punctaj;
        this.runda = runda;
    }

    public int getId_jucator_trimite() {
        return id_jucator_trimite;
    }

    public void setId_jucator_trimite(int id_jucator_trimite) {
        this.id_jucator_trimite = id_jucator_trimite;
    }

    public int getId_jucator_primeste() {
        return id_jucator_primeste;
    }

    public void setId_jucator_primeste(int id_jucator_primeste) {
        this.id_jucator_primeste = id_jucator_primeste;
    }

    public int getPozitie() {
        return pozitie;
    }

    public void setPozitie(int pozitie) {
        this.pozitie = pozitie;
    }

    public int getPunctaj() {
        return punctaj;
    }

    public void setPunctaj(int punctaj) {
        this.punctaj = punctaj;
    }

    public int getRunda() {
        return runda;
    }

    public void setRunda(int runda) {
        this.runda = runda;
    }
}
