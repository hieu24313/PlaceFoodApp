/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
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
@Table(name = "shelf_life")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ShelfLife.findAll", query = "SELECT s FROM ShelfLife s"),
    @NamedQuery(name = "ShelfLife.findByShelflifeId", query = "SELECT s FROM ShelfLife s WHERE s.shelflifeId = :shelflifeId"),
    @NamedQuery(name = "ShelfLife.findByShelflifeName", query = "SELECT s FROM ShelfLife s WHERE s.shelflifeName = :shelflifeName"),
    @NamedQuery(name = "ShelfLife.findByFromDate", query = "SELECT s FROM ShelfLife s WHERE s.fromDate = :fromDate"),
    @NamedQuery(name = "ShelfLife.findByToDate", query = "SELECT s FROM ShelfLife s WHERE s.toDate = :toDate"),
    @NamedQuery(name = "ShelfLife.findByActive", query = "SELECT s FROM ShelfLife s WHERE s.active = :active")})
public class ShelfLife implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "shelflife_id")
    private Integer shelflifeId;
    @Size(max = 255)
    @Column(name = "shelflife_name")
    private String shelflifeName;
    @Column(name = "from_date")
    @Temporal(TemporalType.DATE)
    private Date fromDate;
    @Column(name = "to_date")
    @Temporal(TemporalType.DATE)
    private Date toDate;
    @Column(name = "active")
    private Boolean active;
    @JoinColumn(name = "restaurant_id", referencedColumnName = "restaurant_id")
    @ManyToOne
    private Restaurants restaurantId;
    @JsonIgnore
    @OneToMany(mappedBy = "shelflifeId")
    private Set<Fooditems> fooditemsSet;

    public ShelfLife() {
    }

    public ShelfLife(Integer shelflifeId) {
        this.shelflifeId = shelflifeId;
    }

    public Integer getShelflifeId() {
        return shelflifeId;
    }

    public void setShelflifeId(Integer shelflifeId) {
        this.shelflifeId = shelflifeId;
    }

    public String getShelflifeName() {
        return shelflifeName;
    }

    public void setShelflifeName(String shelflifeName) {
        this.shelflifeName = shelflifeName;
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Restaurants getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Restaurants restaurantId) {
        this.restaurantId = restaurantId;
    }

    @XmlTransient
    public Set<Fooditems> getFooditemsSet() {
        return fooditemsSet;
    }

    public void setFooditemsSet(Set<Fooditems> fooditemsSet) {
        this.fooditemsSet = fooditemsSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (shelflifeId != null ? shelflifeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ShelfLife)) {
            return false;
        }
        ShelfLife other = (ShelfLife) object;
        if ((this.shelflifeId == null && other.shelflifeId != null) || (this.shelflifeId != null && !this.shelflifeId.equals(other.shelflifeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tuantran.pojo.ShelfLife[ shelflifeId=" + shelflifeId + " ]";
    }

}
