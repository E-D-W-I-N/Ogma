<#include "parts/security.ftl">
<#import "parts/common.ftl" as c>
<#import "parts/departmentAdd.ftl" as d>

<@c.page>
    <#if message??>
        <div class="alert alert-${messageType} alert-dismissible fade show" role="alert" style="margin-top: 10px;">
            ${message}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">✖</span>
            </button>
        </div>
    </#if>
    <#if isAdmin || isHeadHunter>
        <@d.departmentAdd />
    </#if>
    <div class="table-responsive">Список отделений
        <a href="/departments/pdf" class="btn btn-primary mb-2" style="float: right">PDF</a>
        <table class="table">
            <thead>
            <tr>
                <th scope="col">ID</th>
                <th scope="col">Название</th>
                <th scope="col">Вакансии</th>
                <th scope="col">Работники</th>
            </tr>
            </thead>
            <tbody>
            <#list departments! as department>
                <tr>
                    <th scope="row">${department.id}</th>
                    <td>${department.departmentName}</td>
                    <td>
                        <#list department.vacancies! as vacancy>
                            ${vacancy.vacancyName}<#sep>,<br></#sep>
                        </#list>
                    </td>
                    <td>
                        <#list department.headHunters! as headHunter>
                            ${headHunter.username}<#sep>,<br></#sep>
                        </#list>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</@c.page>