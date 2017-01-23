package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Game;
import com.mycompany.myapp.domain.GameUser;

import com.mycompany.myapp.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the GameUser entity.
 */
@SuppressWarnings("unused")
public interface GameUserRepository extends JpaRepository<GameUser,Long> {

    @Query("select gameUser from GameUser gameUser where gameUser.user.login = ?#{principal.username}")
    List<GameUser> findByUserIsCurrentUser();

    @Query("select avg(gameUser.points) from GameUser gameUser where gameUser.game = :game")
    Double gameAvg(@Param("game")Game game);

    @Query("select gameUser.game, avg(gameUser.points) from GameUser gameUser" +
    " GROUP BY gameUser order by avg(gameUser.points) desc")

    List<Object[]> fiveFavouriteGames(Pageable pageable);

    Optional<GameUser> findByUserAndGame(User user, Game game);
}
