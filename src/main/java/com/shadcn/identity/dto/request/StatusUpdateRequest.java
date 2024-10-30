package com.shadcn.identity.dto.request;


import com.shadcn.identity.enums.Status;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StatusUpdateRequest {
    List<Long> ids;
    Status status;
}
