/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "receipts")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Receipts.findAll", query = "SELECT r FROM Receipts r"),
    @NamedQuery(name = "Receipts.findByReceiptId", query = "SELECT r FROM Receipts r WHERE r.receiptId = :receiptId"),
    @NamedQuery(name = "Receipts.findByReceiptDate", query = "SELECT r FROM Receipts r WHERE r.receiptDate = :receiptDate"),
    @NamedQuery(name = "Receipts.findByTotalPayment", query = "SELECT r FROM Receipts r WHERE r.totalPayment = :totalPayment"),
    @NamedQuery(name = "Receipts.findByActive", query = "SELECT r FROM Receipts r WHERE r.active = :active")})
public class Receipts implements Serializable {

    @OneToMany(mappedBy = "receiptId")
    @JsonIgnore
    private Set<Locationship> locationshipSet;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "receipt_id")
    private Integer receiptId;
    @Column(name = "receipt_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date receiptDate;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "total_payment")
    private BigDecimal totalPayment;
    @Column(name = "active")
    private Boolean active;
    @JsonIgnore
    @OneToMany(mappedBy = "receiptId")
    private Set<ReceiptDetail> receiptDetailSet;
    @JoinColumn(name = "status_receipt_id", referencedColumnName = "status_receipt_id")
    @ManyToOne
    private ReceiptStatus statusReceiptId;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne
    private Users userId;

    public Receipts() {
    }

    public Receipts(Integer receiptId) {
        this.receiptId = receiptId;
    }

    public Integer getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(Integer receiptId) {
        this.receiptId = receiptId;
    }

    public Date getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(Date receiptDate) {
        this.receiptDate = receiptDate;
    }

    public BigDecimal getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(BigDecimal totalPayment) {
        this.totalPayment = totalPayment;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @XmlTransient
    public Set<ReceiptDetail> getReceiptDetailSet() {
        return receiptDetailSet;
    }

    public void setReceiptDetailSet(Set<ReceiptDetail> receiptDetailSet) {
        this.receiptDetailSet = receiptDetailSet;
    }

    public ReceiptStatus getStatusReceiptId() {
        return statusReceiptId;
    }

    public void setStatusReceiptId(ReceiptStatus statusReceiptId) {
        this.statusReceiptId = statusReceiptId;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (receiptId != null ? receiptId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Receipts)) {
            return false;
        }
        Receipts other = (Receipts) object;
        if ((this.receiptId == null && other.receiptId != null) || (this.receiptId != null && !this.receiptId.equals(other.receiptId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tuantran.pojo.Receipts[ receiptId=" + receiptId + " ]";
    }

    @XmlTransient
    public Set<Locationship> getLocationshipSet() {
        return locationshipSet;
    }

    public void setLocationshipSet(Set<Locationship> locationshipSet) {
        this.locationshipSet = locationshipSet;
    }

}
