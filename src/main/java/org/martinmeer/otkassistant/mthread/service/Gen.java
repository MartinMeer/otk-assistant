package org.martinmeer.otkassistant.mthread.service;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.martinmeer.otkassistant.mthread.domain.DataMap;
import org.martinmeer.otkassistant.mthread.domain.m_thread.NomDiameter;
import org.martinmeer.otkassistant.mthread.domain.m_thread.Pitch;
import org.martinmeer.otkassistant.mthread.domain.MThrdNSpace;

import java.util.HashMap;
import java.util.Map;

public class Gen {



    private DataMap dataMapClass;
    private Map<MThrdNSpace, String> dataMap;
    private SessionFactory sessionFactory;
    private Session session;
    private NomDiameter nomDiameter;
    private Pitch pitch;

    public Gen(Map<MThrdNSpace, String> dataMap) {
        this.dataMap = dataMap;
    }
    public Map<MThrdNSpace, String> generateValueMap() {
        sessionUp();
        Map<MThrdNSpace, String> valueMap = new HashMap<>();
        valueMap.put(MThrdNSpace.NOM_DIAMETER, getNomDiameterValue());
        if (session != null && session.isOpen()) {
            session.getTransaction().commit();
        }
        return valueMap;
    }

    private void sessionUp() {
        sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(NomDiameter.class)
                .addAnnotatedClass(Pitch.class)
                .buildSessionFactory();
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
    }

    private String getNomDiameterValue() {
        String fromColumn = "nom_diam";
        String whereColumn = "nom_diam";
        String inputValue = dataMap.get(MThrdNSpace.NOM_DIAMETER);
        return getValueFromDB(NomDiameter.class, fromColumn, whereColumn, inputValue);

    }

    private <T> String getValueFromDB(Class<T> entity, String fromColumn, String whereColumn, String inputValue) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<String> criteriaQuery = criteriaBuilder.createQuery(String.class);
        Root<T> root = criteriaQuery.from(entity);

        criteriaQuery.select(root.get(fromColumn))
                .where(criteriaBuilder.equal(root.get(whereColumn), inputValue));
        try {
            TypedQuery<String> typedQuery = session.createQuery(criteriaQuery);
            return typedQuery.getSingleResult();

        } catch (NoResultException e) {
            return null;
        }
    }




}
