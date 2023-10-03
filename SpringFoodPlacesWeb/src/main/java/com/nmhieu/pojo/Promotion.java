/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.pojo;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "promotion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Promotion.findAll", query = "SELECT p FROM Promotion p"),
    @NamedQuery(name = "Promotion.findByPromotionId", query = "SELECT p FROM Promotion p WHERE p.promotionId = :promotionId"),
    @NamedQuery(name = "Promotion.findByPromotionName", query = "SELECT p FROM Promotion p WHERE p.promotionName = :promotionName"),
    @NamedQuery(name = "Promotion.findByFromDate", query = "SELECT p FROM Promotion p WHERE p.fromDate = :fromDate"),
    @NamedQuery(name = "Promotion.findByToDate", query = "SELECT p FROM Promotion p WHERE p.toDate = :toDate"),
    @NamedQuery(name = "Promotion.findByPricePromotion", query = "SELECT p FROM Promotion p WHERE p.pricePromotion = :pricePromotion"),
    @NamedQuery(name = "Promotion.findByActive", query = "SELECT p FROM Promotion p WHERE p.active = :active")})
public class Promotion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "promotion_id")
    private Integer promotionId;
    @Size(max = 255)
    @Column(name = "promotion_name")
    private String promotionName;
    @Column(name = "from_date")
    @Temporal(TemporalType.DATE)
    private Date fromDate;
    @Column(name = "to_date")
    @Temporal(TemporalType.DATE)
    private Date toDate;
    @Size(max = 255)
    @Column(name = "price_promotion")
    private String pricePromotion;
    @Column(name = "active")
    private Boolean active;
    @JoinColumn(name = "promotion_type_id", referencedColumnName = "promotion_type_id")
    @ManyToOne
    private PromotionType promotionTypeId;
    @JoinColumn(name = "restaurant_id", referencedColumnName = "restaurant_id")
    @ManyToOne
    private Restaurants restaurantId;

    public Promotion() {
    }

    public Promotion(Integer promotionId) {
        this.promotionId = promotionId;
    }

    public Integer getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Integer promotionId) {
        this.promotionId = promotionId;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
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

    public String getPricePromotion() {
        return pricePromotion;
    }

    public void setPricePromotion(String pricePromotion) {
        this.pricePromotion = pricePromotion;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public PromotionType getPromotionTypeId() {
        return promotionTypeId;
    }

    public void setPromotionTypeId(PromotionType promotionTypeId) {
        this.promotionTypeId = promotionTypeId;
    }

    public Restaurants getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Restaurants restaurantId) {
        this.restaurantId = restaurantId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (promotionId != null ? promotionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Promotion)) {
            return false;
        }
        Promotion other = (Promotion) object;
        if ((this.promotionId == null && other.promotionId != null) || (this.promotionId != null && !this.promotionId.equals(other.promotionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nmhieu.pojo.Promotion[ promotionId=" + promotionId + " ]";
    }
    
}
