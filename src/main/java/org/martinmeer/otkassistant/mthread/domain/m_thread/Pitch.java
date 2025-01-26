package org.martinmeer.otkassistant.mthread.domain.m_thread;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "pitches")
public class Pitch {

    @Id
    private int pitch_id;
    private String pitch;

    @ManyToOne
    @JoinColumn(name = "nom_diameter_id")
    private NomDiameter nomDiameter;

    public Pitch(String pitch, NomDiameter nomDiameter) {
        //this.pitch = pitch;
        this.nomDiameter = nomDiameter;
    }
}
