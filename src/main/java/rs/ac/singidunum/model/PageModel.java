package rs.ac.singidunum.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class PageModel<T> {
    private List<T> content;
    private PageableModel pageable;
    private Integer totalPages;
    private Integer totalElements;
    private Boolean last;
    private Integer size;
    private Integer number;
    private SortModel sort;
    private Integer numberOfElements;
    private Boolean first;
    private Boolean empty;
}
