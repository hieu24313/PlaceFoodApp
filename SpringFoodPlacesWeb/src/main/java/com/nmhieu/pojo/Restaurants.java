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
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "restaurants")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Restaurants.findAll", query = "SELECT r FROM Restaurants r"),
    @NamedQuery(name = "Restaurants.findByRestaurantId", query = "SELECT r FROM Restaurants r WHERE r.restaurantId = :restaurantId"),
    @NamedQuery(name = "Restaurants.findByRestaurantName", query = "SELECT r FROM Restaurants r WHERE r.restaurantName = :restaurantName"),
    @NamedQuery(name = "Restaurants.findByAvatar", query = "SELECT r FROM Restaurants r WHERE r.avatar = :avatar"),
    @NamedQuery(name = "Restaurants.findByLocation", query = "SELECT r FROM Restaurants r WHERE r.location = :location"),
    @NamedQuery(name = "Restaurants.findByConfirmationStatus", query = "SELECT r FROM Restaurants r WHERE r.confirmationStatus = :confirmationStatus"),
    @NamedQuery(name = "Restaurants.findByMapLink", query = "SELECT r FROM Restaurants r WHERE r.mapLink = :mapLink"),
    @NamedQuery(name = "Restaurants.findByActive", query = "SELECT r FROM Restaurants r WHERE r.active = :active")})
public class Restaurants implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "restaurant_id")
    private Integer restaurantId;
    @Size(max = 255)
    @Column(name = "restaurant_name")
    private String restaurantName;
    @Size(max = 255)
    @Column(name = "avatar")
    private String avatar;
    @Size(max = 255)
    @Column(name = "location")
    private String location;
    @Column(name = "confirmation_status")
    private Boolean confirmationStatus;
    @Size(max = 255)
    @Column(name = "map_link")
    private String mapLink;
    @Column(name = "active")
    private Boolean active;
    @JsonIgnore
    @OneToMany(mappedBy = "restaurantId")
    private Set<Comments> commentsSet;
    @JsonIgnore
    @OneToMany(mappedBy = "restaurantId")
    private Set<CategoriesFood> categoriesFoodSet;
    @JsonIgnore
    @OneToMany(mappedBy = "restaurantIdIndex")
    private Set<Follow> followSet;
    @JsonIgnore
    @OneToMany(mappedBy = "restaurantId")
    private Set<ShelfLife> shelfLifeSet;
    @JoinColumn(name = "restaurant_status", referencedColumnName = "status_id")
    @ManyToOne
    private RestaurantStatus restaurantStatus;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne
    private Users userId;
    @JsonIgnore
    @OneToMany(mappedBy = "restaurantId")
    private Set<Fooditems> fooditemsSet;
    @JsonIgnore
    @OneToMany(mappedBy = "restaurantId")
    private Set<Notifications> notificationsSet;

    @Transient
    private MultipartFile file;

    /**
     * @return the file
     */
    public MultipartFile getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public Restaurants() {
    }

    public Restaurants(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getConfirmationStatus() {
        return confirmationStatus;
    }

    public void setConfirmationStatus(Boolean confirmationStatus) {
        this.confirmationStatus = confirmationStatus;
    }

    public String getMapLink() {
        return mapLink;
    }

    public void setMapLink(String mapLink) {
        this.mapLink = mapLink;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @XmlTransient
    public Set<Comments> getCommentsSet() {
        return commentsSet;
    }

    public void setCommentsSet(Set<Comments> commentsSet) {
        this.commentsSet = commentsSet;
    }

    @XmlTransient
    public Set<CategoriesFood> getCategoriesFoodSet() {
        return categoriesFoodSet;
    }

    public void setCategoriesFoodSet(Set<CategoriesFood> categoriesFoodSet) {
        this.categoriesFoodSet = categoriesFoodSet;
    }

    @XmlTransient
    public Set<Follow> getFollowSet() {
        return followSet;
    }

    public void setFollowSet(Set<Follow> followSet) {
        this.followSet = followSet;
    }

    @XmlTransient
    public Set<ShelfLife> getShelfLifeSet() {
        return shelfLifeSet;
    }

    public void setShelfLifeSet(Set<ShelfLife> shelfLifeSet) {
        this.shelfLifeSet = shelfLifeSet;
    }

    public RestaurantStatus getRestaurantStatus() {
        return restaurantStatus;
    }

    public void setRestaurantStatus(RestaurantStatus restaurantStatus) {
        this.restaurantStatus = restaurantStatus;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    @XmlTransient
    public Set<Fooditems> getFooditemsSet() {
        return fooditemsSet;
    }

    public void setFooditemsSet(Set<Fooditems> fooditemsSet) {
        this.fooditemsSet = fooditemsSet;
    }

    @XmlTransient
    public Set<Notifications> getNotificationsSet() {
        return notificationsSet;
    }

    public void setNotificationsSet(Set<Notifications> notificationsSet) {
        this.notificationsSet = notificationsSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (restaurantId != null ? restaurantId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Restaurants)) {
            return false;
        }
        Restaurants other = (Restaurants) object;
        if ((this.restaurantId == null && other.restaurantId != null) || (this.restaurantId != null && !this.restaurantId.equals(other.restaurantId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tuantran.pojo.Restaurants[ restaurantId=" + restaurantId + " ]";
    }

}
