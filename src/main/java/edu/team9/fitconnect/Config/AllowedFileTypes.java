package edu.team9.fitconnect.Config;

import org.springframework.http.MediaType;

public class AllowedFileTypes {
    public static boolean isImageFile(String contentType) {
        return contentType != null && (
                contentType.equals(MediaType.IMAGE_JPEG_VALUE) ||
                        contentType.equals(MediaType.IMAGE_PNG_VALUE) ||
                        contentType.equals(MediaType.IMAGE_GIF_VALUE) ||
                        contentType.equals("image/webp") ||
                        contentType.equals("image/bmp")
        );
    }
}
