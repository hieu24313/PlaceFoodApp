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
@Table(name = "chatmessages")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Chatmessages.findAll", query = "SELECT c FROM Chatmessages c"),
    @NamedQuery(name = "Chatmessages.findByMessageId", query = "SELECT c FROM Chatmessages c WHERE c.messageId = :messageId"),
    @NamedQuery(name = "Chatmessages.findByMessageContent", query = "SELECT c FROM Chatmessages c WHERE c.messageContent = :messageContent"),
    @NamedQuery(name = "Chatmessages.findByCreatedDate", query = "SELECT c FROM Chatmessages c WHERE c.createdDate = :createdDate"),
    @NamedQuery(name = "Chatmessages.findByActive", query = "SELECT c FROM Chatmessages c WHERE c.active = :active")})
public class Chatmessages implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "message_id")
    private Integer messageId;
    @Size(max = 255)
    @Column(name = "message_content")
    private String messageContent;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "active")
    private Boolean active;
    @JoinColumn(name = "sender_id", referencedColumnName = "user_id")
    @ManyToOne
    private Users senderId;
    @JoinColumn(name = "receiver_id", referencedColumnName = "user_id")
    @ManyToOne
    private Users receiverId;

    public Chatmessages() {
    }

    public Chatmessages(Integer messageId) {
        this.messageId = messageId;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Users getSenderId() {
        return senderId;
    }

    public void setSenderId(Users senderId) {
        this.senderId = senderId;
    }

    public Users getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Users receiverId) {
        this.receiverId = receiverId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (messageId != null ? messageId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Chatmessages)) {
            return false;
        }
        Chatmessages other = (Chatmessages) object;
        if ((this.messageId == null && other.messageId != null) || (this.messageId != null && !this.messageId.equals(other.messageId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tuantran.pojo.Chatmessages[ messageId=" + messageId + " ]";
    }

}
