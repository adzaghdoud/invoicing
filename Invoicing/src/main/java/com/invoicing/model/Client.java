package com.invoicing.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="clients")
public class Client {
@Id
@Column(name="raison_sociale")
private String rs;
private String nom;
private String adresse;
@Column(name="code_postale")
private int cp;
private String ville;
private String telephone;
private String mail;
private String siret;
private String rib;

  public String getRib() {
	return rib;
}
public void setRib(String rib) {
	this.rib = rib;
}
public String getRs() {
  return this.rs;	
   }
  public void setRs(String rs) {
  this.rs=rs;
   }
  public String getNom() {
	   return this.nom;	
   }
  public void setNom(String nom) {
	  this.nom=nom;
   }
	
  
  public String getAdresse() {
  return this.adresse;	
   }
  public void setAdresse(String adresse) {
  this.adresse=adresse;
   }
 
   public int getCp() {
   return this.cp;	
   }
   public void setCp(int cp) {
   this.cp=cp;
   }     
  
   public String getVille() {
   return this.ville;	
   }
   public void setVille(String ville) {
   this.ville=ville;
   }         
          
   public String getTelephone() {
   return this.telephone;	
   }
   public void setTelephone(String telephone) {
   this.telephone=telephone;
   }    
                        
   public String getMail() {
   return this.mail;	
   }
   public void setMail(String mail) {
   this.mail=mail;
   }    
   
   public String getSiret() {
   return this.siret;	
   }
   public void setSiret(String siret) {
   this.siret=siret;
   } 
                
                

}
