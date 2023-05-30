package rs.ac.singidunum.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PageableModel {
    private SortModel sort;
    private Integer offset;
    private Integer pageNumber;
    private Integer pageSize;
    private Boolean paged;
    private Boolean unpaged;
}
