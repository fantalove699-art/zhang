<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>我的 Spring MVC 首页</title>
</head>
<body style="text-align: center; padding-top: 100px; background-color: #f4f4f9;">

<h1 style="color: #333;">🎉 欢迎来到纯注解版 Spring MVC 世界！</h1>
<p style="font-size: 18px; color: #666;">大堂经理已经成功把您引导至 index.jsp</p>

<a href="${pageContext.request.contextPath}/user/save"
   style="display: inline-block; margin-top: 20px; padding: 10px 20px; background-color: #007bff; color: white; text-decoration: none; border-radius: 5px;">
    点击测试 User 接口
</a>

</body>
</html>