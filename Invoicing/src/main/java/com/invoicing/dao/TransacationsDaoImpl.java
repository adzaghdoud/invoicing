package com.invoicing.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.invoicing.model.Transaction;

@Repository("TransactionDaoImpl")
	public class TransacationsDaoImpl extends  AbstractDao implements TransactionsDao{

		public void addtransaction(Transaction t) {
			// TODO Auto-generated method stub
			persist(t);
		}

		public List<Transaction> getlist() {
			// TODO Auto-generated method stub
			CriteriaBuilder builder = getSession().getCriteriaBuilder();
			CriteriaQuery<Transaction> criteria = builder.createQuery(Transaction.class);
			Root<Transaction> root = criteria.from(Transaction.class);
			criteria.select(root);
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
		
}

