package fr.simplon.sondageservice.dao.impl;

import fr.simplon.sondageservice.entity.Sondage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SondageRepository extends JpaRepository <Sondage, Long> {
}
