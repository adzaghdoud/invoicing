package com.invoicing.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="company")
public class Company {
	@Id
	@Column(name="raison_sociale")
	private String rs;
	private String siret;
	private String rib;
	private String adresse;
	private String ville;
	@Column(name="code_postale")
	private int cp;
	private String tel;
	private String bankname;
	private String slug;
	private String token;
	private String rcs;
	private String bic;
	private byte[] logo;
	private Timestamp last_refresh_transaction;
	private String numtva;
	private String email;
	private String date_cloture_comptable;
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	public String getSlug() {
		return slug;
	}
	public void setSlug(String slug) {
		this.slug = slug;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getRs() {
		return rs;
	}
	public void setRs(String rs) {
		this.rs = rs;
	}
	public String getRib() {
		return rib;
	}
	public void setRib(String rib) {
		this.rib = rib;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	public int getCp() {
		return cp;
	}
	public void setCp(int cp) {
		this.cp = cp;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getSiret() {
		return siret;
	}
	public void setSiret(String siret) {
		this.siret = siret;
	}
	public String getRcs() {
		return rcs;
	}
	public void setRcs(String rcs) {
		this.rcs = rcs;
	}
	public String getBic() {
		return bic;
	}
	public void setBic(String bic) {
		this.bic = bic;
	}
	public byte[] getLogo() {
		return logo;
	}
	public void setLogo(byte[] logo) {
		this.logo = logo;
	}
	public Timestamp getLast_refresh_transaction() {
		return last_refresh_transaction;
	}
	public void setLast_refresh_transaction(Timestamp last_refresh_transaction) {
		this.last_refresh_transaction = last_refresh_transaction;
	}
	public String getNumtva() {
		return numtva;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setNumtva(String numtva) {
		this.numtva = numtva;
	}
	public String getDate_cloture_comptable() {
		return date_cloture_comptable;
	}
	public void setDate_cloture_comptable(String date_cloture_comptable) {
		this.date_cloture_comptable = date_cloture_comptable;
	}
	
    	
}
