package csci.ooad.arcane.entity;

public class PlayerFactory {

    public Player buildAdventurer(String name){
        return new Player(name);
    }

    public Player buildAdventurer(){

        return new Player();
    }

    public Player buildKnight(){

        return new Knight();
    }

    public Player buildKnight(String name){

        return new Knight(name);
    }

    public Player buildCoward(){
        return new Coward();
    }

    public Player buildCoward(String name){

        return new Coward(name);
    }

    public Player buildGlutton(){

        return new Glutton();
    }

    public Player buildGlutton(String name){

        return new Glutton(name);
    }
}
