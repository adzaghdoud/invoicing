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


	public void updateavatar(String login, byte[] avatar) {
		// TODO Auto-generated method stub
		dao.updateavatar(login, avatar);
	}


	public boolean checkemail(String email) {
		return dao.checkemail(email);
	}


	public Logins getloginbyemail(String email) {
		return dao.getloginbyemail(email);
	}


	public void setresetpassword(String login,String value) {
		dao.setresetpassword(login,value);
	}

}
