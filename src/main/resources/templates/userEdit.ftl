<#import "parts/common.ftl" as c>

<@c.page>
    Редактор пользователей
    <form action="/user" method="post">
        <input class="form-control col-2" type="text" name="username" value="${user.username}">
        <#list roles! as role>
            <div class="custom-control custom-checkbox">
                <input type="checkbox" id="${role}" <#if role == "HEADHUNTER">onchange="handleChange(this)"</#if>
                       class="custom-control-input" name="${role}"
                        ${user.roles?seq_contains(role)?string("checked", "")}>
                <label class="custom-control-label" for="${role}" <#if role == "HEADHUNTER">data-toggle="collapse"
                       data-target="#collapseOne"</#if>>
                    ${role}
                </label>
            </div>
        </#list>
        <div id="collapseOne" class="collapse col-3">
            <select disabled required id="departmentSelect" name="departmentId" class="custom-select mb-2 mr-sm-2">
                <option value="" disabled selected hidden>Select department</option>
                <#list departments! as department>
                    <option value="${department.id}">${department.departmentName}</option>
                <#else>
                    No departments
                </#list>
            </select>
        </div>
        <input type="hidden" value="${user.id}" name="userId">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button class="btn btn-primary mb-2" type="submit">Сохранить</button>
    </form>
    <script>
        function handleChange(checkbox) {
            if (checkbox.checked == true) {
                $('#departmentSelect').prop('disabled', false);
            } else {
                $('#departmentSelect').prop('disabled', true);
            }
        }
    </script>
</@c.page>