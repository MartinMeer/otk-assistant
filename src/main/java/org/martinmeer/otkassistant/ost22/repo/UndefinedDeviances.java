package org.martinmeer.otkassistant.ost22.repo;

import jakarta.persistence.Entity;
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
    private BigDecimal dimRange;
    private BigDecimal deviance;    
}
