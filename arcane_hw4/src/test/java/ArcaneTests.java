
import csci.ooad.arcane.entity.*;
import csci.ooad.layout.IMazeObserver;
import csci.ooad.layout.MazeObserver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ArcaneTests {

    private static final Logger logger =
            LoggerFactory.getLogger(Arcane.class);

    @Test
    public void testAddNeighbors(){

        Room test = new Room("test room");

        Room add1 = new Room("first add");
        Room add2 = new Room("second add");

        Room[] add = {add1,add2};

        ArrayList<Room> neighbors = test.addNeighbors(add);


        Assertions.assertEquals(add1,neighbors.get(0));
        Assertions.assertEquals(add2,neighbors.get(1));

    }

    @Test
    public void testFourRooms() throws FileNotFoundException {

        ArrayList<Player> players = new ArrayList<>();
        ArrayList<Creature> creatures = new ArrayList<>();
        ArrayList<Room> rooms = new ArrayList<>();

        Arcane test = new Arcane(rooms,players,creatures,0);

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


        ArrayList<Room> rooms1 = test.initialize_four_rooms(nw,ne,sw,se);


        Assertions.assertEquals(4,rooms1.size());

        Assertions.assertEquals(rooms.get(0).name, "NorthWest");

    }
    @Test
    public void testInitialize(){

        ArrayList<Player> players = new ArrayList<>();
        ArrayList<Creature> creatures = new ArrayList<>();

        Player p1 = new Player("p1",5);
        Creature c1 = new Creature("c1",3);

        players.add(p1);
        creatures.add(c1);

        Room test_room = new Room("test");
        ArrayList<Room> rooms = new ArrayList<>();
        rooms.add(test_room);

        Arcane test = new Arcane(rooms,players,creatures,0);

        ArrayList<Room> tests = test.getRooms();

        Assertions.assertEquals(tests.size(),1);

        Assertions.assertSame(tests.get(0).getPlayers().get(0), players.get(0));
        Assertions.assertSame(tests.get(0).getCreatures().get(0),creatures.get(0));


    }

    @Test
    public void testPreFightRoom(){

        Room test_room = new Room("prefight test");
        Room neighbor = new Room("neighbor");

        test_room.addNeighbors(new Room[] {neighbor});

        Player highest_player = new Player("highest",5);
        Player middle = new Player("middle",3);
        Player low = new Player("low",1);

        test_room.addPlayer(highest_player);
        test_room.addPlayer(middle);
        test_room.addPlayer(low);

        ArrayList<Player> players = new ArrayList<>();
        players.add(highest_player);
        players.add(middle);
        players.add(low);

        test_room.preFight(players);

        ArrayList<Player> test = test_room.getPlayers();

        Assertions.assertEquals(test.size(),1);
        Assertions.assertSame(test.get(0),highest_player);
    }

    @Test
    public void testFight() throws IOException, InterruptedException {

        Player p1 = new Player("player",5);
        Creature c1 = new Creature("creature",3);
        Room room = new Room("fight room");

        ArrayList<Player> players = new ArrayList<>();
        ArrayList<Creature> creatures = new ArrayList<>();
        ArrayList<Room> rooms = new ArrayList<>();

        players.add(p1);
        creatures.add(c1);
        rooms.add(room);

        Arcane test = new Arcane(rooms,players,creatures,0);

        logger.info("Fight test:\n");
        logger.info("\n");
        int val = test.fight(p1,c1,room);

        while(val==-1){
            val = test.fight(p1,c1,room);
        }


        if(val == 0){ //creature wins

            Assertions.assertEquals(room.getPlayers().size(),0);

        }

        if(val == 1){// player wins

            Assertions.assertEquals(room.getCreatures().size(),0);

        }
        logger.info("\n");


    }

    @Test
    public void testAlive(){

        Player dead_p = new Player("dead",0);
        Creature dead_c = new Creature("dead",0);

        Player alive_p = new Player("alive",1);
        Creature alive_c = new Creature("alive",1);

        ArrayList<Player> players = new ArrayList<>();
        ArrayList<Creature> creatures = new ArrayList<>();

        players.add(dead_p);
        creatures.add(dead_c);

        Room test_room = new Room("test");
        ArrayList<Room> rooms = new ArrayList<>();
        rooms.add(test_room);


        Arcane test = new Arcane(rooms,players,creatures,0);

        Assertions.assertFalse(test.allPlayersAlive());
        Assertions.assertFalse(test.allCreaturesAlive());

        players.add(alive_p);
        creatures.add(alive_c);

        Arcane test2 = new Arcane(rooms,players,creatures,0);

        Assertions.assertTrue(test2.allPlayersAlive());
        Assertions.assertTrue(test2.allCreaturesAlive());

    }

    @Test
    public void testTurn() throws IOException, InterruptedException {

        Room test_room = new Room("test room");
        Room test_room2 = new Room("test room 2");

        test_room.addNeighbors(new Room[]{test_room2});
        test_room2.addNeighbors(new Room[]{test_room});
        ArrayList<Room> rooms = new ArrayList<>();

        rooms.add(test_room);

        ArrayList<Player> players = new ArrayList<>();
        ArrayList<Creature> creatures = new ArrayList<>();

        Arcane test = new Arcane(rooms,players,creatures,0);


        Assertions.assertEquals("none",test.turn(test_room,0,players,creatures));

        Player p1 = new Player("p1",5);
        Creature c1 = new Creature("c1",3);

        players.add(p1);


        Assertions.assertEquals("move",test.turn(test_room,0,players,creatures));

        test_room.addFood(1);

        Assertions.assertEquals("eaten",test.turn(test_room,0,players,creatures));

        creatures.add(c1);

        Arcane test_3 = new Arcane(rooms,players,creatures,1); //new game where players and creatures are in the room

        Assertions.assertEquals("fight",test_3.turn(test_room,0,players,creatures));


    }

    @Test
    public void testPlayAndWinningEval2x2() throws IOException, InterruptedException {

        logger.info("2x2 Test:\n");

        Player p1 = new Player("p1",5);
        Creature c1 = new Creature("c1",3);

        ArrayList<Player> players = new ArrayList<>();
        ArrayList<Creature> creatures = new ArrayList<>();
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

        players.add(p1);
        creatures.add(c1);

        Arcane test = new Arcane(room,players,creatures,0);


        int result = test.play();

        if (!test.allPlayersAlive()){

            Assertions.assertEquals(result,-1);
        }
        else{

            Assertions.assertEquals(result,1);
        }

        logger.info("\n");
        logger.info("\n");


    }

    @Test
    public void testPlayAndWinningEval3x3() throws IOException, InterruptedException {

        logger.info("3x3 test:\n");

        Player p1 = new Player("p1",5);
        Player p2 = new Player("p2",5);


        Creature c1 = new Creature("c1",3);
        Creature c2 = new Creature("c2",3);
        Creature c3 = new Creature("c3",3);
        Creature c4 = new Creature("c4",3);
        Creature c5 = new Creature("c5",3);

        ArrayList<Player> players = new ArrayList<>();
        ArrayList<Creature> creatures = new ArrayList<>();

        players.add(p1);
        players.add(p2);

        creatures.add(c1);
        creatures.add(c2);
        creatures.add(c3);
        creatures.add(c4);
        creatures.add(c5);

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


        Arcane test = new Arcane(rooms,players,creatures,10);

        int result = test.play();

        if(!test.allCreaturesAlive()){

            Assertions.assertEquals(result,1);
        }
        else{
            Assertions.assertEquals(result,-1);
        }

    }

    @Test
    public void buildNRoomsConnected(){

        Maze maze = Maze.newBuilder().buildNRoomsConnected(5).build();

        ArrayList<Room> rooms = maze.getRooms();

        Assertions.assertEquals(rooms.size(),5);
        Assertions.assertEquals(rooms.get(2).name,"r2");

        Assertions.assertEquals(rooms.get(0).getNeighborsSize(),4);


    }

    @Test
    public void buildNRoomsLinear(){

        Maze maze = Maze.newBuilder().buildNRoomsLinear(5).build();

        ArrayList<Room> rooms = maze.getRooms();

        Assertions.assertEquals(rooms.size(),5);
        Assertions.assertEquals(rooms.get(2).name,"r2");

        Assertions.assertEquals(rooms.get(0).getNeighbors().get(0).name,"r1");
    }

    @Test
    public void buildNRoomsConnectedString(){

        ArrayList<String> names = new ArrayList<>(Arrays.asList("room 1","room2"));

        Maze maze = Maze.newBuilder().buildNRoomsConnected(names).build();

        ArrayList<Room> rooms = maze.getRooms();

        Assertions.assertEquals(rooms.get(1).name,"room2");
    }

    @Test
    public void buildNRoomLinearString(){

        ArrayList<String> names = new ArrayList<>(Arrays.asList("room 1","room2"));

        Maze maze = Maze.newBuilder().buildNRoomsConnected(names).build();

        ArrayList<Room> rooms = maze.getRooms();

        Assertions.assertEquals(rooms.get(1).name,"room2");

    }

    @Test
    public void addingPlayerEntitiesRandom(){

        ArrayList<String> player = new ArrayList<>(Arrays.asList("player"));
        ArrayList<String> coward = new ArrayList<>(Arrays.asList("coward"));
        ArrayList<String> knight = new ArrayList<>(Arrays.asList("knight"));
        ArrayList<String> glutton = new ArrayList<>(Arrays.asList("glutton"));


        Maze maze = Maze.newBuilder().distributeRandom().buildNRoomsConnected(3)
                .createAndAddAdventurers(player).createAndAddCowards(coward)
                .createAndAddKnights(knight).createAndAddGlutton(glutton).build();


        int count = 0;
        ArrayList<Player> entities = new ArrayList<>();
        for(int i=0;i<maze.getRooms().size();i++){

            count += maze.getRooms().get(i).getPlayers().size();
            entities.addAll(maze.getRooms().get(i).getPlayers());

        }
        int pc =0;
        int cc =0;
        int kc =0;
        int gc =0;

        for(int i=0;i<entities.size();i++){

            if(entities.get(i).type == "adventurer"){
                pc++;
            }
            if(entities.get(i).type == "coward"){
                cc++;
            }
            if(entities.get(i).type == "knight"){
                kc++;
            }
            if(entities.get(i).type == "glutton"){
                gc++;
            }
        }

        Assertions.assertEquals(1,pc);
        Assertions.assertEquals(1,cc);
        Assertions.assertEquals(1,kc);
        Assertions.assertEquals(1,gc);


        Assertions.assertEquals(4,maze.getPlayers().size());
        Assertions.assertEquals(count,4);






    }

    @Test
    public void addingPlayerEntitiesSequential(){

        ArrayList<String> player = new ArrayList<>(Arrays.asList("player","player2","player3"));



        Maze maze = Maze.newBuilder().distributeSequential().buildNRoomsConnected(3)
                .createAndAddAdventurers(player).build();


        Assertions.assertEquals("player",maze.getRooms().get(0).getPlayers().get(0).name);
        Assertions.assertEquals("player2",maze.getRooms().get(1).getPlayers().get(0).name);
        Assertions.assertEquals("player3",maze.getRooms().get(2).getPlayers().get(0).name);




    }

    @Test
    public void addCreatureEntitiesRandom(){

        ArrayList<String> demons = new ArrayList<>(Arrays.asList("demon"));
        ArrayList<String> creats = new ArrayList<>(Arrays.asList("creature"));


        Maze maze = Maze.newBuilder().distributeRandom().buildNRoomsConnected(3)
                .createAndAddDemons(demons).createAndAddCreatures(creats).build();


        int count = 0;
        ArrayList<Creature> entities = new ArrayList<>();

        for(int i=0;i<maze.getRooms().size();i++){

            count += maze.getRooms().get(i).getCreatures().size();
            entities.addAll(maze.getRooms().get(i).getCreatures());


        }
        int dc =0;
        int cc =0;

        for(int i=0;i<entities.size();i++){

            if(entities.get(i).type == "demon"){
                dc++;
            }
            if(entities.get(i).type == "creature"){
                cc++;
            }
        }

        Assertions.assertEquals(1,dc);
        Assertions.assertEquals(1,cc);



        Assertions.assertEquals(2,maze.getCreatures().size());
        Assertions.assertEquals(count,2);


    }

    @Test
    public void addCreatureEntitiesSequential(){

        ArrayList<String> creatures = new ArrayList<>(Arrays.asList("creature1","creature2"));

        Maze maze = Maze.newBuilder().buildNRoomsLinear(2).distributeSequential().createAndAddCreatures(creatures).build();

        Assertions.assertEquals(maze.getRooms().get(0).getCreatures().get(0).name,"creature1");
        Assertions.assertEquals(maze.getRooms().get(1).getCreatures().get(0).name,"creature2");



        
    }

    @Test
    public void addFoodSequential(){

        ArrayList<String> food = new ArrayList<>(Arrays.asList("cookie","pasta","water"));

        Maze maze = Maze.newBuilder().buildNRoomsLinear(3).distributeSequential().createAndAddFood(food).build();

        Assertions.assertEquals(maze.getRooms().get(0).getFoodItems().get(0),"cookie");
        Assertions.assertEquals(maze.getRooms().get(1).getFoodItems().get(0),"pasta");
        Assertions.assertEquals(maze.getRooms().get(2).getFoodItems().get(0),"water");


    }

    @Test
    public void maze2x2(){

        Maze maze = Maze.newBuilder().maze2x2().build();

        Assertions.assertEquals(maze.getRooms().size(),4);
        Assertions.assertEquals(maze.getRooms().get(0).name, "NorthWest");


    }

    @Test
    public void maze3x3(){

        Maze maze = Maze.newBuilder().maze3x3().build();

        Assertions.assertEquals(maze.getRooms().size(),9);
        Assertions.assertEquals(maze.getRooms().get(1).name,"North");


    }

    @Test
    public void testNewMazePlay() throws IOException, InterruptedException {

        ArrayList<String> players = new ArrayList<>(Arrays.asList("adventurer"));
        ArrayList<String> demons = new ArrayList<>(Arrays.asList("demon"));
        ArrayList<String> knights = new ArrayList<>(Arrays.asList("knight"));
        ArrayList<String> food = new ArrayList<>(Arrays.asList("pasta","cookie"));


        Maze maze = Maze.newBuilder().distributeRandom().maze3x3().createAndAddDemons(demons)
                .createAndAddAdventurers(players).createAndAddKnights(knights)
                .createAndAddFood(food).build();

        Arcane game = new Arcane(maze);

        game.play();

    }


    @Test
    public void testMain() throws IOException, InterruptedException {

        String[] args = new String[]{"4","2","2","2"};

        GameConfigurator.main(args);


    }

    //Tests for observers and event bus
    public class ObserverMock implements IObserver{
        public List<String> events = new ArrayList<>();

        @Override
        public void update(String event){
            events.add(event);
            System.out.println(event);
        }
    }

    @Test
    void testEventBus() throws IOException, InterruptedException {
        EventBus eventBus = EventBus.getInstance();
        ObserverMock observer = new ObserverMock();

        eventBus.attach(observer,EventType.Death);
        eventBus.postMessage(EventType.Death,"The test has died!");

        Assertions.assertEquals(observer.events.get(0),"The test has died!");

    }

    @Test
    void testAudibleObserver() throws IOException, InterruptedException {

            Room room = new Room("test room");
            Player player = new Player("p1");
            Creature creature = new Creature("c1");

            ArrayList<Room> rooms = new ArrayList<>();
            ArrayList<Player> players = new ArrayList<>();
            ArrayList<Creature> creatures = new ArrayList<>();

            rooms.add(room);
            players.add(player);
            creatures.add(creature);

            Arcane game = new Arcane(rooms,players,creatures,0);

            game.observe();

            List<EventType> interested = new ArrayList<>();
            interested.add(EventType.FightOutcome);

            AudibleObserver observer = new AudibleObserver(game,interested,5);

            //running on Mac, audible observer does speak command
            game.play();


    }

    @Test
    void testMazeObserver() throws IOException, InterruptedException {

        IMazeObserver mazeObserver = MazeObserver.getNewBuilder("Arcane Game")
                .useRadialLayoutStrategy().setDelayInSecondsAfterUpdate(3).build();

        ArrayList<String> players = new ArrayList<>(Arrays.asList("adventurer"));
        ArrayList<String> demons = new ArrayList<>(Arrays.asList("demon"));
        ArrayList<String> knights = new ArrayList<>(Arrays.asList("knight"));
        ArrayList<String> food = new ArrayList<>(Arrays.asList("pasta","cookie"));


        Maze maze = Maze.newBuilder().distributeRandom().maze3x3().createAndAddDemons(demons)
                .createAndAddAdventurers(players).createAndAddKnights(knights)
                .createAndAddFood(food).build();

        List<EventType> interested = new ArrayList<>();
        interested.add(EventType.FightOutcome);
        interested.add(EventType.Death);
        interested.add(EventType.GameOver);
        interested.add(EventType.TurnEnded);



        Arcane game = new Arcane(maze);


        game.observe();

        AudibleObserver observer = new AudibleObserver(game,interested,3);
        game.attach(mazeObserver);






        game.play();


    }



}