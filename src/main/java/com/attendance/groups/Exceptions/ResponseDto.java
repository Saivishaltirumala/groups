package com.attendance.groups.Exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDto
{
    private Object success;
    private ErrorDetails error;
}
