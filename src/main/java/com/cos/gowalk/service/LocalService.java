package com.cos.gowalk.service;

import com.cos.gowalk.model.Local;
import com.cos.gowalk.repository.LocalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author   : 윤기환
 * @Class    : LocalService.java
 * @Desc     : 지역 로직 처리 서비스
 * */
@Service
public class LocalService {

    @Autowired
    private LocalRepository localRepository;

    public List<Local> localList() {
        return localRepository.localList();
    }

    public Local selectLocalList(Integer lNum) {
        return localRepository.selectLocalList(lNum);
    }
}
