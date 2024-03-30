package csci.ooad.arcane.entity;

import java.io.IOException;

public interface IObserver {


    void update(String event) throws IOException, InterruptedException;


}
