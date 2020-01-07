package com.acme.games.rps.dao;

import com.acme.games.rps.model.Game;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameDao extends CrudRepository<Game, String> {
}
