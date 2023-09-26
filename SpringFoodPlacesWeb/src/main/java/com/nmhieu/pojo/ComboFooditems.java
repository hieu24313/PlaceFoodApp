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
@Table(name = "combo_fooditems")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ComboFooditems.findAll", query = "SELECT c FROM ComboFooditems c"),
    @NamedQuery(name = "ComboFooditems.findByComboFooditemsId", query = "SELECT c FROM ComboFooditems c WHERE c.comboFooditemsId = :comboFooditemsId"),
    @NamedQuery(name = "ComboFooditems.findByActive", query = "SELECT c FROM ComboFooditems c WHERE c.active = :active")})
public class ComboFooditems implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "combo_fooditems_id")
    private Integer comboFooditemsId;
    @Column(name = "active")
    private Boolean active;
    @JoinColumn(name = "combo_id", referencedColumnName = "combo_id")
    @ManyToOne
    private Combo comboId;
    @JoinColumn(name = "food_id", referencedColumnName = "food_id")
    @ManyToOne
    private Fooditems foodId;

    public ComboFooditems() {
    }

    public ComboFooditems(Integer comboFooditemsId) {
        this.comboFooditemsId = comboFooditemsId;
    }

    public Integer getComboFooditemsId() {
        return comboFooditemsId;
    }

    public void setComboFooditemsId(Integer comboFooditemsId) {
        this.comboFooditemsId = comboFooditemsId;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Combo getComboId() {
        return comboId;
    }

    public void setComboId(Combo comboId) {
        this.comboId = comboId;
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
        hash += (comboFooditemsId != null ? comboFooditemsId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComboFooditems)) {
            return false;
        }
        ComboFooditems other = (ComboFooditems) object;
        if ((this.comboFooditemsId == null && other.comboFooditemsId != null) || (this.comboFooditemsId != null && !this.comboFooditemsId.equals(other.comboFooditemsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nmhieu.pojo.ComboFooditems[ comboFooditemsId=" + comboFooditemsId + " ]";
    }
    
}
