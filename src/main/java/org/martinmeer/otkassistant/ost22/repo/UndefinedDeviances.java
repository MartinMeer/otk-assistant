package org.martinmeer.otkassistant.ost22.repo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(schema = "ost22", name = "unspec_deviances")
public class UndefinedDeviances implements Deviance {

    @Id
    @Column(name = "dim_range")
    private BigDecimal dimRange;
    @Column(name = "deviance")
    private BigDecimal deviance;
}
