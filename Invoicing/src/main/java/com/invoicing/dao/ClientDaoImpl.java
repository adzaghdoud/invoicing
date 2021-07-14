package com.invoicing.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.invoicing.model.Client;
@Repository("ClientDao")
public class ClientDaoImpl  extends  AbstractDao implements ClientDao {

	@Override
	public Client getclientbyraisonsociale(String rs)  {
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<Client> criteria = builder.createQuery(Client.class);
		Root<Client> root = criteria.from(Client.class);
		criteria.select(root).where(builder.equal(root.get("rs"), rs));
		Query<Client> q=getSession().createQuery(criteria);
        return q.getSingleResult();
	}

	@Override
	public void addclient(Client c) {
		persist(c);
		
	}

	@Override
	public List<Client> getallclients() {
		// TODO Auto-generated method stub
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<Client> criteria = builder.createQuery(Client.class);
		Root<Client> root = criteria.from(Client.class);
		criteria.select(root);
		Query<Client> q=getSession().createQuery(criteria);
		return q.list();
		
	}

}
