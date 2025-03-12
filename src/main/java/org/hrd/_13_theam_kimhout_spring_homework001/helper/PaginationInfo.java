package org.hrd._13_theam_kimhout_spring_homework001.helper;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class PaginationInfo {
    private int totalElements;
    private int currentPage;
    private int pageSize;
    private int totalPages;
}
