package org.martinmeer.otkassistant.mthread.repo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pitch_diam_tolerances_tables", schema = "thread_m")
public class PitchDiamTolerancesTable {
    @Id
    @Column(name = "pdt_table", nullable = false, length = Integer.MAX_VALUE)
    private String pdtTable;

/*
 TODO [Reverse Engineering] create field to map the 'nom_diams_range' column
 Available actions: Define target Java type | Uncomment as is | Remove column mapping
    @Column(name = "nom_diams_range", columnDefinition = "numeric [](6, 3)")
    private Object nomDiamsRange;
*/
}