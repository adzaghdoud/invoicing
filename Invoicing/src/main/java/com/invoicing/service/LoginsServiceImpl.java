package com.invoicing.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invoicing.dao.LoginsDao;
import com.invoicing.model.Logins;


@Service("LoginsService")
@Transactional
public class LoginsServiceImpl  implements LoginsService{
	@Autowired
    private LoginsDao dao;
	
	public boolean checkloginpassword(String login, String password) {
		// TODO Auto-generated method stub
		return dao.checkloginpassword(login, password);
	}

	
	public Logins getinfo(String login) {
		// TODO Auto-generated method stub
		return dao.getinfo(login);
	}

}
