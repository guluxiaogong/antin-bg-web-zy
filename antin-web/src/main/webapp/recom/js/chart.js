var internal = {
    init: function () {
        internal.doLayout();

        //**********************************************************************//
        var myChart = echarts.init(document.getElementById('containerChart'));

        myChart.setOption(internal.option());
        //**********************************************************************//

        window.onresize = function () {
            internal.doLayout();
        }
    },
    doLayout: function () {

    },
    option: function () {
        return {
            title: {
                text: 'ECharts 入门示例'
            },
            tooltip: {},
            legend: {
                data: ['销量']
            },
            xAxis: {
                data: ["衬衫", "羊毛衫", "雪纺衫", "裤子", "高跟鞋", "袜子"]
            },
            yAxis: {},
            series: [{
                name: '销量',
                type: 'bar',
                data: [5, 20, 36, 10, 10, 20]
            }]
        };
    }

};