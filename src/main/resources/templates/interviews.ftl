<#include "parts/security.ftl">
<#import "parts/common.ftl" as c>

<@c.page>
    <div class="table-responsive">Список собеседований
        <table class="table">
            <thead>
            <tr>
                <th scope="col">ID</th>
                <th scope="col">№Заявления</th>
                <th scope="col">Работник</th>
                <th scope="col">Дата и время</th>
                <th scope="col">Результат</th>
            </tr>
            </thead>
            <tbody>
            <#list interviews! as interview>
                <tr>
                    <th scope="row">${interview.id}</th>
                    <td>${interview.application.id}</td>
                    <td>
                        <#if isAdmin || isHeadHunter>
                            ${interview.application.user.username}
                        <#else >
                            ${interview.headHunter.username}
                        </#if>
                    </td>
                    <td>
                        ${interview.dateTime.toLocalDate()}
                        ${interview.dateTime.toLocalTime()}
                    </td>
                    <td>
                        <#if (isAdmin || isHeadHunter) && !interview.result?has_content>
                            <button type="button" class="btn btn-primary" data-toggle="modal"
                                    data-target="#exampleModal">
                                Добавить результат
                            </button>
                            <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog"
                                 aria-labelledby="exampleModalLabel" aria-hidden="true">
                                <script>
                                    $('#exampleModal').on('hidden.bs.modal', function () {
                                        $(this).find("input,textarea,select").val('').end();

                                    });
                                </script>
                                <div class="modal-dialog" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="exampleModalLabel">Добавить результат
                                                собеседования</h5>
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                        </div>
                                        <div class="modal-body">
                                            <form action="/interviews/result/add" id="resultForm" method="post">
                                                <div class="form-group">
                                                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                                    <div class="custom-control custom-checkbox">
                                                        <input class="custom-control-input" type="checkbox"
                                                               name="isSuccess"
                                                               id="interviewCheck">
                                                        <label class="custom-control-label" for="interviewCheck">
                                                            Собеседование пройдено
                                                        </label>
                                                    </div>
                                                    <label for="message-text" class="col-form-label">Результат:</label>
                                                    <input type="hidden" name="interviewId" value="${interview.id}"/>
                                                    <textarea name="result" class="form-control"
                                                              id="message-text"></textarea>
                                                </div>
                                            </form>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-dismiss="modal">
                                                Закрыть
                                            </button>
                                            <button type="submit" class="btn btn-primary" form="resultForm">
                                                Сохранить
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        <#elseif !interview.result?has_content>
                            -
                        <#else>
                            ${interview.result!}
                        </#if>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</@c.page>