package edu.team9.fitconnect.service;

import edu.team9.fitconnect.model.Category;
import edu.team9.fitconnect.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    public List<Category> findAllCategories() throws Exception {
        List<Category> category = categoryRepository.findAll();
        if(!category.isEmpty()){
            return category;
        }else{
            //throw new Exception("No Categories was not found");
            return category;
        }
    }

    public List<Category> searchCategory(String name) {
        List<Category> category = categoryRepository.findCategoryByCategory(name);
        return category;
    }

    public void insertCategory(String ctg, String fileName, byte[] data, String type) {
        Category category = new Category(ctg, LocalDateTime.now(), fileName, ByteBuffer.wrap(data), type);
        categoryRepository.save(category);
    }
}
