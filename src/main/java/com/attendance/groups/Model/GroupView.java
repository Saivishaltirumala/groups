package com.attendance.groups.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupView
{

    private String id;
    private String name;
    private String description;
    private boolean isActive;
}
