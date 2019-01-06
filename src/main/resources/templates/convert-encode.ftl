<#import "/spring.ftl" as spring/>
<#import "utils.ftl" as utils />

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Text encoder</title>
    <@utils.libs />
    <script>
        $(document).ready(function () {
            $("#encode-button").click(function () {
                encode("source", "target");
            });
            $("#encode-back-button").click(function () {
                encode("target", "source");
            });
        });

        function encode(sourceKey, targetKey) {
            $.get(
                "<@spring.url '/api/convert/text/encode'/>",
                {
                    "source": $("#"+sourceKey+"-value").val(),
                    "sourceType": $("#"+sourceKey+"-type").val(),
                    "targetType": $("#"+targetKey+"-type").val()
                },
                function (data) {
                    console.log("got it!", data);
                    var result = data.value;
                    $("#"+targetKey+"-value").val(result);
                },
                'json'
            ).fail(function (data) {
                var result = data.responseJSON.message;
                alert('Failed to get response! ' + result);
            });
        }
    </script>
</head>
<body>
<a href="<@spring.url '/'/>">Back to MAIN</a>

<div class="container">
    <div class="measure-block">
        <select id="source-type">
            <#foreach encodeType in encodeTypes>
                <option>${encodeType.getCode()}</option>
            </#foreach>
        </select>
        <input id="source-value" type="text" placeholder="Input text to encode..."/>
    </div>
    <div class="measure-block">
        <select id="target-type">
            <#foreach encodeType in encodeTypes>
                <option>${encodeType.getCode()}</option>
            </#foreach>
        </select>
        <input id="target-value" type="text" readonly="true"/>
    </div>
    <input id="encode-button" type="button" value="Encode"/>
    <input id="encode-back-button" type="button" value="Encode (back)"/>
</div>

<@utils.unit_pages />
<@utils.date_pages />
<@utils.text_pages />

</body>
</html>