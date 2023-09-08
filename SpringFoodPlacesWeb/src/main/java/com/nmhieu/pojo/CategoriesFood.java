/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "categories_food")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CategoriesFood.findAll", query = "SELECT c FROM CategoriesFood c"),
    @NamedQuery(name = "CategoriesFood.findByCategoryfoodId", query = "SELECT c FROM CategoriesFood c WHERE c.categoryfoodId = :categoryfoodId"),
    @NamedQuery(name = "CategoriesFood.findByCategoryname", query = "SELECT c FROM CategoriesFood c WHERE c.categoryname = :categoryname"),
    @NamedQuery(name = "CategoriesFood.findByActive", query = "SELECT c FROM CategoriesFood c WHERE c.active = :active")})
public class CategoriesFood implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "categoryfood_id")
    private Integer categoryfoodId;
    @Size(max = 255)
    @Column(name = "categoryname")
    private String categoryname;
    @Column(name = "active")
    private Boolean active;
    @JoinColumn(name = "restaurant_id", referencedColumnName = "restaurant_id")
    @ManyToOne
    private Restaurants restaurantId;
    @JsonIgnore
    @OneToMany(mappedBy = "categoryfoodId")
    private Set<Fooditems> fooditemsSet;

    public CategoriesFood() {
    }

    public CategoriesFood(Integer categoryfoodId) {
        this.categoryfoodId = categoryfoodId;
    }

    public Integer getCategoryfoodId() {
        return categoryfoodId;
    }

    public void setCategoryfoodId(Integer categoryfoodId) {
        this.categoryfoodId = categoryfoodId;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
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
        hash += (categoryfoodId != null ? categoryfoodId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CategoriesFood)) {
            return false;
        }
        CategoriesFood other = (CategoriesFood) object;
        if ((this.categoryfoodId == null && other.categoryfoodId != null) || (this.categoryfoodId != null && !this.categoryfoodId.equals(other.categoryfoodId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tuantran.pojo.CategoriesFood[ categoryfoodId=" + categoryfoodId + " ]";
    }

}
