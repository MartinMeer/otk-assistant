/*
package org.martinmeer.otkassistant.ost22.domain.db;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.martinmeer.otkassistant.core.DataMap;
import org.martinmeer.otkassistant.ost22.domain.OstInputMap;
import org.martinmeer.otkassistant.ost22.repo.UndefinedDeviances;

import java.math.BigDecimal;
import java.util.Map;

public class OstGetFromDb {

    private Session session;
    private OstInputMap ostInputMap;
    private String fromColumn;
    private String whereColumn;
    private BigDecimal inputValue;

*/
/**поднимает сессию
 * (где делать выбор схемы)?*//*

    private void sessionUp() {
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(UndefinedDeviances.class)
                .buildSessionFactory();
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
    }
*/
/**выбирает нужную таблицу, получает значение по запросу из базы*//*

    private BigDecimal getValueFromDb(DataMap inputMap) {
        Map dataMap = ostInputMap.getDataMap();

        //здесь реализовать выбор таблицы WHERE по undef-ключу
        fromColumn;
        whereColumn;
        inputValue;
        return getValueFromDB(UndefinedDeviances.class, fromColumn, whereColumn, inputValue);

    }
*/
/**строит запрос SELECT*//*

    private <T> BigDecimal getValueFromDB(Class<T> entity, String fromColumn, String whereColumn, BigDecimal inputValue) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<BigDecimal> criteriaQuery = criteriaBuilder.createQuery(BigDecimal.class);
        Root<T> root = criteriaQuery.from(entity);
        criteriaQuery
                .select(root.get(fromColumn))
                .where(criteriaBuilder.equal(root.get(whereColumn), inputValue));
        try {
            TypedQuery<BigDecimal> typedQuery = session.createQuery(criteriaQuery);
            return typedQuery.getSingleResult();

        } catch (NoResultException e) {
            return null;
        }
    }
}
*/
