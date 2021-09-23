package com.attendance.groups.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class UpdateView
{
    private String id;
    private String name;
    private String description;
    private String message;
}
