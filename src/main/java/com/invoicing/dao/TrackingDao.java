package com.invoicing.dao;
import java.util.List;

import com.invoicing.model.Tracking;
public interface TrackingDao {
List<Tracking> GetTracking(String company);

}
