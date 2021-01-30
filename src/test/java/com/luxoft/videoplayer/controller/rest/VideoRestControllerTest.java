package com.luxoft.videoplayer.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luxoft.videoplayer.model.Video;
import com.luxoft.videoplayer.service.VideoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hibernate.cfg.AvailableSettings.URL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest(VideoRestController.class)
class VideoRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    VideoService videoService;

    @Test
    void testAddVideo() throws Exception {
        Video videoStub = new Video(1, "Movie Title", "someUrl", "Horror", "mp4");
        when(videoService.add(any(Video.class))).thenReturn(videoStub);

        String videoStubJson = new ObjectMapper().writeValueAsString(videoStub);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(videoStubJson)).andReturn();

        int status = result.getResponse().getStatus();
        assertEquals(HttpStatus.CREATED.value(), status, "Incorrect Response Status");

        verify(videoService).add(any(Video.class));

        Video resultVideo = new ObjectMapper().readValue(result.getResponse().getContentAsString(), Video.class);;
        assertNotNull(resultVideo);
        assertEquals(1, resultVideo.getId());
    }

}
