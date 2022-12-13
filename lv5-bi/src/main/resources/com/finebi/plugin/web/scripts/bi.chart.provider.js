(function (factory) {
    typeof define === 'function' && define.amd ? define(factory) :
        factory();
}(function () {
        'use strict';
        var config = {
            type: "sunburst",
            text: "旭日图",
            cls: "chart-type-sunburst-column-icon",
            disabledCls: "chart-type-sunburst-column-disabled-icon",
            resultType: BICst.DESIGN.WIDGET.DETAIL,
            providers: {
                chartProvider: {
                    type: "bi.provider.share.chart"
                }
            },
            required: [
                {
                    dimension: ">=3",
                    measure: ">=1"
                }
            ]
        };

        // 全局的hashmap，存储以每个id为root的根系结构
        var IdMap = null;
        var nameMap = null;
        var sname = "";
        var sdid = "";
        var preParam;
        var curParam;

        var totalel=null;
        var totalOption=null;

        function chartDisplayProvider() {

            function Node(id = "", fid = "", name = "", value = 0, children = [], seriesName = "", seriesId = "") {
                var id = id;
                var fid = fid;
                var name = name;
                var value = value;
                var children = children;
                var seriesName = seriesName;
                var seriesId = seriesId;
            }

            function formatData(data) {
                /*//全局map
                * if(IdMap==null){
                *   1.全局map还是空的，是第一次全加载，也就是这时的items是全部数据，把这些数据进行map操作
                *   2.这个函数的返回值就是这个全局map
                * }else{
                   这一次的items只有一个，是在联动，那么用全局map做数据源，以items中的元素为root进行构建
                   需要新的函数
                * }
                * */
                var headers = data.header;//各个列名
                var items = data.items;//每一行

                var idParam = getDidOfParam(headers);//得到列名所指代的did，判断列就靠did
                if (!idParam.success) {
                    return null
                }
                // 1.全局map还是空的，是第一次全加载，也就是这时的items是全部数据，把这些数据进行map操作
                // 2.这个函数的返回值就是这个全局map
                if (IdMap == null) {
                    var itemMap = putItemInMap(idParam, items);
                    return MakeTree(itemMap)
                } else {//map不为空

                    if (items.length == IdMap.size) {
                        //这里不知道怎么回事，点击取消联动时，本来是想直接返回全局map的树形结构
                        //但没成功，索性就重新构建一遍map吧，有时间在搞这个
                        console.log("取消所有联动，回到起点")
                        IdMap = null;
                        var itemMap = putItemInMap(idParam, items);
                        return MakeTree(itemMap)
                    } else {
                        //这一次的items只有一个，是在联动，那么用全局map做数据源，以items中的元素为root进行构建
                        console.log("其他组件联动自己")
                        return findChoosenOne(idParam, items)
                    }
                }
            }

            function findChoosenOne(idParam, items) {
                var root = [];
                BI.each(items, function (i, row) {
                    BI.each(row, function (id, cell) {
                        console.log("cell:", cell)
                        if (cell.dId == idParam.id) {//id
                            var id = cell.value;

                            var target = IdMap.get(id);

                            root.push(target);
                        }
                    });
                })
                return root;
            }

            function getDidOfParam(header) {//id fid name 的did值依次放入ids，value的值单独处理
                var ids = [];
                var tid;

                BI.each(header, function (id, header) {
                    console.log("header:", header.dId, header)
                    if (BI.Utils.isDimDimensionById(header.dId)) {
                        ids.push(header.dId);
                        sdid = header.dId;
                        sname = header.text;
                    }
                    if (!tid && BI.Utils.isTargetById(header.dId)) {
                        tid = header.dId;
                    }
                });
                if (ids.length < 2 || !tid) {
                    return {success: true, id: "", fid: "", tid: ""}
                }

                console.log("DidOfParam:", ids)
                return {
                    success: true,
                    id: ids[0],
                    fid: ids[1],
                    nameId: ids[2],
                    tid: tid
                }
            }

            function putItemInMap(idParam, allRows) {
                if (IdMap == null) {
                    IdMap = new Map();
                } else {
                    return IdMap
                }
                if (nameMap == null) {
                    nameMap = new Map();
                }
                console.log("响应的数据行:", allRows)

                BI.each(allRows, function (i, row) {
                    console.log("第" + i + "row:", row)
                    var node = new Node();
                    BI.each(row, function (id, cell) {
                        console.log("cell:", cell)
                        if (cell.dId == idParam.id) {//id
                            node.id = cell.value;
                            node.seriesId = cell.dId;
                        } else if (cell.dId == idParam.fid) {//fid
                            node.fid = cell.value;
                        } else if (cell.dId == idParam.nameId) {//name
                            node.name = cell.value;
                            node.seriesName = cell.value;
                        } else if (cell.dId == idParam.tid) {//value
                            node.value = cell.value;
                        }
                    });
                    node.children = [];
                    IdMap.set(node.id + "", node)
                    nameMap.set(node.name, node)
                });
                console.log("全局map：", IdMap)
                return IdMap
            }

            function MakeTree(map) {
                var rootKey = []
                for (var [id, node] of map) {
                    if (node.fid == null) {
                        rootKey.push(id)
                        continue
                    }
                    if (map.has(node.fid)) {
                        map.get(node.fid).children.push(node)
                    }
                }
                var res = []
                for (var i = 0; i < rootKey.length; i++) {
                    res.push(map.get(rootKey[i]))
                }
                return res
            }

            this.render = function (el, d) {
                var self = this;
                console.log("el:", el)
                console.log("d:", d)
                var dataResult = formatData(d);
                if (totalel==null){
                    totalel=el;
                }
                echarts.dispose(el);
                this.chart = echarts.init(el);
                var options = {
                    series: {
                        type: 'sunburst',
                        data: dataResult,
                        radius: [0, '90%'],
                        itemStyle: {
                            borderRadius: 7,
                            borderWidth: 2
                        },
                        label: {
                            show: true
                        }
                    }

                };
                if (totalOption==null){
                    totalOption=options;
                }
                this.chart.setOption(options);


                this.chart.on("click", function (param) {
                    //纯纯的天坑，打印出来param发现其seriesId和seriesName属性是乱值
                    //翻出其调用函数发现需要赋值did，想当然的把Node中id的did值赋给了seriesId
                    //然后一直不成功，后来注意到param的name属性值是Node中的name，很奇怪
                    //一开始以为是什么错误，后来索性试一试把seriesId换成name列的did，居然成功了！
                    //但这一切的前提有个疑问，为什么param中的seriesId是乱值？还得特意赋值。。。
                    //而且还得和param的name值匹配，真的难崩，不管怎么说最后是搞定了。
                    preParam = curParam;
                    curParam = param;
                    param.seriesId = sdid;
                    param.seriesName = sname;
                    if (curParam.name == "") {//点击的是中心的返回
                        var preName = preParam.name;
                        var preNode = nameMap.get(preName);
                        var preFnode = IdMap.get(preNode.fid);
                        if (preFnode == null) {//点击了根节点之一中的返回
                            return;
                        } else {
                            curParam.name = preFnode.name;
                        }
                    }
                    self.dimensionTrigger({
                        dId: sdid,
                        //did是name的列did，text是选中的名字不用改
                        // （其实sdid就是要和这个param.name匹配上。。。）
                        value: [{dId: sdid, text: curParam.name}]
                    });
                    console.log("点击para", param)
                });

            };

            this.resize = function (width, height) {
                this.chart &&
                this.chart.resize({
                    width: width,
                    height: height
                });
            };
        }

        BI.provider("bi.provider.share.chart", chartDisplayProvider);

        BI.config("bi.provider.chart", function (provider) {
            provider.inject(config);
        });
    }
));