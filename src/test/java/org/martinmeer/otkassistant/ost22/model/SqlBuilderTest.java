package org.martinmeer.otkassistant.ost22.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.martinmeer.otkassistant.core.model.sceletal.AbstractInputData;
import org.martinmeer.otkassistant.core.service.SqlBuilder;

import static org.assertj.core.api.Assertions.assertThat;

public class SqlBuilderTest {

    private static AbstractInputData inputData;
    private static SqlBuilder sqlBuilder;
    //private static ValueDefiner devianceDefiner;
    private static String input;

    @BeforeAll
    public static void setUp() {
        input = "undef:20.5";
        inputData = new OstInputData();
        sqlBuilder = new OstSqlBuilder();
        inputData.createInputData(input);
        //devianceDefiner = new OstDefinedData((OstInputData) inputData, (OstSqlBuilder) sqlBuilder);
    }

    @Test
    public void generateSqlTest() {
        var devianceDefiner = new OstDefinedData((OstInputData) inputData, (OstSqlBuilder) sqlBuilder);
        devianceDefiner.generateSql();
        //assertThat(devianceDefiner.getSql()).isEqualTo("");
    }
}
