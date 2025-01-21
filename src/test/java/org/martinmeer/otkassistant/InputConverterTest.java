package org.martinmeer.otkassistant;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.martinmeer.otkassistant.io.InputConverter;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class InputConverterTest {

    private static InputConverter inputConverter;
    private static String inputEn;
    private static String inputRu;
    private static String inputEnStar;
    private static String inputRuStar;
    private static ClassPathXmlApplicationContext context;


    @BeforeAll
    public static void setUp() {
        inputEn = "M33x2-6e";
        inputRu = "  М2.25хРh3P0,45- 6е6G - LH";
        inputEnStar = "M33";
        inputRuStar = "М33*2-6е";
        context = new ClassPathXmlApplicationContext("applicationContext.xml");
    }
    @Test
    public void testInputMap() {

        context.getBean("threadInputConv");
        context.getBean("dataMap");



       /* assertThat(dataMap.getParameter(Namespace.NOM_DIAMETER)).isEqualTo("2.25");
        assertThat(dataMap.getParameter(Namespace.MULTISTART_TREAD)).isEqualTo("многозаходная резьба"); //"многозаходная резьба"
        assertThat(dataMap.getParameter(Namespace.PITCH)).isEqualTo("0.45");
        assertThat(dataMap.getParameter(Namespace.TOLERANCE_ZONE)).isEqualTo("6e6g");
        assertThat(dataMap.getParameter(Namespace.DIRECTION)).isEqualTo("левая резьба"); //"левая резьба"*/
    }

}