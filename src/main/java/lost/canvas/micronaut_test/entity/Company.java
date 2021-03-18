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
@AllArgsConstructor
@NoArgsConstructor
//
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "company", indexes = {
        @Index(name = "uk_name", columnList = "name", unique = true)
})
public class Company implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private AuditFields auditFields;

    @Column(columnDefinition = "varchar(50) not null comment '公司名称'")
    private String name;

    @Column(columnDefinition = "tinyint not null comment '公司类型'")
    private Integer type;

    @Singular
    @OneToMany(mappedBy = "company")
    @org.hibernate.annotations.ForeignKey(name = "none")
    private Set<UserCompanyRel> userCompanyRels;
}
