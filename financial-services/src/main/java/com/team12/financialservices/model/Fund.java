package com.team12.financialservices.model;

import com.team12.financialservices.Validation.HtmlInjection;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.access.method.P;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Future;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity
@Data
public class Fund {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    @NotEmpty(message = "Please provide a fund name.")
//    @HtmlInjection
    @Size(min = 1, max = 100, message = "The size of the fund name must be between 1 and 100")
    @Pattern(regexp = "[a-zA-Z .,]+", message = "Please input spaces, letters, ',' or '.'")
//    @NotBlank(message = "Please input a valid name")
    private String name;

    @Column(nullable = false, unique = true)
    @NotEmpty(message = "Please provide a fund symbol.")
//    @HtmlInjection
    @Pattern(regexp = "[a-zA-Z]{1,5}", message = "Please input 1-5 letters")
    private String symbol;

    // can have only one fetch = FetchType.EAGER, others are LAZY
    @OneToMany
    private Collection<FundPriceHistory> histories;

    @OneToMany
    private Collection<Position> positions;

    @OneToMany(mappedBy = "fund")
    private Collection<Transaction> transactions;

    @Override
    public String toString() {
        return this.name;
    }

}
