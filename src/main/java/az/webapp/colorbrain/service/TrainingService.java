package az.webapp.colorbrain.service;

import az.webapp.colorbrain.component.CustomFile;
import az.webapp.colorbrain.component.criteria.TrainingSearchCriteria;
import az.webapp.colorbrain.component.mapper.CustomMapper;
import az.webapp.colorbrain.component.paging.Paged;
import az.webapp.colorbrain.component.paging.Paging;
import az.webapp.colorbrain.component.query.SearchQueries;
import az.webapp.colorbrain.model.dto.request.TrainingRequest;
import az.webapp.colorbrain.model.dto.response.StandardResponse;
import az.webapp.colorbrain.model.entity.FileEntity;
import az.webapp.colorbrain.model.entity.TrainingEntity;
import az.webapp.colorbrain.repository.FileRepository;
import az.webapp.colorbrain.repository.TrainingRepository;
import az.webapp.colorbrain.repository.view.DeleteFileView;
import az.webapp.colorbrain.repository.view.GetView;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static az.webapp.colorbrain.config.ApplicationConstants.TRAINING;
import static az.webapp.colorbrain.config.ApplicationConstants.TRAINING_DEFAULT_IMG_PATH;
import static az.webapp.colorbrain.config.MvcConfig.uploadPath;
import static az.webapp.colorbrain.util.CommonUtils.generateRandomFolderName;

@Service
@RequiredArgsConstructor
public class TrainingService {

    private final TrainingRepository trainingRepository;
    private final FileRepository fileRepository;
    private final CustomMapper customMapper;

    public TrainingEntity getOneTrainingById(Long id) {
        return trainingRepository.getOne(id);
    }

    public StandardResponse saveTraining(TrainingRequest request, List<MultipartFile> files) throws IOException {
        List<ObjectError> errors = ServiceUtil.validateRegistrationParams(request);
        if (errors.isEmpty()) {

            String uuidFolderName = generateRandomFolderName();
            request.setFolderUuid(uuidFolderName);

            if (Objects.nonNull(request.getCover()) && request.getCover().isEmpty()) {
                request.setCoverPath(TRAINING_DEFAULT_IMG_PATH);
            } else {
                CustomFile file = CustomFile.builder()
                        .category(TRAINING)
                        .folder(uuidFolderName)
                        .file(request.getCover())
                        .build();
                request.setCoverPath(FileService.saveSingle(file));
            }

            if (Objects.nonNull(files) && !files.get(0).isEmpty()) {
                request.setFileEntities(FileService.saveMultiple(TRAINING, uuidFolderName, files));
            }

            return new StandardResponse(trainingRepository.save(customMapper.requestToEntity(request)), HttpStatus.OK);
        } else {
            return new StandardResponse(errors, HttpStatus.BAD_REQUEST);
        }
    }

    public void updateTraining(TrainingEntity trainingEntity) throws IOException {

        if (/*checkbox=*/ true) {

            if (trainingEntity.getStartRegistrationDay().isBefore(trainingEntity.getLastRegistrationDay())) {
                //TODO => bashlangic qeydiyyat tarixi son qeydiyyat tarixinden evvel olmalidir.
            }

            if (trainingEntity.getLastRegistrationDay().isBefore(trainingEntity.getStartDate())) {
                //TODO son qeydiyyat tarixi bashlangic tarixinden evvel olmalidir.
            }

            if (trainingEntity.getStartRegistrationDay().isEqual(LocalDate.now())) {
                //TODO qeyiyyat statusu aktiv olsun.
            } else {
                //TODO qeydiyyat statusu gozlemede olsun.
            }

        } else {
            //TODO qeydiyyat yoxdur yeni qeydiyyat stausu yoxdur olsun.
        }

        if (/*telim bashlangic vaxtinnan evvel legv olarsa=*/true) {
            //TODO => info to registrated user
        }

        if (/*telimin bashlangic vaxti deyishdirilerse =*/true) {
            //TODO => info to registrated user
        }

        if (/*qeydyyat tarixlerinde deyishiklik olasa=*/true) {
            //TODO => INFO to registrated user
        }

        if (/*bashlama tarixlerinde deyishiklik olasa=*/true) {
            //TODO => INFO to registrated user
        }


//        var trainingEntityFromDB = getOneTrainingById(trainingEntity.getId());
//        trainingEntity.setCreatedAt(trainingEntityFromDB.getCreatedAt());
//        trainingEntity.setUpdatedAt(LocalDateTime.now());
//        if (trainingEntity.getCover().isEmpty()) {
//            trainingEntity.setCoverPath(trainingEntityFromDB.getCoverPath());
//        } else {
//            String folderUUID = trainingRepository.getFolderUUID(trainingEntity.getId());
//            CustomFile file = CustomFile.builder()
//                    .category(TRAINING)
//                    .folder(folderUUID)
//                    .file(trainingEntity.getCover())
//                    .build();
//            trainingEntity.setCoverPath(FileService.saveSingle(file));
//        }
//        trainingRepository.update(trainingEntity);
    }

    public void deleteTraining(Long id) {
        trainingRepository.deleteById(id);
    }

    public List<GetView.File> getAllFilesByTrainingId(Long id) {
        return fileRepository.findAllByFileCategoryAndRefObjectId(TRAINING, id);
    }

    public boolean getTrainingStatus(Long id) {
        return Boolean.parseBoolean(trainingRepository.getTrainingStatusById(id));
    }

    public void saveAdditionalTrainingFiles(List<MultipartFile> files, Long id) throws IOException {
        if (files.get(0).getSize() != 0) {
            List<FileEntity> fileList = FileService.saveMultiple(TRAINING, trainingRepository.getFolderUUID(id), files);
            fileList.forEach(file -> file.setRefObjectId(id));
            fileRepository.saveAll(fileList);
        }
    }

    public void deleteFileByTrainingId(Long fileId) {
        DeleteFileView file = fileRepository.getFileById(fileId);
        fileRepository.deleteByFileCategoryAndRefObjectId(TRAINING, file.getId());
        FileService.deleteFile(uploadPath + "/" + file.getFilePath());
    }

    public Paged<TrainingEntity> searchAllTraining(
            int page, int size, TrainingSearchCriteria searchRequest
    ) {
        Pageable pageRequest = PageRequest.of(page - 1, size);

        Page<TrainingEntity> postPage = trainingRepository.findAll(
                SearchQueries.createTrainingSpecification(searchRequest),
                pageRequest
        );

        return new Paged<>(postPage, Paging.of(postPage.getTotalPages(), page, size));
    }

    public Paged<TrainingEntity> getAllTraining(int page, int size) {
        Pageable request = PageRequest.of(page - 1, size);
        Page<TrainingEntity> postPage = trainingRepository.findAll(request);
        return new Paged<>(postPage, Paging.of(postPage.getTotalPages(), page, size));
    }
}
