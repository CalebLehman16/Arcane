package csci.ooad.arcane.entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface IObservable {

    void attach(IObserver observer,EventType type);
    void post(EventType event, String description) throws IOException, InterruptedException;

}
