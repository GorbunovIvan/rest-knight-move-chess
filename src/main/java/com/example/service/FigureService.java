package com.example.service;

import com.example.model.Board;
import com.example.model.Figure;
import com.example.model.Location;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FigureService {

    /**
     * Returns the number of moves to reach the destination (locationEnd)
     * starting from the locationStart with the figure with the specified name.
     * @param locationStart - location where to start from
     * @param locationEnd - destination location
     * @param figureName - name of a figure (e.g. 'knight')
     * @return minimum number of moves to reach the destination (locationEnd)
     */
    public int countMinMoves(Location locationStart, Location locationEnd, Board board, String figureName) {

        // First we get all possible last 3 moves (counting from locationEnd position)
        // The total number of moves will not be large (109 at most)
        var figureOnTheEnd = Figure.getFigureByName(figureName);
        figureOnTheEnd.setLocation(locationEnd);
        figureOnTheEnd.setBoard(board);
        var allMovesBackFromTheEnd = getAllMovesWithDepth(figureOnTheEnd, 3);

        var figure = Figure.getFigureByName(figureName);
        figure.setLocation(locationStart);
        figure.setBoard(board);

        int count = 0;

        var moves = new ArrayList<Location>();

        // Just getting to locationEnd as close as possible.
        // But if we step on one of the moves from allMovesBackFromTheEnd,
        // then stop the movement and return the counted moves
        // plus the number of moves from the end (pre-counted in allMovesBackFromTheEnd)
        while (!figure.getLocation().equals(locationEnd)) {

            var nextMoves = figure.getAllPossibleNextMoves();
            if (nextMoves.isEmpty()) {
                throw new RuntimeException("No moves are defined, it should not happen");
            }

            var moveFromTheEnd = allMovesBackFromTheEnd.entrySet().stream()
                    .filter(entry -> nextMoves.contains(entry.getKey()))
                    .min(Map.Entry.comparingByValue())
                    .orElse(null);

            if (moveFromTheEnd != null) {
                count += moveFromTheEnd.getValue() + 1;
                return count;
            }

            Location fastestMove = null;
            int closenessMin = Integer.MAX_VALUE;

            for (var nextMove : nextMoves) {

                if (moves.contains(nextMove)) {
                    continue;
                }

                var closeness = countCloseness(nextMove, locationEnd);

                if (closeness < closenessMin) {
                    fastestMove = nextMove;
                    closenessMin = closeness;
                }
            }

            if (fastestMove == null) {
                throw new RuntimeException("It's stuck after '" + count + "' moves");
            }

            figure.setLocation(fastestMove);
            count++;

            moves.add(fastestMove);
        }

        return count;
    }

    private Map<Location, Integer> getAllMovesWithDepth(Figure figure, int depth) {

        Map<Integer, Set<Location>> depthAndMoves = getAllMovesByDepth(figure, depth);

        Map<Location, Integer> movesByDepth = new HashMap<>();

        var allMoves = depthAndMoves.values().stream().flatMap(Set::stream).toList();

        for (var move : allMoves) {

            int minDepth = Integer.MAX_VALUE;

            for (var entry : depthAndMoves.entrySet()) {
                if (entry.getValue().contains(move)) {
                    minDepth = Math.min(minDepth, entry.getKey());
                }
            }

            if (minDepth == Integer.MAX_VALUE) {
                throw new RuntimeException("Something went wrong");
            }

            movesByDepth.put(move, minDepth);
        }

        return movesByDepth;
    }

    private Map<Integer, Set<Location>> getAllMovesByDepth(Figure figure, int depth) {

        Map<Integer, Set<Location>> depthAndMoves = new HashMap<>();

        depthAndMoves.put(0, Stream.of(figure.getLocation()).collect(Collectors.toSet()));

        for (int iDepth = 1; iDepth <= depth; iDepth++) {

            var previousMoves = depthAndMoves.get(iDepth-1);

            for (var move : previousMoves) {

                figure.setLocation(move);

                var nextMoves = figure.getAllPossibleNextMoves();
                nextMoves.removeAll(depthAndMoves.values().stream().flatMap(Set::stream).toList());

                if (depthAndMoves.containsKey(iDepth)) {
                    depthAndMoves.get(iDepth).addAll(nextMoves);
                } else {
                    depthAndMoves.put(iDepth, new HashSet<>(nextMoves));
                }
            }
        }

        return depthAndMoves;
    }

    private int countCloseness(Location locationA, Location locationB) {
        return Math.abs(locationA.getX() - locationB.getX())
                + Math.abs(locationA.getY() - locationB.getY());
    }
}
