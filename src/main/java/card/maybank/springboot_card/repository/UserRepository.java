package card.maybank.springboot_card.repository;

import card.maybank.springboot_card.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}