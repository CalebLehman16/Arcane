package csci.ooad.arcane.entity;

public class CreatureFactory {

    public Creature buildDemon(){

        return new Demon();

    }

    public Creature buildDemon(String name){

        return new Demon(name);
    }

    public Creature buildCreature(String name){

        return new Creature(name);

    }

    public Creature buildCreature(){

        return new Creature();
    }
}
