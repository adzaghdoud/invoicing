package com.invoicing.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.invoicing.model.Tracking;

@Repository("TrackingDao")

public class TrackingDaoImpl extends  AbstractDao implements TrackingDao {

	public List<Tracking> GetTracking(String company) {
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<Tracking> criteria = builder.createQuery(Tracking.class);
		Root<Tracking> root = criteria.from(Tracking.class);
		criteria.select(root).where(builder.equal(root.get("company"), company));
		Query<Tracking> q=getSession().createQuery(criteria);
		return q.getResultList();
	}

}
