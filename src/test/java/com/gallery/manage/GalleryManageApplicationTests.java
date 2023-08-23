package com.gallery.manage;

import com.gallery.manage.common.service.RoleService;
import com.gallery.manage.common.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class GalleryManageApplicationTests {

    @Autowired
    RoleService roleService;

    @Autowired
    SysUserService sysUserService;

    @Test
    public void contextLoads() throws Exception {
        SAXReader saxReader = new SAXReader();
        InputStream resourceAsStream = Resources.getResourceAsStream("menu.xml");
        Document document = saxReader.read(resourceAsStream);
        Element rootElement = document.getRootElement();
        List<Element> elements = rootElement.elements();
        Iterator<Element> iterator = elements.iterator();
        while (iterator.hasNext()) {
            Element next = iterator.next();
            elementIterator(next);
        }
    }

    public void elementIterator(Element element) {
        List<Attribute> attributes = element.attributes();
        for (Attribute attribute : attributes) {
            System.out.println(attribute.getName() + "=" + attribute.getValue());
        }
        Iterator<Element> iterator = element.elementIterator();
        while (iterator.hasNext()) {
            Element next = iterator.next();
            elementIterator(next);
        }
    }



}
