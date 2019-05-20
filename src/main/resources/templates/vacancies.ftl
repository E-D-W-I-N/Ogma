<#include "parts/security.ftl">
<#import "parts/common.ftl" as c>
<#import "parts/vacancyAdd.ftl" as v>

<@c.page>
    <link rel="stylesheet" href="/static/vacancies.css">
    <#if isAdmin || isHeadHunter>
        <@v.vacancyAdd />
    </#if>
    <#if message??>
        <div class="alert alert-danger alert-dismissible fade show" role="alert">${message}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </#if>
    Список вакансий
    <a href="/vacancy/pdf" class="btn btn-primary mb-2" style="float: right">PDF</a>
    <div class="row">
        <div class="col-4">
            <div class="list-group" id="list-tab" role="tablist">
                <#list departments as department>
                    <a class="list-group-item list-group-item-action" data-toggle="list" href="#list-${department.id}"
                       role="tab">${department.departmentName}
                        <span class="badge badge-primary badge-pill ml-3">${department.vacancies?size}</span>
                    </a>
                <#else>
                    Нет отделов
                </#list>
            </div>
        </div>
        <div class="col-8">
            <div class="tab-content" id="nav-tabContent">
                <#list departments as department>
                <div class="tab-pane fade" id="list-${department.id}" role="tabpanel"
                     style="overflow-y: scroll;height: 85vh; overflow-x: hidden;">
                    <div class="row">
                    <#list department.vacancies>
                        <#items as vacancy>
                            <div class="col-md-6 col-lg-5 col-xl-5" id="card-wrapper">
                                <div class="card h-100">
                                    <img class="card-img-top img-fluid" src="/uploads/img/seller.jpg"
                                         alt="Card image cap">
                                    <div class="card-body text-dark">
                                        <h5 class="card-title">${vacancy.vacancyName}</h5>
                                        <h4 class="card-text">${vacancy.salary}</h4>
                                        <p class="card-text">${vacancy.description}</p>
                                    </div>
                                    <div class="card-footer">
                                        <form action="/applications/add" method="post">
                                            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                            <input type="hidden" name="vacancyId" value="${vacancy.id}"/>
                                            <button type="submit" class="btn btn-primary btn-block">
                                                <p class="createBtn">Подать заявление</p>
                                            </button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </#items>
                        </div>
                        </div>
                    <#else>
                        <p style="position: absolute;">Нет вакансий</p>
                    </#list>
                </#list>
            </div>
        </div>
    </div>
</@c.page>