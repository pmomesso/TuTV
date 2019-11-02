package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
    @SequenceGenerator(sequenceName = "users_id_seq", name = "users_id_seq", allocationSize = 1)
    @Column(name = "id")
    private long id;

    @Column(name = "username", length = 50)
    private String userName;

    @Column(name = "mail")
    private String mailAddress;

    @Column(name = "password")
    private String password;

    private Date birthDate;

    @Column(name = "confirmation_key", length = 60)
    private String confirmationKey;

    @Column(name = "isadmin", nullable = false)
    private boolean isAdmin = false;

    @Column(name = "isbanned", nullable = false)
    private boolean isBanned = false;

    @Column(name = "avatar")
    private byte[] userAvatar;

    public User() {}

    public User(String userName,String password,String mailAddress,boolean isAdmin){
        this.userName = userName;
        this.password = password;
        this.mailAddress = mailAddress;
        this.isAdmin = isAdmin;
    }
    public String getConfirmationKey() {
        return confirmationKey;
    }
    public void setConfirmationKey(String confirmationKey) {
        this.confirmationKey = confirmationKey;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMailAddress() {
        return mailAddress;
    }
    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean getIsBanned() {
        return isBanned;
    }

    public void setIsBanned(boolean banned) {
        isBanned = banned;
    }

    public void setUserAvatar(byte[] userAvatar) {
        this.userAvatar = userAvatar;
    }
    
    public byte[] getUserAvatar() {
        return userAvatar;
    }
}
