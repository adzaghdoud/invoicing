package com.invoicing.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.invoicing.model.Article;
import com.invoicing.model.Client;
@Repository("ArticleDao")
public class ArticlesDaoImpl extends  AbstractDao implements ArticlesDao {

	@Override
	public List<Article> getlistarticles() {
		// TODO Auto-generated method stub
		
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<Article> criteria = builder.createQuery(Article.class);
		Root<Article> root = criteria.from(Article.class);
		criteria.select(root);
		Query<Article> q=getSession().createQuery(criteria);		
		return q.list();
	}

	@Override
	public Article getarticlebydesignation(String designation) {
		// TODO Auto-generated method stub
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<Article> criteria = builder.createQuery(Article.class);
		Root<Article> root = criteria.from(Article.class);
		criteria.select(root).where(builder.equal(root.get("designation"), designation));
		Query<Article> q=getSession().createQuery(criteria);
        return q.getSingleResult();
	}

}
