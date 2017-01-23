package com.mycompany.myapp.service;


import com.mycompany.myapp.domain.Game;
import com.mycompany.myapp.repository.GameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service
@Transactional

public class GameService {

    private final Logger log = LoggerFactory.getLogger(GameService.class);

    @Inject
    private GameRepository gameRepository;

    public Game save(Game game){
        log.debug("Request to save Game : {}", game);
        Game result = gameRepository.save(game);
        return result;
    }

    @Transactional(readOnly = true)
    public Page<Game> findAll(Pageable pageable) {
        log.debug("Request to get all Games");
        Page<Game> result = gameRepository.findAll(pageable);
        return result;
    }

    @Transactional(readOnly = true)
    public Game findOne(Long id) {
        log.debug("Request to get Game : {}", id);
        Game game = gameRepository.findOne(id);
        return game;
    }

    public void delete(Long id) {
        log.debug("Request to delete Game : {}", id);
        gameRepository.delete(id);
    }

}
