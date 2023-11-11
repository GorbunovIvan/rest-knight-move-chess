package com.example.model;

import lombok.*;

import java.util.List;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@EqualsAndHashCode
@ToString
public abstract class Figure {

    private Board board;
    private Location location;

    public abstract List<Location> getAllPossibleNextMoves();

    public static Figure getFigureByName(String name) {

        if (name.equalsIgnoreCase("knight")) {
            return new Knight();
        }

        throw new RuntimeException("No figure found by name: '" + name + "'");
    }
}
