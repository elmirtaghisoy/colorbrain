package az.webapp.colorbrain.component.criteria;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ProjectSearchCriteria {
    private String fromDate;
    private String toDate;
    private String header;
    private Integer status;
    private Integer reg;
    //    private String trainer;
}
