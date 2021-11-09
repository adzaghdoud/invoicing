package com.invoicing.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invoicing.dao.TrackingDao;
import com.invoicing.model.Tracking;
@Service("TrackingService")
@Transactional
public class TrackingServiceImpl implements TrackingService{
	@Autowired
    private TrackingDao dao;

	public List<Tracking> GetTracking(String company) {
		return dao.GetTracking(company);
	}
}
