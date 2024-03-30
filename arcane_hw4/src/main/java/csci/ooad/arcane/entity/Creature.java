package csci.ooad.arcane.entity;

public class Creature {

     double health = 3;
    public String name;

    public String type;



    public  Creature(String name,double health){

        this.name = name;
        this.health = health;
        this.type = "creature";

    }

    public Creature(String name){

        this.name = name;
        this.health = 5;
        this.type = "creature";
    }

    public Creature() {

    }

    public boolean isAlive(){

        return(health > 0);
    }

    public double changeHealth(double health){

        this.health +=health;
        return this.health;
    }

    public boolean isDemon(){

        return this.name == "Demon";
    }

}
