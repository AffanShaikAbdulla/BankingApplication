package com.fintech.cards.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fintech.cards.entity.Cards;

public interface CardsRepository extends JpaRepository<Cards, Long>{
Optional<Cards> findByMobileNumber(String mobileNumber);
Optional<Cards> findByCardNumber(String cardNumber);
}
