package card.maybank.springboot_card.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import card.maybank.springboot_card.entity.User;
import card.maybank.springboot_card.entity.Card;
import card.maybank.springboot_card.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private CardService cardService;

    @Transactional (rollbackFor = Exception.class)
    public User addUser(User user) throws Exception {
        User userSavedToDB = this.userRepository.save(user);

        if (user.getFirstName().equalsIgnoreCase("error")) {
            throw new RuntimeException("Simulated Exception: Forcing rollback!");
        }
        return userSavedToDB;
    }
}