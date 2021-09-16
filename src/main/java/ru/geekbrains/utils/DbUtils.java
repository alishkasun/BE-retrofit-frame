package ru.geekbrains.utils;

import lombok.experimental.UtilityClass;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import ru.geekbrains.db.dao.CategoriesMapper;
import ru.geekbrains.db.dao.ProductsMapper;
import ru.geekbrains.db.model.Categories;
import ru.geekbrains.db.model.CategoriesExample;
import ru.geekbrains.db.model.ProductsExample;

import java.io.IOException;

@UtilityClass
public class DbUtils {
    private  String resource = "mybatisConfig.xml";
    public SqlSession getSqlSession() {
        SqlSessionFactory sqlSessionFactory = null;
        try {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(resource));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sqlSessionFactory.openSession(true);
    }

    public ProductsMapper getProductsMapper() {
       return getSqlSession().getMapper(ProductsMapper.class);
    }
    public CategoriesMapper getCategoriesMapper() {
        return getSqlSession().getMapper(CategoriesMapper.class);
    }

    public  Categories getNewTestCategory(String quote) {
        // создаем категорию
        getCategoriesMapper().insert(new Categories(quote));
        // создаем фильтр
        CategoriesExample categoriesExample = new CategoriesExample();
        categoriesExample.createCriteria().andTitleEqualTo(quote);
        // делаем select этой категории
        return getCategoriesMapper().selectByExample(categoriesExample).get(0);
    }

    public  void deleteAllProductsWithTheCategory(Integer categoryId) {
        // создаем фильтр для удаления продукта по категории
        ProductsExample example = new ProductsExample();
        example.createCriteria().andCategory_idEqualTo(Long.valueOf(categoryId));
        // удаляем все продукты этой категории
        getProductsMapper().deleteByExample(example);
    }
}
