package bank.api.entities;


import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.text.DecimalFormat;

@Entity
@Table(name = "cards")
@Getter
@NoArgsConstructor
public class Cards {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Version
    private long version;

    @Setter
    @Column(name = "number")
    private long number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_bank_account")
    @Setter
    @JsonIgnore
    private BankAccounts bankAccounts;

    @JsonGetter("number")
    public String getJsonNumber() {
        DecimalFormat df = new DecimalFormat("0000,0000,0000,0000");
        return df.format(number);
    }

    @JsonSetter("number")
    public void setJsonNumber(String number) {
        this.number = Long.parseLong(number.replaceAll(String.valueOf(number.toCharArray()[4]), ""));
    }

    public Cards(long number) {
        this.number = number;
    }

    @JsonIgnore
    public BankAccounts getBankAccounts() {
        return bankAccounts;
    }

    @Override
    public String toString() {
        return "Cards{" +
                "number='" + number + '\'' +
                '}';
    }
}
