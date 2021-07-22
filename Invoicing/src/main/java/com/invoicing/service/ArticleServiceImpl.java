package com.invoicing.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invoicing.dao.ArticlesDao;
import com.invoicing.dao.ClientDao;
import com.invoicing.model.Article;

@Service("ArticleService")
@Transactional
public class ArticleServiceImpl implements ArticleService {
	@Autowired
    private ArticlesDao dao;
	
	public List<Article> getlistarticles() {
		// TODO Auto-generated method stub
		return dao.getlistarticles();
	}
	
	public Article getarticlebydesignation(String designation) {
		// TODO Auto-generated method stub
		return dao.getarticlebydesignation(designation);
	}

	@Override
	public void addarticle(Article a) {
		dao.addarticle(a);
		
	}

}
