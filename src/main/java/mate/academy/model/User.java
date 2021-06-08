package mate.academy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    @Column(unique = true)
    private String email;
    private String password;
    private byte[] salt;
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public byte[] getSalt() {
        return salt;
    }
    
    public void setSalt(byte[] salt) {
        this.salt = salt;
    }
    
    @Override
    public String toString() {
        return "User{"
                + ", email='" + email + '\''
                + ", password='" + password + '\''
                + '}';
    }
}
