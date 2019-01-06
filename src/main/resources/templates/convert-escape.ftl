<#import "/spring.ftl" as spring/>
<#import "utils.ftl" as utils />

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Text escaper</title>
    <@utils.libs />
    <script>
        $(document).ready(function () {
            $("#escape-button").click(function () {
                escape();
            });
            $("#unescape-button").click(function () {
                unescape();
            });
        });

        function escape() {
            callServer("escape");
        }

        function unescape() {
            callServer("unescape")
        }

        function callServer(method) {
            $.get(
                "<@spring.url '/api/convert/text/'/>" + method,
                {
                    "source": $("#source-value").val(),
                    "type": $("#escape-type").val()
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
        <select id="escape-type">
            <#foreach esType in esTypes>
                <option>${esType.getCode()}</option>
            </#foreach>
        </select>
        <input id="source-value" type="text" placeholder="Input text to escape..."/>
    </div>
    <div class="measure-block">
        <input id="target-value" type="text" readonly="true"/>
    </div>
    <input id="escape-button" type="button" value="Escape"/>
    <input id="unescape-button" type="button" value="Unescape"/>
</div>

<@utils.unit_pages />
<@utils.date_pages />
<@utils.text_pages />

</body>
</html>