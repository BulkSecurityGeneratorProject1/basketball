package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Game;
import com.mycompany.myapp.domain.GameUser;

import com.mycompany.myapp.repository.GameRepository;
import com.mycompany.myapp.repository.GameUserRepository;
import com.mycompany.myapp.domain.DTO.GameDTO;
import com.mycompany.myapp.repository.UserRepository;
import com.mycompany.myapp.security.SecurityUtils;
import com.mycompany.myapp.service.GameService;
import com.mycompany.myapp.web.rest.util.HeaderUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing GameUser.
 */
@RestController
@RequestMapping("/api")
public class GameUserResource {

    private final Logger log = LoggerFactory.getLogger(GameUserResource.class);

    @Inject
    private GameUserRepository gameUserRepository;

    @Inject
    private GameRepository gameRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private GameService gameService;

    @PostMapping("/game-users")
    @Timed
    public ResponseEntity<GameUser> createGameUser(@RequestBody GameUser gameUser) throws URISyntaxException {
        log.debug("REST request to save GameUser : {}", gameUser);
        if (gameUser.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("gameUser", "idexists", "A new gameUser cannot already have an ID")).body(null);
        }

        if(gameService.findOne(gameUser.getGame().getId()) == null){
            return ResponseEntity.badRequest().
                headers(HeaderUtil.
                    createFailureAlert("gameUser","gamenotexists","Game not Exists")).body(null);

        }


        gameUser.setUser(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get());
        gameUser.setTime(ZonedDateTime.now());

        Optional<GameUser> gameUserOptional = gameUserRepository.
            findByUserAndGame(gameUser.getUser(),gameUser.getGame());

        GameUser result = null;

        if (gameUserOptional.isPresent()){
            result = gameUserOptional.get();
            result.setPoints(gameUser.getPoints());
            result.setTime(gameUser.getTime());
            result = gameUserRepository.save(result);
        } else{
            result = gameUserRepository.save(gameUser);
        }

        return ResponseEntity.created(new URI("/api/game-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("gameUser", result.getId().toString()))
            .body(result);
    }


    @PutMapping("/game-users")
    @Timed
    public ResponseEntity<GameUser> updateGameUser(@RequestBody GameUser gameUser) throws URISyntaxException {
        log.debug("REST request to update GameUser : {}", gameUser);
        if (gameUser.getId() == null) {
            return createGameUser(gameUser);
        }
        GameUser result = gameUserRepository.save(gameUser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("gameUser", gameUser.getId().toString()))
            .body(result);
    }

    @GetMapping("/game-users")
    @Timed
    public List<GameUser> getAllGameUsers() {
        log.debug("REST request to get all GameUsers");
        List<GameUser> gameUsers = gameUserRepository.findAll();
        return gameUsers;
    }


    @GetMapping("/game-users/{id}")
    @Timed
    public ResponseEntity<GameUser> getGameUser(@PathVariable Long id) {
        log.debug("REST request to get GameUser : {}", id);
        GameUser gameUser = gameUserRepository.findOne(id);
        return Optional.ofNullable(gameUser)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @DeleteMapping("/game-users/{id}")
    @Timed
    public ResponseEntity<Void> deleteGameUser(@PathVariable Long id) {
        log.debug("REST request to delete GameUser : {}", id);
        gameUserRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("gameUser", id.toString())).build();
    }


    @GetMapping("/avgGame/{id}")
    public ResponseEntity<GameDTO> avgGame(Long idGame) throws URISyntaxException{

        Game game = gameRepository.findOne(idGame);

        if(game==null){

            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("gameUserRepository","gameNotExists","El partido no existe")).body(null);

        }else{
            Double avg = gameUserRepository.gameAvg(game);

            GameDTO gameDTO = new GameDTO(game,avg);

            return new ResponseEntity<>(gameDTO,HttpStatus.OK);
        }

    }


    @GetMapping("/fiveGames")
    public ResponseEntity<List<GameDTO>> fiveGames()throws URISyntaxException{

        Pageable pageable = new PageRequest(0,5);

        List<Object[]> topGames = gameUserRepository.fiveFavouriteGames(pageable);

        List<GameDTO> result = new ArrayList<>();

        topGames.forEach(
            topGame -> {
                GameDTO g = new GameDTO();
                g.setGame((Game) topGame[0]);
                g.setCount((Double) topGame[1]);

                result.add(g);
            }
        );
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
