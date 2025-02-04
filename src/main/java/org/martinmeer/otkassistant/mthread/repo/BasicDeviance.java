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
@Table(name = "basic_deviances", schema = "thread_m")
public class BasicDeviance {
    @Id
    @Column(name = "deviance", nullable = false, length = Integer.MAX_VALUE)
    private String deviance;

    @Column(name = "devi_arr", precision = 4, scale = 3)
    private BigDecimal deviArr;

}