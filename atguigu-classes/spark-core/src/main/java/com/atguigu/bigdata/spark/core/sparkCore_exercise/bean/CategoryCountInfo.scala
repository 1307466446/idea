package com.atguigu.bigdata.spark.core.sparkCore_exercise.bean

case class CategoryCountInfo(categoryId: String, //品类id
                             var clickCount: Long, //点击次数
                             var orderCount: Long, //订单次数
                             var payCount: Long) {}
