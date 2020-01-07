package com.acme.games.rps.controller;

import com.acme.games.rps.model.Choice;
import com.acme.games.rps.model.Game;
import com.acme.games.rps.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/games")
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;

    @PostMapping
    public ResponseEntity<Void> startGame() {
        Game game = gameService.createGame();

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(game.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public Game getGame(@PathVariable String id) {
        return gameService.getGame(id);
    }

    @PutMapping("/{id}/finish")
    public void finishGame(@PathVariable String id) {
        gameService.finishGame(id);
    }

    @PostMapping("/{id}/moves")
    public Game makeMove(@PathVariable String id, @RequestParam Choice playerChoice) {
        return gameService.makeMove(id, playerChoice);
    }
}
