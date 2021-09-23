package com.attendance.groups.Model;

import com.attendance.groups.Exceptions.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Allgroupsview
{
    private int total;
    private Map<String, List<GroupView>> groups;
}
