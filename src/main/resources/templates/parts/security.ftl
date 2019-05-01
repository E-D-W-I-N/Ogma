<#assign
known = Session.SPRING_SECURITY_CONTEXT??
>

<#if known>

    <#assign
    user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
    name = user.getUsername()
    isAdmin = user.isAdmin()
    isHeadHunter = user.isHeadHunter()
    currentUserId = user.getId()
    >
<#else>
    <#assign
    name = "unknown"
    isAdmin = false
    isHeadHunter = false
    currentUserId = -1
    >
</#if>