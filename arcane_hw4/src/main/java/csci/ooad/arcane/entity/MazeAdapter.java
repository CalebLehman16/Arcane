package csci.ooad.arcane.entity;

import csci.ooad.layout.IMaze;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MazeAdapter implements IMaze {
    private Maze maze;

    public MazeAdapter(Maze maze){
        this.maze = maze;
    }


    @Override
    public List<String> getRooms() {

        return Collections.unmodifiableList(maze.getRoomNames());

    }

    @Override
    public List<String> getNeighborsOf(String s) {

        return Collections.unmodifiableList(maze.getNeighborOfRoom(s));
    }

    @Override
    public List<String> getContents(String s) {

        return Collections.unmodifiableList(maze.getContents(s));
    }
}
