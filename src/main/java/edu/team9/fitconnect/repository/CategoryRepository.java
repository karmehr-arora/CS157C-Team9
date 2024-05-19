package edu.team9.fitconnect.repository;

import edu.team9.fitconnect.model.Category;
import edu.team9.fitconnect.model.Photo;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.List;
import java.util.UUID;


public interface CategoryRepository extends CassandraRepository<Category, UUID> {
    @AllowFiltering
    List<Category> findCategoryByCategory(String category);
}
