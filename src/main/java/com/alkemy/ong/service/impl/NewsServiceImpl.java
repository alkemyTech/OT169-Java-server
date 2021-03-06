package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.NewsDto;
import com.alkemy.ong.entity.News;
import com.alkemy.ong.repository.NewsRepository;
import com.alkemy.ong.service.NewsService;
import com.alkemy.ong.utils.Mapper;
import com.alkemy.ong.utils.NewsMapper;
import com.amazonaws.services.simplesystemsmanagement.model.ParameterNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsMapper newsMapper;

    @Autowired
    private NewsRepository newsRepository;


    @Override
    public NewsDto save(NewsDto newsDto) {
        News newsEntity = newsMapper.newsDto2Entity(newsDto, new News());
        News saved = newsRepository.save(newsEntity);
        NewsDto result = newsMapper.newsEntity2Dto(saved, new NewsDto());

        return result;
    }

    @Override
    public NewsDto getNewsById(String id) {

        Optional<News> response = newsRepository.findById(id);
        if (response.isPresent()) {
            News news = response.get();
            return newsMapper.newsEntity2Dto(news, new NewsDto());
        } else {
            throw new ParameterNotFoundException("");
        }

    }

    @Override
    public void delete(String id) {
        Optional<News> optional = newsRepository.findById(id);

        if (optional.isPresent()) {
            newsRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }


    @Override
    public NewsDto updateNews(String id, NewsDto newsDto) {

        Optional<News> optional = newsRepository.findById(id);

        if (optional.isPresent()) {
            News updatedNews = newsMapper.updateValues(newsRepository.findById(id).get(), newsDto);
            newsRepository.save(updatedNews);
                return newsDto;
        } else {
            throw new ParameterNotFoundException("");

        }
    }

    @Override
    public News getOne(String id) throws Exception {
        Optional<News> entity = newsRepository.findById(id);
        if(!entity.isPresent()){
            throw new Exception("Id news not found");
        }
        return entity.get();
    }

    @Override
    public Map<String, Object> getAllPages(Integer page) throws Exception{
        Integer size = 10;
        try {
            List<List<LinkedHashMap>> newsList = new ArrayList<List<LinkedHashMap>>();
            Pageable paging = PageRequest.of(page,size);
            String url = "/news?page=";

            Page<List<LinkedHashMap>> pagedNews;
            pagedNews = newsRepository.findPage(paging);
            newsList = pagedNews.getContent();
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("Total Items", pagedNews.getTotalElements());
            response.put("Total Pages", pagedNews.getTotalPages());
            response.put("Current Page", pagedNews.getNumber());

            if (pagedNews.getNumber() == pagedNews.getTotalPages() - 1) {
                response.put("Next Page", "This is the last page");
            } else {
                response.put("Next Page", url.concat(String.valueOf(pagedNews.getNumber() + 1)));
            }
            if (pagedNews.getNumber() == 0) {
                response.put("Previous Page", "This is the first page");
            } else {
                response.put("Previous Page", url.concat(String.valueOf(pagedNews.getNumber() - 1)));
            }
            response.put("News", newsList);

            return response;

        } catch (Exception e) {
            throw new Exception("Fail to load pages");
        }
        }


}
