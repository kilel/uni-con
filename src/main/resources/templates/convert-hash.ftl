<#import "/spring.ftl" as spring/>
<#import "utils.ftl" as utils />

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Text hasher</title>
    <@utils.libs />
    <script>
        $(document).ready(function () {
            $("#hash-button").click(function () {
                hash();
            });
        });

        function hash() {
            $.get(
                "<@spring.url '/api/convert/text/hash'/>",
                {
                    "source": $("#source-value").val(),
                    "encoding": $("#encode-type").val(),
                    "encodingTgt": $("#encode-type-tgt").val(),
                    "hashType": $("#hash-type").val()
                },
                function (data) {
                    console.log("got it!", data);
                    var result = data.value;
                    $("#target-value").val(result);
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
        <select id="encode-type">
            <#foreach encodeType in encodeTypes>
                <option>${encodeType.getCode()}</option>
            </#foreach>
        </select>
        <input id="source-value" type="text" placeholder="Input text to hash..."/>
    </div>
    <div class="measure-block">
        <select id="hash-type">
            <#foreach hashType in hashTypes>
                <option>${hashType.getCode()}</option>
            </#foreach>
        </select>
        <select id="encode-type-tgt">
            <#foreach encodeType in encodeTypes>
                <option>${encodeType.getCode()}</option>
            </#foreach>
        </select>
        <input id="target-value" type="text" readonly="true"/>
    </div>
    <input id="hash-button" type="button" value="Hash"/>
</div>

<@utils.unit_pages />
<@utils.date_pages />
<@utils.text_pages />

</body>
</html>