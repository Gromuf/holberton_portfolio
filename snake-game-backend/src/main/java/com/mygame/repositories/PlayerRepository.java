package com.mygame.repositories;

import com.mygame.models.Player;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
	Optional<Player> findByUsername(String username);

	Optional<Player> findByEmail(String email);

	Optional<Player> findById(Long id);

	boolean existsByUsername(String username);

	boolean existsByEmail(String email);
}
