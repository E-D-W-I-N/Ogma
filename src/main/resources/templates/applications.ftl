<#import "parts/common.ftl" as c>

<@c.page>

    List of Applications
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Date</th>
            <th>User</th>
            <th>Vacancy</th>
        </tr>
        </thead>
        <tbody>
        <#list applications as application>
            <tr>
                <td>${application.id}</td>
                <td>${application.date}</td>
                <td>${application.user.username}</td>
                <td>${application.vacancy}</td>
            </tr>
        <#else>
            <td>No applications</td>
        </#list>
        </tbody>
    </table>
</@c.page>