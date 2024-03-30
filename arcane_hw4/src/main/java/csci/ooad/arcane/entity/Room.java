package csci.ooad.arcane.entity;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Room{

    public String name;
    ArrayList<Room> neighbors;

    ArrayList<Player> players;

    ArrayList<Creature> creatures;

    int food;

    ArrayList<String> food_items;
    int neighbor_index = 0;

    public Room(String name1){

        name = name1;
        this.neighbors = new ArrayList<>();
        this.players =  new ArrayList<>();
        this.creatures = new ArrayList<>();
        this.food_items = new ArrayList<>();

    }

    public ArrayList<Room> addNeighbors(Room[] n1){

        neighbors.addAll(List.of(n1));

        return neighbors;

    }

    public ArrayList<Room> getNeighbors(){

        return this.neighbors;
    }

    public int getNeighborsSize(){

        return this.neighbors.size();
    }

    public void addFoodItem(String food){

        this.food_items.add(food);
        this.food++;
    }

    public ArrayList<String> getFoodItems(){

        return this.food_items;
    }

    public void addFoodItem(ArrayList<String> food){

        this.food_items.addAll(food);
    }

    public boolean hasKnight(){

        for(int i=0;i<players.size();i++){

            if(players.get(i).type=="knight"){
                return true;
            }
        }

        return false;
    }

    public int getKnight(){

        for(int i=0;i<players.size();i++){

            if(players.get(i).type=="knight"){
                return i;
            }
        }

        return -1;
    }

    public void preFight(ArrayList<Player> players){

        int max_player_index = 0;
        double max = 0;

        ArrayList<Player> knights = new ArrayList<>();

        if(!hasKnight()){

            for(int i=0;i<players.size();i++){

                if(players.get(i).changeHealth(0) > max){
                    max = players.get(i).changeHealth(0);
                    max_player_index = i;
                }

            }

            Player max_player = players.get(max_player_index);
            removePlayers(max_player);

            for(int i=0;i<players.size();i++){

                movePlayer(players.get(i));

            }


            if(max_player.type !="coward"){
                addPlayer(max_player);
                return;
            }

            max_player.changeHealth(-0.5); //max health player is coward, so he runs and loses more health

        }else{

            Player fighter = players.get(getKnight());
            removePlayers(fighter);

            for(int i=0;i<players.size();i++){

                movePlayer(players.get(i));
            }

            addPlayer(fighter);
        }


        
    }

    public void addNeighbor(Room room){

        this.neighbors.add(room);
    }

    public ArrayList<Player> getPlayers(){

        return players;
    }

    public ArrayList<Creature> getCreatures(){

        return creatures;
    }

    public void addCreature(Creature creature){

        creatures.add(creature);
    }

    public void addPlayer(Player player){

        players.add(player);
    }

    public void movePlayer(Player player){

        Random rand = new Random();
        int move = rand.nextInt(this.neighbors.size());

        neighbors.get(move).addPlayer(player);
        removePlayers(player);
    }

        /*public void moveCreature(Creature creature){

            Random rand = new Random();
            int move = rand.nextInt(this.neighbors.size());
            neighbors.get(move).addCreature(creature);
            removeCreatures(creature);

        }*/

    public void removePlayers(Player players) {

        this.players.remove(players);

    }

    public void removeCreatures(Creature creatures){

        this.creatures.remove(creatures);
    }

    public int addFood(int value){

        food+=value;
        return food;
    }

    public Player getUnhealthiesPlayer(double max_health,ArrayList<Player> players){

        double min = max_health;
        int min_index =0;

        for(int i=0;i<players.size();i++){

            if(players.get(i).changeHealth(0) < min){
                min = players.get(i).changeHealth(0);
                min_index = i;
            }
        }

        if (!players.isEmpty()){
            return players.get(min_index);
        }

        return null;
    }

    public boolean hasGlutton(){

        for(int i=0;i<players.size();i++){

            if(players.get(i).type =="glutton"){
                return true;
            }
        }

        return false;
    }

    public Player getGlutton(){

        for(int i=0;i<players.size();i++){

            if(players.get(i).type =="glutton"){
                return players.get(i);
            }
        }

        return null;
    }

    public void eat(ArrayList<Player> players){

        while(addFood(0) > 0){

            Player low;

            if(this.hasGlutton()){

                low = this.getGlutton();
            }
            else{
                low = getUnhealthiesPlayer(5,players);
            }

            low.changeHealth(1);
            addFood(-1);

            if(!food_items.isEmpty()){
                food_items.remove(0);
            }

        }


    }

    public boolean hasDemon(){

        for(int i=0;i<creatures.size();i++){

            if(creatures.get(i).isDemon()){

                return true;

            }
        }

        return false;
    }







}