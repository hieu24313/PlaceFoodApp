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
@Table(name = "shelflife_fooditems")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ShelflifeFooditems.findAll", query = "SELECT s FROM ShelflifeFooditems s"),
    @NamedQuery(name = "ShelflifeFooditems.findByShelflifeFooditemsId", query = "SELECT s FROM ShelflifeFooditems s WHERE s.shelflifeFooditemsId = :shelflifeFooditemsId")})
public class ShelflifeFooditems implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "shelflife_fooditems_id")
    private Integer shelflifeFooditemsId;
    @JoinColumn(name = "food_id", referencedColumnName = "food_id")
    @ManyToOne
    private Fooditems foodId;
    @JoinColumn(name = "shelflife_id", referencedColumnName = "shelflife_id")
    @ManyToOne
    private ShelfLife shelflifeId;

    public ShelflifeFooditems() {
    }

    public ShelflifeFooditems(Integer shelflifeFooditemsId) {
        this.shelflifeFooditemsId = shelflifeFooditemsId;
    }

    public Integer getShelflifeFooditemsId() {
        return shelflifeFooditemsId;
    }

    public void setShelflifeFooditemsId(Integer shelflifeFooditemsId) {
        this.shelflifeFooditemsId = shelflifeFooditemsId;
    }

    public Fooditems getFoodId() {
        return foodId;
    }

    public void setFoodId(Fooditems foodId) {
        this.foodId = foodId;
    }

    public ShelfLife getShelflifeId() {
        return shelflifeId;
    }

    public void setShelflifeId(ShelfLife shelflifeId) {
        this.shelflifeId = shelflifeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (shelflifeFooditemsId != null ? shelflifeFooditemsId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ShelflifeFooditems)) {
            return false;
        }
        ShelflifeFooditems other = (ShelflifeFooditems) object;
        if ((this.shelflifeFooditemsId == null && other.shelflifeFooditemsId != null) || (this.shelflifeFooditemsId != null && !this.shelflifeFooditemsId.equals(other.shelflifeFooditemsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tuantran.pojo.ShelflifeFooditems[ shelflifeFooditemsId=" + shelflifeFooditemsId + " ]";
    }
    
}
