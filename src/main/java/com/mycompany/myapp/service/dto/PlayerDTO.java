package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.Player;

/**
 * Created by 25369405Z on 09/01/2017.
 */
public class PlayerDTO {

    private Player player;
    private Long count;

    public PlayerDTO(){
    }

    public PlayerDTO(Player player, Long count){
        this.player = player;
        this.count = count;
    }

    public Player getPlayer(){
        return player;
    }

    public void setPlayer (Player player){
        this.player = player;
    }

    public Long getCount(){
        return count;
    }

    public void setCount(Long count){
        this.count = count;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayerDTO playerDTO = (PlayerDTO) o;

        if (player != null ? !player.equals(playerDTO.player) : playerDTO.player !=null) return false;
        return count != null ? count.equals(playerDTO.count) : playerDTO.count == null;
    }

    @Override
    public int hashCode(){
        int result = player != null ? player.hashCode() : 0;
        result = 31 * result + (count != null ? count.hashCode() : 0);
        return result;
    }

    @Override
    public String toString(){
        return "PlayerDTO{"+
            "player=" + player +
            ", count=" + count +
            '}';
    }
}
