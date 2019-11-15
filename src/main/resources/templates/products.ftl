<#include "parts/security.ftl">
<#import "parts/common.ftl" as c>
<#import "parts/productAdd.ftl" as v>

<@c.page>

    <#if message??>
        <div class="alert alert-${messageType} alert-dismissible fade show" role="alert" style="margin-top: 10px;">
            ${message}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">✖</span>
            </button>
        </div>
    </#if>
    <div class="table-responsive">
        <#if isAdmin || isModerator>
            <button class="btn btn-primary m-2" type="button" data-toggle="collapse" data-target="#collapseExample"
                    aria-expanded="false" aria-controls="collapseExample" style="float: left">
                Добавить товар
            </button>
            <div class="collapse" id="collapseExample">
                <div class="card card-body m-2">
                    <@v.productAdd />
                </div>
            </div>
        </#if>
        <a href="/catalog/products/pdf" class="btn btn-primary m-2" style="float: right">PDF</a>
        <table class="table">
            <thead>
            <tr>
                <th scope="col">Название</th>
                <th scope="col">Описание</th>
                <th scope="col">Цена</th>
                <th scope="col">Вес</th>
                <th scope="col">Дата выпуска</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <#list products as product>
                <tr>
                    <td>
                        <a href="/catalog/products/${springMacroRequestContext.requestUri?keep_after_last("/")}/${product.id}">${product.name}</a>
                    </td>
                    <td>${product.description?truncate(30, '...')}</td>
                    <td>${product.price}</td>
                    <td>${product.weight}</td>
                    <td>${product.date}</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</@c.page>