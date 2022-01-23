package com.invoicing.dao;

import java.sql.Timestamp;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.invoicing.model.Company;
import com.invoicing.model.Logins;
import com.invoicing.model.Prestations;

@Repository("CompanyDao")
public class CompanyDaoImpl extends  AbstractDao implements CompanyDao {

	
	public Company getinfo(String rs) {
		// TODO Auto-generated method stub
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<Company> criteria = builder.createQuery(Company.class);
		Root<Company> root = criteria.from(Company.class);
		criteria.select(root).where(builder.equal(root.get("rs"), rs));	
		Query<Company> q=getSession().createQuery(criteria);
		return q.uniqueResult();
	}

	public void updatecompany(String field , String value) {
		// TODO Auto-generated method stub
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
        // create update
        CriteriaUpdate<Company> criteriaUpdate  = builder.createCriteriaUpdate(Company.class);
        criteriaUpdate.from(Company.class);
        if (field.contentEquals("cp")) {
        	criteriaUpdate.set(field,Integer.parseInt(value));
        }else {
      	
        	criteriaUpdate.set(field,value);
        } 

        getSession().createQuery(criteriaUpdate).executeUpdate();

   
		
	}

	public Company getcompanybyraison(String raison) {
		// TODO Auto-generated method stub
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<Company> criteria = builder.createQuery(Company.class);
		Root<Company> root = criteria.from(Company.class);
		criteria.select(root).where(builder.equal(root.get("rs"), raison));	
		Query<Company> q=getSession().createQuery(criteria);
		return q.uniqueResult();
	}

	public void updatelogocompany(byte[] logo, String raison) {
		// TODO Auto-generated method stub
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
        // create update
        CriteriaUpdate<Company> criteriaUpdate  = builder.createCriteriaUpdate(Company.class);
        Root<Company> root = criteriaUpdate.from(Company.class);
        criteriaUpdate.where(builder.equal(root.get("rs"), raison));  
        criteriaUpdate.set("logo",logo);
        getSession().createQuery(criteriaUpdate).executeUpdate();
	}

	public void updatetimestamprefresh(Timestamp t , String rs) {
		// TODO Auto-generated method stub
        CriteriaBuilder builder = getSession().getCriteriaBuilder();    
        CriteriaUpdate<Company> criteriaUpdate  = builder.createCriteriaUpdate(Company.class);
        criteriaUpdate.from(Company.class);
        Root<Company> root = criteriaUpdate.from(Company.class);
        criteriaUpdate.set("last_refresh_transaction",t);
        criteriaUpdate.where(builder.equal(root.get("rs"),rs));
        getSession().createQuery(criteriaUpdate).executeUpdate();
	}



		    public void updatlogo(String rs, byte[] logo) {
			CriteriaBuilder builder = getSession().getCriteriaBuilder();    
	        CriteriaUpdate<Company> criteriaUpdate  = builder.createCriteriaUpdate(Company.class);
	        criteriaUpdate.from(Company.class);
	        Root<Company> root = criteriaUpdate.from(Company.class);
	        criteriaUpdate.where(builder.equal(root.get("rs"),rs));
	        criteriaUpdate.set("logo", logo);
	        getSession().createQuery(criteriaUpdate).executeUpdate();
		}

			@Override
			public void updatedatecloturecomptable(String rs,String newdate) {
				CriteriaBuilder builder = getSession().getCriteriaBuilder();    
		        CriteriaUpdate<Company> criteriaUpdate  = builder.createCriteriaUpdate(Company.class);
		        criteriaUpdate.from(Company.class);
		        Root<Company> root = criteriaUpdate.from(Company.class);
		        criteriaUpdate.where(builder.equal(root.get("rs"),rs));
		        criteriaUpdate.set("date_cloture_comptable", newdate);
		        getSession().createQuery(criteriaUpdate).executeUpdate();	
				
				
			}

}
