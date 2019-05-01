<#import "parts/common.ftl" as c>

<@c.page>
    <div class="table-responsive">Список пользователей
        <table class="table">
            <thead>
            <tr>
                <th scope="col">Логин</th>
                <th scope="col">Роль</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <#list users as user>
                <tr>
                    <td>${user.username}</td>
                    <td><#list user.roles as role>${role}<#sep>, </#list></td>
                    <td><a href="/user/${user.id}" class="btn btn-primary">Редактировать</a></td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</@c.page>