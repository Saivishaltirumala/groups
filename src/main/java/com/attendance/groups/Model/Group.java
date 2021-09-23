package com.attendance.groups.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.relational.core.sql.In;

import javax.persistence.*;
import javax.annotation.Generated;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "inst_groups")
public class Group
{
    @Id
    private String id;
    @NotNull
    private String name;
    private boolean isActive=true;
    private String description;

    @ManyToOne
    @JoinColumn(name="instituteid",referencedColumnName = "id")
    private Institute institute;
    @CreationTimestamp
    private LocalDateTime createdOn;
    @UpdateTimestamp
    private LocalDateTime updatedOn;

}
