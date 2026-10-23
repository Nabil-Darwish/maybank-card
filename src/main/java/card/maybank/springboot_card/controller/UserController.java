package card.maybank.springboot_card.controller;

import card.maybank.springboot_card.entity.Card;
import card.maybank.springboot_card.entity.User;
import card.maybank.springboot_card.repository.CardRepository;
import card.maybank.springboot_card.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository users;
    private final CardRepository cards;

    public UserController(UserRepository users, CardRepository cards) {
        this.users = users;
        this.cards = cards;
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return users.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    @GetMapping("/{id}/cards")
    public List<Card> getUserCards(@PathVariable Long id) {
        if (!users.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        return cards.findByUserId(id);
    }
}