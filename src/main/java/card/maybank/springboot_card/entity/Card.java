package card.maybank.springboot_card.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CardID")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "UserID")
    @JsonIgnore
    private User user;

    @Column(name = "CardNumber") private String cardNumber;
    @Column(name = "ExpiryDate") private LocalDate expiryDate;

    protected Card() {}

    public Card(User user, String cardNumber, LocalDate expiryDate) {
        this.user = user;
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
    }

    public Integer getId() { return id; }
    public User getUser() { return user; }
    public String getCardNumber() { return cardNumber; }
    public LocalDate getExpiryDate() { return expiryDate; }
}