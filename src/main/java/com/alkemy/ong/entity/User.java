package com.alkemy.ong.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.ForeignKey;
import javax.persistence.CascadeType;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE users SET soft_delete = TRUE WHERE id=?")
@Where(clause = "soft_delete = false")
public class User {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	
    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String photo;

    private Timestamp timestamp = Timestamp.from(Instant.now());

    
    @JoinColumn(name = "role_id",nullable = false,foreignKey=@ForeignKey(name = "Fk_role_id"))
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH})
    private Role roleId;
    

    @Column(name = "soft_delete")
    private boolean softDelete = Boolean.FALSE;


}
