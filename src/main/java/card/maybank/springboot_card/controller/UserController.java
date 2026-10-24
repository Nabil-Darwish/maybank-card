package card.maybank.springboot_card.controller;

import card.maybank.springboot_card.entity.Card;
import card.maybank.springboot_card.entity.User;
import card.maybank.springboot_card.repository.CardRepository;
import card.maybank.springboot_card.repository.UserRepository;
import card.maybank.springboot_card.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository users;
    private final CardRepository cards;
    private final UserService userService;

    public UserController(UserRepository users, CardRepository cards, UserService userService) {
        this.users = users;
        this.cards = cards;
        this.userService = userService;
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

    @GetMapping()
    public Page<User> getAllUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return users.findAll(PageRequest.of(page, Math.min(size, 100), Sort.by("id")));
    }

    @PostMapping("/add")
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        try {
            User userSaved = userService.addUser(user);
            return ResponseEntity.ok(userSaved);
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Transaction failed: " + e.getMessage());
        }
    }

}