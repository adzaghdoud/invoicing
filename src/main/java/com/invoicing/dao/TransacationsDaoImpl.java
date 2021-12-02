package com.invoicing.dao;


import java.sql.Timestamp;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.invoicing.model.Company;
import com.invoicing.model.Transaction;

@Repository("TransactionDaoImpl")
	public class TransacationsDaoImpl extends  AbstractDao implements TransactionsDao{

		public void addtransaction(Transaction t) {
			// TODO Auto-generated method stub
			persist(t);
		}

		public List<Transaction> getlist(String company) {
			// TODO Auto-generated method stub
			CriteriaBuilder builder = getSession().getCriteriaBuilder();
			CriteriaQuery<Transaction> criteria = builder.createQuery(Transaction.class);
			Root<Transaction> root = criteria.from(Transaction.class);
			criteria.select(root).where(builder.equal(root.get("company"), company));
			criteria.orderBy(builder.desc(root.get("updated_at")));
			Query<Transaction> q=getSession().createQuery(criteria);		
			
			return q.list();
		}

		public boolean checkexistancetransaction(String transactionID) {
			// TODO Auto-generated method stub
			CriteriaBuilder builder = getSession().getCriteriaBuilder();
			CriteriaQuery<Transaction> criteria = builder.createQuery(Transaction.class);
			Root<Transaction> root = criteria.from(Transaction.class);
			criteria.select(root).where(builder.equal(root.get("transaction_id"), transactionID));
			Query<Transaction> q=getSession().createQuery(criteria);
	         try {
	        	 q.getSingleResult(); 
	         }catch(NoResultException e) {
	         return true; 
	         }
			return false;
			
	}

		public long countnbtransaction() {
			// TODO Auto-generated method stub
		    CriteriaBuilder builder = getSession().getCriteriaBuilder();
	        
	        CriteriaQuery<Long> Query = builder.createQuery(Long.class);
	        
	        Root<Transaction> Root = Query.from(Transaction.class);
	        Expression<Long> countExpression = builder.count(Root);
	        Query.select(countExpression);
	        TypedQuery<Long> typedQuery = getSession().createQuery(Query);
	        Long count = typedQuery.getSingleResult();
	        return count;
		}

		public void updatetvatransaction(String setted_at, double amount_ht,Timestamp t,String updated_at) {
			// TODO Auto-generated method stub
			CriteriaBuilder builder = getSession().getCriteriaBuilder();
	        CriteriaUpdate<Transaction> criteriaUpdate  = builder.createCriteriaUpdate(Transaction.class);
	        criteriaUpdate.from(Transaction.class);
	        Root<Transaction> root = criteriaUpdate.from(Transaction.class);
	        criteriaUpdate.set("amount_HT",amount_ht);
	        criteriaUpdate.set("manual_validation",t);
	        criteriaUpdate.where(builder.equal(root.get("settled_at"),setted_at));
	        criteriaUpdate.where(builder.equal(root.get("updated_at"),updated_at));
	        getSession().createQuery(criteriaUpdate).executeUpdate();
		}

		public List<Transaction> searchtransacbetweentwodates(String datedeb, String datefin ,String company)  {
			// TODO Auto-generated method stub
			javax.persistence.Query query = getSession().createNamedQuery("searchtransactionbetweentwodates_all", Transaction.class);
			query.setParameter(1, datedeb);
			query.setParameter(2, datefin+" 23:59:59");
			query.setParameter(3, company);
			@SuppressWarnings("unchecked")
			List<Transaction> list_transactions = query.getResultList();  
		    return list_transactions;
		}

		public List<Transaction> searchtransacbetweentwodates_with_tva(String datedeb, String datefin, String company) {
			javax.persistence.Query query = getSession().createNamedQuery("searchtransactionbetweentwodates_with_tva", Transaction.class);
			query.setParameter(1, datedeb);
			query.setParameter(2, datefin+" 23:59:59");
			query.setParameter(3, company);
			@SuppressWarnings("unchecked")
			List<Transaction> list_transactions = query.getResultList();  
		    return list_transactions;
		}

		@Override
		public long count_debit_trancactions(String company) {
			CriteriaBuilder builder = getSession().getCriteriaBuilder();
			CriteriaQuery<Transaction> criteria = builder.createQuery(Transaction.class);
			Root<Transaction> root = criteria.from(Transaction.class);
			Predicate cond = null;
			cond = builder.and(builder.equal(root.get("company"), company), builder.equal(root.get("side"), "debit"));
			return  getSession().createQuery(criteria.select(root).where(cond)).getResultList().size();
		}

		@Override
		public long count_credit_trancactions(String company) {
			CriteriaBuilder builder = getSession().getCriteriaBuilder();
			CriteriaQuery<Transaction> criteria = builder.createQuery(Transaction.class);
			Root<Transaction> root = criteria.from(Transaction.class);
			Predicate cond = null;
			cond = builder.and(builder.equal(root.get("company"), company), builder.equal(root.get("side"), "credit"));
			return  getSession().createQuery(criteria.select(root).where(cond)).getResultList().size();
		}

		@Override
		public void updateproof(String settled_at, String updated_at, String proof_file_name) {
			CriteriaBuilder builder = getSession().getCriteriaBuilder();
	        CriteriaUpdate<Transaction> criteriaUpdate  = builder.createCriteriaUpdate(Transaction.class);
	        criteriaUpdate.from(Transaction.class);
	        Root<Transaction> root = criteriaUpdate.from(Transaction.class);
	        criteriaUpdate.set("proof_filename",proof_file_name);
	        criteriaUpdate.where(builder.equal(root.get("updated_at"),updated_at),builder.equal(root.get("settled_at"),settled_at));
	        getSession().createQuery(criteriaUpdate).executeUpdate();		
		}

		@Override
		public boolean checkeexistproof(String settled_at, String updated_at,String company) {
			CriteriaBuilder builder = getSession().getCriteriaBuilder();
			CriteriaQuery<Transaction> criteria = builder.createQuery(Transaction.class);
			Root<Transaction> root = criteria.from(Transaction.class);
			criteria.select(root).where(builder.equal(root.get("settled_at"), settled_at),builder.equal(root.get("updated_at"), updated_at),builder.equal(root.get("company"), company));
			Query<Transaction> q=getSession().createQuery(criteria);
			Transaction t;
			  try {
	        	 t=q.getSingleResult();
	        	 
	         }catch(NoResultException e) {
	         return false; 
	         }
			  if (t.getProof_filename() == null||t.getProof_filename().length() == 0  ) {
			  return true;
			  }else {
				  return false;
			  }
		}
			
}

