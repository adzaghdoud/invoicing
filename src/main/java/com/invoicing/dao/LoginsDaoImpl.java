package com.invoicing.dao;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.invoicing.model.Client;
import com.invoicing.model.Logins;
@Repository("LoginsDao")
public class LoginsDaoImpl extends  AbstractDao implements LoginsDao{


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

	
	public Logins getinfo(String login) {
		// TODO Auto-generated method stub
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<Logins> criteria = builder.createQuery(Logins.class);
		Root<Logins> root = criteria.from(Logins.class);
		criteria.select(root).where(builder.equal(root.get("login"), login));
		Query<Logins> q=getSession().createQuery(criteria);
		return q.getSingleResult();
	}


	public void updateavatar(String login, byte[] avatar) {
		// TODO Auto-generated method stub
		    CriteriaBuilder builder = getSession().getCriteriaBuilder();    
	        CriteriaUpdate<Logins> criteriaUpdate  = builder.createCriteriaUpdate(Logins.class);
	        criteriaUpdate.from(Logins.class);
	        Root<Logins> root = criteriaUpdate.from(Logins.class);
	        criteriaUpdate.where(builder.equal(root.get("login"),login));
	        criteriaUpdate.set("avatar", avatar);
	        getSession().createQuery(criteriaUpdate).executeUpdate();
	}


	public boolean checkemail(String email) {
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<Logins> criteria = builder.createQuery(Logins.class);
		Root<Logins> root = criteria.from(Logins.class);
		criteria.select(root).where(builder.equal(root.get("email"), email));
		Query<Logins> q=getSession().createQuery(criteria);
		
        try {
        q.getSingleResult();	
        return true;	
        }catch (NoResultException e) {
        return false;	
        }
	}


	public Logins getloginbyemail(String email) {
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<Logins> criteria = builder.createQuery(Logins.class);
		Root<Logins> root = criteria.from(Logins.class);
		criteria.select(root).where(builder.equal(root.get("email"), email));
		Query<Logins> q=getSession().createQuery(criteria);
		return q.getSingleResult();
	}


	public void setresetpassword(String login,String value) {
		    CriteriaBuilder builder = getSession().getCriteriaBuilder();    
	        CriteriaUpdate<Logins> criteriaUpdate  = builder.createCriteriaUpdate(Logins.class);
	        criteriaUpdate.from(Logins.class);
	        Root<Logins> root = criteriaUpdate.from(Logins.class);
	        criteriaUpdate.where(builder.equal(root.get("login"),login));
	        criteriaUpdate.set("resetpassword", value);
	        getSession().createQuery(criteriaUpdate).executeUpdate();
	}
}
