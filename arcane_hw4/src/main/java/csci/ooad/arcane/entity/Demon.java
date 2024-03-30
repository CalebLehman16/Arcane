package csci.ooad.arcane.entity;

public class Demon extends Creature{

    public Demon(){

        name = "Demon";
        health = 15;
        this.type = "demon";
    }

    public Demon(String name){

        this.name = name;
        health = 15;
        this.type = "demon";
    }

}
