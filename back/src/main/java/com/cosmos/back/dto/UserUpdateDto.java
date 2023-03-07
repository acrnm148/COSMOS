package com.cosmos.back.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateDto {
    private Long userSeq; //수정불가
    private boolean coupleYn;
    private Long coupleUserId;
}
