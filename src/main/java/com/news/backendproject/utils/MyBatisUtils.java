package com.news.backendproject.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MyBatisUtils {
    // 全局唯一的SqlSessionFactory
    private static SqlSessionFactory sqlSessionFactory;

    static {
        try {
            // 加载MyBatis核心配置文件（之前创建的mybatis-config.xml）
            InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
            // 初始化SqlSessionFactory
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("MyBatis初始化失败");
        }
    }

    // 获取SqlSession（自动提交事务：参数true）
    public static SqlSession getSqlSession() {
        return sqlSessionFactory.openSession(true); // true表示自动提交，简化操作
    }
}
