<!--用户在线-->
var roleId = localStorage.getItem("roleId");
if (roleId != 1) {
    window.location.href = "/page/rolePage.html"
}
// if (roleId == 1) {
//     $(document).ready(function () {
//         // webSocket();
//         layer.msg("初始化完毕！", {icon: 6});
//     });
// }

$('.li-2').on('click', function () {
    if (roleId == 1) {
        sseSocket();
    }

});

// 获取到这个DOM节点，然后初始化
var count = echarts.init(document.getElementById("count"));

var yesterday = [];
var today = [];
$.ajax({
    //几个参数需要注意一下
    type: "POST",//方法类型
    dataType: "json",//预期服务器返回的数据类型
    url: "/margin/gallery/admin/user/online",//url
    data: null,
    success: function (data) {
        for (var i = 0; i < data.yesterday.length; i++) {
            yesterday.push(data.yesterday[i].userCount);
        }
        for (var i = 0; i < data.today.length; i++) {
            today.push(data.today[i].userCount);
        }
        count.hideLoading();
        count.setOption({        //加载数据图表
            series: [{
                // 根据名字对应到相应的系列
                name: '今天',
                data: today
            }],
        });
        count.setOption({        //加载数据图表
            series: [{
                // 根据名字对应到相应的系列
                name: '昨天',
                data: yesterday
            }],
        });
    }
});


// option 里面的内容基本涵盖你要画的图表的所有内容
option = {
    color: ["#628CFF", "#9cdfa1"],
    title: {
        text: '用户在线图',
        subtext: '(今天对比昨天)'

    },
    tooltip: {
        trigger: 'axis'
    },
    legend: {
        data: ['今天', '昨天']
    },
    toolbox: {
        show: true,
        feature: {
            mark: {show: true},
            dataView: {show: true, readOnly: false},
            magicType: {show: true, type: ['line', 'bar']},
            restore: {show: true},
            saveAsImage: {show: true}
        }
    },
    calculable: true,
    xAxis: [{
        type: 'category',
        boundaryGap: false,
        data: ['0:00', '0:30', '1:00', '1:30', '2:00', '2:30', '3:00', '3:30', '4:00', '4:30', '5:00', '5:30', '6:00', '6:30', '7:00', '7:30', '8:00', '8:30', '9:00', '9:30',
            '10:00', '10:30', '11:00', '11:30', '12:00', '12:30', '13:00', '13:30', '14:00', '14:30', '15:00', '15:30', '16:00', '16:30', '17:00', '17:30', '18:00', '18:30', '19:00', '19:30',
            '20:00', '20:30', '21:00', '21:30', '22:00', '22:30', '23:00', '23:30']
    }],


    yAxis: [
        {
            type: 'value'
        }
    ],
    series: [
        {
            symbol: 'none', //去掉折线图中的节点
            name: '昨天',
            type: 'line',
            stack: '总量',
            areaStyle: {
                normal: {}
            },
            smooth: true,   //关键点，为true是不支持虚线的，实线就用true
            data: []
        },
        {
            name: '今天',
            data: [],
            type: 'line',
            smooth: true,
            itemStyle: {
                normal: {
                    lineStyle: {
                        width: 3,
                        type: 'dotted' //'dotted'虚线 'solid'实线

                    }
                }
            },
        },

    ],
};

//十分重要，相当于渲染
count.setOption(option);


<!-- 注册折线图-->


var num = []
var myDate = new Date();
var year = myDate.getFullYear();
var month = myDate.getMonth() + 1;
var day = myDate.getDate();
var maxNum = null;


$.ajax({
    //几个参数需要注意一下
    type: "POST",//方法类型
    dataType: "json",//预期服务器返回的数据类型
    url: "/margin/gallery/admin/userReg/monthView",//url
    data: null,
    success: function (data) {
        maxNum = data.result.maxNum;
        var arr = eval(data.result.listNum)
        for (var i = 1; i < arr.length; i++) {
            num.push(arr[i])
        }
        regView.hideLoading();
        regView.setOption({        //加载数据图表
            series: [{
                // 根据名字对应到相应的系列
                name: '注册',
                data: num
            }],
            yAxis: [{
                max: maxNum + 3
            }]
        });
    }
});


// 获取到这个DOM节点，然后初始化
var regView = echarts.init(document.getElementById("box"));

// option 里面的内容基本涵盖你要画的图表的所有内容
option = {
    title: {
        text: '注册折线图',
        subtext: '(本月数据)'
    },
    tooltip: {
        trigger: 'axis'
    },
    legend: {
        data: ['注册']
    },
    toolbox: {
        show: true,
        feature: {
            mark: {show: true},
            dataView: {show: true, readOnly: false},
            magicType: {show: true, type: ['line', 'bar']},
            restore: {show: true},
            saveAsImage: {show: true}
        }
    },
    calculable: true,
    xAxis: [{
        type: 'category',
        boundaryGap: false,

        data: function () {

            var list = [];
            for (var i = 1; i <= day; i++) {
                list.push(year + "-" + month + "-" + i);
            }
            return list;
        }()
    }],


    yAxis: [
        {
            type: 'value'
        }
    ],
    series: [
        {
            name: '注册',
            type: 'line',
            stack: '总量',
            data: []
        }]
};

//十分重要，相当于渲染
regView.setOption(option);


<!--地域分布-->


var res = [];
var main;
$.ajax({
    //几个参数需要注意一下
    type: "POST",//方法类型
    dataType: "json",//预期服务器返回的数据类型
    url: "/margin/gallery/admin/userReg/cityView",//url
    data: null,
    success: function (data) {
        var arr = eval(data);
        for (var i = 0; i < arr.length; i++) {
            res.push({
                name: arr[i].province,
                //两个字符串拼接
                value: arr[i].cityCode
            });
        }
        //初始化echarts实例
        main = echarts.init(document.getElementById('main'));
        main.setOption({


            backgroundColor: '#FFFFFF',
            title: {
                text: '用户地域分布',
                subtext: '(中国区域)',
                x: 'center'
            },
            tooltip: {
                trigger: 'item'
            },

            //左侧小导航图标
            visualMap: {
                show: true,
                x: 'left',
                y: 'center',
                splitList: [
                    {start: 1000, end: 2000},
                    {start: 300, end: 800},
                    {start: 50, end: 200},
                    {start: 1, end: 50},

                ],
                color: ['#f50f0c', '#ef505b', '#1660ea', '#5779ff', '#6ad94f']
            },

            //配置属性
            series: [{
                name: '用户数量',
                type: 'map',
                mapType: 'china',
                roam: true,
                roam: false,
                label: {
                    normal: {
                        show: false  //省份名称
                    },
                    emphasis: {
                        show: false
                    }
                },

                data: res  //数据
            }]
        });
    }
});


<!--用户来源（操作系统）-->

var myChart1 = echarts.init(document.getElementById('active'));
$.ajax({
    url: "/margin/gallery/admin/userReg/getOsType",                //后台方法路径（返回json格式）
    type: "POST",
    dataType: "json",
    data: null,
    contentType: "application/json",
    cache: false,
    timeout: 30000,
    success: function (json) {
        var data = json;
        var servicedata = [];
        var android = new Object();
        android.name = "android";
        android.value = data.android;

        var ios = new Object();
        ios.name = "ios";
        ios.value = data.ios;

        var pc = new Object();
        pc.name = "后台用户";
        pc.value = data.pc;

        servicedata[0] = android;
        servicedata[1] = ios;
        servicedata[2] = pc;


        myChart1.setOption({
            title: {
                text: '用户来源',
                subtext: '(全部数据)',
                x: 'center'
            },
            tooltip: {
                trigger: 'item',
                formatter: "{b} <br/>{c} : {d} %"      //a 系列名称，b 数据项名称，c 数值，d 百分比
            },
            legend: {
                orient: 'vertical',
                x: 'left',
                data: ['android', 'ios', '后台用户']                                //绑定名称
            },
            toolbox: {
                show: true,
                feature: {
                    mark: {show: true},
                    dataView: {show: true, readOnly: false},
                    magicType: {
                        show: true,
                        type: ['pie', 'funnel'],
                        option: {
                            funnel: {
                                x: '25%',
                                width: '50%',
                                funnelAlign: 'left',
                                max: 1548
                            }
                        }
                    },
                    restore: {show: true},
                    saveAsImage: {show: true}
                }
            },
            calculable: true,
            series: [
                {
                    name: '点击量',
                    type: 'pie',
                    radius: '55%',//饼图的半径大小
                    center: ['50%', '60%'],//饼图的位置
                    data: servicedata                               //填充数据
                }
            ]
        })

    },
    error: function (errorMsg) {
        //请求失败时执行该函数
        layer.msg("图表请求数据失败,请重试！", {icon: 2})
        myChart1.hideLoading();
    }
});

<!--用户行为-->

$.ajax({
    url: "/margin/gallery/admin/userReg/getPicture",
    type: 'GET',
    dataType: 'json',
    success: function (data) {
        var date = []
        var index = [];
        var show = [];
        var content = [];
        for (var i = 0; i < data.length; i++) {
            index.push(data[i].index)
            show.push(data[i].show)
            content.push(data[i].content)
            date.push(data[i].time)
        }

        zFun(date, index, show, content);

    },
});

var statistics;

function zFun(date, y, y2, y3) {
    // 基于准备好的dom，初始化echarts实例
    statistics = echarts.init(document.getElementById('statistics'));
    // 指定图表的配置项和数据
    statistics.setOption({
        title: {
            text: '浏览量统计',
            subtext: '(近7天)'
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross',
                label: {
                    backgroundColor: '#6a7985'
                }
            }
        },
        legend: {
            data: ['首页', '发现', '评论']
        },
        toolbox: {},
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: [{
            type: 'category',
            boundaryGap: false,
            data: date,
            axisLabel: {
                textStyle: {
                    fontSize: 10
                }
            }
        }],
        yAxis: [{
            type: 'value'
        }],
        series: [{
            name: '首页',
            type: 'line',
            label: {
                normal: {
                    show: true,
                    position: 'top'
                }
            },
            stack: '总量',
            itemStyle: {
                normal: {
                    color: '#d4b6bb'
                }
            },

            areaStyle: {
                normal: {}
            },
            data: y
        }, {
            name: '发现',
            type: 'line',
            stack: '总量',
            itemStyle: {
                normal: {
                    color: '#a8bcd4'
                }
            },
            areaStyle: {
                normal: {}
            },
            data: y2
        },

            {
                name: '评论',
                type: 'line',
                stack: '总量',
                itemStyle: {
                    normal: {
                        color: '#739d74'
                    }
                },
                areaStyle: {
                    normal: {}
                },
                data: y3
            }]

    });
}


<!--数字滚动插件countUp-->


var userCount;
var newUser;
var online;
var indexBrowse;
var StartTime;

$(document).ready(function () {

    onlineNow();
});

var element = layui.element;

$(".li-1").click(function () {
    //新增一个Tab项
    parent.layui.element.tabAdd('bodyTab', {
        title: "用户列表" + '<i id="addli1" onclick="$(\'.top_tab > .layui-this\').remove(); $(window.top.document.querySelector(\'#top_tabs\').children).last().click() " class="layui-icon layui-unselect">ဆ</i>',
        content: '<iframe class="addif" name="iframe" src="/margin/gallery/admin/user/list" frameborder="0" style="display:block;width: 100%;"></iframe>',
        id: 1
    });
    $(window.top.document.querySelector('#top_tabs').children).last().click()
    element.init();
});
$(".li-3").click(function () {
    //新增一个Tab项
    parent.layui.element.tabAdd('bodyTab', {
        title: "用户列表" + '<i id="addli1" onclick="$(\'.top_tab > .layui-this\').remove(); $(window.top.document.querySelector(\'#top_tabs\').children).last().click() " class="layui-icon layui-unselect">ဆ</i>',
        content: '<iframe class="addif" name="iframe" src="/margin/gallery/admin/user/list?addType=b" frameborder="0" style="display:block;width: 100%;"></iframe>',
        id: 2
    });
    $(window.top.document.querySelector('#top_tabs').children).last().click()
    element.init();
});

// var hot = echarts.init(document.getElementById("hotMap"));
var hot;
var value3 = [];
var nameMap = {
    'Afghanistan': '阿富汗',
    'Albania': '阿尔巴尼亚',
    'Algeria': '阿尔及利亚',
    'Andorra': '安道尔',
    'Angola': '安哥拉',
    'Antarctica': '南极洲',
    'Antigua and Barbuda': '安提瓜和巴布达',
    'Argentina': '阿根廷',
    'Armenia': '亚美尼亚',
    'Australia': '澳大利亚',
    'Austria': '奥地利',
    'Azerbaijan': '阿塞拜疆',
    'The Bahamas': '巴哈马',
    'Bahrain': '巴林',
    'Bangladesh': '孟加拉国',
    'Barbados': '巴巴多斯',
    'Belarus': '白俄罗斯',
    'Belgium': '比利时',
    'Belize': '伯利兹',
    'Benin': '贝宁',
    'Bermuda': '百慕大',
    'Bhutan': '不丹',
    'Bolivia': '玻利维亚',
    'Bosnia and Herzegovina': '波斯尼亚和黑塞哥维那',
    'Botswana': '博茨瓦纳',
    'Brazil': '巴西',
    'Brunei': '文莱',
    'Bulgaria': '保加利亚',
    'Burkina Faso': '布基纳法索',
    'Burundi': '布隆迪',
    'Cambodia': '柬埔寨',
    'Cameroon': '喀麦隆',
    'Canada': '加拿大',
    'Cape Verde': '佛得角',
    'Central African Republic': '中非共和国',
    'Chad': '乍得',
    'Chile': '智利',
    'China': '中国',
    'Colombia': '哥伦比亚',
    'Comoros': '科摩罗',
    'Republic of the Congo': '刚果共和国',
    'Costa Rica': '哥斯达黎加',
    'Croatia': '克罗地亚',
    'Cuba': '古巴',
    'Cyprus': '塞浦路斯',
    'Czech Republic': '捷克共和国',
    'Denmark': '丹麦',
    'Djibouti': '吉布提',
    'Dominica': '多米尼加',
    'Dominican Republic': '多明尼加共和国',
    'Ecuador': '厄瓜多尔',
    'Egypt': '埃及',
    'El Salvador': '萨尔瓦多',
    'Equatorial Guinea': '赤道几内亚',
    'Eritrea': '厄立特里亚',
    'Estonia': '爱沙尼亚',
    'Ethiopia': '埃塞俄比亚',
    'Falkland Islands': '福克兰群岛',
    'Faroe Islands': '法罗群岛',
    'Fiji': '斐济',
    'Finland': '芬兰',
    'France': '法国',
    'French Guiana': '法属圭亚那',
    'French Southern and Antarctic Lands': '法属南半球和南极领地',
    'Gabon': '加蓬',
    'Gambia': '冈比亚',
    'Gaza Strip': '加沙',
    'Georgia': '格鲁吉亚',
    'Germany': '德国',
    'Ghana': '加纳',
    'Greece': '希腊',
    'Greenland': '格陵兰',
    'Grenada': '格林纳达',
    'Guadeloupe': '瓜德罗普',
    'Guatemala': '危地马拉',
    'Guinea': '几内亚',
    'Guinea Bissau': '几内亚比绍',
    'Guyana': '圭亚那',
    'Haiti': '海地',
    'Honduras': '洪都拉斯',
    'Hong Kong': '香港',
    'Hungary': '匈牙利',
    'Iceland': '冰岛',
    'India': '印度',
    'Indonesia': '印尼',
    'Iran': '伊朗',
    'Iraq': '伊拉克',
    'Iraq-Saudi Arabia Neutral Zone': '伊拉克阿拉伯中立区',
    'Ireland': '爱尔兰',
    'Isle of Man': '马恩岛',
    'Israel': '以色列',
    'Italy': '意大利',
    'Ivory Coast': '科特迪瓦',
    'Jamaica': '牙买加',
    'Jan Mayen': '扬马延岛',
    'Japan': '日本',
    'Jordan': '约旦',
    'Kazakhstan': '哈萨克斯坦',
    'Kenya': '肯尼亚',
    'Kerguelen': '凯尔盖朗群岛',
    'Kiribati': '基里巴斯',
    'North Korea': '北朝鲜',
    'South Korea': '韩国',
    'Kuwait': '科威特',
    'Kyrgyzstan': '吉尔吉斯斯坦',
    'Laos': '老挝',
    'Latvia': '拉脱维亚',
    'Lebanon': '黎巴嫩',
    'Lesotho': '莱索托',
    'Liberia': '利比里亚',
    'Libya': '利比亚',
    'Liechtenstein': '列支敦士登',
    'Lithuania': '立陶宛',
    'Luxembourg': '卢森堡',
    'Macau': '澳门',
    'Macedonia': '马其顿',
    'Madagascar': '马达加斯加',
    'Malawi': '马拉维',
    'Malaysia': '马来西亚',
    'Maldives': '马尔代夫',
    'Mali': '马里',
    'Malta': '马耳他',
    'Martinique': '马提尼克',
    'Mauritania': '毛里塔尼亚',
    'Mauritius': '毛里求斯',
    'Mexico': '墨西哥',
    'Moldova': '摩尔多瓦',
    'Monaco': '摩纳哥',
    'Mongolia': '蒙古',
    'Morocco': '摩洛哥',
    'Mozambique': '莫桑比克',
    'Myanmar': '缅甸',
    'Namibia': '纳米比亚',
    'Nepal': '尼泊尔',
    'Netherlands': '荷兰',
    'New Caledonia': '新喀里多尼亚',
    'New Zealand': '新西兰',
    'Nicaragua': '尼加拉瓜',
    'Niger': '尼日尔',
    'Nigeria': '尼日利亚',
    'Northern Mariana Islands': '北马里亚纳群岛',
    'Norway': '挪威',
    'Oman': '阿曼',
    'Pakistan': '巴基斯坦',
    'Panama': '巴拿马',
    'Papua New Guinea': '巴布亚新几内亚',
    'Paraguay': '巴拉圭',
    'Peru': '秘鲁',
    'Philippines': '菲律宾',
    'Poland': '波兰',
    'Portugal': '葡萄牙',
    'Puerto Rico': '波多黎各',
    'Qatar': '卡塔尔',
    'Reunion': '留尼旺岛',
    'Romania': '罗马尼亚',
    'Russia': '俄罗斯',
    'Rwanda': '卢旺达',
    'San Marino': '圣马力诺',
    'Sao Tome and Principe': '圣多美和普林西比',
    'Saudi Arabia': '沙特阿拉伯',
    'Senegal': '塞内加尔',
    'Seychelles': '塞舌尔',
    'Sierra Leone': '塞拉利昂',
    'Singapore': '新加坡',
    'Slovakia': '斯洛伐克',
    'Slovenia': '斯洛文尼亚',
    'Solomon Islands': '所罗门群岛',
    'Somalia': '索马里',
    'South Africa': '南非',
    'Spain': '西班牙',
    'Sri Lanka': '斯里兰卡',
    'St. Christopher-Nevis': '圣',
    'St. Lucia': '圣露西亚',
    'St. Vincent and the Grenadines': '圣文森特和格林纳丁斯',
    'Sudan': '苏丹',
    'Suriname': '苏里南',
    'Svalbard': '斯瓦尔巴特群岛',
    'Swaziland': '斯威士兰',
    'Sweden': '瑞典',
    'Switzerland': '瑞士',
    'Syria': '叙利亚',
    'Taiwan': '台湾',
    'Tajikistan': '塔吉克斯坦',
    'United Republic of Tanzania': '坦桑尼亚',
    'Thailand': '泰国',
    'Togo': '多哥',
    'Tonga': '汤加',
    'Trinidad and Tobago': '特里尼达和多巴哥',
    'Tunisia': '突尼斯',
    'Turkey': '土耳其',
    'Turkmenistan': '土库曼斯坦',
    'Turks and Caicos Islands': '特克斯和凯科斯群岛',
    'Uganda': '乌干达',
    'Ukraine': '乌克兰',
    'United Arab Emirates': '阿联酋',
    'United Kingdom': '英国',
    'United States of America': '美国',
    'Uruguay': '乌拉圭',
    'Uzbekistan': '乌兹别克斯坦',
    'Vanuatu': '瓦努阿图',
    'Venezuela': '委内瑞拉',
    'Vietnam': '越南',
    'Western Sahara': '西撒哈拉',
    'Western Samoa': '西萨摩亚',
    'Yemen': '也门',
    'Yugoslavia': '南斯拉夫',
    'Democratic Republic of the Congo': '刚果民主共和国',
    'Zambia': '赞比亚',
    'Zimbabwe': '津巴布韦',
    'South Sudan': '南苏丹',
    'Somaliland': '索马里兰',
    'Montenegro': '黑山',
    'Kosovo': '科索沃',
    'Republic of Serbia': '塞尔维亚',

};


$.ajax({
    //几个参数需要注意一下
    type: "POST",//方法类型
    dataType: "json",//预期服务器返回的数据类型
    url: "/margin/gallery/admin/userReg/cityView",//url
    data: null,
    success: function (data) {
        var arr = eval(data);
        for (var i = 0; i < arr.length; i++) {
            value3.push({
                name: arr[i].province,
                //两个字符串拼接
                value: arr[i].cityCode
            });
        }
        //初始化echarts实例
        hot = echarts.init(document.getElementById('hotMap'));
        option = {
            timeline: {
                axisType: 'category',
                orient: 'vertical',
                autoPlay: true,
                inverse: true,
                playInterval: 5000,
                left: null,
                right: -105,
                top: 20,
                bottom: 20,
                width: 46,
                data: ['2016', '2017', '2018']
            },
            baseOption: {
                visualMap: {
                    type: 'piecewise', //分段型。
                    splitNumber: 6,
                    inverse: true,
                    pieces: [{
                        min: '',
                        max: 10,
                        color: '#759aa0'
                    }, {
                        min: 10,
                        max: 20,
                        color: '#73b9bc'
                    }, {
                        min: 20,
                        max: 40,
                        color: '#8dc1a9'
                    }, {
                        min: 40,
                        max: 70,
                        color: '#e69d87'
                    }, {
                        min: 70,
                        max: 140,
                        color: '#ea7e53'
                    }, {
                        min: 140,
                        //max: 1000,
                        color: '#dd6b66'
                    }],
                    left: 'left',
                    top: 'top',
                    textStyle: {
                        color: '#f8f8f8'
                    },
                    //min: 0,
                    //max: 60000,
                    //text:['High','Low'],
                    //realtime: true,
                    //calculable: true,
                    //color: ['red','orange','lightgreen']
                },
                series: [{
                    type: 'map',
                    map: 'world',
                    roam: false,
                    itemStyle: {
                        emphasis: {label: {show: false}}
                    },
                    nameMap: nameMap

                }]
            },

            options: [{
                backgroundColor: '#404a59',
                tooltip: {
                    trigger: 'item',
                    formatter: function (params) {
                        var value = (params.value);
                        value = value;
                        return params.name + ' : ' + value;
                    }
                },
                series: {
                    data: value3

                }
            },]
        }
        hot.setOption(option);
    }
});


function onlineNow() {
    $.ajax({
        type: "POST",//方法类型
        url: "/margin/gallery/admin/userReg/getUserCount",
        dataType: "json",
        async: false,
        success: function (data) {
            indexBrowse = eval(data.indexBrowse)
            StartTime = eval(data.startTime)
            if (roleId != 1) {
                layer.tips('权限不足，无法查看', '#productNumber_1', {
                    tips: [1, '#cc2215'],
                    time: 5000
                });
                layer.tips('权限不足，无法查看', '#productNumber_3', {
                    tips: [1, '#cc2215'],
                    time: 5000
                });


                userCount = 0;
                newUser = 0;

            } else {
                online = eval(data.online)
                userCount = eval(data.userCount)
                newUser = eval(data.newUser)
            }
        }
    });

    document.getElementById('productNumber_1').setAttribute("data-sum", userCount);
    document.getElementById('productNumber_2').setAttribute("data-sum", online);
    document.getElementById('productNumber_3').setAttribute("data-sum", newUser);
    document.getElementById('productNumber_4').setAttribute("data-sum", indexBrowse);
    document.getElementById('productNumber_5').setAttribute("data-sum", StartTime);
    productNumber = function () {

        layer.tips('点击可以实时更新窝！', '#productNumber_2', {
            tips: [1, '#a8e0a4'],
            time: 3000
        });
        var arr = [];
        $('#productNumber li').each(function (i, dom) {
            var iDom = $(dom).find('i'),
                decimals = 0,
                sum = iDom.data('sum');
            //初始化传参，参数为上面注释中的参数
            arr.push(new CountUp(iDom.attr('id'), 0, sum, decimals, 3, {
                useEasing: true,//效果
                separator: ''//数字分隔符
            }));
            arr[i].start();
        });
    }();
}

var oldNum = 0;

var source = null;

function sseSocket() {
    if (typeof (EventSource) !== "undefined") {
        if (source == null) {
            layer.msg("正在建立连接......", {icon: 4})
            source = new EventSource("http://47.244.42.220:8800/online/sseMsg");
            source.onmessage = function (event) {
                var num = event.data;
                $('#productNumber_2').text(num);
                if (num > oldNum) {
                    oldNum = num;
                    $('#icon').html("<i id=\"icon\" class=\"layui-icon\">&#xe619;</i>");
                } else {
                    oldNum = num;
                    $('#icon').html("<i id=\"icon\" class=\"layui-icon\">&#xe61a;</i>");
                }
                var form = layui.form; //只有执行了这一步，部分表单元素才会自动修饰成功
                layui.use('form', function () {
                    form.render();

                });

            };
            source.onerror = function (event) {
                console.log("error" + event);
            }
            source.onopen = function () {
                layer.msg("连接成功!", {icon: 6})
                console.log('开始连接');
            }
        } else {
            layer.msg("连接已打开，别碰我!", {icon: 5})
        }
    } else {
        layer.msg("你的浏览器不支持SSE推送，无法实时获取在线数据", {icon: 5});
    }


}


function webSocket() {
    if ("WebSocket" in window) {
        // 打开一个 web socket
        var ws = new WebSocket("ws://localhost:8100/websocket/magin");

        ws.onopen = function () {
            // Web Socket 已连接上，使用 send() 方法发送数据
            ws.send("客户端请求连接...");
        };

        ws.onmessage = function (evt) {
            var received_msg = evt.data;
            console.log("收到信息:" + received_msg);
            $('.num').text(received_msg)
        };

        ws.onclose = function () {
            // 关闭 websocket
            console.log("连接关闭.")
        };
    } else {
        // 浏览器不支持 WebSocket
        layer.msg("您的浏览器不支持 WebSocket,部分数据自己刷新窝", {icon: 6});
    }

}


var form = layui.form; //只有执行了这一步，部分表单元素才会自动修饰成功
layui.use('form', function () {

});
form.render();


