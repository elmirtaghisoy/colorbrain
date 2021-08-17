package az.webapp.colorbrain.component.mapper;

import az.webapp.colorbrain.model.dto.request.TrainingRequest;
import az.webapp.colorbrain.model.entity.TrainingEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class CustomMapper {

    public abstract TrainingEntity requestToEntity(TrainingRequest request);

}
