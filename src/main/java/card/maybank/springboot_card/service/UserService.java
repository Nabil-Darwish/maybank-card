package card.maybank.springboot_card.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import card.maybank.springboot_card.entity.User;
import card.maybank.springboot_card.entity.Card;
import card.maybank.springboot_card.repository.UserRepository;

import java.math.BigDecimal;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional (rollbackFor = Exception.class)
    public User addUser(User user) throws Exception {
        User userSavedToDB = this.userRepository.save(user);

        if (user.getFirstName().equalsIgnoreCase("error")) {
            throw new RuntimeException("Simulated Exception: Forcing rollback!");
        }
        return userSavedToDB;
    }

    @Transactional
    public User updateUserAnnualIncome(long id, BigDecimal income) {
        if (income == null || income.signum() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Income must be zero or positive");
        }
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        user.setAnnualIncome(income);
        return user;
    }
}