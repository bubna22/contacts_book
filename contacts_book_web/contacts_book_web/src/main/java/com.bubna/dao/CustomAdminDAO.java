package com.bubna.dao;

import com.bubna.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

public class CustomAdminDAO implements AdminDAO {
    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    private ApplicationContext applicationContext;

    CustomAdminDAO() {
    }

    @Transactional
    public BigInteger userCount() throws CustomException {
        Query nq = entityManager.createNativeQuery("SELECT * FROM users_count");
        return (BigInteger) nq.getSingleResult();
    }

    @Transactional
    public ArrayList<String> userContactsCount() throws CustomException {
        Query nq = entityManager.createNativeQuery("SELECT * FROM user_contacts_count");
        ArrayList<Object[]> list = (ArrayList<Object[]>) nq.getResultList();
        ArrayList<String> dataReturned = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Object[] cols = list.get(i);
            dataReturned.add(cols[0] + " - " + cols[1]);
        }
        return dataReturned;
    }

    @Transactional
    public ArrayList<String> userGroupsCount() throws CustomException {
        Query nq = entityManager.createNativeQuery("SELECT * FROM user_groups_count");
        ArrayList<Object[]> list = (ArrayList<Object[]>) nq.getResultList();
        ArrayList<String> dataReturned = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Object[] cols = list.get(i);
            dataReturned.add(cols[0] + " - " + cols[1]);
        }
        return dataReturned;
    }

    @Transactional
    public BigDecimal userAVGGroupsCount() throws CustomException {
        Query nq = entityManager.createNativeQuery("SELECT * FROM avg_users_in_group_count");
        return (BigDecimal) nq.getSingleResult();
    }

    @Transactional
    public BigDecimal userAVGContactsCount() throws CustomException {
        Query nq = entityManager.createNativeQuery("SELECT * FROM avg_contacts_by_user_count");
        return (BigDecimal) nq.getSingleResult();
    }

    @Transactional
    public ArrayList<String> inactiveUsers() throws CustomException {
        Query nq = entityManager.createNativeQuery("SELECT * FROM get_inactive_users");
        ArrayList<Object[]> list = (ArrayList<Object[]>) nq.getResultList();
        ArrayList<String> dataReturned = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Object[] cols = list.get(i);
            dataReturned.add(cols[1].toString());
        }
        return dataReturned;
    }
}
