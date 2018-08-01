package com.luv2code.springdemo.dao;

import com.luv2code.springdemo.entity.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class CustomerDaoImpl implements CustomerDao {

    @Autowired
    private SessionFactory sessionFactory;

    public List<Customer> getCustomers() {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<Customer> theQuery = currentSession.createQuery("from Customer order by lastName", Customer.class);
        List<Customer> customers = theQuery.getResultList();
        return customers;
    }

    public void saveCustomer(Customer customer) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(customer);
    }

    public Customer getCustomer(int id) {
        Session session = sessionFactory.getCurrentSession();
        Customer customer = session.get(Customer.class, id);
        return customer;
    }

    public void deleteCustomer(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query theQuery = session.createQuery("delete from Customer where id=:customerId");
        theQuery.setParameter("customerId", id);
        theQuery.executeUpdate();
    }

    public List<Customer> searchCustomers(String theSearchName) {
        Session session = sessionFactory.getCurrentSession();
        Query query = null;
        if (theSearchName != null && theSearchName.trim().length() > 0) {
            query = session.createQuery("from Customer where lower(firstName) " +
                    "like: theName or lower(lastName) like: theName", Customer.class);
            query.setParameter("theName", "%" + theSearchName.toLowerCase() + "%");
        } else {
            query = session.createQuery("from Customer", Customer.class);
        }
        List<Customer> customers = query.getResultList();
        return customers;
    }
}
