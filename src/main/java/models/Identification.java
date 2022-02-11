package models;

public class Identification {
    Creator creator;

    public Identification() {

    }

    public Identification(Creator creator) {
        this.creator = creator;
    }

    public Creator getCreator() {
        return creator;
    }

    public void setCreator(Creator creator) {
        this.creator = creator;
    }
}
