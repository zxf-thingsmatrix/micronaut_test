package lost.canvas.micronaut_test.common.data.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDateTime;


@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
//
@Embeddable
public class AuditFields implements Serializable {

    @Column(columnDefinition = "bigint comment '创建人id'")
    private Long createBy;

    @Column(columnDefinition = "bigint comment '最近一次更新人id'")
    private Long updateBy;

    @Column(columnDefinition = "datetime comment '创建时间'")
    private LocalDateTime createAt;

    @Column(columnDefinition = "datetime comment '最近一次更新时间'")
    private LocalDateTime updateAt;

}
