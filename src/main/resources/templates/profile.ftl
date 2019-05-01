<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>
    <h5><#if user??>${user.username}</#if></h5>
    ${message!}
    <@l.login "/user/profile" false true/>
</@c.page>