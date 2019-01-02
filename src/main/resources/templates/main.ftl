<#import "/spring.ftl" as spring/>

<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Main</title>
</head>
<body>

<h1>Simple conversions</h1>

<ul>
    <#foreach type in types>
        <li><a href="<@spring.url '/convert/simple?type=${type}'/>">Convert ${type}</a></li>
    </#foreach>
</ul>

</body>
</html>