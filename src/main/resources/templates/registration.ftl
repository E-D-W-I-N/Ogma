<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>
    <h5>Регистрация пользователя</h5>
    <#if message??>
        <div class="alert alert-${messageType} alert-dismissible fade show" role="alert" style="margin-top: 10px;">
            ${message}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </#if>
    <@l.login "/registration" true false/>
</@c.page>