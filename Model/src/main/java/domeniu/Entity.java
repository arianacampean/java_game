package domeniu;

import java.io.Serializable;

public class Entity implements Serializable {
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
