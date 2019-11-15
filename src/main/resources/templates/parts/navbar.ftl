<#include "security.ftl">
<#import "login.ftl" as l>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="/">Ogma</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="/">Главная</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/catalog/products/books">Книги</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/catalog/products/office-supplies">Канцелярия</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/catalog/products/souvenirs">Сувениры</a>
            </li>
            <#if isAdmin>
                <li class="nav-item">
                    <a class="nav-link" href="/user">Пользователи</a>
                </li>
            </#if>
            <#if currentUserId != -1>
                <li class="nav-item">
                    <a class="nav-link" href="/user/profile">Профиль</a>
                </li>
            </#if>
        </ul>

        <div class="navbar-text mr-3"><#if currentUserId != -1>${name}<#else>Пожалуйста, войдите в аккаунт </#if></div>
        <a class="btn btn-primary m-2" href="/shoppingCart">Корзина</a>
        <@l.logout/>
    </div>
</nav>