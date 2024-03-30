package csci.ooad.arcane.entity;

public class Player {

        public double health;
        public String name;
        public String type;


        public Player(String name, int health){

            this.health = health;
            this.name = name;
            this.type = "adventurer";
        }

        public Player(String name){ //polymorphism, function overloading for if you want to use default value, or set a new one for starting health

            this.name = name;
            this.health = 5;
            this.type = "adventurer";
        }

    public Player() {

    }

    public boolean isAlive(){

            return (health > 0);
        }

        public void eat(){

            this.health +=1;
        }

        public double changeHealth(double health){
            this.health+=health;
            return this.health;
        }
}

