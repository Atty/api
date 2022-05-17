package bank.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bank_accounts")
@Getter
@Setter
@NoArgsConstructor
public class BankAccounts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Version
    private long version;

    @Column(name = "number")
    private String number;

    @Column(name = "balance")
    private int balance;

    @ManyToOne
    @JoinColumn(name = "clients_id")
    @JsonIgnore
    private Clients clients;

    @OneToMany(
            mappedBy = "bankAccounts",
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    @JsonIgnore
    private List<Cards> cardsList;

    public BankAccounts(String number) {
        this.number  = number;
        this.balance = 0;
    }

    public BankAccounts(String number, int balance) {
        this.number  = number;
        this.balance = balance;
    }

    public void addCards(Cards card) {
        if (cardsList == null) cardsList = new ArrayList<>();
        cardsList.add(card);
        card.setBankAccounts(this);
    }

    public void removeCards(Cards card) {
        cardsList.remove(card);
    }

    public void addMoney(int value) {
        balance += value;
    }

    @JsonIgnore
    public List<Cards> getCardsList() {
        return cardsList;
    }

    @JsonIgnore
    public Clients getClients() {
        return clients;
    }

    @Override
    public String toString() {
        return "BankAccounts{" +
                "number='" + number + '\'' +
                ", balance=" + balance +
                '}';
    }
}
