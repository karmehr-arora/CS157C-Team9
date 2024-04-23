package edu.team9.fitconnect.service;

import edu.team9.fitconnect.model.Photo;
import edu.team9.fitconnect.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.util.UUID;

@Service
public class PhotoService {

    @Autowired
    private PhotoRepository photoRepository;

    public void uploadFile(String fileName, byte[] fileBytes) {
        Photo fileEntity = new Photo();
        fileEntity.setId(UUID.randomUUID());
        fileEntity.setFileName(fileName);
        fileEntity.setFileData(ByteBuffer.wrap(fileBytes));

        photoRepository.save(fileEntity);
    }

}
