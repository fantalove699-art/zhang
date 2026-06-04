<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>纯注解餐厅 - 点菜单</title>
</head>
<body style="text-align: center; padding-top: 50px; background-color: #f4f4f9;">

<h1 style="color: #333;">🎉 ${dynamicData}</h1>

<div style="background-color: white; padding: 20px; border-radius: 10px; width: 400px; margin: 20px auto; box-shadow: 0 4px 8px rgba(0,0,0,0.1);">
    <h3 style="color: #007bff;">✍️ 请填写点菜单</h3>

    <form action="placeOrder" method="post">
        <p>客官姓名：<input type="text" name="customerName" placeholder="比如：张三" required></p>
        <p>想吃什么：<input type="text" name="dishName" placeholder="比如：鱼香肉丝" required></p>

        <button type="submit" style="margin-top: 10px; padding: 10px 20px; background-color: #28a745; color: white; border: none; border-radius: 5px; cursor: pointer;">
            提交订单
        </button>
    </form>
</div>

<hr style="margin: 30px auto; width: 50%;"/>

<h3 style="color: #555;">📋 后厨正在准备的订单：</h3>
<ul style="list-style-type: none; padding: 0;">
    <#list orders as order>
        <li style="font-size: 18px; color: #d32f2f; margin-bottom: 10px;">
            🍲 ${order}

        <a href="cancelOrder?orderName=${order}"
           style="margin-left: 20px; font-size: 14px; background-color: #dc3545; color: white; padding: 4px 10px; text-decoration: none; border-radius: 4px;">
            ❌ 取消
        </a>
        </li>
    </#list>
</ul>

</body>
</html>