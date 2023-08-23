package com.gallery.manage.common.util;

import com.gallery.manage.admin.pojo.MenuVO;
import com.gallery.manage.common.model.Menu;
import com.gallery.manage.common.model.Permission;
import com.gallery.manage.common.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.InputStream;
import java.util.*;

@Slf4j
@Component
@Transactional
public class MenuUtil {

    private static final Set<Menu> menuList = new TreeSet<>();

    private static final Set<String> stringPermissions = new LinkedHashSet<>();

    @Autowired
    MenuService menuService;

    private static void init() {
        try {
            InputStream resourceAsStream = Resources.getResourceAsStream("menu.xml");
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(resourceAsStream);
            Element rootElement = document.getRootElement();
            List<Element> elements = rootElement.elements();
            Iterator<Element> firstMenuList = elements.iterator();
            while (firstMenuList.hasNext()) {
                Element firstMenu = firstMenuList.next();
                Menu menu = generateMenu(firstMenu);
                Iterator<Element> secondMenuList = firstMenu.elementIterator();
                Set<Menu> secondMenuLists = new TreeSet<>();
                while (secondMenuList.hasNext()) {
                    Element secondMenu = secondMenuList.next();
                    Menu generateMenu = generateMenu(secondMenu);
                    Iterator<Element> iterator = secondMenu.elementIterator();
                    List<Permission> permissionList = new LinkedList<>();
                    while (iterator.hasNext()) {
                        Element element = iterator.next();
                        Permission permission = new Permission();
                        String name = element.attributeValue("name");
                        permission.setName(name);
                        String code = element.attributeValue("code");
                        permission.setCode(code);
                        permissionList.add(permission);
                        stringPermissions.add(code);
                    }
                    generateMenu.setPermissionList(permissionList);
                    secondMenuLists.add(generateMenu);
                }
                menu.setChildMenuList(secondMenuLists);
                menuList.add(menu);
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.info("菜单初始化异常");
        }
    }

    private static Menu generateMenu(Element element) {
        Menu menu = new Menu();
        String name = element.attributeValue("name");
        menu.setName(name);
        String code = element.attributeValue("code");
        menu.setCode(code);
        String url = element.attributeValue("url", "");
        menu.setUrl(url);
        String icon = element.attributeValue("icon");
        menu.setIcon(icon);
        String sort = element.attributeValue("sort", "100");
        menu.setSort(Integer.valueOf(sort));
        return menu;
    }

    public static Set<Menu> getMenuList() {
        return menuList;
    }


    public static Collection<String> getAllPermissions() {
        return stringPermissions;
    }


    public boolean initMenu() {
        init();
        try {
            if (CollectionUtils.isEmpty(menuList)) {
                return false;
            }
            menuList.forEach(menu -> {
                Menu serviceMenu = menuService.getMenu(menu);
                if (serviceMenu == null) {
                    menu.setParentId(0L);
                    boolean save = menuService.save(menu);
                } else {
                    menu.setId(serviceMenu.getId());
                }
                Long parentId = menu.getId();
                Set<Menu> subMenuList = menu.getChildMenuList();
                if (!CollectionUtils.isEmpty(subMenuList)) {
                    subMenuList.forEach(subMenu -> {
                        subMenu.setParentId(parentId);
                        Menu serviceSubMenu = menuService.getMenu(subMenu);
                        if (serviceSubMenu == null) {
                            boolean save = menuService.save(subMenu);
                        } else {
                            subMenu.setId(serviceSubMenu.getId());
                        }
                    });
                }
            });
        } catch (Exception e) {
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            log.info("菜单初始化异常");
            return false;
        }
        return true;
    }

    public static MenuVO generateMenuVO(Menu menu) {
        return new MenuVO().setTitle(menu.getName()).setHref(SystemUtil.getBaseRequestUrl() + menu.getUrl()).setIcon(menu.getIcon());
    }
}
