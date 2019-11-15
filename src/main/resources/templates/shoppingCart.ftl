<#import "parts/common.ftl" as c>

<@c.page>
    <table class="table m-2">
        <thead>
        <tr>
            <th scope="col">Название</th>
            <th scope="col">Цена</th>
            <th scope="col">Вес</th>
            <th scope="col">Количество</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <#list products as productName, productKey>
            <tr>
                <td>${productName.name}</td>
                <td>${productName.price}</td>
                <td>${productName.weight}</td>
                <td>${productKey}</td>
                <td>
                    <form action="/shoppingCart/removeProduct/${productName.id}" method="get"
                          enctype="multipart/form-data">
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                        <button class="btn btn-primary mb-2" type="submit">✖</button>
                    </form>
                </td>
            </tr>
        </#list>
        </tbody>
    </table>
    <h2 class="m-4">
        Итог: ${total} Рублей.
    </h2>
</@c.page>