package bc.bookchat.aws.s3.controller;

import bc.bookchat.aws.s3.service.S3Service;
import bc.bookchat.board.controller.dto.ImageUploadResponse;
import bc.bookchat.common.response.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class S3Controller {

    private final S3Service s3Service;

    @PostMapping("/images")
    public ResponseEntity<Object> uploadImage(@RequestParam(value = "image") MultipartFile image) {
        String imageUrl = s3Service.uploadFile(image);
        ImageUploadResponse response = new ImageUploadResponse(imageUrl);
        return ResponseHandler.generateResponse("이미지가 업로드 되었습니다.", HttpStatus.CREATED, response);
    }
}
