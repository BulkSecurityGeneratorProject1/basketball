package com.mycompany.myapp.domain.DTO;

import com.mycompany.myapp.domain.Game;

/**
 * Created by 25369405Z on 16/01/2017.
 */
public class GameDTO {

    private Game game;
    private Double count;

    public GameDTO() {

    }

    public GameDTO(Game game, Double count) {
        this.game = game;
        this.count = count;
    }


    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Double getCount() {
        return count;
    }

    public void setCount(Double avgScore) {
        this.count = avgScore;
    }

    @Override
    public String toString() {
        return "GameDTO{" +
            "game=" + game +
            ", avgScore=" + count +
            '}';
    }
}
