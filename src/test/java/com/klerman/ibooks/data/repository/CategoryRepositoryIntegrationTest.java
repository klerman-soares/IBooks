package com.klerman.ibooks.data.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.klerman.ibooks.data.entity.Category;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class CategoryRepositoryIntegrationTest {
	
	private static final String CATEGORY_NAME = "Category Name";
	private static final String CATEGORY_NEW_NAME = "Category New Name";

	@Autowired
    private TestEntityManager entityManager;
	
	@Autowired
	private CategoryRepository categoryRepository;	
	
	@Before
	public void setup() throws Exception {
		// setup data scenario
		Category category = new Category(CATEGORY_NAME);
		entityManager.persist(category);
	}
	
	@Test
	public void testFindAllCategories () {
		ArrayList<Category> list = new ArrayList<>();
		categoryRepository.findAll().forEach(category -> {
			list.add(category);
		});
		assertThat(1, is(list.size()));
	}
	
	@Test
	public void testAddCategory () {
		Category category = new Category(CATEGORY_NAME);
		
		Category savedCategory = categoryRepository.save(category);
		assertThat(category, is(equalTo(savedCategory)));
	}
	
	@Test
	public void testUpdateCategoryName () {
		Category category = categoryRepository.findByName(CATEGORY_NAME);
		category.setName(CATEGORY_NEW_NAME);
		Category savedCategory = categoryRepository.save(category);
		assertThat(CATEGORY_NEW_NAME, is(equalTo(savedCategory.getName())));
	}
	
	@Test
	public void testDeleteCategory() {
		Category category = categoryRepository.findByName(CATEGORY_NAME);
		categoryRepository.delete(category);
		Category deletedCategory = categoryRepository.findByName(CATEGORY_NAME);
		assertNull("This category was supposed to be deleted", deletedCategory);
	}     
	
	@Test
    public void testFindByName() {
		Category foundCategory = categoryRepository.findByName(CATEGORY_NAME);        
        assertThat(foundCategory.getName(), is(equalTo(CATEGORY_NAME)));
    }
}
