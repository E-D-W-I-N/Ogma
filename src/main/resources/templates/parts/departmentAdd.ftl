<#macro departmentAdd>
    <form method="post" class="form-inline" style="margin-top: 10px;">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <input class="form-control mb-2 mr-sm-2 ${(departmentNameError??)?string('is-invalid', '')}" type="text"
               name="departmentName"
               placeholder="Название отделения"/>
        <button class="btn btn-primary mb-2" type="submit">Добавить</button>
        <#if departmentNameError??>
            <div class="invalid-feedback">
                ${departmentNameError}
            </div>
        </#if>
    </form>
</#macro>