package repository.interfete;

import domeniu.Jucator;

public interface RepoJucator extends Repository<Jucator> {
    Jucator find_one(String username,String parola);
}
