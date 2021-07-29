package com.invoicing.dao;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.invoicing.model.Logins;
@Repository("LoginsDao")
public class LoginsDaoImpl extends  AbstractDao implements LoginsDao{

	@Override
	public boolean checkloginpassword(String login, String password) {

		
		// TODO Auto-generated method stub
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<Logins> criteria = builder.createQuery(Logins.class);
		Root<Logins> root = criteria.from(Logins.class);
		criteria.select(root).where(builder.equal(root.get("login"), login));
		Query<Logins> q=getSession().createQuery(criteria);
        if (q.getResultList().size() != 1) {
        	return false;
        }
        
       return true;
        
	}

	@Override
	public Logins getinfo(String login) {
		// TODO Auto-generated method stub
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<Logins> criteria = builder.createQuery(Logins.class);
		Root<Logins> root = criteria.from(Logins.class);
		criteria.select(root).where(builder.equal(root.get("login"), login));
		Query<Logins> q=getSession().createQuery(criteria);
		return q.getSingleResult();
	}
}
