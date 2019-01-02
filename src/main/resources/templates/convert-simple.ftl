<#import "/spring.ftl" as spring/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Converter</title>
    <link rel="stylesheet" type="text/css" href="<@spring.url '/web/base.css'/>"/>
    <script src="<@spring.url '/web/jq.js'/>"></script>
    <script>
        $( document ).ready(function() {
            $( "#convert-button" ).click(function() {
                var source=$("#source-unit").val();
                var target=$("#target-unit").val();
                var value=$("#source-value").val();
                $.get(
                    "<@spring.url '/api/convert/measure?'/>" + 'type=${currentType}&source='+source+'&target='+target+'&value='+value,
                    function( data ) {
                        console.log("got it!",data);
                        var result = data.value;
                        $("#target-value").val(result);
                    },
                    'json'
                );
            });
        });
    </script>
</head>
<body>
<a href="<@spring.url '/'/>">Back to MAIN</a>

<div class="container">
    <div class="measure-block">
        <select id="source-unit">
            <#foreach unit in units>
                <option>${unit.getCode()}</option>
            </#foreach>
        </select>
        <input id="source-value" type="text" placeholder="Input value..."/>
    </div>
    <div class="measure-block">
        <select id="target-unit">
            <#foreach unit in units>
                <option>${unit.getCode()}</option>
            </#foreach>
        </select>
        <input id="target-value" type="text" readonly = "true"/>
    </div>
    <input id="convert-button" type="button" value="Convert"/>
</div>

<h1>Simple conversions</h1>

<ul>
    <#foreach type in types>
        <li><a href="<@spring.url '/convert/simple?type=${type}'/>">Convert ${type}</a></li>
    </#foreach>
</ul>
</body>
</html>