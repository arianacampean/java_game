package domeniu;

public class Pozitii extends Entity {
    private int id_juc;
    private int pozitie;

    public Pozitii() {
    }

    public Pozitii(int id_juc, int pozitie) {
        this.id_juc = id_juc;
        this.pozitie = pozitie;
    }

    public int getId_juc() {
        return id_juc;
    }

    public void setId_juc(int id_juc) {
        this.id_juc = id_juc;
    }

    public int getPozitie() {
        return pozitie;
    }

    public void setPozitie(int pozitie) {
        this.pozitie = pozitie;
    }
}
