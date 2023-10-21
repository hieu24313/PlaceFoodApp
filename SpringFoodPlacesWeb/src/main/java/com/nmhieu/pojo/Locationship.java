/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.pojo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "locationship")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Locationship.findAll", query = "SELECT l FROM Locationship l"),
    @NamedQuery(name = "Locationship.findByLocationshipId", query = "SELECT l FROM Locationship l WHERE l.locationshipId = :locationshipId"),
    @NamedQuery(name = "Locationship.findByLocation", query = "SELECT l FROM Locationship l WHERE l.location = :location"),
    @NamedQuery(name = "Locationship.findByPhonenumber", query = "SELECT l FROM Locationship l WHERE l.phonenumber = :phonenumber")})
public class Locationship implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "locationship_id")
    private Integer locationshipId;
    @Size(max = 255)
    @Column(name = "location")
    private String location;
    @Size(max = 255)
    @Column(name = "phonenumber")
    private String phonenumber;
    @JoinColumn(name = "receipt_id", referencedColumnName = "receipt_id")
    @ManyToOne
    private Receipts receiptId;

    public Locationship() {
    }

    public Locationship(Integer locationshipId) {
        this.locationshipId = locationshipId;
    }

    public Integer getLocationshipId() {
        return locationshipId;
    }

    public void setLocationshipId(Integer locationshipId) {
        this.locationshipId = locationshipId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public Receipts getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(Receipts receiptId) {
        this.receiptId = receiptId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (locationshipId != null ? locationshipId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Locationship)) {
            return false;
        }
        Locationship other = (Locationship) object;
        if ((this.locationshipId == null && other.locationshipId != null) || (this.locationshipId != null && !this.locationshipId.equals(other.locationshipId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nmhieu.pojo.Locationship[ locationshipId=" + locationshipId + " ]";
    }
    
}
