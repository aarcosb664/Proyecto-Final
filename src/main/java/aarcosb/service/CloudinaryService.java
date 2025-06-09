package aarcosb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

@Service
public class CloudinaryService {
    @Autowired
    private Cloudinary cloudinary;

    public List<String> uploadImages(List<MultipartFile> images) throws IOException {
        if (images == null || images.size() < 3 || images.size() > 10)
            throw new IllegalArgumentException("You must upload between 3 and 10 images");
        List<String> urls = new ArrayList<>();
        for (MultipartFile img : images) {
            if (img.isEmpty()) throw new IllegalArgumentException("All images must be non-empty");
            String type = img.getContentType();
            if (type == null || !(type.equals("image/png") || type.equals("image/jpeg")))
                throw new IllegalArgumentException("Only PNG and JPEG images are allowed");
            urls.add((String) cloudinary.uploader().upload(img.getBytes(), ObjectUtils.asMap("resource_type", "image", "folder", "listings/images")).get("secure_url"));
        }
        return urls;
    }

    public String uploadVideo(MultipartFile video) throws IOException {
        if (video == null || video.isEmpty()) return null;
        String type = video.getContentType();
        if (type == null || !type.startsWith("video/"))
            throw new IllegalArgumentException("Only video files are allowed");
        return (String) cloudinary.uploader().upload(video.getBytes(), ObjectUtils.asMap("resource_type", "video", "folder", "listings/videos")).get("secure_url");
    }
} 