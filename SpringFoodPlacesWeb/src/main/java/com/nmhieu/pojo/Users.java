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
@Table(name = "users")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u"),
    @NamedQuery(name = "Users.findByUserId", query = "SELECT u FROM Users u WHERE u.userId = :userId"),
    @NamedQuery(name = "Users.findByFirstname", query = "SELECT u FROM Users u WHERE u.firstname = :firstname"),
    @NamedQuery(name = "Users.findByLastname", query = "SELECT u FROM Users u WHERE u.lastname = :lastname"),
    @NamedQuery(name = "Users.findByPhonenumber", query = "SELECT u FROM Users u WHERE u.phonenumber = :phonenumber"),
    @NamedQuery(name = "Users.findByUsername", query = "SELECT u FROM Users u WHERE u.username = :username"),
    @NamedQuery(name = "Users.findByPassword", query = "SELECT u FROM Users u WHERE u.password = :password"),
    @NamedQuery(name = "Users.findByAvatar", query = "SELECT u FROM Users u WHERE u.avatar = :avatar"),
    @NamedQuery(name = "Users.findByLocation", query = "SELECT u FROM Users u WHERE u.location = :location"),
    @NamedQuery(name = "Users.findByActive", query = "SELECT u FROM Users u WHERE u.active = :active"),
    @NamedQuery(name = "Users.findByEmail", query = "SELECT u FROM Users u WHERE u.email = :email"),
    @NamedQuery(name = "Users.findByOtp", query = "SELECT u FROM Users u WHERE u.otp = :otp")})
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "user_id")
    private Integer userId;
    @Size(max = 255)
    @Column(name = "firstname")
    private String firstname;
    @Size(max = 255)
    @Column(name = "lastname")
    private String lastname;
    @Size(max = 255)
    @Column(name = "phonenumber")
    private String phonenumber;
    @Size(max = 255)
    @Column(name = "username")
    private String username;
    @Size(max = 255)
    @Column(name = "password")
    private String password;
    @Size(max = 255)
    @Column(name = "avatar")
    private String avatar;
    @Size(max = 255)
    @Column(name = "location")
    private String location;
    @Column(name = "active")
    private Boolean active;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 255)
    @Column(name = "email")
    private String email;
    @Size(max = 255)
    @Column(name = "otp")
    private String otp;
    @JsonIgnore
    @OneToMany(mappedBy = "senderId")
    private Set<Chatmessages> chatmessagesSet;
    @JsonIgnore
    @OneToMany(mappedBy = "receiverId")
    private Set<Chatmessages> chatmessagesSet1;
    @JsonIgnore
    @OneToMany(mappedBy = "userId")
    private Set<Comments> commentsSet;
    @JsonIgnore
    @OneToMany(mappedBy = "userIdIndex")
    private Set<Follow> followSet;
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    @ManyToOne
    private Roles roleId;
    @JsonIgnore
    @OneToMany(mappedBy = "userId")
    private Set<Receipts> receiptsSet;
    @JsonIgnore
    @OneToMany(mappedBy = "userId")
    private Set<Restaurants> restaurantsSet;
    @JsonIgnore
    @OneToMany(mappedBy = "userId")
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

    @Transient
    private String confirmPassword;

    public String getConfirmPassword() {
        return confirmPassword;
    }

    /**
     * @param confirmPassword the confirmPassword to set
     */
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Transient
    private String confirmOTP;

    /**
     * @return the confirmOTP
     */
    public String getConfirmOTP() {
        return confirmOTP;
    }

    /**
     * @param confirmOTP the confirmOTP to set
     */
    public void setConfirmOTP(String confirmOTP) {
        this.confirmOTP = confirmOTP;
    }

    public Users() {
    }

    public Users(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    @XmlTransient
    public Set<Chatmessages> getChatmessagesSet() {
        return chatmessagesSet;
    }

    public void setChatmessagesSet(Set<Chatmessages> chatmessagesSet) {
        this.chatmessagesSet = chatmessagesSet;
    }

    @XmlTransient
    public Set<Chatmessages> getChatmessagesSet1() {
        return chatmessagesSet1;
    }

    public void setChatmessagesSet1(Set<Chatmessages> chatmessagesSet1) {
        this.chatmessagesSet1 = chatmessagesSet1;
    }

    @XmlTransient
    public Set<Comments> getCommentsSet() {
        return commentsSet;
    }

    public void setCommentsSet(Set<Comments> commentsSet) {
        this.commentsSet = commentsSet;
    }

    @XmlTransient
    public Set<Follow> getFollowSet() {
        return followSet;
    }

    public void setFollowSet(Set<Follow> followSet) {
        this.followSet = followSet;
    }

    public Roles getRoleId() {
        return roleId;
    }

    public void setRoleId(Roles roleId) {
        this.roleId = roleId;
    }

    @XmlTransient
    public Set<Receipts> getReceiptsSet() {
        return receiptsSet;
    }

    public void setReceiptsSet(Set<Receipts> receiptsSet) {
        this.receiptsSet = receiptsSet;
    }

    @XmlTransient
    public Set<Restaurants> getRestaurantsSet() {
        return restaurantsSet;
    }

    public void setRestaurantsSet(Set<Restaurants> restaurantsSet) {
        this.restaurantsSet = restaurantsSet;
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
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tuantran.pojo.Users[ userId=" + userId + " ]";
    }

}
