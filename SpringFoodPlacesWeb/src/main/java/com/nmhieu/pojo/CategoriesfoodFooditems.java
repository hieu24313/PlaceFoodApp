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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "categoriesfood_fooditems")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CategoriesfoodFooditems.findAll", query = "SELECT c FROM CategoriesfoodFooditems c"),
    @NamedQuery(name = "CategoriesfoodFooditems.findByCategoriesfoodFooditemsId", query = "SELECT c FROM CategoriesfoodFooditems c WHERE c.categoriesfoodFooditemsId = :categoriesfoodFooditemsId")})
public class CategoriesfoodFooditems implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "categoriesfood_fooditems_id")
    private Integer categoriesfoodFooditemsId;
    @JoinColumn(name = "categoryfood_id", referencedColumnName = "categoryfood_id")
    @ManyToOne
    private CategoriesFood categoryfoodId;
    @JoinColumn(name = "food_id", referencedColumnName = "food_id")
    @ManyToOne
    private Fooditems foodId;

    public CategoriesfoodFooditems() {
    }

    public CategoriesfoodFooditems(Integer categoriesfoodFooditemsId) {
        this.categoriesfoodFooditemsId = categoriesfoodFooditemsId;
    }

    public Integer getCategoriesfoodFooditemsId() {
        return categoriesfoodFooditemsId;
    }

    public void setCategoriesfoodFooditemsId(Integer categoriesfoodFooditemsId) {
        this.categoriesfoodFooditemsId = categoriesfoodFooditemsId;
    }

    public CategoriesFood getCategoryfoodId() {
        return categoryfoodId;
    }

    public void setCategoryfoodId(CategoriesFood categoryfoodId) {
        this.categoryfoodId = categoryfoodId;
    }

    public Fooditems getFoodId() {
        return foodId;
    }

    public void setFoodId(Fooditems foodId) {
        this.foodId = foodId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (categoriesfoodFooditemsId != null ? categoriesfoodFooditemsId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CategoriesfoodFooditems)) {
            return false;
        }
        CategoriesfoodFooditems other = (CategoriesfoodFooditems) object;
        if ((this.categoriesfoodFooditemsId == null && other.categoriesfoodFooditemsId != null) || (this.categoriesfoodFooditemsId != null && !this.categoriesfoodFooditemsId.equals(other.categoriesfoodFooditemsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tuantran.pojo.CategoriesfoodFooditems[ categoriesfoodFooditemsId=" + categoriesfoodFooditemsId + " ]";
    }
    
}
