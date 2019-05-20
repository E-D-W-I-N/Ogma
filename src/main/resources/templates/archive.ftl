<#import "parts/common.ftl" as c>
<@c.page>
    <div class="table-responsive">Архив
        <a href="/archive/pdf" class="btn btn-primary mb-2" style="float: right">PDF</a>
        <table class="table">
            <thead>
            <tr>
                <th scope="col">ID</th>
                <th scope="col">Вакансия</th>
                <th scope="col">Кандидат</th>
                <th scope="col">Работник</th>
                <th scope="col">Дата и время</th>
                <th scope="col">Результат</th>
                <th scope="col">Вердикт</th>
            </tr>
            </thead>
            <tbody>
            <#list archives! as archive>
                <tr>
                    <th scope="row">${archive.id}</th>
                    <td>${archive.vacancy.vacancyName}</td>
                    <td>${archive.candidate.username}</td>
                    <td>${archive.headHunter.username}</td>
                    <td>
                        ${archive.dateTime.toLocalDate()}
                        ${archive.dateTime.toLocalTime()[0..7]}
                    </td>
                    <td>${archive.isSuccess?string('✔', '✖')}</td>
                    <td>${archive.result}</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</@c.page>