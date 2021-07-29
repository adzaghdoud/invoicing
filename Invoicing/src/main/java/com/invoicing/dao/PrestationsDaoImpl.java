package com.invoicing.dao;

import java.util.HashMap;
import java.util.List;


import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import com.invoicing.model.Prestations;

@Repository("PrestationsDao")
public class PrestationsDaoImpl extends  AbstractDao implements PrestationsDao{

	@Override
	public void addprestation(Prestations p) {
		// TODO Auto-generated method stub
		persist(p);
	}

	@Override
	public Long getlast_id_prestation() {
		// TODO Auto-generated method stub
		       
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
        
        CriteriaQuery<Long> Query = builder.createQuery(Long.class);
        
        Root<Prestations> Root = Query.from(Prestations.class);
        Expression<Long> countExpression = builder.count(Root);
      
        Query.select(countExpression);
        TypedQuery<Long> typedQuery = getSession().createQuery(Query);
        Long count = typedQuery.getSingleResult();
        
        return count;
	}

	@Override
	public List<Prestations> getlistprestations() {
		// TODO Auto-generated method stub
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<Prestations> criteria = builder.createQuery(Prestations.class);
		Root<Prestations> root = criteria.from(Prestations.class);
		criteria.select(root);
		criteria.orderBy(builder.desc(root.get("id")));
		Query<Prestations> q=getSession().createQuery(criteria);
		return q.list();
	}

	@Override
	public Prestations getperstationbynomfacture(String nomfacture) {
		// TODO Auto-generated method stub
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<Prestations> criteria = builder.createQuery(Prestations.class);
		Root<Prestations> root = criteria.from(Prestations.class);
		criteria.select(root).where(builder.equal(root.get("nomfacture"), nomfacture));
		Query<Prestations> q=getSession().createQuery(criteria);
        return q.getSingleResult();
	}

	@Override
	public List<Prestations> getpendingpaiement() {
		// TODO Auto-generated method stub
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<Prestations> criteria = builder.createQuery(Prestations.class);
		Root<Prestations> root = criteria.from(Prestations.class);
		criteria.select(root).where(builder.equal(root.get("statut_paiement"), "en attente"));
		Query<Prestations> q=getSession().createQuery(criteria);
        return q.getResultList();
	}

	@Override
	public void validate_paiement(String numfacture) {
		
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
        
        CriteriaUpdate<Prestations> criteriaUpdate  = builder.createCriteriaUpdate(Prestations.class);
        criteriaUpdate.from(Prestations.class);
        Root<Prestations> root = criteriaUpdate.from(Prestations.class);
        criteriaUpdate.set("statut_paiement","valid�");
        criteriaUpdate.where(builder.equal(root.get("numfacture"),numfacture));
        getSession().createQuery(criteriaUpdate).executeUpdate();

   
		
	}

	@Override
	public double chiffre_affaire() {
		// TODO Auto-generated method stub
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<Double> query = builder.createQuery(Double.class);
		Root<Prestations> root = query.from(Prestations.class);
		query.select(builder.sum(root.get("totalttc")));
		TypedQuery<Double> typedQuery = getSession().createQuery(query);
	    Double sum = typedQuery.getSingleResult();
	      
		  return sum;
        
     
	}

	@Override
	public long number_paiement_to_validate() {
		// TODO Auto-generated method stub

	    CriteriaBuilder builder = getSession().getCriteriaBuilder();
        
        CriteriaQuery<Long> Query = builder.createQuery(Long.class);
        
        Root<Prestations> Root = Query.from(Prestations.class);
        Expression<Long> countExpression = builder.count(Root);
        Query.select(countExpression);
        Query.where(builder.equal(Root.get("statut_paiement"),"en attente"));
        TypedQuery<Long> typedQuery = getSession().createQuery(Query);
        Long count = typedQuery.getSingleResult();
        
        return count;
	}

	@Override
	public long number_paiement_validate() {
		// TODO Auto-generated method stub
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<Long> Query = builder.createQuery(Long.class);
        Root<Prestations> Root = Query.from(Prestations.class);
        Expression<Long> countExpression = builder.count(Root);
        Query.select(countExpression);
        Query.where(builder.equal(Root.get("statut_paiement"),"valid�"));
        TypedQuery<Long> typedQuery = getSession().createQuery(Query);
        Long count = typedQuery.getSingleResult();
        return count;
	}


		
		
	
	
	
	
		
		
	}
	

