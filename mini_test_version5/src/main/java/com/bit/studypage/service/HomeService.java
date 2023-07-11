package com.bit.studypage.service;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;


import com.bit.studypage.entity.SearchBoard;

import java.util.List;
@Service
public interface HomeService {


    List<SearchBoard> getTop12Posts();

    List<SearchBoard> getTop12PostsByCategory(String category);

    List<SearchBoard> getTop12PostsByCommentCount();


}
