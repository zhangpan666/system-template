package com.gallery.manage.api.controller.open;

import com.gallery.manage.api.service.OpenApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gallery/manage/api/open")
public class OpenApiController {

    private static final Logger log = LoggerFactory.getLogger(OpenApiController.class);

    @Autowired
    OpenApiService openApiService;



}
