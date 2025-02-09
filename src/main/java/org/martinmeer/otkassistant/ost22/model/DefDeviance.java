package org.martinmeer.otkassistant.ost22.model;

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
@Table(name = "def_deviances", schema = "ost22")
public class DefDeviance {
    @Id
    @Column(name = "nom_dim_range", columnDefinition = "numrange not null")
    private Object id;

    @Column(name = "hole", precision = 4, scale = 3)
    private BigDecimal hole;

    @Column(name = "shaft", precision = 4, scale = 3)
    private BigDecimal shaft;

    @Column(name = "quasi_hole", precision = 4, scale = 3)
    private BigDecimal quasiHole;

    @Column(name = "quasi_shaft", precision = 4, scale = 3)
    private BigDecimal quasiShaft;

}