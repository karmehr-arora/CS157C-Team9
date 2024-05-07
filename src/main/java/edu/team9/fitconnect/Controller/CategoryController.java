package edu.team9.fitconnect.Controller;

import edu.team9.fitconnect.model.Category;
import edu.team9.fitconnect.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;



public class CategoryController {
    @Autowired
    private CategoryService categoryService;
}
