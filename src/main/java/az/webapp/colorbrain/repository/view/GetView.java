package az.webapp.colorbrain.repository.view;

public interface GetView {

    interface File extends GetView {

        Long getId();

        int getFileType();

        String getFilePath();

        int getFileCategory();

        Long getRefObjectId();
    }

}
