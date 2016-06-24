package main.model;
import org.codehaus.jackson.map.annotate.JsonView;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "Customer", uniqueConstraints = {@UniqueConstraint(columnNames = "ID")})
public class Customer implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;

    @Column(name = "EMAIL", unique = false, nullable = false, length = 100)
    @JsonView(Views.Customer.class)
    private String email;


    @Column(name = "FIRST_NAME", unique = false, nullable = false, length = 100)
    @JsonView(Views.Customer.class)
    private String firstName;

    @Column(name = "LAST_NAME", unique = false, nullable = false, length = 100)
    @JsonView(Views.Customer.class)
    private String lastName;

    @Column(name = "PHONE", unique = false, length = 100)
    @JsonView(Views.Customer.class)
    private String phone;

    @Column(name = "CITY", unique = false, nullable = false, length = 30)
    @JsonView(Views.Customer.class)
    private String city;

    @Column(name = "POSTAL_CODE", unique = false, nullable = false, length = 30)
    @JsonView(Views.Customer.class)
    private String postalCode;

    @Column(name = "STREET_NAME", unique = false, nullable = false, length = 30)
    @JsonView(Views.Customer.class)
    private String streetName;

    @Column(name = "HOUSE_NUMBER", unique = false, nullable = false, length = 10)
    @JsonView(Views.Customer.class)
    private String houseNumber;

    @Column(name = "DATE_OF_BIRTH", unique = false)
    @JsonView(Views.Customer.class)
    private Timestamp dateOfBirth;

    @Column(name = "INSURANCE_ID", unique = true, nullable = false)
    @JsonView(Views.Customer.class)
    private Integer insuranceID;

    @Column(name = "PERSONAL_ID", unique = true, nullable = false, length = 100)
    @JsonView(Views.Customer.class)
    private String personalID;

    @Column(name = "FRAUD_HINT", columnDefinition = "TINYINT(1)")
    @JsonView(Views.Customer.class)
    private Boolean fraudHint;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public Timestamp getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Timestamp dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Integer getInsuranceID() {
        return insuranceID;
    }

    public void setInsuranceID(Integer insuranceID) {
        this.insuranceID = insuranceID;
    }

    public String getPersonalID() {
        return personalID;
    }

    public void setPersonalID(String personalID) {
        this.personalID = personalID;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Boolean getFraudHint() {
        return fraudHint;
    }

    public void setFraudHint(Boolean fraudHint) {
        this.fraudHint = fraudHint;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", city='" + city + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", streetName='" + streetName + '\'' +
                ", houseNumber='" + houseNumber + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", insuranceID=" + insuranceID +
                ", personalID='" + personalID + '\'' +
                ", fraudHint=" + fraudHint +
                '}';
    }
}
