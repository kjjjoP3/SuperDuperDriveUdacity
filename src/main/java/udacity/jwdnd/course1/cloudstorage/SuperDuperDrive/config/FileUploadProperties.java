package udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
@Component
@ConfigurationProperties(prefix = "spring.servlet.multipart")
public class FileUploadProperties {
    private String maxFileSize;

    public String getMaxFileSize() {
        return maxFileSize;
    }

    public void setMaxFileSize(String maxFileSize) {
        this.maxFileSize = maxFileSize;
    }
}
