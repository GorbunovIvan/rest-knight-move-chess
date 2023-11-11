package com.example.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter @Setter
@EqualsAndHashCode(callSuper = true)
@ToString
public class Knight extends Figure {

    @Override
    public List<Location> getAllPossibleNextMoves() {

        if (getBoard() == null) {
            throw new RuntimeException("Board is undefined");
        }

        var locations = new ArrayList<Location>();

        final int x = getLocation().getX();
        final int y = getLocation().getY();

        // All possible moves if the piece is in the center of the board
        locations.add(new Location(x+1, y+2));
        locations.add(new Location(x+1, y-2));
        locations.add(new Location(x-1, y+2));
        locations.add(new Location(x-1, y-2));

        locations.add(new Location(x+2, y+1));
        locations.add(new Location(x+2, y-1));
        locations.add(new Location(x-2, y+1));
        locations.add(new Location(x-2, y-1));

        // Removing all impossible moves (those that go beyond the board)
        locations.removeIf(l -> l.getX() <= 0 || l.getY() <= 0
                                || l.getX() > getBoard().width() || l.getY() > getBoard().height());

        return locations;
    }
}
