package bank.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "clients")
public class Clients {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Version
    private long version;

    @Column(name = "name")
    private String name;

    @OneToMany(
            mappedBy = "clients",
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    @JsonIgnore
    private List<BankAccounts> bankAccountsList;

    public Clients(String name) {
        this.name = name;
    }

    public void addBankAccounts(BankAccounts bankAccount) {
        if (bankAccountsList == null) bankAccountsList = new ArrayList<>();
        bankAccountsList.add(bankAccount);
        bankAccount.setClients(this);
    }

    public void removeBankAccount(BankAccounts bankAccount) {
        bankAccountsList.remove(bankAccount);
    }

    @JsonIgnore
    public List<BankAccounts> getBankAccountsList() {
        return bankAccountsList;
    }

    @Override
    public String toString() {
        return "Clients{" +
                "name='" + name + '\'' +
                '}';
    }
}
