<#import "parts/common.ftl" as c>

<@c.page>
    <#if product??>
        <div class="card mb-12 mt-5">
            <div class="row no-gutters">
                <div class="col-md-4" style="display: flex; flex-direction: column; justify-content: center;
            align-content: center; padding: 1%;">
                    <#if product.filename??>
                        <img src="/uploads/img/${product.filename}" class="card-img" alt="product">
                    </#if>
                </div>
                <div class="col-md-8">
                    <div class="card-body">
                        <h5 class="card-title">${product.name}</h5>
                        <p class="card-text">${product.description}</p>
                        <p class="card-text">Цена: ${product.price}</p>
                        <p class="card-text">Вес: ${product.weight}</p>
                        <p class="card-text">Дата выпуска: ${product.date}</p>
                        <form action="/shoppingCart/addProduct/${product.id}" method="get"
                              enctype="multipart/form-data">
                            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                            <button class="btn btn-primary mb-2" type="submit">Добавить в корзину</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    <#else>
        <div class="alert alert-danger alert-dismissible fade show" role="alert" style="margin-top: 10px;">
            Товар не найден
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">✖</span>
            </button>
        </div>
    </#if>
</@c.page>