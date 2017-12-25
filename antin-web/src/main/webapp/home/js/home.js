var internal = {
    init: function () {
        internal.doLayout();

        var items = [
            {
                name: '预 约 推 荐',
                class: 'fa-desktop',
                url: '../recom/index'
            },
            {
                name: '居 民 画 像',
                class: 'fa-tachometer',
                //url: 'http://192.168.14.97:8080/hpor/index'
                url: 'http://192.168.14.97:8080/hpor/'
            }

        ];
        internal.buildItems(items);

        window.onresize = function () {
            internal.doLayout();
        };
        //console.log($(window).height());

    },
    buildItems: function (items) {
        $.each(items, function (index, item) {
            var leftList = $('#leftList'),
                li = $("<li class=''><a href='#'><i class='fa fa-fw'></i><span class='menu-text'></span><span class='selected'></span></a> </li>");
            if (!index)
                li.addClass('active');
            li.find('i').addClass(item.class || 'fa-tachometer');
            li.find('.menu-text').text(item.name);
            li.on('click', function () {
                leftList.find('li').removeClass('active');
                $(this).addClass('active');
                $('.content-box>iframe').prop('src', item.url);
            });
            li.appendTo('#leftList');
        });

    },
    doLayout: function () {
        var headerH = $('header').height();
        $('.content-box').height($(window).height() - headerH);
    }


};