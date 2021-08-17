package az.webapp.colorbrain.component.criteria;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class MediaSearchCriteria {
    private String fromDate;
    private String toDate;
    private String header;
}
