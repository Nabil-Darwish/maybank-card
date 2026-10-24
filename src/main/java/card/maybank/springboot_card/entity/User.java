package card.maybank.springboot_card.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID")
    private Integer id;

    @Column(name = "FirstName") private String firstName;
    @Column(name = "LastName") private String lastName;
    @Column(name = "DateOfBirth") private LocalDate dateOfBirth;
    @Column(name = "Address1") private String address1;
    @Column(name = "Address2") private String address2;
    @Column(name = "Postcode") private String postcode;
    @Column(name = "AnnualIncome") private BigDecimal annualIncome;
    @Column(name = "Citizen") private boolean citizen;
    @Column(name = "ContactNumber") private String contactNumber;
    @Column(name = "ICNumber") private String icNumber;

    protected User() {}

    public User(String firstName, String lastName, LocalDate dateOfBirth, String address1,
                String address2, String postcode, BigDecimal annualIncome,
                boolean citizen, String contactNumber, String icNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.address1 = address1;
        this.address2 = address2;
        this.postcode = postcode;
        this.annualIncome = annualIncome;
        this.citizen = citizen;
        this.contactNumber = contactNumber;
        this.icNumber = icNumber;
    }

    public Integer getAge() { // computed, not stored
        return dateOfBirth == null ? null : Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    // getters for every field
    public Integer getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public String getAddress1() { return address1; }
    public String getAddress2() { return address2; }
    public String getPostcode() { return postcode; }
    public BigDecimal getAnnualIncome() { return annualIncome; }
    public boolean isCitizen() { return citizen; }
    public String getContactNumber() { return contactNumber; }
    public String getIcNumber() { return icNumber; }

    // setters for every field
    public void setAnnualIncome(BigDecimal annualIncome) {
        this.annualIncome = annualIncome;
    }
}