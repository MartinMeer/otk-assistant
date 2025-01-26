package org.martinmeer.otkassistant;/*
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.martinmeer.io.InputConverter;
//import org.martinmeer.params.Deviation;
import org.martinmeer.params.GostValidator;
import org.martinmeer.params.ParamMap;
import org.martinmeer.params.NominalSize;
import org.martinmeer.params.Pitch;
import org.martinmeer.utils.Namespace;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MainTest {


    private static List<String> inputEn = List.of(
            "  М33хРh3P1.5- 7e6e - LH", //33, d2=, es=-0.067, ei_d2=-0.257, ei_d=-0.303
            "M33x1.5-6e",
            "M8-LH",
            "M33-LH"
    );
    private static GostValidator gostValidator;
    private static ParamMap paramMap;
    private static InputConverter inputConverter;
    private static ClassPathXmlApplicationContext context =
            new ClassPathXmlApplicationContext("applicationContext.xml");

    @BeforeAll
    public static void setUp() throws SQLException, IOException {
        String input = inputEn.get(0);
        inputConverter = new InputConverter();
        //inputConverter.setInput(input);
        inputConverter = context.getBean("inputConverter", InputConverter.class);
        gostValidator = new GostValidator();
        //gostValidator = context.getBean("GostValidator", GostValidator.class);
        paramMap = new ParamMap(inputConverter.generateInputMap());
    }

    @Test
    public void testGostValidator() throws SQLException, IOException {
        var isNotValid = gostValidator.validate(inputConverter.generateInputMap());
        assertThat(isNotValid).isEqualTo(false);
    }

    @Test
    public void testNominalSize() {
        NominalSize nominalSize = new NominalSize(paramMap.getParameter(Namespace.NOMINAL_SIZE));
        assertThat(nominalSize.getNominalSize()).isEqualTo("33");
    }

    @Test
    public void testPitch() throws SQLException, IOException {
        NominalSize nominalSize = new NominalSize(paramMap.getParameter(Namespace.NOMINAL_SIZE));
        Pitch pitch = new Pitch(nominalSize, paramMap.getParameter(Namespace.PITCH));
        assertThat(pitch.getValue()).isEqualTo("1.5");
    }

    */
/*@Test
    public void testDeviation_d2() throws SQLException, IOException {
        Pitch pitch = new Pitch(inputMap.getPitch());
        Deviation deviation = new Deviation(inputMap.get(ParamNames.TOLERANCE_ZONE), pitch);
        deviation.generateValues();
        assertThat(deviation.getPitchDiamDeviance()).isEqualTo(50);
    }
    /*@Test
    public void testDeviation_d() throws SQLException, IOException {
        Pitch pitch = new Pitch(inputMap.get(ParamNames.PITCH));
        Deviation deviation = new Deviation(inputMap.get(ParamNames.TOLERANCE_ZONE), pitch);
        deviation.generateValues();
        assertThat(deviation.getMajorDiamDeviance()).isEqualTo(50);
    }

    public static String indices(List<Double> pitches, List<Double> givenPitches) {
        //List<Integer> indices = new ArrayList<>();
        StringBuilder sb = new StringBuilder("(");
        givenPitches.forEach(e -> sb.append(pitches.indexOf(e) + 1).append("), ("));
        return sb.toString();
    }*//*


}
*/
