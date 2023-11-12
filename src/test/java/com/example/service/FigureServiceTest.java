package com.example.service;

import com.example.model.Board;
import com.example.model.Location;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class FigureServiceTest {

    @Autowired
    private FigureService figureService;

    private final Board standartBoard = new Board(8, 8);

    @Test
    void testCountMinMovesForKnightGivenB1A3ThenGet1Move() {

        var locationStart = new Location(2, 1);
        var locationEnd = new Location(1, 3);

        var numberOfMoves = figureService.countMinMoves(locationStart, locationEnd, standartBoard,"knight");
        assertEquals(1, numberOfMoves);
    }

    @Test
    void testCountMinMovesForKnightGivenD5G8ThenGet2Moves() {

        var locationStart = new Location(4, 5);
        var locationEnd = new Location(7, 8);

        var numberOfMoves = figureService.countMinMoves(locationStart, locationEnd, standartBoard,"knight");
        assertEquals(2, numberOfMoves);
    }

    @Test
    void testCountMinMovesForKnightGivenF1B6ThenGet5Moves() {

        var locationStart = new Location(6, 1);
        var locationEnd = new Location(2, 6);

        var numberOfMoves = figureService.countMinMoves(locationStart, locationEnd, standartBoard,"knight");
        assertEquals(3, numberOfMoves);
    }

    @Test
    void testCountMinMovesForKnightGivenG1B6ThenGet4Moves() {

        var locationStart = new Location(7, 1);
        var locationEnd = new Location(2, 6);

        var numberOfMoves = figureService.countMinMoves(locationStart, locationEnd, standartBoard,"knight");
        assertEquals(4, numberOfMoves);
    }

    @Test
    void testCountMinMovesForKnightGivenA8H8ThenGet5Moves() {

        var locationStart = new Location(1, 8);
        var locationEnd = new Location(8, 8);

        var numberOfMoves = figureService.countMinMoves(locationStart, locationEnd, standartBoard,"knight");
        assertEquals(5, numberOfMoves);
    }

    @Test
    void testCountMinMovesForKnightGivenA1H8ThenGet6Moves() {

        var locationStart = new Location(1, 1);
        var locationEnd = new Location(8, 8);

        var numberOfMoves = figureService.countMinMoves(locationStart, locationEnd, standartBoard,"knight");
        assertEquals(6, numberOfMoves);
    }
}