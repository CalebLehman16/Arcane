package csci.ooad.arcane.entity;

public class Knight extends Player {
    public Knight() {

        name = "knight";
        health = 8;
        this.type = "knight";

    }

    public Knight(String name){

        this.name = name;
        health = 8;
        this.type = "knight";
    }
}
