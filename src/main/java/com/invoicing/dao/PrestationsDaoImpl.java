package com.invoicing.dao;

import java.time.LocalDate;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.query.Query;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Repository;
import com.invoicing.context.AppConfig;
import com.invoicing.model.Article;
import com.invoicing.model.Prestations;
import com.invoicing.model.Transaction;
import com.invoicing.service.CompanyService;

@Repository("PrestationsDao")
public class PrestationsDaoImpl extends  AbstractDao implements PrestationsDao{


	public void addprestation(Prestations p) {
		// TODO Auto-generated method stub
		persist(p);
	}

	
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


	public List<Prestations> getlistprestations(String company) {
		// TODO Auto-generated method stub
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<Prestations> criteria = builder.createQuery(Prestations.class);
		Root<Prestations> root = criteria.from(Prestations.class);
		criteria.select(root).where(builder.equal(root.get("company"), company));;
		criteria.orderBy(builder.desc(root.get("id")));
		Query<Prestations> q=getSession().createQuery(criteria);
		return q.list();
	}


	public Prestations getperstationbynumfacture(String numfacture,String company) {
		// TODO Auto-generated method stub
		
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<Prestations> criteria = builder.createQuery(Prestations.class);
		Root<Prestations> root = criteria.from(Prestations.class);
		Predicate cond = null;
		criteria.select(root).where(builder.equal(root.get("numfacture"), numfacture));
		cond = builder.and(builder.equal(root.get("numfacture"), numfacture), builder.equal(root.get("company"), company));
		Query<Prestations> q=getSession().createQuery(criteria.select(root).where(cond));
        return q.getSingleResult();
	}


	public List<Prestations> getpendingpaiement() {
		// TODO Auto-generated method stub
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<Prestations> criteria = builder.createQuery(Prestations.class);
		Root<Prestations> root = criteria.from(Prestations.class);
		criteria.select(root).where(builder.equal(root.get("statut_paiement"), "en attente"));
		Query<Prestations> q=getSession().createQuery(criteria);
        return q.getResultList();
	}

	public void validate_paiement(String numfacture) {
		
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
        
        CriteriaUpdate<Prestations> criteriaUpdate  = builder.createCriteriaUpdate(Prestations.class);
        criteriaUpdate.from(Prestations.class);
        Root<Prestations> root = criteriaUpdate.from(Prestations.class);
        criteriaUpdate.set("statut_paiement","validé");
        criteriaUpdate.where(builder.equal(root.get("numfacture"),numfacture));
        getSession().createQuery(criteriaUpdate).executeUpdate();

   
		
	}



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

	
	public long number_paiement_validate() {
		// TODO Auto-generated method stub
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<Long> Query = builder.createQuery(Long.class);
        Root<Prestations> Root = Query.from(Prestations.class);
        Expression<Long> countExpression = builder.count(Root);
        Query.select(countExpression);
        Query.where(builder.equal(Root.get("statut_paiement"),"validé"));
        TypedQuery<Long> typedQuery = getSession().createQuery(Query);
        Long count = typedQuery.getSingleResult();
        return count;
	}


	public List<Prestations> getlistprestationsbyyear(String company) {
		javax.persistence.Query query = getSession().createNamedQuery("search_prestations", Prestations.class);
		LocalDate todaydate = LocalDate.now();
		int currentyear = todaydate.getYear();
		query.setParameter(1, currentyear+"-01-01");
		query.setParameter(2, currentyear+"-12-31"+" 23:59:59");
		query.setParameter(3, company);
	
		@SuppressWarnings("unchecked")
		List<Prestations> list_prestations = query.getResultList(); 
	    return list_prestations;
	}


	public List<Prestations> getlistprestations_until_date_cloture(String company, String datecloture) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		CompanyService srvcompany = (CompanyService) context.getBean("CompanyService");
		javax.persistence.Query query = getSession().createNamedQuery("search_prestations", Prestations.class);
		LocalDate todaydate = LocalDate.now();
		int currentyear = todaydate.getYear();
		query.setParameter(1, currentyear+"-01-01");
		query.setParameter(2, srvcompany.getcompanybyraison(company).getDate_cloture_comptable().toString().substring(0,9)+" 23:59:59");
		query.setParameter(3, company);
		@SuppressWarnings("unchecked")
		List<Prestations> list_prestations = query.getResultList(); 
		context.close();
	    return list_prestations;
	}


	public Prestations getperstationbynomfacture(String nomfacture, String company) {
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<Prestations> criteria = builder.createQuery(Prestations.class);
		Root<Prestations> root = criteria.from(Prestations.class);
		Predicate cond = null;
		criteria.select(root).where(builder.equal(root.get("nomfacture"), nomfacture));
		cond = builder.and(builder.equal(root.get("nomfacture"), nomfacture), builder.equal(root.get("company"), company));
		Query<Prestations> q=getSession().createQuery(criteria.select(root).where(cond));
        return q.getSingleResult();
	}


	@Override
	public void DeletePrestation(String invoicename) {
		javax.persistence.Query queryreset = getSession().createNamedQuery("reset_auto_increment", Prestations.class);
		CriteriaBuilder criteriaBuilder  = getSession().getCriteriaBuilder();
		CriteriaDelete<Prestations> query = criteriaBuilder.createCriteriaDelete(Prestations.class);
		//get last auto increment
		Long lastid=getlast_id_prestation();
		Root<Prestations> root = query.from(Prestations.class);
		query.where(criteriaBuilder.equal(root.get("nomfacture"),invoicename));
		getSession().createQuery(query).executeUpdate();
		//reset auto increment after delete
		queryreset.setParameter(1, lastid-1);
		queryreset.executeUpdate();
	}


	@Override
	public void UpdatePrestation(Prestations p ) {		
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaUpdate<Prestations> criteriaUpdate  = builder.createCriteriaUpdate(Prestations.class);
        criteriaUpdate.from(Prestations.class);
        Root<Prestations> root = criteriaUpdate.from(Prestations.class);
        criteriaUpdate.set("statut_paiement",p.getStatut_paiement()); 
        criteriaUpdate.set("quantite",p.getQuantite());
        criteriaUpdate.set("montantHT",p.getMontantHT());
        criteriaUpdate.set("taxe",p.getTaxe());
        criteriaUpdate.set("valtaxe",p.getValtaxe());
        criteriaUpdate.set("montantTTC",p.getMontantTTC());
        criteriaUpdate.set("totalttc",p.getTotalttc());
        criteriaUpdate.where(builder.equal(root.get("nomfacture"),p.getNomfacture()),builder.equal(root.get("company"),p.getCompany()));	    
        getSession().createQuery(criteriaUpdate).executeUpdate();
	}


		
		
	
	
	
	
		
		
	}
	

