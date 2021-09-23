package com.attendance.groups.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "institutions")
public class Institute
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id= UUID.randomUUID().toString();
    @NotNull
    private String registrationId;
    private String name;
    private long phoneNum;
    private String email;
    private String address;
    private boolean active=true;
    @CreationTimestamp
    private LocalDateTime createdOn;

    @UpdateTimestamp
    private LocalDateTime updatedOn;
}
