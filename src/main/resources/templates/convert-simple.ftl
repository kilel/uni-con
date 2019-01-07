<#import "/spring.ftl" as spring/>
<#import "utils.ftl" as utils />

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${currentType} converter</title>
    <@utils.libs />
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

<@utils.unit_pages />
<@utils.date_pages />
<@utils.text_pages />

</body>
</html>