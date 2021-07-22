package com.invoicing.dao;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.invoicing.model.Client;
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


	public List<Client> getallclients() {
		// TODO Auto-generated method stub
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<Client> criteria = builder.createQuery(Client.class);
		Root<Client> root = criteria.from(Client.class);
		criteria.select(root);
		Query<Client> q=getSession().createQuery(criteria);
		return q.list();
		
	}


	@Override
	public Long numberclient() {
		// TODO Auto-generated method stub
CriteriaBuilder builder = getSession().getCriteriaBuilder();
        
        CriteriaQuery<Long> Query = builder.createQuery(Long.class);
        
        Root<Client> Root = Query.from(Client.class);
        Expression<Long> countExpression = builder.count(Root);
        Query.select(countExpression);
        TypedQuery<Long> typedQuery = getSession().createQuery(Query);
        Long count = typedQuery.getSingleResult();
        return count;
	}

}
