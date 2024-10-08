package com.shadcn.identity.dto.request;


import com.shadcn.identity.enums.Status;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StatusUpdateRequest {
    Status status;
}
