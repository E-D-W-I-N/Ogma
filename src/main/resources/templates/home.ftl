<#import "parts/common.ftl" as c>

<@c.page>
    <div>Welcome to JobHunter</div>
    <p>Click <a href="/vacancy">here</a> to see out vacancy list.</p>
    <p>or</p>
    <input type="button" onclick="location.href='/login';" value="Login"/>
    <input type="button" onclick="location.href='/registration';" value="Registration"/>
</@c.page>