package com.jdbc.demo.domain.security;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
 
@Entity
@Table(name="UserRoles")
public class UserRole {
 
    @Id
    @Column(name = "user_roles_id")
    @SequenceGenerator(sequenceName = "USER_ROLES_ID_SEQ", name = "UserRolessIdSequence")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UserRolessIdSequence")
    private Integer id; 
 
    @Column(name="type", length=15, unique=true, nullable=false)
    private String type = UserRoleType.USER.getUserRoleType();
     
    public Integer getId() {
        return id;
    }
 
    public void setId(int id) {
        this.id = id;
    }
 
    public String getType() {
        return type;
    }
 
    public void setType(String type) {
        this.type = type;
    }
 
 
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = id;
        result = prime * result + id;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }
 
    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null)
            return false;
        if (!(object instanceof UserRole))
            return false;
        UserRole another = (UserRole) object;
        if (id != another.id)
            return false;
        if (type == null) {
            if (another.type != null)
                return false;
        } else if (!type.equals(another.type))
            return false;
        return true;
    }
 
    @Override
    public String toString() {
        return "UserRole [id=" + id + ",  type=" + type  + "]";
    }
     
 
}