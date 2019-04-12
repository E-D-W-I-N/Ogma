<#import "parts/common.ftl" as c>
<#import "parts/departmentAdd.ftl" as d>
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />

<@c.page>
    <@security.authorize  access="hasAnyAuthority('ADMIN', 'HEADHUNTER')">
        <@d.departmentAdd />
    </@security.authorize>

    <div>Список отделений</div>
    <#list departments as department>
        <div>
            <b>${department.id}</b>
            <span>${department.departmentName}</span>
            <div>
                <#list department.headHunters?if_exists as headHunter>
                    <span>${headHunter.user.username}</span>
                <#else>
                    No HeadHunters
                </#list>
            </div>
        </div>
    <#else>
        No departments
    </#list>
</@c.page>