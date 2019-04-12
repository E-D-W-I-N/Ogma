<#macro departmentAdd>
    <div>
        <form method="post">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <input type="text" name="departmentName" placeholder="Введите название отделения">
            <button type="submit">Добавить</button>
        </form>
    </div>
</#macro>