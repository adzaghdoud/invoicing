package com.invoicing.service;

import java.util.List;

import com.invoicing.model.Tracking;

public interface TrackingService {
	List<Tracking> GetTracking(String company);
}
