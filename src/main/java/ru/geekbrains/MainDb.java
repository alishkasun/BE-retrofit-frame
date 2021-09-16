package ru.geekbrains;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import ru.geekbrains.db.dao.ProductsMapper;
import ru.geekbrains.db.model.Products;
import ru.geekbrains.db.model.ProductsExample;

import java.io.IOException;

@Slf4j
public class MainDb {
    static Faker faker = new Faker();
    private static String resource = "mybatisConfig.xml";
    public static void main(String[] args) {
        SqlSessionFactory sqlSessionFactory = null;
        try {
             sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(resource));
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        ProductsMapper productsMapper = sqlSession.getMapper(ProductsMapper.class);
        Products product = productsMapper.selectByPrimaryKey(10399l);
        log.info(product.getTitle());
//        productsMapper.deleteByPrimaryKey(9159l);
        // посчитать все сущности в таблице (без фильтров)
        long count = productsMapper.countByExample(new ProductsExample());
        log.info(String.valueOf(count));
        ProductsExample example = new ProductsExample();
        example.createCriteria().andPriceGreaterThan(1000);
        log.info(String.valueOf(productsMapper.countByExample(example)));

        example.clear();

        example.createCriteria().andCategory_idEqualTo(3l).andPriceGreaterThan(1000);
        productsMapper.selectByExample(example).forEach(e -> log.info(String.valueOf(e)));
//        sqlSession.commit(); //коммитит изменения в базу
//
//        int newId = productsMapper.insert(new Products(faker.animal().name(), 1500, 3l));
//        log.info(String.valueOf(newId));
//        example.clear();
//        example.createCriteria().andCategory_idEqualTo(550l);
//        productsMapper.deleteByExample(example);

    }
}
