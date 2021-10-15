package com.invoicing.service;

import com.invoicing.model.Logins;

public interface LoginsService {
	boolean checkloginpassword(String login , String password);
	Logins getinfo(String login);
	void updateavatar(String login, byte[] avatar);
	boolean checkemail(String email);
	Logins getloginbyemail(String email);
	void setresetpassword(String login,String value);
}
