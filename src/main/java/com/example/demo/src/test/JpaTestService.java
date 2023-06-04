package com.example.demo.src.test;

import com.example.demo.config.BaseException;
import com.example.demo.src.test.model.JpaTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
@Transactional
public class JpaTestService {

    private final JpaTestRepository jpaTestRepository;

    @Autowired
    public JpaTestService (JpaTestRepository jpaTestRepository) {
        this.jpaTestRepository = jpaTestRepository;
    }

    public JpaTest save(JpaTest jpaTest) throws BaseException {
        try {
            return jpaTestRepository.save(jpaTest);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
