package az.webapp.colorbrain.repository.view;

public interface GetView {

    interface File extends GetView {

        Long getId();

        int getFileType();

        String getFilePath();

        int getFileCategory();

        String getFolderUuid();

        interface Media extends File {
            Long getMediaId();
        }

        interface News extends File {
            Long getNewsId();
        }

        interface Blog extends File {
            Long getBlogId();
        }

        interface Project extends File {
            Long getProjectId();
        }

        interface Training extends File {
            Long getTrainingId();
        }
    }

}
