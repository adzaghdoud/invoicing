package com.invoicing.model;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="articles")
public class Article {
	@Id
	private int id;
	private String designation;
	private String famille;
	@Column(name="PV_HT")
	private double pvht;
	@Column(name="PA_HT")
	private double paht;
	@Column(name="TAXE")
	private String taxe;
	@Column(name="val_taxe")
	private int valtaxe;
	@Column(name="PV_TTC")
	private double pvttc;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getFamille() {
		return famille;
	}
	public void setFamille(String famille) {
		this.famille = famille;
	}
	public double getPvht() {
		return pvht;
	}
	public void setPvht(double pvht) {
		this.pvht = pvht;
	}
	public double getPaht() {
		return paht;
	}
	public void setPaht(double paht) {
		this.paht = paht;
	}
	public String getTaxe() {
		return taxe;
	}
	public void setTaxe(String taxe) {
		this.taxe = taxe;
	}
	public int getValtaxe() {
		return valtaxe;
	}
	public void setValtax(int valtaxe) {
		this.valtaxe = valtaxe;
	}
	public double getPvttc() {
		return pvttc;
	}
	public void setPvttc(double pvttc) {
		this.pvttc = pvttc;
	}

}
