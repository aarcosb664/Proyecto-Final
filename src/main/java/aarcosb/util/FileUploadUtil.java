package aarcosb.util;

import lombok.experimental.UtilityClass;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class FileUploadUtil {
    public static final long MAX_IMAGE_FILE_SIZE = 20 * 1024 * 1024;
    public static final String IMAGE_FILE_PATTERN = "^.+\\.(?i)(jpg|png|jpeg)$";

    public static final long MAX_VIDEO_FILE_SIZE = 100 * 1024 * 1024;
    public static final String VIDEO_FILE_PATTERN = "^.+\\.(?i)(mp4|mov|avi|wmv|flv|webm|mkv)$";
    
    public static final String DATE_FORMAT = "yyyyMMddHHmmss";
    public static final String IMAGE_FILE_NAME_FORMAT = "%s_%s";

    public static boolean isAllowedExtension(final String fileName, final String pattern) {
        final Matcher matcher = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE).matcher(fileName);
        return matcher.matches();
    }

    public static void assertImageAllowed(MultipartFile file) {
        final long size = file.getSize();
        final String fileName = file.getOriginalFilename();

        if (size > MAX_IMAGE_FILE_SIZE) {
            throw new IllegalArgumentException("Max file size is 20MB");
        }

        if (!isAllowedExtension(fileName, IMAGE_FILE_PATTERN)) {
            throw new IllegalArgumentException("Only jpg, png and jpeg files are allowed");
        }
    }

    public static void assertVideoAllowed(MultipartFile file) {
        final long size = file.getSize();
        final String fileName = file.getOriginalFilename();

        if (size > MAX_VIDEO_FILE_SIZE) {
            throw new IllegalArgumentException("Max video size is 100MB");
        }

        if (!isAllowedExtension(fileName, VIDEO_FILE_PATTERN)) {
            throw new IllegalArgumentException("Only mp4, mov, avi, wmv, flv, webm and mkv files are allowed");
        }
    }

    public static String getFileName(final String name) {
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        final String date = LocalDateTime.now().format(dateTimeFormatter);
        return String.format(IMAGE_FILE_NAME_FORMAT, name, date);
    }
}
