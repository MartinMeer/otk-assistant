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
@Table(name = "nom_diam_tolerances", schema = "thread_m")
public class NomDiamTolerance {
    @Id
    @Column(name = "tolerance_number", nullable = false)
    private Integer id;

/*
 TODO [Reverse Engineering] create field to map the 'nom_diam_tolerance' column
 Available actions: Define target Java type | Uncomment as is | Remove column mapping
    @Column(name = "nom_diam_tolerance", columnDefinition = "numeric [](5, 3)")
    private List nomDiamTolerance;
*/
}