package card.maybank.springboot_card.repository;

import card.maybank.springboot_card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByUserId(Long userId); // Spring writes the query from the method name
}