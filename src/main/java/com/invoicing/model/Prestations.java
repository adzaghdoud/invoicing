package com.invoicing.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="prestations")
public class Prestations {
    @Id
    @Column(name = "id_prestation")
	private int id;
	private String numfacture;
	private Timestamp date;
	private int quantite;
	@Column(name = "montant_HT")
	private Double montantHT;
	@Column(name = "montant_TTC")
	private Double montantTTC;
	@Column(name = "id_article")
	private int idarticle;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNumfacture() {
		return numfacture;
	}
	public void setNumfacture(String numfacture) {
		this.numfacture = numfacture;
	}
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	public int getQuantite() {
		return quantite;
	}
	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}
	public Double getMontantHT() {
		return montantHT;
	}
	public void setMontantHT(Double montantHT) {
		this.montantHT = montantHT;
	}
	public Double getMontantTTC() {
		return montantTTC;
	}
	public void setMontantTTC(Double montantTTC) {
		this.montantTTC = montantTTC;
	}
	public int getIdarticle() {
		return idarticle;
	}
	public void setIdarticle(int idarticle) {
		this.idarticle = idarticle;
	}
	
}
