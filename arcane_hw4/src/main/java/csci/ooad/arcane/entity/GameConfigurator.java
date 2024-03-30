package csci.ooad.arcane.entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GameConfigurator {

    public static void main(String[] args) throws IOException, InterruptedException {

        int num_rooms = Integer.parseInt(args[0]);
        int num_adv = Integer.parseInt(args[1]);
        int num_creat = Integer.parseInt(args[2]);
        int num_food = Integer.parseInt(args[3]);



        //going to take number of adventurers and randomly choose between all types (glutton knight etc)
        //going to do the same for creatures and demons
        //same goes for food items.


        ArrayList<String> player_types = new ArrayList<>(Arrays.asList("adventurer","coward","knight","glutton"));
        ArrayList<String> creature_types = new ArrayList<>(Arrays.asList("creature","demon"));
        ArrayList<String> food_types = new ArrayList<>(Arrays.asList("cookie","burger","fries","water","juice","bread","jerky"));

        int pc=0; //adventurer
        int cc=0; //coward
        int kc=0; //knight
        int gc=0; //glutton

        int crc=0; //creature
        int dc =0; //demon

        Random rand = new Random();

        for(int i=0;i<num_adv;i++){

            int num = rand.nextInt(4);

            if(num==0){

                pc++;
            }

            if(num==1){

                cc++;

            }

            if(num==2){

                kc++;
            }

            if(num==3){

                gc++;

            }

        }

        for(int i=0;i<num_creat;i++){

            int num = rand.nextInt(2);

            if(num==0){
                crc++;
            }

            if(num==1){
                dc++;
            }
        }

        ArrayList<String> food = new ArrayList<>();

        int j=0;

        for(int i=0;i<num_food;i++){

            if(j == food_types.size()){
                j=0;
            }

            food.add(food_types.get(j));
            j++;

        }



        Maze maze = Maze.newBuilder().buildNRoomsConnected(num_rooms).distributeRandom().createAndAddAdventurers(pc)
                .createAndAddKnights(kc).createAndAddGlutton(gc).createAndAddCowards(cc)
                .createAndAddDemons(dc).createAndAddCreatures(crc).createAndAddFood(food).build();


        Arcane game = new Arcane(maze);

        game.play();



















    }
}
