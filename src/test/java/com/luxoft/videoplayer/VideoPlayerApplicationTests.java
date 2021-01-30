package com.luxoft.videoplayer;

import static org.assertj.core.api.Assertions.assertThat;

import com.luxoft.videoplayer.controller.MainController;
import com.luxoft.videoplayer.controller.rest.UserRestController;
import com.luxoft.videoplayer.controller.rest.VideoRestController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@DisplayName("VideoPlayerApplication")
@SpringBootTest
class VideoPlayerApplicationTests {

	@Autowired
	private MainController mainController;

	@Autowired
	private UserRestController userRestController;

	@Autowired
	private VideoRestController videoRestController;

	@DisplayName("should load not null mainController, userRestController, videoRestController")
	@Test
	void contextLoads() {
		assertThat(mainController).isNotNull();
		assertThat(userRestController).isNotNull();
		assertThat(videoRestController).isNotNull();
	}

}
