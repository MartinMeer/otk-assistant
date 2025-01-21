package org.martinmeer.otkassistant.POJOparams.m_thread;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "nom_diams")
public class NomDiameter {

    @Id
    private String nom_diam;
    private String pitch_default;

}
