package csci.ooad.arcane.entity;

public class Coward extends Player{

    public Coward(){

        name = "Coward";
        health = 5;
        this.type = "coward";
    }

    public Coward(String name){

        this.name = name;
        health = 5;
        this.type = "coward";
    }


}
