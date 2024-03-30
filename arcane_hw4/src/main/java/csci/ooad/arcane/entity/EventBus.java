package csci.ooad.arcane.entity;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class EventBus{

    private static EventBus instance = new EventBus();
    private ArrayList<IObserver> observers = new ArrayList<>();
    private final HashMap<EventType,ArrayList<IObserver>> observerEvents = new HashMap<>();
    private EventBus(){
    }

    public void attach(IObserver observer, EventType eventType){

        observers.add(observer);
        ArrayList<IObserver> currentObservers = observerEvents.get(eventType);

        if(currentObservers == null){

            ArrayList<IObserver> fresh_addition = new ArrayList<>();
            fresh_addition.add(observer);
            observerEvents.put(eventType,fresh_addition);
            return;
        }

        currentObservers.add(observer);
        observerEvents.put(eventType,currentObservers);

    }


    public void postMessage(EventType eventType, String event) throws IOException, InterruptedException {

        ArrayList<IObserver> observers = observerEvents.get(eventType);

        if(observers == null){ //bad even type input, no event observers
            return;
        }
        for (IObserver observer : observers) {

            observer.update(event);
        }

    }

    public static EventBus getInstance(){

        return instance;
    }


}
