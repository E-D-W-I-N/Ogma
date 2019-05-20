<#include "parts/security.ftl">
<#import "parts/common.ftl" as c>

<@c.page>
    <link rel="stylesheet" href="/static/applications.css">
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/jquery-datetimepicker/2.5.4/jquery.datetimepicker.min.css"/>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-datetimepicker/2.5.4/build/jquery.datetimepicker.full.min.js"></script>

    <#if message??>
        <div class="alert alert-${messageType} alert-dismissible fade show" role="alert" style="margin-top: 10px;">
            ${message}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </#if>
    Список заявлений
    <a href="/applications/pdf" class="btn btn-primary mb-2" style="float: right">PDF</a>
    <div class="wrapper">
        <div class="container">
            <div class="row">
                <#list applications! as application>
                    <div class="col-xs-12 col-sm-6">
                        <div class="card">
                            <img class="img-card" src="/uploads/img/seller.jpg"/>
                            <div class="card-content">
                                <div class="d-flex w-100 justify-content-between">
                                    <h5 class="mb-1">${application.vacancy.vacancyName}</h5>
                                    <small class="endString">${application.vacancy.department.departmentName}</small>
                                </div>
                                <p class="mb-1">${application.vacancy.description}</p>
                                <small>
                                    ${application.dateTime.toLocalDate()}
                                    ${application.dateTime.toLocalTime()[0..7]}
                                </small>
                                <label class="endString">
                                    #${application.id}
                                </label>
                            </div>
                            <div class="card-footer">
                                <#if (isAdmin || isHeadHunter) && application.user.id != currentUserId>
                                    <div class="btn-group dropright" style="width: 100%;">
                                        <button class="btn btn-secondary dropdown-toggle" type="button"
                                                id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true"
                                                aria-expanded="false">
                                            <p class="Btn">Запланировать собеседование</p>
                                        </button>
                                        <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                            <form action="/interviews/add" method="post">
                                                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                                <input type="hidden" name="applicationId" value="${application.id}"/>
                                                <input required id="datetimepicker" name="stringDateTime" type="text">
                                                <script>
                                                    $(document).ready(function () {
                                                        $.datetimepicker.setLocale('ru');
                                                        $('#datetimepicker').datetimepicker({
                                                            closeOnInputClick: true,
                                                            closeOnWithoutClick: true,
                                                            format: 'Y-m-d H:i'
                                                        });
                                                    });
                                                </script>
                                                <div class="dropdown-divider"></div>
                                                <button type="submit" class="btn btn-primary btn-block">
                                                    <p class="Btn">Создать</p>
                                                </button>
                                            </form>
                                        </div>
                                    </div>
                                <#else >
                                    <form action="/applications/delete" method="post">
                                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                        <input type="hidden" name="applicationId" value="${application.id}"/>
                                        <button type="submit" class="btn btn-primary btn-block">
                                            <p class="Btn">Удалить</p>
                                        </button>
                                    </form>
                                </#if>
                            </div>
                        </div>
                    </div>
                </#list>
            </div>
        </div>
    </div>
</@c.page>