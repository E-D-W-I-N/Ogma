<#import "parts/common.ftl" as c>

<@c.page>
    <h5>${user.username}</h5>
    ${message?if_exists}
    <form method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>

        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> User Name: </label>
            <div class="col-sm-6">
                <input type="text" name="username" class="form-control" placeholder="username"
                       value="${user.username!''}"/>
            </div>
        </div>

        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> Password: </label>
            <div class="col-sm-6">
                <input type="password" name="password" class="form-control" placeholder="Password"/>
            </div>
        </div>

        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> Email: </label>
            <div class="col-sm-6">
                <input type="email" name="email" class="form-control" placeholder="some@some.com"
                       value="${user.email!''}"/>
            </div>
        </div>

        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> First Name: </label>
            <div class="col-sm-6">
                <input type="text" name="firstName" class="form-control" placeholder="First Name"
                       value="${user.firstName!''}"/>
            </div>
        </div>

        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> Last Name: </label>
            <div class="col-sm-6">
                <input type="text" name="lastName" class="form-control" placeholder="Last Name"
                       value="${user.lastName!''}"/>
            </div>
        </div>

        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> Education: </label>
            <div class="col-sm-6">
                <input type="text" name="education" class="form-control" placeholder="Eduation"
                       value="${user.education!''}"/>
            </div>
        </div>

        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> Experience: </label>
            <div class="col-sm-6">
                <input type="number" name="experience" class="form-control" placeholder="Experience"
                       step="any" value="${user.experience!''}"/>
            </div>
        </div>

        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> Phone: </label>
            <div class="col-sm-6">
                <input type="tel" name="phone" class="form-control" placeholder="Phone Number"
                       pattern="7\([0-9]{3}\)[0-9]{3}-[0-9]{2}-[0-9]{2}" value="${user.phone!''}"/>
            </div>
        </div>
        <button type="submit" class="btn btn-primary">Save</button>
    </form>
</@c.page>