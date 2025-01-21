package org.martinmeer.otkassistant.engine;

import lombok.Getter;

import lombok.Setter;
import org.martinmeer.otkassistant.POJOparams.m_thread.NomDiameter;
import org.martinmeer.otkassistant.POJOparams.m_thread.Pitch;

@Getter
@Setter
public class ThreadValueGen extends BasicValueGen implements ValueGenerator{


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
