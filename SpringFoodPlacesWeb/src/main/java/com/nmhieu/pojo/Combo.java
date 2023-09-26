/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "combo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Combo.findAll", query = "SELECT c FROM Combo c"),
    @NamedQuery(name = "Combo.findByComboId", query = "SELECT c FROM Combo c WHERE c.comboId = :comboId"),
    @NamedQuery(name = "Combo.findByComboName", query = "SELECT c FROM Combo c WHERE c.comboName = :comboName"),
    @NamedQuery(name = "Combo.findByFromDate", query = "SELECT c FROM Combo c WHERE c.fromDate = :fromDate"),
    @NamedQuery(name = "Combo.findByToDate", query = "SELECT c FROM Combo c WHERE c.toDate = :toDate"),
    @NamedQuery(name = "Combo.findByPriceCombo", query = "SELECT c FROM Combo c WHERE c.priceCombo = :priceCombo"),
    @NamedQuery(name = "Combo.findByActive", query = "SELECT c FROM Combo c WHERE c.active = :active")})
public class Combo implements Serializable {

    @OneToMany(mappedBy = "comboId")
    @JsonIgnore
    private Set<ComboFooditems> comboFooditemsSet;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "combo_id")
    private Integer comboId;
    @Size(max = 255)
    @Column(name = "combo_name")
    private String comboName;
    @Column(name = "from_date")
    @Temporal(TemporalType.DATE)
    private Date fromDate;
    @Column(name = "to_date")
    @Temporal(TemporalType.DATE)
    private Date toDate;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "price_combo")
    private BigDecimal priceCombo;
    @Column(name = "active")
    private Boolean active;

    public Combo() {
    }

    public Combo(Integer comboId) {
        this.comboId = comboId;
    }

    public Integer getComboId() {
        return comboId;
    }

    public void setComboId(Integer comboId) {
        this.comboId = comboId;
    }

    public String getComboName() {
        return comboName;
    }

    public void setComboName(String comboName) {
        this.comboName = comboName;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public BigDecimal getPriceCombo() {
        return priceCombo;
    }

    public void setPriceCombo(BigDecimal priceCombo) {
        this.priceCombo = priceCombo;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (comboId != null ? comboId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Combo)) {
            return false;
        }
        Combo other = (Combo) object;
        if ((this.comboId == null && other.comboId != null) || (this.comboId != null && !this.comboId.equals(other.comboId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nmhieu.pojo.Combo[ comboId=" + comboId + " ]";
    }

    @XmlTransient
    public Set<ComboFooditems> getComboFooditemsSet() {
        return comboFooditemsSet;
    }

    public void setComboFooditemsSet(Set<ComboFooditems> comboFooditemsSet) {
        this.comboFooditemsSet = comboFooditemsSet;
    }
    
}
