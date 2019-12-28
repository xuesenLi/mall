package com.lxs.mall;

import com.lxs.mall.dao.CategoryMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MallTest {


	@Autowired
	private CategoryMapper categoryMapper;
	@Test
	public void contextLoads() {
		/*Category category = categoryMapper.queryById(100005);
		System.out.println(category);*/
	}

}
