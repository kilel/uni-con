<#import "/spring.ftl" as spring/>

<#macro libs>
    <link rel="stylesheet" type="text/css" href="<@spring.url '/web/base.css'/>"/>
    <script src="<@spring.url '/web/jq.js'/>"></script>
</#macro>

<#macro unit_pages>
    <h1>Simple conversions</h1>

    <ul>
        <#foreach type in types>
            <li><a href="<@spring.url '/convert/simple?type=${type}'/>">Convert ${type}</a></li>
        </#foreach>
    </ul>
</#macro>

<#macro date_pages>
    <h1>Dates conversions</h1>
    <a href="<@spring.url '/convert/date'/>">Convert dates</a>
</#macro>

<#macro text_pages>
    <h1>Text conversions</h1>
    <ul>
        <li><a href="<@spring.url '/convert/escape'/>">Escape/unescape</a></li>
        <li><a href="<@spring.url '/convert/encode'/>">Encode/decode</a></li>
        <li><a href="<@spring.url '/convert/hash'/>">Hash</a></li>
    </ul>
</#macro>
