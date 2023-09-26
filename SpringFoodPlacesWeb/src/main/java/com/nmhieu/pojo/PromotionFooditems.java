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
@Table(name = "promotion_fooditems")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PromotionFooditems.findAll", query = "SELECT p FROM PromotionFooditems p"),
    @NamedQuery(name = "PromotionFooditems.findByPromotionFooditemsId", query = "SELECT p FROM PromotionFooditems p WHERE p.promotionFooditemsId = :promotionFooditemsId"),
    @NamedQuery(name = "PromotionFooditems.findByActive", query = "SELECT p FROM PromotionFooditems p WHERE p.active = :active")})
public class PromotionFooditems implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "promotion_fooditems_id")
    private Integer promotionFooditemsId;
    @Column(name = "active")
    private Boolean active;
    @JoinColumn(name = "food_id", referencedColumnName = "food_id")
    @ManyToOne
    private Fooditems foodId;
    @JoinColumn(name = "promotion_id", referencedColumnName = "promotion_id")
    @ManyToOne
    private Promotion promotionId;

    public PromotionFooditems() {
    }

    public PromotionFooditems(Integer promotionFooditemsId) {
        this.promotionFooditemsId = promotionFooditemsId;
    }

    public Integer getPromotionFooditemsId() {
        return promotionFooditemsId;
    }

    public void setPromotionFooditemsId(Integer promotionFooditemsId) {
        this.promotionFooditemsId = promotionFooditemsId;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Fooditems getFoodId() {
        return foodId;
    }

    public void setFoodId(Fooditems foodId) {
        this.foodId = foodId;
    }

    public Promotion getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Promotion promotionId) {
        this.promotionId = promotionId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (promotionFooditemsId != null ? promotionFooditemsId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PromotionFooditems)) {
            return false;
        }
        PromotionFooditems other = (PromotionFooditems) object;
        if ((this.promotionFooditemsId == null && other.promotionFooditemsId != null) || (this.promotionFooditemsId != null && !this.promotionFooditemsId.equals(other.promotionFooditemsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nmhieu.pojo.PromotionFooditems[ promotionFooditemsId=" + promotionFooditemsId + " ]";
    }
    
}
