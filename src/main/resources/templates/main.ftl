<#import "/spring.ftl" as spring/>

<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Main</title>
    <#--<link rel="stylesheet" type="text/css" href="<@spring.url '/css/style.css'/>"/>-->
</head>
<body>
<h1>Basic base</h1>

<#foreach type in types>
    <a href="<@spring.url '/convert-${type}'/>">Convert ${type}</a>
</#foreach>

</body>
</html>