package com.mygame.repositories;

import com.mygame.models.Settings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingsRepository extends JpaRepository<Settings, Long> {

	Settings findByPlayerId(Long playerId);
}
