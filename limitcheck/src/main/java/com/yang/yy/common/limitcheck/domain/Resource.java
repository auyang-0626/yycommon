package com.yang.yy.common.limitcheck.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@Data
@Entity
@Table(name = "t_resource")
public class Resource implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false,unique = true)
    private String name;
    @Column(name = "modify_count")
    private Long modifyCount;

    public Resource(String name, Long modifyCount) {
        this.name = name;
        this.modifyCount = modifyCount;
    }
}
