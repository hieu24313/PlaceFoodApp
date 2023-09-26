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
@Table(name = "promotion_type")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PromotionType.findAll", query = "SELECT p FROM PromotionType p"),
    @NamedQuery(name = "PromotionType.findByPromotionTypeId", query = "SELECT p FROM PromotionType p WHERE p.promotionTypeId = :promotionTypeId"),
    @NamedQuery(name = "PromotionType.findByPromotionTypeName", query = "SELECT p FROM PromotionType p WHERE p.promotionTypeName = :promotionTypeName"),
    @NamedQuery(name = "PromotionType.findByActive", query = "SELECT p FROM PromotionType p WHERE p.active = :active")})
public class PromotionType implements Serializable {

    @OneToMany(mappedBy = "promotionTypeId")
    @JsonIgnore
    private Set<Promotion> promotionSet;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "promotion_type_id")
    private Integer promotionTypeId;
    @Size(max = 255)
    @Column(name = "promotion_type_name")
    private String promotionTypeName;
    @Column(name = "active")
    private Boolean active;

    public PromotionType() {
    }

    public PromotionType(Integer promotionTypeId) {
        this.promotionTypeId = promotionTypeId;
    }

    public Integer getPromotionTypeId() {
        return promotionTypeId;
    }

    public void setPromotionTypeId(Integer promotionTypeId) {
        this.promotionTypeId = promotionTypeId;
    }

    public String getPromotionTypeName() {
        return promotionTypeName;
    }

    public void setPromotionTypeName(String promotionTypeName) {
        this.promotionTypeName = promotionTypeName;
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
        hash += (promotionTypeId != null ? promotionTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PromotionType)) {
            return false;
        }
        PromotionType other = (PromotionType) object;
        if ((this.promotionTypeId == null && other.promotionTypeId != null) || (this.promotionTypeId != null && !this.promotionTypeId.equals(other.promotionTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nmhieu.pojo.PromotionType[ promotionTypeId=" + promotionTypeId + " ]";
    }

    @XmlTransient
    public Set<Promotion> getPromotionSet() {
        return promotionSet;
    }

    public void setPromotionSet(Set<Promotion> promotionSet) {
        this.promotionSet = promotionSet;
    }
    
}
