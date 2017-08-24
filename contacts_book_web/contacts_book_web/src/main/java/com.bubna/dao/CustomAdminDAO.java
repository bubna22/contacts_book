package com.bubna.dao;

import com.bubna.exception.CustomException;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

public class CustomAdminDAO implements AdminDAO {

    private Session session;
    @Autowired
    private ApplicationContext applicationContext;

    CustomAdminDAO() {
    }

    @Override
    public void prepare() throws CustomException {
        session = (Session) applicationContext.getBean("session");
    }

    @Override
    public void close() throws CustomException {
        if (session != null && session.isOpen()) session.close();
    }

    @Transactional(value = Transactional.TxType.REQUIRED, rollbackOn = Exception.class)
    public BigInteger userCount() throws CustomException {
        NativeQuery nq = session.createSQLQuery("SELECT * FROM users_count;");
        return (BigInteger) nq.getSingleResult();
    }

    @Transactional(value = Transactional.TxType.REQUIRED, rollbackOn = Exception.class)
    public ArrayList<String> userContactsCount() throws CustomException {
        NativeQuery nq = session.createSQLQuery("SELECT * FROM user_contacts_count;");
        ArrayList<Object[]> list = (ArrayList<Object[]>) nq.getResultList();
        ArrayList<String> dataReturned = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Object[] cols = list.get(i);
            dataReturned.add(cols[0] + " - " + cols[1]);
        }
        return dataReturned;
    }

    @Transactional(value = Transactional.TxType.REQUIRED, rollbackOn = Exception.class)
    public ArrayList<String> userGroupsCount() throws CustomException {
        NativeQuery nq = session.createSQLQuery("SELECT * FROM user_groups_count;");
        ArrayList<Object[]> list = (ArrayList<Object[]>) nq.getResultList();
        ArrayList<String> dataReturned = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Object[] cols = list.get(i);
            dataReturned.add(cols[0] + " - " + cols[1]);
        }
        return dataReturned;
    }

    @Transactional(value = Transactional.TxType.REQUIRED, rollbackOn = Exception.class)
    public BigDecimal userAVGGroupsCount() throws CustomException {
        NativeQuery nq = session.createSQLQuery("SELECT * FROM avg_users_in_group_count;");
        return (BigDecimal) nq.getSingleResult();
    }

    @Transactional(value = Transactional.TxType.REQUIRED, rollbackOn = Exception.class)
    public BigDecimal userAVGContactsCount() throws CustomException {
        NativeQuery nq = session.createSQLQuery("SELECT * FROM avg_contacts_by_user_count;");
        return (BigDecimal) nq.getSingleResult();
    }

    @Transactional(value = Transactional.TxType.REQUIRED, rollbackOn = Exception.class)
    public ArrayList<String> inactiveUsers() throws CustomException {
        NativeQuery nq = session.createSQLQuery("SELECT * FROM get_inactive_users;");
        ArrayList<Object[]> list = (ArrayList<Object[]>) nq.getResultList();
        ArrayList<String> dataReturned = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Object[] cols = list.get(i);
            dataReturned.add(cols[1].toString());
        }
        return dataReturned;
    }
}
