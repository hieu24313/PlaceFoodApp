/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "fooditems")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fooditems.findAll", query = "SELECT f FROM Fooditems f"),
    @NamedQuery(name = "Fooditems.findByFoodId", query = "SELECT f FROM Fooditems f WHERE f.foodId = :foodId"),
    @NamedQuery(name = "Fooditems.findByFoodName", query = "SELECT f FROM Fooditems f WHERE f.foodName = :foodName"),
    @NamedQuery(name = "Fooditems.findByAvatar", query = "SELECT f FROM Fooditems f WHERE f.avatar = :avatar"),
    @NamedQuery(name = "Fooditems.findByPrice", query = "SELECT f FROM Fooditems f WHERE f.price = :price"),
    @NamedQuery(name = "Fooditems.findByAvailable", query = "SELECT f FROM Fooditems f WHERE f.available = :available"),
    @NamedQuery(name = "Fooditems.findByDescription", query = "SELECT f FROM Fooditems f WHERE f.description = :description"),
    @NamedQuery(name = "Fooditems.findByActive", query = "SELECT f FROM Fooditems f WHERE f.active = :active")})
public class Fooditems implements Serializable {

    @OneToMany(mappedBy = "foodId")
    @JsonIgnore
    private Set<ComboFooditems> comboFooditemsSet;
    @OneToMany(mappedBy = "foodId")
    @JsonIgnore
    private Set<PromotionFooditems> promotionFooditemsSet;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "food_id")
    private Integer foodId;
    @Size(max = 255)
    @Column(name = "food_name")
    private String foodName;
    @Size(max = 255)
    @Column(name = "avatar")
    private String avatar;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "available")
    private Boolean available;
    @Size(max = 255)
    @Column(name = "description")
    private String description;
    @Column(name = "active")
    private Boolean active;
    @JsonIgnore
    @OneToMany(mappedBy = "foodId")
    private Set<Comments> commentsSet;
    @JsonIgnore
    @OneToMany(mappedBy = "fooditemId")
    private Set<ReceiptDetail> receiptDetailSet;
    @JoinColumn(name = "categoryfood_id", referencedColumnName = "categoryfood_id")
    @ManyToOne
    private CategoriesFood categoryfoodId;
    @JoinColumn(name = "restaurant_id", referencedColumnName = "restaurant_id")
    @ManyToOne
    private Restaurants restaurantId;
    @JoinColumn(name = "shelflife_id", referencedColumnName = "shelflife_id")
    @ManyToOne
    private ShelfLife shelflifeId;

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

    public Fooditems() {
    }

    public Fooditems(Integer foodId) {
        this.foodId = foodId;
    }

    public Integer getFoodId() {
        return foodId;
    }

    public void setFoodId(Integer foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
    public Set<ReceiptDetail> getReceiptDetailSet() {
        return receiptDetailSet;
    }

    public void setReceiptDetailSet(Set<ReceiptDetail> receiptDetailSet) {
        this.receiptDetailSet = receiptDetailSet;
    }

    public CategoriesFood getCategoryfoodId() {
        return categoryfoodId;
    }

    public void setCategoryfoodId(CategoriesFood categoryfoodId) {
        this.categoryfoodId = categoryfoodId;
    }

    public Restaurants getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Restaurants restaurantId) {
        this.restaurantId = restaurantId;
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
        hash += (foodId != null ? foodId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Fooditems)) {
            return false;
        }
        Fooditems other = (Fooditems) object;
        if ((this.foodId == null && other.foodId != null) || (this.foodId != null && !this.foodId.equals(other.foodId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tuantran.pojo.Fooditems[ foodId=" + foodId + " ]";
    }

    @XmlTransient
    public Set<ComboFooditems> getComboFooditemsSet() {
        return comboFooditemsSet;
    }

    public void setComboFooditemsSet(Set<ComboFooditems> comboFooditemsSet) {
        this.comboFooditemsSet = comboFooditemsSet;
    }

    @XmlTransient
    public Set<PromotionFooditems> getPromotionFooditemsSet() {
        return promotionFooditemsSet;
    }

    public void setPromotionFooditemsSet(Set<PromotionFooditems> promotionFooditemsSet) {
        this.promotionFooditemsSet = promotionFooditemsSet;
    }

}
