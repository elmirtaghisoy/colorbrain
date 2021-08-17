package az.webapp.colorbrain.component.criteria;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@Component
public class TrainingSearchCriteria implements Serializable {
    private String fromDate;
    private String toDate;
    private String header;
    private String trainer;
    private Integer reg;
    private Integer status;
}
