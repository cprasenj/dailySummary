//package dailySummary.service;
//
//import dailySummary.contract.ImageUploadResponse;
//import dailySummary.repository.ImageRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//@Service
//public class ImageService {
//
//    private ImageRepository imageRepository;
//
//    @Autowired
//    public ImageService(ImageRepository imageRepository) {
//        this.imageRepository = imageRepository;
//    }
//
//    public ImageUploadResponse saveUploadedFiles(MultipartFile file) throws IOException {
//
//        byte[] bytes = file.getBytes();
//
//        Path path = Paths.get(file.getOriginalFilename());
//
//        Files.write(path, bytes);
//
//        return imageRepository.upload(new File(file.getOriginalFilename()));
//    }
//}
