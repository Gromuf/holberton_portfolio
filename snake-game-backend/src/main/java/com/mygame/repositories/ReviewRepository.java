package com.mygame.repositories;

import com.mygame.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
	List<Review> findByPlayerId(Long playerId);

	List<Review> findAllByOrderByIdDesc(); // Trie les reviews par ID décroissant (les plus récentes en premier)
}
