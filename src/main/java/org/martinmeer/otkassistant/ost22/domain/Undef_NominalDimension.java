package org.martinmeer.otkassistant.ost22.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.martinmeer.otkassistant.core.DataMap;
import org.martinmeer.otkassistant.core.GetValueFromDB;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
@Entity
@Table(schema = "ost22", name = "unspec_deviances")
public class Undef_NominalDimension implements GetValueFromDB<BigDecimal> {

    @Id
    @Column(name = "dim_range")
    private BigDecimal dimRange;
    @Column(name = "deviance")
    private BigDecimal deviance;

    private DataMap<OstNSpace> dataMap;

    private BigDecimal nominalDimension;
    private Map<String, String> outputMap;

    @Override
    public BigDecimal getValueFromDb() {


        return null;
    }
}

