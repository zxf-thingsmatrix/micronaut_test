package lost.canvas.micronaut_test.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
//
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "user_company_rel", indexes = {
        @Index(name = "uk_userId_companyId", columnList = "user_id,company_id", unique = true),
        @Index(name = "uk_companyId_owner", columnList = "company_id,owner", unique = true)
})
public class UserCompanyRel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", columnDefinition = "bigint not null comment '用户id'",
            foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", columnDefinition = "bigint not null comment '公司id'",
            foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private Company company;

    @Column(columnDefinition = "tinyint not null comment '状态'")
    private Integer status;

    @Column(columnDefinition = "bit default null comment '是否公司owner'")
    private Boolean owner;

    @Column(columnDefinition = "datetime comment '创建时间'")
    private LocalDateTime createAt;

    @Column(columnDefinition = "bigint comment '创建人id'")
    private Long createBy;
}
