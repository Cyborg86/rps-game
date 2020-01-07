package com.acme.games.rps.service;

import com.acme.games.rps.model.Choice;
import com.acme.games.rps.model.Move;
import com.acme.games.rps.model.MoveResult;
import org.springframework.stereotype.Service;

@Service
public class MoveService {

    public Move evaluateMove(Choice playerChoice, Choice ourChoice) {
        MoveResult result = evaluateResult(playerChoice, ourChoice);
        return new Move(playerChoice, ourChoice, result);
    }

    private MoveResult evaluateResult(Choice playerChoice, Choice ourChoice) {
        //TODO
        return MoveResult.DRAW;
    }
}
