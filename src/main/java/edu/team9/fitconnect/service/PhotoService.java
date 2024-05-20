package edu.team9.fitconnect.service;

import edu.team9.fitconnect.model.Photo;
import edu.team9.fitconnect.model.User;
import edu.team9.fitconnect.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PhotoService {

    @Autowired
    private PhotoRepository photoRepository;

    public void uploadFile(String fileName, byte[] fileBytes, User user, String contentType) {
        Photo fileEntity = new Photo(UUID.randomUUID(), fileName, ByteBuffer.wrap(fileBytes), contentType, LocalDateTime.now(), user.getWeight(), user.getEmail());
        photoRepository.save(fileEntity);
    }

    public Photo getPhotoById(UUID id) {
        return photoRepository.findById(id).orElseThrow();
    }

    public List<Photo> getPhotosByUser(String username) {
        return photoRepository.findPhotosByUserId(username);
    }

    public void deletePhotoById(UUID id){
        photoRepository.deleteById(id);
    }
}
