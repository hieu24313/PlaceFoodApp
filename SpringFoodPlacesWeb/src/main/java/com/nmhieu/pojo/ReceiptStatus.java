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
@Table(name = "receipt_status")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ReceiptStatus.findAll", query = "SELECT r FROM ReceiptStatus r"),
    @NamedQuery(name = "ReceiptStatus.findByStatusReceiptId", query = "SELECT r FROM ReceiptStatus r WHERE r.statusReceiptId = :statusReceiptId"),
    @NamedQuery(name = "ReceiptStatus.findByStatusReceipt", query = "SELECT r FROM ReceiptStatus r WHERE r.statusReceipt = :statusReceipt"),
    @NamedQuery(name = "ReceiptStatus.findByActive", query = "SELECT r FROM ReceiptStatus r WHERE r.active = :active")})
public class ReceiptStatus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "status_receipt_id")
    private Integer statusReceiptId;
    @Size(max = 255)
    @Column(name = "status_receipt")
    private String statusReceipt;
    @Column(name = "active")
    private Boolean active;
    @JsonIgnore
    @OneToMany(mappedBy = "statusReceiptId")
    private Set<Receipts> receiptsSet;

    public ReceiptStatus() {
    }

    public ReceiptStatus(Integer statusReceiptId) {
        this.statusReceiptId = statusReceiptId;
    }

    public Integer getStatusReceiptId() {
        return statusReceiptId;
    }

    public void setStatusReceiptId(Integer statusReceiptId) {
        this.statusReceiptId = statusReceiptId;
    }

    public String getStatusReceipt() {
        return statusReceipt;
    }

    public void setStatusReceipt(String statusReceipt) {
        this.statusReceipt = statusReceipt;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @XmlTransient
    public Set<Receipts> getReceiptsSet() {
        return receiptsSet;
    }

    public void setReceiptsSet(Set<Receipts> receiptsSet) {
        this.receiptsSet = receiptsSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (statusReceiptId != null ? statusReceiptId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReceiptStatus)) {
            return false;
        }
        ReceiptStatus other = (ReceiptStatus) object;
        if ((this.statusReceiptId == null && other.statusReceiptId != null) || (this.statusReceiptId != null && !this.statusReceiptId.equals(other.statusReceiptId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tuantran.pojo.ReceiptStatus[ statusReceiptId=" + statusReceiptId + " ]";
    }

}
