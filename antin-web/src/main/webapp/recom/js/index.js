var internal = {
    userList: null,
    init: function () {
        internal.doLayout();
        internal.loadUser();
        internal.loadOrg();
        internal.loadRecom({});
        internal.clearSelect();

        window.onresize = function () {
            internal.doLayout();
        };
    },
    loadUser: function () {//加载用户
        //$('.loading').show();
        internal.loadSelectData({
            id: 'userBox',
            url: '../recom/queryDemoUser',
            data: null,
            func: internal.loadUserHistory
        }, true);
    },
    loadOrg: function () {//加载机构
        $('#deptBox').children().remove();
        $('#doctorBox').children().remove();
        internal.loadSelectData({
            id: 'orgBox',
            url: '../recom/queryOrg',
            data: null,
            func: function (e) {
                //e.stopPropagation();
                var orgName = $('#orgName'), deptName = $('#deptName'),doctorBox = $('#doctorBox'),
                    doctorName = $('#doctorName');
                var orgId = $(this).prop('code');
                orgName.val($(this).find('a').text());
                orgName.prop('code', orgId);
                deptName.val('');
                deptName.prop('code', '');
                doctorName.val('');
                doctorName.prop('code', '');
                doctorBox.nextAll().remove();
                internal.loadRecom({orgId: orgId});
                internal.loadDept(orgId);
            }
        }, true);
    },
    loadDept: function (orgId) {//加载科室
        $('#doctorBox').children().remove();
        internal.loadSelectData({
            id: 'deptBox',
            url: '../recom/queryDeptByOrgId',
            data: {orgId: orgId},
            func: function (e) {
                //e.stopPropagation();
                var deptNode = $('#deptName'), doctorName = $('#doctorName');
                var deptCode = $(this).prop('code');
                deptNode.val($(this).find('a').text());
                deptNode.prop('code', deptCode);
                doctorName.val('');
                doctorName.prop('code', '');
                internal.loadRecom({orgId: orgId, deptCode: deptCode});
                internal.loadDoctor(orgId, deptCode);
            }
        }, true);
    },
    loadDoctor: function (orgId, deptCode) {//加载医生
        internal.loadSelectData({
            id: 'doctorBox',
            url: '../recom/queryDoctorByOrgIdAndDeptCode',
            data: {orgId: orgId, deptCode: deptCode},
            func: function (e) {
                // e.stopPropagation();
                $('#doctorName').val($(this).find('a').text());
                var doctorCode = $(this).prop('code');
                internal.loadRecom({orgId: orgId, deptCode: deptCode, doctorCode: doctorCode});
            }
        }, true);
    },
    loadRecom: function (params) {//加载推荐列表
        //$('#doctorList').html(params.orgId + '<br/>' + params.deptCode + '<br/>' + params.doctorCode);
        var doctorList = $('#doctorList>table>tbody');
        doctorList.children().remove();

        //加载推荐列表
        internal.ajax({
            url: '../recom/recomEngine',
            data: $.extend(params, {personId: $('#userId').prop('personId')}),
            callback: function (result) {
                //$('#doctorList').html(JSON.stringify(result));
                $.each(result, function (index, item) {
                    var tr = $("<tr><th scope='row' style='width:35px;'></th><td></td><td></td><td style='min-width: 45px;'></td><td style='min-width: 150px;'></td></tr>");
                    tr.find('th').text(index + 1);
                    $(tr.find('td')[0]).text(item.orgName);
                    $(tr.find('td')[1]).text(item.deptName);
                    $(tr.find('td')[2]).text(item.doctorName);
                    $(tr.find('td')[3]).html(internal.dateFormat(item.startTime) + '<br/>' + internal.dateFormat(item.endTime));
                    tr.prop('rom', item.rom);
                    tr.on('click', function () {
                        internal.loadTimeList($(this).prop('rom'));
                    });
                    tr.appendTo(doctorList);
                    if (index == 0) {
                        var process = $('#process');
                        process.html('');
                        process.html("<font color='green'>" + (item.process || '&nbsp;') + "</font>")
                        internal.loadTimeList(item.rom.reverse());
                    }
                });

            }
        }, false);
    },
    loadTimeList: function (lis) {
        var timeList = $('#timeList');
        timeList.children().remove();
        $.each(lis, function (index, item) {
            var li = $("<li class='list-group-item'></li>");
            li.text((index + 1) + ' ' + ' ' + item.doctorName + '  ' + internal.dateFormat(item.startTime) + ' -- ' + internal.dateFormat(item.endTime));
            li.appendTo(timeList);
        });
    },
    loadSelectData: function (params, isHidden) {//加载下拉通用
        internal.ajax({
            url: params.url,
            data: params.data,
            callback: function (result) {
                //alert(JSON.stringify(result));
                $.each(result, function (index, item) {
                    var li = $("<li><a href='#'></a></li>");
                    li.prop("code", item.id || item.doctorCode || item.deptCode || item.orgId);
                    li.find('a').text(item.name);
                    li.on('click', params.func);
                    $('#' + params.id).after(li);
                });
                if (params.id == 'userBox')
                    internal.userList = result;

            }
        }, isHidden);
    },
    loadUserHistory: function () {//用户历史预约记录
        var userList = $('#userList>table>tbody');
        userList.children().remove();

        var userId = $('#userId'), orgName = $('#orgName'),
            deptName = $('#deptName'), doctorName = $('#doctorName'),deptBox = $('#deptBox'), doctorBox = $('#doctorBox');

        $('#userName').val($(this).find('a').text());
        var personId = $(this).prop("code");
        userId.prop('personId', personId);

        orgName.val('');
        orgName.prop('code', '');
        deptName.val('');
        deptName.prop('code', '');
        doctorName.val('');
        doctorName.prop('code', '');
        deptBox.nextAll().remove();
        doctorBox.nextAll().remove();

        internal.loadRecom({personId: personId});

        internal.ajax({
            url: '../recom/queryUserHistory',
            data: {
                userId: $(this).prop('code')
            },
            callback: function (result) {
                // alert(JSON.stringify(result));
                $.each(result, function (index, item) {
                    var tr = $("<tr><th scope='row' style='width:35px;'></th><td></td><td></td><td style='min-width: 45px;'></td><td style='min-width: 150px;'></td></tr>");
                    tr.find('th').text(index + 1);
                    $(tr.find('td')[0]).text(item.orgName);
                    $(tr.find('td')[1]).text(item.deptName);
                    $(tr.find('td')[2]).text(item.doctorName);
                    $(tr.find('td')[3]).html(item.startTime + '<br/>' + item.endTime);
                    tr.appendTo(userList);
                });

            }
        }, true);
    },
    clearSelect: function () {
        var userId = $('#userId'), orgName = $('#orgName'),
            deptName = $('#deptName'), doctorName = $('#doctorName'), deptBox = $('#deptBox'), doctorBox = $('#doctorBox');
        $('#clearUser').on('click', function () {
            userId.prop('personId', '');
            $('#userName').val('');
            $('#userList>table>tbody').children().remove();
            orgName.val('');
            orgName.prop('code', '');
            deptName.val('');
            deptName.prop('code', '');
            deptBox.nextAll().remove();
            doctorBox.nextAll().remove();
            doctorName.prop('code', '');
            doctorName.val('');
            internal.loadRecom({});
        });
        $('#clearOrg').on('click', function () {
            orgName.val('');
            orgName.prop('code', '');
            deptName.val('');
            deptName.prop('code', '');
            doctorName.val('');
            doctorName.prop('code', '');
            deptBox.nextAll().remove();
            doctorBox.nextAll().remove();
            internal.loadRecom({peronId: userId.prop('personId')});
        });
        $('#clearDept').on('click', function () {
            deptName.val('');
            deptName.prop('code', '');
            doctorName.val('');
            doctorBox.nextAll().remove();
            internal.loadRecom({peronId: userId.prop('personId'), orgId: orgName.prop('code')});
        });
        $('#clearDoctor').on('click', function () {
            doctorName.val('');
            internal.loadRecom({
                peronId: userId.prop('personId'),
                orgId: orgName.prop('code'),
                deptCode: deptName.prop('code')
            });
        });

    },
    autoSearchUser: function () {
        $('#userName').keydown(function (event) {
            if (event && event.keyCode == 13) { // enter 键
                //TODO
            }
        });
    },
    ajax: function (params, isHidden) {//ajax
        var $loading = $('.loading');
        $.ajax({
            url: params.url,
            data: params.data,
            beforeSend: function (XMLHttpRequest) {
                if (!isHidden)
                    $loading.show();
            },
            success: function (result) {
                try {
                    if (result && result.length)
                        params.callback(result);
                } catch (e) {
                    $loading.hide();
                    alert(e);
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                $loading.hide();
            },
            complete: function (XMLHttpRequest, textStatus) {
                if (!isHidden)
                    $loading.hide();
            }
        });
    },
    dateFormat: function (time) {
        try {
            if (time) {
                var date = new Date(time);
                return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate() + ' ' + date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds();
            }
        } catch (e) {
            console.log(e);
        }
        return '';
    },
    doLayout: function () {
        var winHeight = $(window).height(),
            offsetHight = 40 + 20 + 34 + 20 + 41 + 22;
        $('#userList').height(winHeight - offsetHight - 30);
        $('#doctorList,#timeList').height(winHeight - offsetHight);
    }

};