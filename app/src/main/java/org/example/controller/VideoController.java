/*
 * Copyright 2021-2024 spring-boot-extension the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.example.controller;

import com.livk.commons.util.WebUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.support.VideoResourceHttpRequestHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * <p>
 * VideoController
 * </p>
 *
 * @author livk
 */
@RestController
@RequestMapping("video")
public class VideoController {

	private static final String VIDEO_SUFFIX = ".mp4";

	@Resource
	private VideoResourceHttpRequestHandler requestHandler;

	@GetMapping
	public void video(HttpServletRequest request, HttpServletResponse response,
					  @RequestParam(defaultValue = "test") String videoName) throws ServletException, IOException {
		// 查询视频是否存在
		if ("test".equals(videoName)) {
			request.setAttribute(VideoResourceHttpRequestHandler.ATTR, "classpath:" + videoName + VIDEO_SUFFIX);
			requestHandler.handleRequest(request, response);
		}
		else {
			Map<String, Serializable> map = Map.of("msg", "视频资源不存在", "status", HttpServletResponse.SC_NOT_FOUND);
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
			WebUtils.outJson(response, map);
		}
	}

}
