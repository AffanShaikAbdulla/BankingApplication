package com.fintech.cards.servcies;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.fintech.cards.constants.CardsConstants;
import com.fintech.cards.dto.CardsDto;
import com.fintech.cards.entity.Cards;
import com.fintech.cards.exception.CardAlreadyExistsException;
import com.fintech.cards.exception.ResourceNotFoundException;
import com.fintech.cards.mapper.CardMapper;
import com.fintech.cards.repository.CardsRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CardServiceImpl implements ICardService {

    private CardsRepository cardsRepository;

    @Override
    public void createCard(String mobileNumber) {

        Optional<Cards> optionalCards =
                cardsRepository.findByMobileNumber(mobileNumber);

        if (optionalCards.isPresent()) {
            throw new CardAlreadyExistsException(
                    "Card already registered with given mobile number "
                            + mobileNumber
            );
        }

        Cards newCard = createNewCard(mobileNumber);
        newCard.setMobileNumber(mobileNumber);
        
        newCard.setCardType("CREDIT");
        newCard.setTotalLimit(50000);
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(50000);

        cardsRepository.save(newCard);
    }

    private Cards createNewCard(String mobileNumber) {

        Cards newCard = new Cards();

        long randomCardNumber =
                100000000000L + new Random().nextInt(900000000);

        newCard.setCardNumber(Long.toString(randomCardNumber));
        newCard.setMobileNumber(mobileNumber);
        newCard.setCardType(CardsConstants.CREDIT_CARD);
        newCard.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
        newCard.setCreatedAt(LocalDateTime.now());
        newCard.setCreatedBy("CARD_MS");
        return newCard;
    }

    @Override
    public CardsDto fetchCard(String mobileNumber) {

        Cards cards = cardsRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Card",
                                "mobileNumber",
                                mobileNumber
                        ));

        return CardMapper.mapToCardsDto(cards, new CardsDto());
    }

    @Override
    public boolean updateCard(CardsDto cardsDto) {

        Cards cards = cardsRepository
                .findByCardNumber(cardsDto.getCardNumber())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Card",
                                "cardNumber",
                                cardsDto.getCardNumber()
                        ));

        CardMapper.mapToCards(cardsDto, cards);

        cardsRepository.save(cards);

        return true;
    }

    @Override
    public boolean deleteCard(String mobileNumber) {

        Cards cards = cardsRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Card",
                                "mobileNumber",
                                mobileNumber
                        ));

        cardsRepository.deleteById(cards.getCardId());

        return true;
    }
}