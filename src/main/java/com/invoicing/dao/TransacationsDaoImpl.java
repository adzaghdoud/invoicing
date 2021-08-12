package com.invoicing.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.invoicing.model.Prestations;
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

		public void updatetvatransaction(String setted_at, double amount_ht) {
			// TODO Auto-generated method stub
			CriteriaBuilder builder = getSession().getCriteriaBuilder();
	        
	        CriteriaUpdate<Transaction> criteriaUpdate  = builder.createCriteriaUpdate(Transaction.class);
	        criteriaUpdate.from(Transaction.class);
	        Root<Transaction> root = criteriaUpdate.from(Transaction.class);
	        criteriaUpdate.set("amount_HT",amount_ht);
	        criteriaUpdate.where(builder.equal(root.get("settled_at"),setted_at));
	        getSession().createQuery(criteriaUpdate).executeUpdate();
		}

		public List<Transaction> searchtransacbetweentwodates(String datedeb, String datefin)  {
			// TODO Auto-generated method stub
			javax.persistence.Query query = getSession().createNamedQuery("searchtransactionbetweentwodates", Transaction.class);
			query.setParameter(1, datedeb);
			query.setParameter(2, datefin);
			List<Transaction> list_transactions = query.getResultList();           
		      return list_transactions;
		}
		
		
		
		
		
}

