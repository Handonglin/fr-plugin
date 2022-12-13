!(function () {
    Van.FRChartBridge.demoWrapper = Van.FRChartBridge.AbstractChart.extend({
        _init: function (dom, option) {
            var chart = echarts.init(dom);
            chart.on('click', this.getLinkFun());
            chart.setOption(option);
            return chart;
        },

        _refresh: function (chart, option) {
            chart.setOption(option);
        },

        _resize: function (chart) {
            chart.resize();
        },

        _emptyData: function (options) {
            return options.series.data.length === 0;
        }
    })
})();