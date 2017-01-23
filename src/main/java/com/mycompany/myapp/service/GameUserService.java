package com.mycompany.myapp.service;


import com.mycompany.myapp.domain.GameUser;
import com.mycompany.myapp.repository.GameUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service
@Transactional
public class GameUserService {

    private final Logger log = LoggerFactory.getLogger(GameUserService.class);

    @Inject
    private GameUserRepository gameUserRepository;


    public GameUser save(GameUser gameUser){
        log.debug("Request to save GameUser : {}", gameUser);
        GameUser result = gameUserRepository.save(gameUser);
        return result;
    }

    @Transactional(readOnly = true)
    public Page<GameUser> findAll(Pageable pageable) {
        log.debug("Request to get all GameRatings");
        Page<GameUser> result = gameUserRepository.findAll(pageable);
        return result;
    }

    @Transactional(readOnly = true)
    public GameUser findOne(Long id) {
        log.debug("Request to get GameRating : {}", id);
        GameUser gameUser = gameUserRepository.findOne(id);
        return gameUser;
    }

    public void delete(Long id) {
        log.debug("Request to delete GameRating : {}", id);
        gameUserRepository.delete(id);
    }

}
