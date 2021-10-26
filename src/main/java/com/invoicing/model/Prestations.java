package com.invoicing.model;

import java.sql.Timestamp;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NamedNativeQuery;

import com.fasterxml.jackson.annotation.JsonFormat;
@Entity
@Table(name="prestations")
@NamedNativeQuery(
        name = "search_prestations",
        query = "SELECT * FROM invoicing.prestations  where date between ? and  ? and company=?",
                    resultClass=Prestations.class
    )

public class Prestations {
    @Id
    @Column(name = "id_prestation")
	private int id;
	private String numfacture;
	private String nomfacture;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "Europe/Paris")
	private Timestamp date;
	private float quantite;
	@Column(name = "montant_HT")
	private Double montantHT;
	@Column(name = "montant_TTC")
	private Double montantTTC;
	private String article;
	@Column(name = "total_TTC")
	private double totalttc;
	private String taxe;
	private double valtaxe;
	private String statut_paiement;
	private String client;
	@Column(name = "mode_paiement")
	private String modepaiement;
	@Column(name = "date_paiement_attendue")
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date datepaiementattendue;
	private String company;
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
	public float getQuantite() {
		return quantite;
	}
	public void setQuantite(float quantite) {
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
	public String getArticle() {
		return article;
	}
	public void setArticle(String  article) {
		this.article = article;
	}
	public double getTotalttc() {
		return totalttc;
	}
	public void setTotalttc(double totalttc) {
		this.totalttc = totalttc;
	}
	public String getTaxe() {
		return taxe;
	}
	public void setTaxe(String taxe) {
		this.taxe = taxe;
	}
	public double getValtaxe() {
		return valtaxe;
	}
	public void setValtaxe(double valtaxe) {
		this.valtaxe = valtaxe;
	}
	public String getStatut_paiement() {
		return statut_paiement;
	}
	public void setStatut_paiement(String statut_paiement) {
		this.statut_paiement = statut_paiement;
	}
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public String getNomfacture() {
		return nomfacture;
	}
	public void setNomfacture(String nomfacture) {
		this.nomfacture = nomfacture;
	}
	public String getModepaiement() {
		return modepaiement;
	}
	public void setModepaiement(String modepaiement) {
		this.modepaiement = modepaiement;
	}
	public Date getDatepaiementattendue() {
		return datepaiementattendue;
	}
	public void setDatepaiementattendue(Date datepaiementattendue) {
		this.datepaiementattendue = datepaiementattendue;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}	
	
}
