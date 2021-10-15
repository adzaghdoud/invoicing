package com.invoicing.dao;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.invoicing.model.Client;
import com.invoicing.model.Company;
import com.invoicing.model.Prestations;
@Repository("ClientDao")
public class ClientDaoImpl  extends  AbstractDao implements ClientDao {

	
	public Client getclientbyraisonsociale(String rs)  {
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<Client> criteria = builder.createQuery(Client.class);
		Root<Client> root = criteria.from(Client.class);
		criteria.select(root).where(builder.equal(root.get("rs"), rs));
		Query<Client> q=getSession().createQuery(criteria);
        return q.getSingleResult();
	}

	
	public void addclient(Client c) {
		persist(c);
		
	}


	public List<Client> getallclients(String ownedcompany) {
		// TODO Auto-generated method stub
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<Client> criteria = builder.createQuery(Client.class);
		Root<Client> root = criteria.from(Client.class);
		criteria.select(root).where(builder.equal(root.get("ownedcompany"), ownedcompany));
		Query<Client> q=getSession().createQuery(criteria);
		return q.list();
		
	}



	public Long numberclient(String company) {
		// TODO Auto-generated method stub
CriteriaBuilder builder = getSession().getCriteriaBuilder();
        
        CriteriaQuery<Long> Query = builder.createQuery(Long.class);
        
        Root<Client> Root = Query.from(Client.class);
        Expression<Long> countExpression = builder.count(Root);
        Query.select(countExpression).where(builder.equal(Root.get("ownedcompany"), company));
        TypedQuery<Long> typedQuery = getSession().createQuery(Query);
        Long count = typedQuery.getSingleResult();
        return count;
	}


	public Client getclientbyemail(String mail) {
		// TODO Auto-generated method stub

		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<Client> criteria = builder.createQuery(Client.class);
		Root<Client> root = criteria.from(Client.class);
		criteria.select(root).where(builder.equal(root.get("mail"), mail));
		Query<Client> q=getSession().createQuery(criteria);
        return q.getSingleResult();
	}


	public Client getclientbyemailandraisonsociale(String rs, String mail) {
		// TODO Auto-generated method stub
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<Client> criteria = builder.createQuery(Client.class);
		Query<Client> q=getSession().createQuery(criteria);
        return q.getSingleResult();
	}


	public void updateclient(Client c) {
		// TODO Auto-generated method stub
	    CriteriaBuilder builder = getSession().getCriteriaBuilder();    
        CriteriaUpdate<Client> criteriaUpdate  = builder.createCriteriaUpdate(Client.class);
        criteriaUpdate.from(Client.class);
        Root<Client> root = criteriaUpdate.from(Client.class);
        criteriaUpdate.set("adresse",c.getAdresse());
        criteriaUpdate.set("cp",c.getCp());
        criteriaUpdate.set("ville",c.getVille());
        criteriaUpdate.set("telephone",c.getTelephone());
        criteriaUpdate.set("mail",c.getMail());
        criteriaUpdate.where(builder.equal(root.get("rs"),c.getRs()));
        getSession().createQuery(criteriaUpdate).executeUpdate();
	}

}
