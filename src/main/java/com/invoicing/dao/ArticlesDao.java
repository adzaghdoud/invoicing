package com.invoicing.dao;

import java.util.List;

import com.invoicing.model.Article;

public interface ArticlesDao {
	List<Article> getlistarticles(String rs);
	Article getarticlebydesignation(String designation);
	void addarticle(Article a);
	void updatearticle(Article a);
	void deletearticle(String designation);

}
