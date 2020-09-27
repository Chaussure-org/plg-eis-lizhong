package com.prolog.eis.location.controller;

import com.prolog.eis.location.service.LocationService;
import com.prolog.framework.common.message.RestMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/btn/location")
public class LocationController {

    @Autowired
	private LocationService locationService;

	@PostMapping("/test")
	public RestMessage<Long> locationTest() throws Exception {
		StopWatch sw = new StopWatch("test");
		sw.start();
		locationService.doContainerPathTaskByContainer(null, null);
		sw.stop();
		System.out.println(sw.prettyPrint());
		return RestMessage.newInstance(true, "保存成功");
	}
}
