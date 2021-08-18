package com.invoicing.dao;

import java.util.List;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.invoicing.model.Article;
@Repository("ArticleDao")
public class ArticlesDaoImpl extends  AbstractDao implements ArticlesDao {

	
	public List<Article> getlistarticles() {
		// TODO Auto-generated method stub
		
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<Article> criteria = builder.createQuery(Article.class);
		Root<Article> root = criteria.from(Article.class);
		criteria.select(root);
		Query<Article> q=getSession().createQuery(criteria);		
		return q.list();
	}

	
	public Article getarticlebydesignation(String designation) {
		// TODO Auto-generated method stub
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<Article> criteria = builder.createQuery(Article.class);
		Root<Article> root = criteria.from(Article.class);
		criteria.select(root).where(builder.equal(root.get("designation"), designation));
		Query<Article> q=getSession().createQuery(criteria);
        return q.getSingleResult();
	}



	public void addarticle(Article a) {
		persist(a);
		
	}


	public void updatearticle(Article a) {
		    CriteriaBuilder builder = getSession().getCriteriaBuilder();    
	        CriteriaUpdate<Article> criteriaUpdate  = builder.createCriteriaUpdate(Article.class);
	        criteriaUpdate.from(Article.class);
	        Root<Article> root = criteriaUpdate.from(Article.class);
	        criteriaUpdate.where(builder.equal(root.get("designation"),a.getDesignation()));
	        criteriaUpdate.set("famille", a.getFamille());
	        criteriaUpdate.set("pvht", a.getPvht());
	        criteriaUpdate.set("paht", a.getPaht());
	        criteriaUpdate.set("taxe", a.getTaxe());
	        criteriaUpdate.set("valtaxe", a.getValtaxe());
	        criteriaUpdate.set("pvttc", a.getPvttc());
	        criteriaUpdate.set("last_modification", a.getLast_modification());
	        criteriaUpdate.set("bywho", a.getBywho());
	        getSession().createQuery(criteriaUpdate).executeUpdate();
		
		
	}


	public void deletearticle(String designation) {
		
		CriteriaBuilder criteriaBuilder  = getSession().getCriteriaBuilder();
		CriteriaDelete<Article> query = criteriaBuilder.createCriteriaDelete(Article.class);
		Root<Article> root = query.from(Article.class);
		query.where(criteriaBuilder.equal(root.get("designation"),designation));
		getSession().createQuery(query).executeUpdate();	
	}

}
