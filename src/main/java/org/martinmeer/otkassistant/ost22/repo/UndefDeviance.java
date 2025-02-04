package org.martinmeer.otkassistant.ost22.repo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "undef_deviances", schema = "ost22")
public class UndefDeviance {
    @Id
    @Column(name = "dim_range", columnDefinition = "numrange not null")
    private Object id;

    @Column(name = "deviance", precision = 4, scale = 3)
    private BigDecimal deviance;

}