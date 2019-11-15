<#macro login path isRegisterForm isProfile>
    <form action="${path}" method="post" style="margin-top: 10px;">
        <div class="form-group row">
            <label class="col-auto col-form-label">Логин :</label>
            <div class="col-auto">
                <input type="text" name="username" class="form-control ${(usernameError??)?string('is-invalid', '')}"
                       placeholder="Логин"
                       value="<#if user??>${user.username}</#if>"/>
                <#if usernameError??>
                    <div class="invalid-feedback">
                        ${usernameError}
                    </div>
                </#if>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-auto col-form-label">Пароль:</label>
            <div class="col-auto">
                <input type="password" name="password"
                       class="form-control ${(passwordError??)?string('is-invalid', '')}"
                       placeholder="Пароль"/>
                <#if passwordError??>
                    <div class="invalid-feedback">
                        ${passwordError}
                    </div>
                </#if>
            </div>
        </div>
        <#if isRegisterForm || isProfile>
            <div class="form-group row">
                <label class="col-auto col-form-label">Повторите пароль:</label>
                <div class="col-auto">
                    <input type="password" name="password2"
                           class="form-control ${(password2Error??)?string('is-invalid', '')}"
                           placeholder="Повторите пароль"/>
                    <#if password2Error??>
                        <div class="invalid-feedback">
                            ${password2Error}
                        </div>
                    </#if>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-auto col-form-label">Email:</label>
                <div class=col-auto>
                    <input type="email" name="email" class="form-control ${(emailError??)?string('is-invalid', '')}"
                           placeholder="some@some.com" value="<#if user??>${user.email}</#if>"/>
                    <#if emailError??>
                        <div class="invalid-feedback">
                            ${emailError}
                        </div>
                    </#if>
                </div>
            </div>

            <div class="form-group row">
                <label class="col-auto col-form-label">Имя:</label>
                <div class="col-auto">
                    <input type="text" name="firstName"
                           class="form-control ${(firstNameError??)?string('is-invalid', '')}"
                           placeholder="Имя" value="<#if user??>${user.firstName}</#if>"/>
                    <#if firstNameError??>
                        <div class="invalid-feedback">
                            ${firstNameError}
                        </div>
                    </#if>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-auto col-form-label">Фамилия:</label>
                <div class="col-auto">
                    <input type="text" name="lastName"
                           class="form-control ${(lastNameError??)?string('is-invalid', '')}"
                           placeholder="Фамилия" value="<#if user??>${user.lastName}</#if>"/>
                    <#if lastNameError??>
                        <div class="invalid-feedback">
                            ${lastNameError}
                        </div>
                    </#if>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-auto col-form-label">Телефон:</label>
                <div class="col-auto">
                    <input type="tel" name="phone"
                           class="form-control ${(phoneError??)?string('is-invalid', '')}" placeholder="+7-XXX-XXX-XXXX"
                           pattern="\+\d(-\d{3}){2}-\d{4}"
                           value="<#if user??>${user.phone}</#if>"/>
                    <#if phoneError??>
                        <div class="invalid-feedback">
                            ${phoneError}
                        </div>
                    </#if>
                </div>
            </div>

            <div style="margin-bottom: 20px;">
                <div class="g-recaptcha" data-sitekey="6LcZq5sUAAAAAO28fqABGA827DiJbjmx5b0ybjyQ"></div>
                <#if captchaError??>
                    <div class="alert alert-danger" role="alert">
                        ${captchaError}
                    </div>
                </#if>
            </div>

        </#if>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button class="btn btn-primary" type="submit" style="margin-bottom: 20px;">
            <#if isRegisterForm>Создать<#elseif isProfile>Сохранить<#else>Войти</#if>
        </button>
        <#if !isRegisterForm && !isProfile>
            <a href="/registration" class="btn btn-primary" style="margin-bottom: 20px;">Зарегистрироваться</a>
        </#if>
    </form>
</#macro>

<#macro logout>
    <#include "security.ftl">
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button class="btn btn-primary" type="submit"><#if currentUserId != -1>Выйти<#else>Войти</#if></button>
    </form>
</#macro>