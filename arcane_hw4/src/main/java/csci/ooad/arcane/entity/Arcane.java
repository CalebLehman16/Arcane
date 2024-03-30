package csci.ooad.arcane.entity;


import csci.ooad.layout.IMaze;
import csci.ooad.layout.IMazeObserver;
import csci.ooad.layout.IMazeSubject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;


//no system.out
//take not of:
//cohesion
//encapsulation
//dependency injection
//polymorphism
//inheritance

//expansions
//multiple adventurers
//multiple creatures
//differences between both
//bigger maze
//food
//game play

public class Arcane implements IObservable, IMazeSubject {


    private static final Logger logger =
            LoggerFactory.getLogger(Arcane.class);

    ArrayList<Room> rooms;
    ArrayList<Player> players;
    ArrayList<Creature> creatures;
    int turn =0;

    Maze maze;

    EventBus eventBus;



    boolean observable = false;


    //dependency injection
    //lists for rooms, players, and creatures are injected when initializing arcane game
    public Arcane(ArrayList <Room> rooms, ArrayList<Player> players, ArrayList<Creature> creatures,int food_amount)  { // dependency injection for the rooms, players, and creatures


        this.rooms = rooms; //encapsulating the rooms, players, and creatures
        this.players = players;
        this.creatures = creatures;
        addFood(food_amount);

        if(rooms!=null && players!=null && creatures!=null){
            randomizeEntities(players,creatures,rooms.size());
        }


    }


    public Arcane(Maze maze){

        this.maze = maze;
        this.rooms = maze.getRooms();
        this.creatures = maze.getCreatures();
        this.players = maze.getPlayers();
        this.eventBus = EventBus.getInstance();


    }

    public void observe(){

        observable = true;
    }

    public void addFood(int amount){

        Random rand = new Random();
        for(int i=0;i<amount;i++){

            int room = rand.nextInt(rooms.size());

            rooms.get(room).addFood(1);
        }
    }

    public ArrayList<Room> getRooms(){
        return this.rooms;
    }

    private void randomizeEntities(ArrayList<Player>players,ArrayList<Creature>creatures,int room_size){

        Random rand = new Random();

        for(int i=0;i<players.size();i++){

            int spot = rand.nextInt(room_size);

            this.rooms.get(spot).addPlayer(players.get(i));


        }

        for(int i=0;i<creatures.size();i++){

            int spot = rand.nextInt(room_size);

            this.rooms.get(spot).addCreature(creatures.get(i));

        }


    }


    public ArrayList<Room> initialize_four_rooms(Room nw, Room ne, Room sw, Room se){


        rooms.add(nw);
        rooms.add(ne);
        rooms.add(sw);
        rooms.add(se);
        return rooms;
    }

    public void display_arcane(){

       logger.info("Turn: " + this.turn + "\n");

        for(int i=0;i<rooms.size();i++){

            Room room = rooms.get(i);

            logger.info(room.name + ": \n");

            for(int j=0;j<room.getPlayers().size();j++){

                Player player = room.getPlayers().get(j);

                logger.info("Adventurer " + player.name + " is here. Health: " + player.changeHealth(0) + "\n");
            }

            for(int j=0;j<room.getCreatures().size();j++){

                Creature creature = room.getCreatures().get(j);

                logger.info("Creature " + creature.name + " is here. Health: " + creature.changeHealth(0) + "\n");
            }

            for(int j=0;j<room.getFoodItems().size();j++){

                logger.info("Food: " + room.getFoodItems().get(j) + "\n");
            }
        }

        logger.info("\n");


    }

    public int fight(Player player, Creature creature,Room cur_room) throws IOException, InterruptedException {


        //roll two dice and have them fight


        Random rand = new Random();
        int roll1 = rand.nextInt(6) + 1;
        int roll2 = rand.nextInt(6) + 1;

        int val = roll1 - roll2;

        creature.changeHealth(-0.5);
        player.changeHealth(-0.5);

        if(val > 0){

            creature.changeHealth(-val);
        }
        else{

            player.changeHealth(val); //encapsulation, user cannot directly modify or see the players health
        }

        if(!creature.isAlive()){

            cur_room.removeCreatures(creature);
            logger.info("Creature " + creature.name + " is dead!\n");
            post(EventType.Death,"Creature " + creature.name + " is dead!\n");
            post(EventType.FightOutcome,"Adventurer " + player.name + " won the fight!");
            return 1; //player wins fight
        }

        if(!player.isAlive()){

            cur_room.removePlayers(player);
            logger.info("Adventurer " + player.name + " is dead!\n");
            post(EventType.Death,"Adventurer " + player.name + " is dead!\n");
            post(EventType.FightOutcome,"Creature " + creature.name + " won the fight!");
            return 0; //creature wins fight
        }

        return -1;

    }

    public boolean allPlayersAlive(){

        boolean alive = false;

        for (Player player : players) {

            if (player.isAlive()) {
                alive = true;
            }

        }
        return alive;
    }

    public boolean allCreaturesAlive(){

        boolean alive = false;

        for (Creature creature : creatures) {

            if (creature.isAlive()) {

                alive = true;
            }
        }

        return alive;
    }

    public int winner_eval() throws IOException, InterruptedException {

        if(allPlayersAlive() && !allCreaturesAlive()){


            //log that players win
            post(EventType.GameOver,"Adventurers Win!");
            return 1;
        }

        if(!allPlayersAlive() && allCreaturesAlive()){

            //log that creatures win
            post(EventType.GameOver,"Creatures Win!");
            return -1;
        }

        return 0;

    }

    public String turn(Room room, int i, ArrayList<Player> players, ArrayList<Creature>creatures) throws IOException, InterruptedException {

        if(!players.isEmpty()){

            if(!creatures.isEmpty()){

                if(!room.hasDemon()){

                    room.preFight(players);

                }

                if(!rooms.get(i).getPlayers().isEmpty()){

                    fight(rooms.get(i).getPlayers().get(0), rooms.get(i).getCreatures().get(0),rooms.get(i));
                }


                return "fight";

            }else{

                boolean eaten = false;
                while(room.addFood(0) > 0){

                    room.eat(players);
                    eaten = true;
                    post(EventType.AteSomething,"The room has eaten!");

                    return "eaten";
                }

                if(!eaten){

                    for(int index=0;index<room.getPlayers().size();index++){

                        room.movePlayer(room.getPlayers().get(index));

                    }

                    return "move";


                }
            }


        }


        return "none";

    }

    public int play() throws IOException, InterruptedException { //plays the game, also writes output to simulation.txt files


        post(EventType.GameStart,"The game has started!");
        //logger.info("Starting Play...");
        while(winner_eval()==0){

            this.turn++;
            display_arcane();

            for(int i=0;i<rooms.size();i++){

                Room room = rooms.get(i);

                ArrayList<Player> players = room.getPlayers();
                ArrayList<Creature> creatures = room.getCreatures();


                turn(room, i,players,creatures);
            }
            post(EventType.TurnEnded,"Turn " + this.turn + " has ended");

            if(observable){
                notifyObservers("Turn " + this.turn + " just ended");
            }

        }

        if(winner_eval()==1){

           logger.info("Adventurers win!\n");


        }
        else{

            logger.info("Creatures win!\n");
        }


        return winner_eval();


    }

    @Override
    public void attach(IObserver observer, EventType type) {
        EventBus.getInstance().attach(observer,type);
    }



    @Override
    public void post(EventType event, String description) throws IOException, InterruptedException {

        if(this.observable){
            EventBus.getInstance().postMessage(event,description);
        }


    }

    @Override
    public IMaze getMaze() {
        return new MazeAdapter(this.maze);
    }
}
