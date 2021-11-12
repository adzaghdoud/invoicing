package com.invoicing.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="suivi_import ")
public class Tracking {
@Id
private int id;
private String date;
private int nb_transaction_imported;
private int nb_credit;
private int nb_debit;
private double total_credit;
private double total_debit;
private double old_balance;
private double new_balance;
private String state;
private String comment;
private String company;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
}
public int getNb_transaction_imported() {
	return nb_transaction_imported;
}
public void setNb_transaction_imported(int nb_transaction_imported) {
	this.nb_transaction_imported = nb_transaction_imported;
}
public String getState() {
	return state;
}
public void setState(String state) {
	this.state = state;
}
public String getComment() {
	return comment;
}
public void setComment(String comment) {
	this.comment = comment;
}
public String getCompany() {
	return company;
}
public void setCompany(String company) {
	this.company = company;
}
public int getNb_credit() {
	return nb_credit;
}
public void setNb_credit(int nb_credit) {
	this.nb_credit = nb_credit;
}
public int getNb_debit() {
	return nb_debit;
}
public void setNb_debit(int nb_debit) {
	this.nb_debit = nb_debit;
}
public double getTotal_credit() {
	return total_credit;
}
public void setTotal_credit(double total_credit) {
	this.total_credit = total_credit;
}
public double getTotal_debit() {
	return total_debit;
}
public void setTotal_debit(double total_debit) {
	this.total_debit = total_debit;
}
public double getNew_balance() {
	return new_balance;
}
public void setNew_balance(double new_balance) {
	this.new_balance = new_balance;
}
public double getOld_balance() {
	return old_balance;
}
public void setOld_balance(double old_balance) {
	this.old_balance = old_balance;
}




	
}
