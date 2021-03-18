package lost.canvas.micronaut_test.entity;

import lombok.*;
import lost.canvas.micronaut_test.common.data.entity.AuditFields;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
//
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "user", indexes = {
        @Index(name = "uk_username", columnList = "username", unique = true),
        @Index(name = "uk_email", columnList = "email", unique = true)
})
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private AuditFields auditFields;

    @Column(columnDefinition = "varchar(50) not null comment '用户名'")
    private String username;

    @Column(columnDefinition = "varchar(50) not null comment '邮箱'")
    private String email;

    @Column(columnDefinition = "varchar(50) comment 'first name'")
    private String firstName;

    @Column(columnDefinition = "varchar(50) comment 'last name'")
    private String lastName;

    @Singular
    @OneToMany(mappedBy = "user")
    @org.hibernate.annotations.ForeignKey(name = "none")
    private Set<UserCompanyRel> userCompanyRels;
}
