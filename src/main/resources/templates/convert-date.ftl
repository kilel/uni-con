<#import "/spring.ftl" as spring/>
<#import "utils.ftl" as utils />

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Date converter</title>
    <@utils.libs />
    <script>
        $(document).ready(function () {
            $("#convert-to-interval").click(function () {
                var source = $("#source-date").val();
                var target = $("#target-date").val();
                var intervalType = $("#duration-unit").val();
                $.get(
                    "<@spring.url '/api/convert/date/toInterval?'/>",
                    {
                        "source":source,
                        "target":target,
                        "durationUnit":intervalType
                    },
                    function (data) {
                        console.log("got it!", data);
                        var result = data.value;
                        $("#duration-value").val(result);
                    },
                    'json'
                ).fail(function (data) {
                    var result = data.responseJSON.message;
                    alert('Failed to get response! ' + result)
                });
            });

            $("#convert-to-date").click(function () {
                var source = $("#source-date").val();
                var value = $("#duration-value").val();
                var intervalType = $("#duration-unit").val();
                $.get(
                    "<@spring.url '/api/convert/date/toDate?'/>",
                    {
                        "source":source,
                        "interval":value,
                        "durationUnit":intervalType
                    },
                    function (data) {
                        console.log("got it!", data);
                        var result = data.value;
                        $("#target-date").val(result);
                    },
                    'json'
                ).fail(function (data) {
                    var result = data.responseJSON.message;
                    alert('Failed to get response! ' + result)
                });
            });
        });
    </script>
</head>
<body>
<a href="<@spring.url '/'/>">Back to MAIN</a>

<div class="container">
    <div class="measure-block">
        <span>Source date: </span>
        <input id="source-date" type="text" value="2018-31-12 13:55:02.255+0300"/>
    </div>
    <div class="measure-block">
        <span>Target date: </span>
        <input id="target-date" type="text" value="2019-01-03 20:04:71.427+0300"/>
    </div>
    <div class="measure-block">
        <span>Interval:</span>
        <input id="duration-value" type="text"/>
        <select id="duration-unit">
            <#foreach unit in units>
                <option>${unit.getCode()}</option>
            </#foreach>
        </select>
    </div>
    <input id="convert-to-interval" type="button" value="Build interval based on dates date based on interval"/>
    <input id="convert-to-date" type="button" value="Build target date based on interval"/>
</div>

<@utils.unit_pages />

</body>
</html>