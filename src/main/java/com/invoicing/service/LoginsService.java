package com.invoicing.service;

import com.invoicing.model.Logins;

public interface LoginsService {
	boolean checkloginpassword(String login , String password);
	Logins getinfo(String login);
}
