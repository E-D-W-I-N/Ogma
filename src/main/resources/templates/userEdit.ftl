<#import "parts/common.ftl" as c>

<@c.page>
    Редактор пользователей
    <form action="/user" method="post">
        <input class="form-control col-2" type="text" name="username" value="${user.username}">
        <#list roles! as role>
            <div class="custom-control custom-checkbox">
                <input type="checkbox" id="${role}" <#if role == "MODERATOR">onchange="handleChange(this)"</#if>
                       class="custom-control-input" name="${role}"
                        ${user.roles?seq_contains(role)?string("checked", "")}>
                <label class="custom-control-label" for="${role}" <#if role == "MODERATOR">data-toggle="collapse"
                       data-target="#collapseOne"</#if>>
                    ${role}
                </label>
            </div>
        </#list>
        <input type="hidden" value="${user.id}" name="userId">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button class="btn btn-primary mb-2" type="submit">Сохранить</button>
    </form>
</@c.page>