package com.attendance.groups.Exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetails
{

    private Date timestamp;
    private int errorCode;
    private String message;
}
