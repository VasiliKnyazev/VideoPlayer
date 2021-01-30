package com.luxoft.videoplayer.service;

import com.luxoft.videoplayer.dao.VideoRepository;
import com.luxoft.videoplayer.model.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class VideoServiceImpl implements VideoService {

    private VideoRepository videoRepository;

    public VideoServiceImpl(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    @Override
    public List<Video> findAll() {
        return videoRepository.findAll();
    }

    @Override
    public List<String> findAllTitles() {
        return videoRepository.findAllTitles();
    }

    @Override
    public List<Video> findByGenre(String genre) {
        return videoRepository.findByGenre(genre);
    }

    @Override
    public List<Video> findByFormat(String format) {
        return videoRepository.findByFormat(format);
    }

    @Override
    public Video findById(int id) {
        return videoRepository.findById(id).get();
    }

    @Override
    public Video findByTitle(String title) {
        return videoRepository.findByTitle(title);
    }

    @Override
    public Video add(Video video) {
        return videoRepository.save(video);
    }

    @Override
    public void deleteById(int videoId) {
        videoRepository.deleteById(videoId);
    }

    @Override
    public void edit(Video video) {
        videoRepository.save(video);
    }

    @Override
    public Page<Video> findVideoPage(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        return videoRepository.findAll(pageable);
    }
}
