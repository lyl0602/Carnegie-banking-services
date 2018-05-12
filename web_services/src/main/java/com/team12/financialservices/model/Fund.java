package com.team12.financialservices.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Fund {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String symbol;

//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateCreated;

    private Double value;

    @OneToMany(mappedBy = "fund")
    private Collection<Position> positions;

    public Fund(String name, String symbol, Date dateCreated, Double value) {
        this.name = name;
        this.symbol = symbol;
        this.dateCreated = dateCreated;
        this.value = value;
    }
}
