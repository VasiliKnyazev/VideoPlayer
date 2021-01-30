package com.luxoft.videoplayer.service;

import com.luxoft.videoplayer.model.Video;
import org.springframework.data.domain.Page;

import java.util.List;

public interface VideoService {

    List<Video> findAll();

    List<String> findAllTitles();

    List<Video> findByGenre(String genre);

    List<Video> findByFormat(String format);

    Video findById(int id);

    Video findByTitle(String title);

    Video add(Video video);

    void deleteById(int videoId);

    void edit(Video video);

    Page<Video> findVideoPage(int pageNumber, int pageSize);

}
