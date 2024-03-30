package csci.ooad.arcane.entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AudibleObserver implements IObserver{

    private List<EventType> eventType;
    public List<String> events = new ArrayList<>();

    public IObservable game;

    int delay;

    public AudibleObserver(IObservable arcaneGame, List<EventType> interests, int delay){

        this.eventType = interests;
        this.game = arcaneGame;
        this.delay = delay;

        for (EventType type : eventType) {

            this.game.attach(this, type);
        }
    }
    public void update(String event) throws IOException, InterruptedException {

        events.add(event);
        //get it to announce event

        String [] cmd = {"say",event};
        Runtime.getRuntime().exec(cmd);

        Thread.sleep(this.delay* 1000L);
    }
}
