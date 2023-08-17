package bc.bookchat.aws.s3.service;

import bc.bookchat.common.exception.CustomException;
import bc.bookchat.common.exception.ErrorCode;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadFile(MultipartFile multipartFile) {
        StringBuilder sb = new StringBuilder();
        sb.append(UUID.randomUUID() + multipartFile.getOriginalFilename());
        String fileName = sb.toString();

        ObjectMetadata metaData = new ObjectMetadata();
        metaData.setContentLength(multipartFile.getSize());
        metaData.setContentType(multipartFile.getContentType());

        try {
            amazonS3.putObject(bucket, fileName, multipartFile.getInputStream(), metaData);
        } catch (IOException e) {
            throw new CustomException(ErrorCode.IMAGE_UPLOAD_FAIL);
        }
        return amazonS3.getUrl(bucket, fileName).toString();
    }

}