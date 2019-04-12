<#macro vacancyAdd>
    <div>
        <form action="/vacancy/add" method="post">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <input type="number" name="departmentId" placeholder="Введите ID отделения">
            <input type="text" name="vacancyName" placeholder="Введите название вакансии">
            <input type="text" name="description" placeholder="Введите описание вакансии">
            <input type="number" name="salary" placeholder="Введите зарплату">
            <button type="submit">Добавить</button>
        </form>
    </div>
</#macro>