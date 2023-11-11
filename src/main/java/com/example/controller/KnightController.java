package com.example.controller;

import com.example.model.Board;
import com.example.model.Location;
import com.example.service.FigureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/knights")
@RequiredArgsConstructor
public class KnightController {

    private final FigureService figureService;

    @GetMapping("/count")
    public ResponseEntity<Integer> countMinMoves(@RequestParam("width") int width,
                                                    @RequestParam("height") int height,
                                                    @RequestParam("start") String start,
                                                    @RequestParam("end") String end) {

        var board = new Board(width, height);

        var locationStart = Location.stringToLocation(start);
        var locationEnd = Location.stringToLocation(end);

        var numberOfMoves = figureService.countMinMoves(locationStart, locationEnd, board, "knight");

        return ResponseEntity.ok(numberOfMoves);
    }
}
