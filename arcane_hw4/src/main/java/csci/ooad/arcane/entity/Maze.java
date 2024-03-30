package csci.ooad.arcane.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Maze {

    ArrayList<Room> rooms = new ArrayList<>();

    ArrayList<Player> players = new ArrayList<>();
    ArrayList<Creature> creatures = new ArrayList<>();

    public ArrayList<Room> getRooms(){

        return this.rooms;
    }

    public ArrayList<Player> getPlayers(){

        return this.players;
    }

    public ArrayList<Creature> getCreatures(){

        return this.creatures;
    }
    public List<String> getRoomNames(){

        List<String> names = new ArrayList<>();
        for(Room room: this.rooms){
            names.add(room.name);
        }

        return names;
    }
    public List<String> getRoomNames(ArrayList<Room> rooms_to_iterate){

        List<String> names = new ArrayList<>();
        for(Room room: rooms_to_iterate){
            names.add(room.name);
        }

        return names;
    }

    public List<String> getNeighborOfRoom(String room_name){

        for(Room room: this.rooms){

            if(Objects.equals(room.name, room_name)){

                return getRoomNames(room.getNeighbors());
            }
        }

        return null;

    }

    public List<String> getContents(String room_name){
        List<String> objects = new ArrayList<>();
        for(Room room: this.rooms){

            if(Objects.equals(room.name,room_name)){

                for(int i=0;i<room.getPlayers().size();i++){

                    objects.add(room.getPlayers().get(i).name);
                }

                for(int i=0;i<room.getCreatures().size();i++){
                    objects.add(room.getCreatures().get(i).name);
                }

                objects.addAll(room.getFoodItems());

            }
        }

        return objects;
    }

    public static class Builder{

        PlayerFactory pf;
        CreatureFactory cf;
        FoodFactory ff;

        Boolean random; //true is random, false is sequential

        Maze maze;

        public Builder(){

            this.pf = new PlayerFactory();
            this.ff = new FoodFactory();
            this.cf = new CreatureFactory();
            this.maze = new Maze();
        }
        public Builder(PlayerFactory pf, CreatureFactory cf, FoodFactory ff){

            this.pf = pf;
            this.cf = cf;
            this.ff = ff;
            this.maze = new Maze();

        }

        public Builder buildNRoomsConnected(ArrayList<String> names){

            ArrayList<Room> rooms = new ArrayList<>();

            for(int i=0;i<names.size();i++){

                Room room = new Room(names.get(i));

                rooms.add(room);
            }

            for(int i=0;i<rooms.size();i++){

                for(int j=0;j<rooms.size();j++){


                    if(i!=j){

                        rooms.get(i).addNeighbor(rooms.get(j));

                    }


                }
            }

            this.maze.rooms = rooms;

            return this;


        }
        public Builder buildNRoomsConnected(int n){

            ArrayList<Room> rooms = new ArrayList<>();

            for(int i=0;i<n;i++){

                String name = "r" + i;

                Room room = new Room(name);

                rooms.add(room);

            }

            for(int i=0;i<rooms.size();i++){


                for(int j=0;j<rooms.size();j++){


                    if(i!=j){

                        rooms.get(i).addNeighbor(rooms.get(j));
                    }
                }
            }

            this.maze.rooms = rooms;

            return this;


        }

        public Builder buildNRoomsLinear(ArrayList<String> names){

            ArrayList<Room> rooms = new ArrayList<>();

            for(int i=0;i<names.size();i++){

                Room room = new Room(names.get(i));

                rooms.add(room);
            }


            for(int i=0;i<names.size()-1;i++){

                rooms.get(i).addNeighbor(rooms.get(i+1));
            }

            this.maze.rooms = rooms;

            return this;


        }



        public Builder buildNRoomsLinear(int n){

            ArrayList<Room> rooms = new ArrayList<>();

            for(int i=0;i<n;i++){

                String name = "r" + i;

                Room room = new Room(name);

                rooms.add(room);

            }

            for(int i=0;i<n-1;i++){

                rooms.get(i).addNeighbor(rooms.get(i+1));

            }

            this.maze.rooms = rooms;


            return this;
        }

        public Builder distributeSequential(){

            this.random = false;
            return this;
        }

        public Builder distributeRandom(){

            this.random = true;
            return this;
        }
        public Builder createAndAddCowards(int n){


            ArrayList<Room> rooms = maze.getRooms();
            Random rand = new Random();

            if(this.random){

                for(int i=0;i<n;i++){

                    Player coward = pf.buildCoward();

                    int num = rand.nextInt(rooms.size());

                    rooms.get(num).addPlayer(coward);
                    this.maze.players.add(coward);

                }




                return this;
            }

            for(int i=0;i<n;i++){

                int j =i;

                Player coward = pf.buildCoward();

                if(i == rooms.size()){
                    j =0;
                }

                rooms.get(j).addPlayer(coward);
                this.maze.players.add(coward);

            }



            return this;


        }

        public Builder createAndAddCowards(ArrayList<String> names){

            int n = names.size();
            ArrayList<Room> rooms = maze.getRooms();
            Random rand = new Random();

            if(this.random){

                for(int i=0;i<n;i++){

                    Player coward = pf.buildCoward(names.get(i));

                    int num = rand.nextInt(rooms.size());

                    rooms.get(num).addPlayer(coward);
                    this.maze.players.add(coward);

                }




                return this;
            }

            for(int i=0;i<n;i++){

                int j =i;

                Player coward = pf.buildCoward(names.get(i));

                if(i == rooms.size()){
                    j =0;
                }

                rooms.get(j).addPlayer(coward);
                this.maze.players.add(coward);

            }



            return this;


        }

        public Builder createAndAddGlutton(ArrayList<String> names){

            int n = names.size();
            ArrayList<Room> rooms = maze.getRooms();
            Random rand = new Random();

            if(this.random){

                for(int i=0;i<n;i++){

                    Player glutton = pf.buildGlutton(names.get(i));

                    int num = rand.nextInt(rooms.size());

                    rooms.get(num).addPlayer(glutton);
                    this.maze.players.add(glutton);

                }



                return this;
            }

            for(int i=0;i<n;i++){

                int j =i;

                Player glutton = pf.buildGlutton(names.get(i));

                if(i == rooms.size()){
                    j =0;
                }

                rooms.get(j).addPlayer(glutton);
                this.maze.players.add(glutton);

            }



            return this;


        }
        public Builder createAndAddGlutton(int n){


            ArrayList<Room> rooms = maze.getRooms();
            Random rand = new Random();

            if(this.random){

                for(int i=0;i<n;i++){

                    Player glutton = pf.buildGlutton();

                    int num = rand.nextInt(rooms.size());

                    rooms.get(num).addPlayer(glutton);
                    this.maze.players.add(glutton);

                }



                return this;
            }

            for(int i=0;i<n;i++){

                int j =i;

                Player glutton = pf.buildGlutton();

                if(i == rooms.size()){
                    j =0;
                }

                rooms.get(j).addPlayer(glutton);
                this.maze.players.add(glutton);

            }



            return this;


        }
        public Builder createAndAddKnights(ArrayList<String> names){

            int n = names.size();
            ArrayList<Room> rooms = maze.getRooms();
            Random rand = new Random();

            if(this.random){

                for(int i=0;i<n;i++){

                    Player knight = pf.buildKnight(names.get(i));

                    int num = rand.nextInt(rooms.size());

                    rooms.get(num).addPlayer(knight);
                    this.maze.players.add(knight);

                }



                return this;
            }

            for(int i=0;i<n;i++){

                int j =i;

                Player knight = pf.buildKnight(names.get(i));

                if(i == rooms.size()){
                    j =0;
                }

                rooms.get(j).addPlayer(knight);
                this.maze.players.add(knight);

            }



            return this;
        }

        public Builder createAndAddKnights(int n){


            ArrayList<Room> rooms = maze.getRooms();
            Random rand = new Random();

            if(this.random){

                for(int i=0;i<n;i++){

                    Player knight = pf.buildKnight();

                    int num = rand.nextInt(rooms.size());

                    rooms.get(num).addPlayer(knight);
                    this.maze.players.add(knight);

                }



                return this;
            }

            for(int i=0;i<n;i++){

                int j =i;

                Player knight = pf.buildKnight();

                if(i == rooms.size()){
                    j =0;
                }

                rooms.get(j).addPlayer(knight);
                this.maze.players.add(knight);

            }



            return this;
        }

        public Builder createAndAddAdventurers(ArrayList<String> names){

            int n = names.size();
            ArrayList<Room> rooms = maze.getRooms();
            Random rand = new Random();

            if(this.random){

                for(int i=0;i<n;i++){

                    Player adv = pf.buildAdventurer(names.get(i));

                    int num = rand.nextInt(rooms.size());

                    rooms.get(num).addPlayer(adv);
                    this.maze.players.add(adv);

                }



                return this;
            }

            for(int i=0;i<n;i++){

                int j =i;

                Player adv = pf.buildAdventurer(names.get(i));

                if(i == rooms.size()){
                    j =0;
                }

                rooms.get(j).addPlayer(adv);
                this.maze.players.add(adv);

            }



            return this;

        }

        public Builder createAndAddAdventurers(int n){


            ArrayList<Room> rooms = maze.getRooms();
            Random rand = new Random();

            if(this.random){

                for(int i=0;i<n;i++){

                    Player adv = pf.buildAdventurer();

                    int num = rand.nextInt(rooms.size());

                    rooms.get(num).addPlayer(adv);
                    this.maze.players.add(adv);

                }



                return this;
            }

            for(int i=0;i<n;i++){

                int j =i;

                Player adv = pf.buildAdventurer();

                if(i == rooms.size()){
                    j =0;
                }

                rooms.get(j).addPlayer(adv);
                this.maze.players.add(adv);

            }



            return this;

        }

        public Builder createAndAddDemons(ArrayList<String> names){

            int n = names.size();
            ArrayList<Room> rooms = maze.getRooms();
            Random rand = new Random();

            if(this.random){

                for(int i=0;i<n;i++){

                    Creature demon = cf.buildDemon(names.get(i));

                    int num = rand.nextInt(rooms.size());

                    rooms.get(num).addCreature(demon);
                    this.maze.creatures.add(demon);

                }



                return this;
            }

            for(int i=0;i<n;i++){

                int j = i;

                if (i==rooms.size()){
                    j = 0;
                }

                Creature demon = cf.buildDemon(names.get(i));
                rooms.get(j).addCreature(demon);
                this.maze.creatures.add(demon);
            }

            

            return this;


        }

        public Builder createAndAddDemons(int n){


            ArrayList<Room> rooms = maze.getRooms();
            Random rand = new Random();

            if(this.random){

                for(int i=0;i<n;i++){

                    Creature demon = cf.buildDemon();

                    int num = rand.nextInt(rooms.size());

                    rooms.get(num).addCreature(demon);
                    this.maze.creatures.add(demon);

                }



                return this;
            }

            for(int i=0;i<n;i++){

                int j = i;

                if (i==rooms.size()){
                    j = 0;
                }

                Creature demon = cf.buildDemon();
                rooms.get(j).addCreature(demon);
                this.maze.creatures.add(demon);
            }



            return this;


        }

        public Builder createAndAddCreatures(ArrayList<String> names){

            int n = names.size();
            ArrayList<Room> rooms = maze.getRooms();
            Random rand = new Random();

            if(this.random){

                for(int i=0;i<n;i++){

                    Creature creat = cf.buildCreature(names.get(i));

                    int num = rand.nextInt(rooms.size());

                    rooms.get(num).addCreature(creat);
                    this.maze.creatures.add(creat);

                }



                return this;
            }

            for(int i=0;i<n;i++){

                int j = i;

                if (i==rooms.size()){
                    j = 0;
                }

                Creature creat = cf.buildCreature(names.get(i));
                rooms.get(j).addCreature(creat);
                this.maze.creatures.add(creat);
            }



            return this;


        }

        public Builder createAndAddCreatures(int n){


            ArrayList<Room> rooms = maze.getRooms();
            Random rand = new Random();

            if(this.random){

                for(int i=0;i<n;i++){

                    Creature creat = cf.buildCreature();

                    int num = rand.nextInt(rooms.size());

                    rooms.get(num).addCreature(creat);
                    this.maze.creatures.add(creat);

                }



                return this;
            }

            for(int i=0;i<n;i++){

                int j = i;

                if (i==rooms.size()){
                    j = 0;
                }

                Creature creat = cf.buildCreature();
                rooms.get(j).addCreature(creat);
                this.maze.creatures.add(creat);
            }



            return this;


        }


        public Builder createAndAddFood(ArrayList<String> food){

            int n = food.size();
            ArrayList<Room> rooms = maze.getRooms();
            Random rand = new Random();

            if(this.random){

                for(int i=0;i<n;i++){

                    int num = rand.nextInt(rooms.size());
                    rooms.get(num).addFoodItem(food.get(i));
                }



                return this;
            }

            for(int i=0;i<n;i++){

                int j = i;

                if (i==rooms.size()){
                    j = 0;
                }

                rooms.get(j).addFoodItem(food.get(i));

            }



            return this;
        }

        public Builder maze2x2(){

            ArrayList<Room> room = new ArrayList<>();

            Room nw = new Room("NorthWest");
            Room ne = new Room("NorthEast");
            Room sw = new Room("SouthWest");
            Room se = new Room("SouthEast");

            Room[] nwn = {ne,sw}; //northwest neighbors
            Room[] nen = {nw,se}; //northeast neighbors
            Room[] swn = {nw,se}; //southwest neighbors
            Room[] sen = {sw,ne}; //southeast neighbors

            nw.addNeighbors(nwn);
            ne.addNeighbors(nen);
            se.addNeighbors(sen);
            sw.addNeighbors(swn);

            room.add(nw);
            room.add(ne);
            room.add(se);
            room.add(sw);

            this.maze.rooms = room;

            return this;
        }
        public Builder maze3x3(){
            Room nw = new Room("NorthWest");
            Room north = new Room("North");
            Room ne = new Room("NorthEast");

            Room west = new Room("West");
            Room center = new Room("Center");
            Room east = new Room("East");

            Room sw = new Room("SouthWest");
            Room south = new Room("South");
            Room se = new Room("SouthEast");


            Room[] nwn = {north,west};
            Room[] nn = {nw,ne,center};
            Room[] nen = {north,east};

            Room[] wn = {nw,sw,center};
            Room[] cn = {north,south,east,west};
            Room[] en = {ne,se,center};

            Room[] swn = {west,south};
            Room[] sn = {sw,center,se};
            Room[] sen = {south,east};

            nw.addNeighbors(nwn);
            north.addNeighbors(nn);
            ne.addNeighbors(nen);

            east.addNeighbors(en);
            center.addNeighbors(cn);
            west.addNeighbors(wn);

            sw.addNeighbors(swn);
            south.addNeighbors(sn);
            se.addNeighbors(sen);

            ArrayList<Room> rooms = new ArrayList<>();

            rooms.add(nw);
            rooms.add(north);
            rooms.add(ne);

            rooms.add(west);
            rooms.add(center);
            rooms.add(east);

            rooms.add(sw);
            rooms.add(south);
            rooms.add(se);

            this.maze.rooms = rooms;

            return this;


        }

        public Maze build(){

            return this.maze;
        }


    }

    public static Builder newBuilder(){

        return new Builder();
    }
}
