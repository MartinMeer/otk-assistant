package org.martinmeer.otkassistant.mthread.repo;

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
@Table(name = "nom_diams", schema = "thread_m")
public class NomDiam {
    @Id
    @Column(name = "nom_diam", nullable = false, precision = 6, scale = 3)
    private BigDecimal id;

    @Column(name = "pitch_default", precision = 6, scale = 3)
    private BigDecimal pitchDefault;

}