<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

    <script type="text/javascript" src="https://cdn.goeasy.io/goeasy-1.2.1.js"></script>
    <script src="${path}/bootstrap/js/jquery.min.js"></script>
    <script src="${path}/bootstrap/js/echarts.js"></script>
    <script src="${path}/bootstrap/js/china.js"></script>
    <script type="text/javascript">
        $(function(){
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main'));

            $.get("${path}/echarts/getUserRegirestChinaDatas",function(datas){

                //data=[{}]
                //声明一个数组变量
                var series=[]

                for(var i=0;i<datas.length;i++){
                    //获取数组中的一个对象
                    var data = datas[i];

                    series.push(
                        {
                            name: data.sex,
                            type: 'map',
                            mapType: 'china',
                            roam: false,
                            label: {
                                normal: {
                                    show: false
                                },
                                emphasis: {
                                    show: true
                                }
                            },
                            data: data.city
                        }
                    )
                }

                // 指定图表的配置项和数据
                var option = {
                    title : {
                        text: '用户注册量分布图',
                        subtext: '纯属虚构',
                        left: 'center'
                    },
                    tooltip : {
                        trigger: 'item'
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left',
                        data:['小男生','小姑娘']  //选项卡
                    },
                    visualMap: {
                        min: 0,
                        max: 1000,
                        left: 'left',
                        top: 'bottom',
                        text:['高','低'],           // 文本，默认为数值文本
                        calculable : true
                    },
                    toolbox: {
                        show: true,
                        orient : 'vertical',
                        left: 'right',
                        top: 'center',
                        feature : {
                            mark : {show: true},
                            dataView : {show: true, readOnly: false},
                            restore : {show: true},
                            saveAsImage : {show: true}
                        }
                    },
                    series : series
                };

                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);
            },"json");

        });
    </script>

    <script type="text/javascript">
        var goEasy = GoEasy.getInstance({
            host:'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
            appkey: "BC-af99dc7b9e2c48ce9cf54bfd0237bb2e" //替换为您的应用appkey
        });

        goEasy.connect({
            onSuccess: function () {  //连接成功
                console.log("GoEasy connect successfully.") //连接成功
            },
            onFailed: function (error) { //连接失败
                console.log("Failed to connect GoEasy, code:"+error.code+ ",error:"+error.content);
            },
            onProgress:function(attempts) { //连接或自动重连中
                console.log("GoEasy is connecting", attempts);
            }
        });

        $(function (){
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main'));

            goEasy.subscribe({
                channel: "my_channel",//替换为您自己的channel
                onMessage: function (message) {
                    console.log("Channel:" + message.channel + " content:" + message.content);

                    var GoEasyData= message.content;

                    //讲json对象转换为javascript对象
                    var datas = JSON.parse(GoEasyData);

                    //data=[{}]
                    //声明一个数组变量
                    var series=[]

                    for(var i=0;i<datas.length;i++){
                        //获取数组中的一个对象
                        var data = datas[i];

                        series.push(
                            {
                                name: data.sex,
                                type: 'map',
                                mapType: 'china',
                                roam: false,
                                label: {
                                    normal: {
                                        show: false
                                    },
                                    emphasis: {
                                        show: true
                                    }
                                },
                                data: data.city
                            }
                        )
                    }

                    // 指定图表的配置项和数据
                    var option = {
                        title : {
                            text: '用户注册量分布图',
                            subtext: '纯属虚构',
                            left: 'center'
                        },
                        tooltip : {
                            trigger: 'item'
                        },
                        legend: {
                            orient: 'vertical',
                            left: 'left',
                            data:['小男生','小姑娘']  //选项卡
                        },
                        visualMap: {
                            min: 0,
                            max: 1000,
                            left: 'left',
                            top: 'bottom',
                            text:['高','低'],           // 文本，默认为数值文本
                            calculable : true
                        },
                        toolbox: {
                            show: true,
                            orient : 'vertical',
                            left: 'right',
                            top: 'center',
                            feature : {
                                mark : {show: true},
                                dataView : {show: true, readOnly: false},
                                restore : {show: true},
                                saveAsImage : {show: true}
                            }
                        },
                        series : series
                    };

                    // 使用刚指定的配置项和数据显示图表。
                    myChart.setOption(option);
                }/*,
            onSuccess: function () {
                console.log("Channel订阅成功。");
            },
            onFailed: function (error) {
                console.log("Channel订阅失败, 错误编码：" + error.code + " 错误信息：" + error.content)
            }*/
            });
        });
    </script>
<div id="main" style="width: 600px;height: 400px"></div>
