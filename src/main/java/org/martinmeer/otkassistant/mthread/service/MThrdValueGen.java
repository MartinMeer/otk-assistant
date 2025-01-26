package org.martinmeer.otkassistant.mthread.service;

import lombok.Getter;

import lombok.Setter;
import org.martinmeer.otkassistant.core.ValueGenerator;
import org.martinmeer.otkassistant.mthread.domain.m_thread.NomDiameter;
import org.martinmeer.otkassistant.mthread.domain.m_thread.Pitch;

@Getter
@Setter
public class MThrdValueGen extends BasicValueGen implements ValueGenerator {


    private NomDiameter nomDiameter;
    private Pitch pitch;
    /*private TreadDirection treadDirection;
    private Multitstart multistart;
    private Deviation deviation;
    private PitchDiameter pitchDiameter;
    private UpperTolerance upperTolerance;
    private LowerTolerance lowerTolerance;
    private UpperMeasuringValue upperMeasuringValue;
    private LowerMeasuringValue lowerMeasuringValue;*/


}
