package org.martinmeer.otkassistant;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.martinmeer.otkassistant.mthread.domain.m_thread.NomDiameter;
import org.martinmeer.otkassistant.mthread.domain.m_thread.Pitch;
import org.martinmeer.otkassistant.mthread.service.Gen;
import org.martinmeer.otkassistant.mthread.domain.MThrdNSpace;

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
        Map<MThrdNSpace, String> dataMap = new HashMap<>();
                dataMap.put(MThrdNSpace.NOM_DIAMETER, "8.8");
                dataMap.put(MThrdNSpace.PITCH, null);
        Gen gen = new Gen(dataMap);
        Map valueMap = gen.generateValueMap();

        assertThat(valueMap.get(MThrdNSpace.NOM_DIAMETER)).isEqualTo(null);

    }



}
