/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "receipt_detail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ReceiptDetail.findAll", query = "SELECT r FROM ReceiptDetail r"),
    @NamedQuery(name = "ReceiptDetail.findByReceiptdetailId", query = "SELECT r FROM ReceiptDetail r WHERE r.receiptdetailId = :receiptdetailId"),
    @NamedQuery(name = "ReceiptDetail.findByQuantity", query = "SELECT r FROM ReceiptDetail r WHERE r.quantity = :quantity"),
    @NamedQuery(name = "ReceiptDetail.findByUnitPrice", query = "SELECT r FROM ReceiptDetail r WHERE r.unitPrice = :unitPrice"),
    @NamedQuery(name = "ReceiptDetail.findByAmount", query = "SELECT r FROM ReceiptDetail r WHERE r.amount = :amount")})
public class ReceiptDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "receiptdetail_id")
    private Integer receiptdetailId;
    @Column(name = "quantity")
    private Integer quantity;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "unitPrice")
    private BigDecimal unitPrice;
    @Column(name = "amount")
    private BigDecimal amount;
    @JoinColumn(name = "fooditem_id", referencedColumnName = "food_id")
    @ManyToOne
    private Fooditems fooditemId;
    @JoinColumn(name = "receipt_id", referencedColumnName = "receipt_id")
    @ManyToOne(cascade = CascadeType.ALL)
    private Receipts receiptId;

    public ReceiptDetail() {
    }

    public ReceiptDetail(Integer receiptdetailId) {
        this.receiptdetailId = receiptdetailId;
    }

    public Integer getReceiptdetailId() {
        return receiptdetailId;
    }

    public void setReceiptdetailId(Integer receiptdetailId) {
        this.receiptdetailId = receiptdetailId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Fooditems getFooditemId() {
        return fooditemId;
    }

    public void setFooditemId(Fooditems fooditemId) {
        this.fooditemId = fooditemId;
    }

    public Receipts getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(Receipts receiptId) {
        this.receiptId = receiptId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (receiptdetailId != null ? receiptdetailId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReceiptDetail)) {
            return false;
        }
        ReceiptDetail other = (ReceiptDetail) object;
        if ((this.receiptdetailId == null && other.receiptdetailId != null) || (this.receiptdetailId != null && !this.receiptdetailId.equals(other.receiptdetailId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tuantran.pojo.ReceiptDetail[ receiptdetailId=" + receiptdetailId + " ]";
    }

}
