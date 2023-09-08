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
@Table(name = "restaurant_status")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RestaurantStatus.findAll", query = "SELECT r FROM RestaurantStatus r"),
    @NamedQuery(name = "RestaurantStatus.findByStatusId", query = "SELECT r FROM RestaurantStatus r WHERE r.statusId = :statusId"),
    @NamedQuery(name = "RestaurantStatus.findByRestaurantStatus", query = "SELECT r FROM RestaurantStatus r WHERE r.restaurantStatus = :restaurantStatus"),
    @NamedQuery(name = "RestaurantStatus.findByActive", query = "SELECT r FROM RestaurantStatus r WHERE r.active = :active")})
public class RestaurantStatus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "status_id")
    private Integer statusId;
    @Size(max = 255)
    @Column(name = "restaurant_status")
    private String restaurantStatus;
    @Column(name = "active")
    private Boolean active;
    @JsonIgnore
    @OneToMany(mappedBy = "restaurantStatus")
    private Set<Restaurants> restaurantsSet;

    public RestaurantStatus() {
    }

    public RestaurantStatus(Integer statusId) {
        this.statusId = statusId;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public String getRestaurantStatus() {
        return restaurantStatus;
    }

    public void setRestaurantStatus(String restaurantStatus) {
        this.restaurantStatus = restaurantStatus;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @XmlTransient
    public Set<Restaurants> getRestaurantsSet() {
        return restaurantsSet;
    }

    public void setRestaurantsSet(Set<Restaurants> restaurantsSet) {
        this.restaurantsSet = restaurantsSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (statusId != null ? statusId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RestaurantStatus)) {
            return false;
        }
        RestaurantStatus other = (RestaurantStatus) object;
        if ((this.statusId == null && other.statusId != null) || (this.statusId != null && !this.statusId.equals(other.statusId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tuantran.pojo.RestaurantStatus[ statusId=" + statusId + " ]";
    }

}
