package com.invoicing.service;

import java.util.List;

import com.invoicing.model.Article;

public interface ArticleService {
	List<Article> getlistarticles();
	Article getarticlebydesignation(String designation);

}
