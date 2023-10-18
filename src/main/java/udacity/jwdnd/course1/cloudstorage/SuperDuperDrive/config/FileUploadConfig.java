package udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;
@Configuration
public class FileUploadConfig {

    private final FileUploadProperties fileUploadProperties;

    public FileUploadConfig(FileUploadProperties fileUploadProperties) {
        this.fileUploadProperties = fileUploadProperties;
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.parse(fileUploadProperties.getMaxFileSize()));
        return factory.createMultipartConfig();
    }
}
