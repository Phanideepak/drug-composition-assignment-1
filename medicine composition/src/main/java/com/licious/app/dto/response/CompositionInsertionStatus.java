package com.licious.app.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CompositionInsertionStatus {
    String compositionName;
    String insertionStatus;
}
