package com.gallery.manage.common.util;

import com.alibaba.fastjson.JSON;
import com.gallery.manage.websocket.message.Message;
import com.light.config.util.ApplicationContextUtil;
import org.springframework.kafka.core.KafkaTemplate;

public class KafkaUtil {

    public static <T extends Message> void sendMessage(String topic, T message) {
        KafkaTemplate kafkaTemplate = ApplicationContextUtil.getBean(KafkaTemplate.class);
        String data = JSON.toJSONString(message);
        kafkaTemplate.send(topic, data);
    }
}
