package com.acme.games.rps.service.impl;

import com.acme.games.rps.model.Choice;
import com.acme.games.rps.model.Game;
import com.acme.games.rps.model.Move;
import com.acme.games.rps.service.ChoiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.IntStream;

import static java.util.Collections.emptyMap;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

/**
 * A Markov chain implementation that tries to predict player's next choice basing on a previous one.
 * <p>
 * Currently in analyses only user choices without paying attention to server choices. I.e. it is based on simple
 * 2D matrix, where 1st dimension is previous choice, 2nd dimension is next choice and values are counts of such responses.
 * No "decay" is used, i.e. the model remembers all moves.
 * <p>
 * Also this matrix is recalculated from scratch every time. This is done on purpose, to avoid saving any state in
 * the application.
 */
@Service
@ConditionalOnProperty(value = "choice.service", havingValue = "markov")
@Slf4j
public class MarkovChoiceServiceImpl implements ChoiceService {
    @Override
    public Choice makeChoice(Game game, Choice playerChoice) {
        Choice choice = makeSmartChoice(game);
        log.debug("Server makes choice={} in game Id={}", choice, game.getId());
        return choice;
    }

    private Choice makeSmartChoice(Game game) {
        MarkovModel model = new MarkovModel(game.getMoves());
        Optional<Choice> expectedPlayerChoice = model.getMostProbableNext();

        log.debug("Expected next player choice is {}", expectedPlayerChoice);

        return expectedPlayerChoice.map(Choice::beatedBy).orElseGet(Choice::random);
    }

    private static class MarkovModel {
        //Markov matrix for player choices. Values are stored as: <previous, <next, count>>
        private final Map<Choice, Map<Choice, Long>> matrix;
        private final List<Move> moves;

        public MarkovModel(List<Move> moves) {
            this.matrix = createMatrixFromHistory(moves);
            this.moves = moves;
        }

        private Map<Choice, Map<Choice, Long>> createMatrixFromHistory(List<Move> moves) {
            EnumMap<Choice, Map<Choice, Long>> historyMatrix = IntStream.range(1, moves.size())
                    .mapToObj(i -> Arrays.asList(moves.get(i - 1).getPlayerChoice(), moves.get(i).getPlayerChoice()))
                    .collect(
                            groupingBy(cs -> cs.get(0), () -> new EnumMap<>(Choice.class),
                                    groupingBy(cs -> cs.get(1), () -> new EnumMap<>(Choice.class),
                                            counting())));

            log.debug("Markov matrix is built from {} moves: {}", moves.size(), historyMatrix);

            return historyMatrix;
        }

        public Optional<Choice> getMostProbableNext() {
            if (moves.isEmpty()) {
                return Optional.empty();
            } else {
                Move lastMove = moves.get(moves.size() - 1);
                return matrix
                        .getOrDefault(lastMove.getPlayerChoice(), emptyMap())
                        .entrySet().stream()
                        .max(Map.Entry.comparingByValue())
                        .map(Map.Entry::getKey);
            }
        }
    }
}
