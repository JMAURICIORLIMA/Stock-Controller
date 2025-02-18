package com.stock.product.services.implementations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.stock.product.builders.CategoryBuilder;
import com.stock.product.dto.CategoryDTO;
import com.stock.product.dto.CategoryFormDTO;
import com.stock.product.entities.Category;
import com.stock.product.exceptions.ResourceNotFoundException;
import com.stock.product.repositories.CategoryRepository;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class CategoryServiceImplTest {

	@Autowired
	private CategoryServiceImpl service;

	@MockBean
	CategoryRepository repository;

	@Test
	public void shouldInsertACategory() {
		Category category = CategoryBuilder.getCategory();

		when(this.repository.save(any(Category.class))).thenReturn(category);

		CategoryDTO categoryDto = this.service.save(CategoryBuilder.getCategoryFormDTO());

		assertThat(categoryDto.getId()).isNotNull();
		assertThat(categoryDto.getName()).isEqualTo(category.getName());
	}

	@Test
	public void shouldSearchAllCategoriesWithPagination() {
		Category category = CategoryBuilder.getCategory();
		List<Category> list = Arrays.asList(category, category);
		Page<Category> categoryAsPage = new PageImpl<>(list);

		when(this.repository.findAll(any(Pageable.class))).thenReturn(categoryAsPage);

		Page<CategoryDTO> categoryDtoAsPage = service.listCategories(PageRequest.of(0, 12, Direction.ASC, "name"));
		CategoryDTO categoryDto = categoryDtoAsPage.getContent().get(0);

		assertThat(categoryDtoAsPage.getContent().size()).isGreaterThan(0);
		assertThat(categoryDto.getId()).isNotNull();
		assertThat(categoryDto.getName()).isEqualTo(category.getName());
	}

	@Test
	public void shouldUpdateACategoryById() {
		Category category = CategoryBuilder.getCategory();
		CategoryFormDTO categoryForm = CategoryBuilder.getCategoryFormDTO();
		categoryForm.setName("Eletrônico");

		when(this.repository.findById(anyLong())).thenReturn(Optional.of(category));
		when(this.repository.save(any(Category.class))).thenReturn(category);

		CategoryDTO categoryDto = this.service.updateCategory(1L, categoryForm);

		assertThat(categoryDto.getId()).isNotNull();
		assertThat(categoryDto.getName()).isEqualTo(category.getName());
	}

	@Test
	public void shouldNotUpdateACategoryBecauseThereIsNoCategoryWithTheInformedId() {
		when(this.repository.findById(anyLong())).thenReturn(Optional.empty());

		assertThatExceptionOfType(ResourceNotFoundException.class)
				.isThrownBy(() -> this.service.updateCategory(1L, CategoryBuilder.getCategoryFormDTO()));
	}

	@Test
	public void shouldFindACategoryById() {
		Category category = CategoryBuilder.getCategory();

		when(this.repository.findById(anyLong())).thenReturn(Optional.of(category));

		CategoryDTO categoryDto = this.service.findById(1L);

		assertThat(categoryDto.getId()).isEqualTo(category.getId());
		assertThat(categoryDto.getName()).isEqualTo(category.getName());
	}

	@Test
	public void shouldNotFindACategoryBecauseThereIsNoCategoryWithTheInformedId() {
		when(this.repository.findById(anyLong())).thenReturn(Optional.empty());

		assertThatExceptionOfType(ResourceNotFoundException.class).isThrownBy(() -> this.service.findById(1L));
	}

	@Test
	public void shouldDeleteACategoryById() {
		Category category = CategoryBuilder.getCategory();

		when(this.repository.findById(anyLong())).thenReturn(Optional.of(category));

		this.service.deleteCategory(1L);

		verify(this.repository, times(1)).delete(category);
	}

	@Test
	public void shouldNotDeleteACategoryBecauseThereIsNoCategoryWithTheInformedId() {
		when(this.repository.findById(anyLong())).thenReturn(Optional.empty());

		assertThatExceptionOfType(ResourceNotFoundException.class).isThrownBy(() -> this.service.deleteCategory(1L));
	}
}