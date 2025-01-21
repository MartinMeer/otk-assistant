package org.martinmeer.otkassistant;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.martinmeer.otkassistant.POJOparams.m_thread.NomDiameter;
import org.martinmeer.otkassistant.POJOparams.m_thread.Pitch;
import org.martinmeer.otkassistant.engine.Gen;
import org.martinmeer.otkassistant.utils.Namespace;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class EntitiesTest {

    private static SessionFactory sessionFactory;
    private static Session session;
    private static NomDiameter nomDiameter;
    private static Pitch pitch;

    @BeforeAll
    static void setUp() {
        /*sessionFactory = new Configuration()
                .configure()
                .addAnnotatedClass(NomDiameter.class)
                .addAnnotatedClass(Pitch.class)
                .buildSessionFactory();*/
    }

    @Test
    public void getValue() {
        Map<Namespace, String> dataMap = new HashMap<>();
                dataMap.put(Namespace.NOM_DIAMETER, "8.8");
                dataMap.put(Namespace.PITCH, null);
        Gen gen = new Gen(dataMap);
        Map valueMap = gen.generateValueMap();

        assertThat(valueMap.get(Namespace.NOM_DIAMETER)).isEqualTo(null);

    }



}
