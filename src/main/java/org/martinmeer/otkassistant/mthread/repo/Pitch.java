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
@Table(name = "pitches", schema = "thread_m")
public class Pitch {
    @Id
    @Column(name = "pitch_id", nullable = false)
    private Integer id;

    @Column(name = "pitch", precision = 6, scale = 3)
    private BigDecimal pitch;

}