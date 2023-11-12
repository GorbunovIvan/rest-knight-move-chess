package com.example.controller;

import com.example.model.Board;
import com.example.model.Location;
import com.example.service.FigureService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class KnightControllerTest {

    @Autowired
    private MockMvc mvc;

    @SpyBean
    private FigureService figureService;

    private final String baseURI = "/api/v1/knights";

    @Test
    void testCountMinMovesGivenB1A3ThenGet1Move() throws Exception {

        int boardWidth = 8;
        int boardHeight = 8;

        String start = "B1";
        String end = "A3";

        String paramsOfBoardInURI = String.format("?width=%d&height=%d", boardWidth, boardHeight);
        String paramsOfLocationsInURI = String.format("&start=%s&end=%s", start, end);

        mvc.perform(MockMvcRequestBuilders.get(baseURI + "/count" + paramsOfBoardInURI + paramsOfLocationsInURI))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));

        var locationStart = Location.stringToLocation(start);
        var locationEnd = Location.stringToLocation(end);
        var board = new Board(boardWidth, boardHeight);
        verify(figureService, times(1)).countMinMoves(locationStart, locationEnd, board,"knight");
    }

    @Test
    void testCountMinMovesGivenA1H8ThenGet6Moves() throws Exception {

        int boardWidth = 8;
        int boardHeight = 8;

        String start = "A1";
        String end = "H8";

        String paramsOfBoardInURI = String.format("?width=%d&height=%d", boardWidth, boardHeight);
        String paramsOfLocationsInURI = String.format("&start=%s&end=%s", start, end);

        mvc.perform(MockMvcRequestBuilders.get(baseURI + "/count" + paramsOfBoardInURI + paramsOfLocationsInURI))
                .andExpect(status().isOk())
                .andExpect(content().string("6"));

        var locationStart = Location.stringToLocation(start);
        var locationEnd = Location.stringToLocation(end);
        var board = new Board(boardWidth, boardHeight);
        verify(figureService, times(1)).countMinMoves(locationStart, locationEnd, board,"knight");
    }
}