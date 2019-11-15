<#macro productAdd>
    <form action="/catalog/products" method="post" enctype="multipart/form-data">
        <#list types! as type>
            <div class="custom-control custom-checkbox">
                <input type="checkbox" id="${type}" class="custom-control-input" name="${type}">
                <label class="custom-control-label" for="${type}">
                    ${type}
                </label>
            </div>
        </#list>
        <input class="form-control mb-2 mr-sm-2" type="text" name="name" placeholder="Название">
        <input class="form-control mb-2 mr-sm-2" type="text" name="description" placeholder="Описание">
        <input class="form-control mb-2 mr-sm-2" type="number" name="price" placeholder="Цена">
        <input class="form-control mb-2 mr-sm-2" type="number" name="weight" placeholder="Вес, г.">
        <input class="form-control mb-2 mr-sm-2" type="date" name="date" placeholder="Дата выпуска">
        <input class="form-control mb-2 mr-sm-2" type="file" name="file">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button class="btn btn-primary mb-2" type="submit">Добавить</button>
    </form>
</#macro>