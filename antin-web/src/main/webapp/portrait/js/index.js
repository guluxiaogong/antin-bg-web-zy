var internal = {
    flag: true,
    //tagNum: 9,
    qualifiers: [],
    init: function () {
        //搜索加载数据
        internal.search();
        internal.doLayout();
        window.onresize = function () {
            internal.doLayout();
            //标签位置随窗口大小自缩放
            if (internal.qualifiers.length > 0)
                internal.buildTag(internal.qualifiers);
        };
        //临时测试用
        internal.buildDropdown();
        internal.loadData();//TODO
        internal.showBackGroupTemp();//TODO
        // alert(internal.getUrlParam(window.location,'rowKey'));
    },
    doLayout: function () {
        var $personBox = $('.person-box'), $contentBox = $('#contentBox'),
            personBoxH = $(window).height() - $('#searchBox').outerHeight() - 51;
        $personBox.height(personBoxH);
        $personBox.css('background-size', personBoxH * 0.2 + 'px ' + personBoxH * 0.8 + 'px');
        $('.loading').css({//
            left: ($('.col-sm-6').outerWidth() / 2 - 100 ) + 'px'
        });
        if ($(window).width() < 768)
            $contentBox.css('height', '100%');
        else
            $contentBox.height($(window).height() - $('#searchBox').outerHeight() - 80);

    },
    search: function () {
        //搜索按钮加载
        $('#persionSearch').on('click', function () {
            internal.loadData();
        });
        //enter 键加载
        $(document).keydown(function (event) {
            //console.log(event.keyCode);
            if (event && event.keyCode == 13) { // enter 键
                internal.loadData();
            }
        });

    },
    loadData: function () {
        $.ajax({
            url: 'hbase/findByRowKeyTest',
            data: {
                rowKey: $.trim($('#xmanId').val()),
                rowKeyTest: internal.getUrlParam(window.location, 'rowKey') || $.trim($('#inputText').val()),
                category: false,
                latelyDay: 0
            },
            beforeSend: function (XMLHttpRequest) {
                internal.clearTag();
                $('.loading').show();
            },
            success: function (result) {
                if (result && result.length) {
                    internal.qualifiers = result;
                    //$.each(result, function (index, item) {
                    //    if (item.qualifier == 'sex') {//如果没有sex字段就按个人基本信息中获取的数据
                    //        internal.showPersonImage(item.value);
                    //    }
                    //});
                    internal.buildTag(result);
                } else {
                    internal.clearTag();
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                $('.loading').hide();
            },
            complete: function (XMLHttpRequest, textStatus) {
                $('.loading').hide();
            }
        });
    },
    clearTag: function () {
        var $personBox = $('.person-box');
        if ($personBox.children().length) {
            $personBox.children().remove();
            //$personBox.css({
            //    'background': ''
            //});
            var sex = $('#sex').val();
            if (sex)
                internal.showPersonImage(sex);
        }
    },

    //position.x + left_x - short_x - 55
    buildTag: function (qualifiers) {//标签集合
        var $personBox = $('.person-box'),
            personBoxH = $personBox.height(),
        //left_x = $(window).width() / 2,
            left_x = $('.col-sm-6').outerWidth() / 2,
            short_x = personBoxH * 0.2,//短轴长
            long_y = personBoxH * 0.4,//长轴长
            count = qualifiers.length,
            step = 360 / (count | 1),//60//参数表示几等分
            angle = 30;//初始角度
        var personTag = $('.person-tag');
        for (var i = 0; i < count; i++) {
            //if (qualifiers[i].qualifier != 'name' && qualifiers[i].qualifier != 'sex') {
            if (angle >= 180)
                angle = angle - 360;

            var position = internal.getCoordinate(short_x, long_y, angle);//(短半轴，长半轴)//0 60 120 180 240 300

            if (!personTag.length) {//新建
                var qualifier = qualifiers[i];

                var value = qualifier.value;
                //if (qualifier.hasChildren) {//类别
                //    value = qualifier.qualifierName
                //}
                //else
                if (jQuery.isArray(value))
                    value = value[0].name;

                var $tag = $("<div class='person-tag' style='min-width:100px;background-color: #0be386;border-radius:10px;position: absolute;left:" + (position.x + left_x - short_x - 55) + "px;top:" + (position.y + 40) + "px;'><label style='display:block;font-size:24px; text-align:center;color:white;font-family:宋体;margin-bottom: 2px;padding: 2px 6px;'>" + value + "</label></div>");
                $tag.css('backgroundColor', qualifier.dict.color);
                $tag.appendTo('.person-box');
            } else {//频幕缩放
                $(personTag[i]).css({
                    top: position.y + 80,
                    //top: position.y + 140,
                    left: position.x + left_x - short_x - 55
                });
            }
            angle = angle + step;//角度增长
            // }
        }
    },
    // 根据角度 获取椭圆上一点的坐标
    // x2⒈/a2+y2/b2=1
    // y = x * tanα
    // a长半轴
    // b短半轴
    // rotate角度
    getCoordinate: function (a, b, rotate) {
        var x = 0, y = 0, tan = 0, rad = 0;

        if (Math.abs(rotate) > 90)
            rad = (Math.abs(rotate) - 90);
        else
            rad = (90 - Math.abs(rotate));
        rad = rad * 2 * Math.PI / 360;
        tan = Math.tan(rad);
        x = Math.sqrt(1 / (1 / (a * a) + (tan * tan) / (b * b)));
        y = x * tan;

        if (rotate < 0)
            x = 0 - x;
        if (rotate > -90 && rotate < 90)
            y = 0 - y;
        x = a + x;
        y = b + y;

        return {
            x: Math.round(x),
            y: Math.round(y)
        };
    },
    buildDropdown: function () {
        $.ajax({
            url: 'hbase/listDemoUser',
            async: 'false',
            success: function (result) {

                $.each(result, function (index, item) {
                    var el = $("<li><a href='#' code='" + item.id + "' sex='" + item.sex + "'>" + item.name + "</a></li>");

                    el.on('click', function () {
                        //alert(el.find('a').text());
                        var $a = el.find('a');
                        $('#inputText').val($a.text());
                        $('#xmanId').val($a.attr("code"));
                        $('#sex').val($a.attr("sex"));
                        //alert($a.attr("code"));
                        internal.showPersonImage($a.attr("sex"));
                        internal.loadData();
                    });
                    el.appendTo('#dropdownLi')
                });
            }
        });

    },
    showPersonImage: function (sex) {
        var imag = '';
        if (sex == '女') {
            imag = 'url(portrait/image/wman.png) no-repeat center';
            internal.flag = false;
        } else {
            imag = 'url(portrait/image/man.png) no-repeat center';
            internal.flag = true;
        }
        var personBoxH = $(window).height() - $('#searchBox').outerHeight() - 51;

        $('.person-box').css({
            'background': imag,
            'backgroundSize': personBoxH * 0.2 + 'px ' + personBoxH * 0.8 + 'px'
        });
    },
    /**
     * 获取指定URL的参数值
     * @param url  指定的URL地址
     * @param name 参数名称
     * @return 参数值
     */
    getUrlParam: function (url, name) {
        var pattern = new RegExp("[?&]" + name + "\=([^&]+)", "g");
        var matcher = pattern.exec(url);
        var items = null;
        if (null != matcher) {
            try {
                items = decodeURIComponent(decodeURIComponent(matcher[1]));
            } catch (e) {
                try {
                    items = decodeURIComponent(matcher[1]);
                } catch (e) {
                    items = matcher[1];
                }
            }
        }
        return items;
    },
    showBackGroupTemp: function () {//TODO
        var backbround = $('.person-box').css('background');
        if (backbround.indexOf('none') > 0) {
            var sex = internal.getUrlParam(window.location, 'sex');
            if (sex && (sex == '1' || sex == '2')) {
                internal.showPersonImage(sex);
            } else {
                internal.showPersonImage('1');
            }
        }

    }
};