<#include "security.ftl">
<#import "login.ftl" as l>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="/">JobHunter</a>
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
                <a class="nav-link" href="/vacancy">Вакансии</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/departments">Отделы</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/applications">Заявления</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/interviews">Собеседования</a>
            </li>
            <#if isAdmin || isHeadHunter>
                <li class="nav-item">
                    <a class="nav-link" href="/archive">Архив</a>
                </li>
            </#if>
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
        <@l.logout/>
    </div>
</nav>