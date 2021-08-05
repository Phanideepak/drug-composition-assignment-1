package com.licious.app.service;

import com.licious.app.model.Composition;
import com.licious.app.repository.CompositionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CompositionService {
    @Autowired
    CompositionsRepository compositionsRepository;

    @Transactional(readOnly = true)
    public Composition getCompositionByName(String name){
        return  compositionsRepository.findOneByName(name).get();
    }
}
