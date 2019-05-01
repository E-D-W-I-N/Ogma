<#macro vacancyAdd>
    <form action="/vacancy/add" method="post" class="form-inline" style="margin-top: 10px;">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <select name="departmentId" class="custom-select mb-2 mr-sm-2">
            <option value="" disabled selected hidden>Выберите отдел</option>
            <#list departments! as department>
                <option value="${department.id}">${department.departmentName}</option>
            </#list>
        </select>
        <input class="form-control mb-2 mr-sm-2" type="text" name="vacancyName" placeholder="Название вакансии">
        <input class="form-control mb-2 mr-sm-2" type="text" name="description" placeholder="Описание вакансии">
        <input class="form-control mb-2 mr-sm-2" type="number" name="salary" placeholder="Зарплата">
        <button class="btn btn-primary mb-2" type="submit">Добавить</button>
    </form>
</#macro>