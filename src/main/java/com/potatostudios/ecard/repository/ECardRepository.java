package com.potatostudios.ecard.repository;

import com.potatostudios.ecard.model.ECard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ECardRepository extends JpaRepository<ECard, UUID> {

}
