package com.gallery.manage.api.service.impl;

import com.gallery.manage.api.service.OpenApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = {Exception.class})
public class OpenApiServiceImpl implements OpenApiService {

    private static final Logger log = LoggerFactory.getLogger(OpenApiServiceImpl.class);



}
