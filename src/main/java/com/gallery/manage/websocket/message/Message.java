package com.gallery.manage.websocket.message;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class Message implements Serializable {


    private String sendUserNickname;

    private Long sendUserId;

    private Long receiveUserId;

    private Byte messageType;

    private String content;

    private String createTime;

    private String title;

    private String url;

}
