package org.hrd._13_theam_kimhout_spring_homework001.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hrd._13_theam_kimhout_spring_homework001.helper.PaginationInfo;
import org.hrd._13_theam_kimhout_spring_homework001.model.Ticket;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResponseListTicket {
    ArrayList<Ticket> tickets;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    PaginationInfo pagination;
}
