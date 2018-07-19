<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Processing Call Center</title>
    <!--<spring:url value="/resources/styles.css" var="stylesCSS" />-->
    <link href="<c:url value='resources/styles.css' />" rel="stylesheet">
</head>
<body>
<form:form method = "POST" action = "/call-center/processDispatcher" commandName = "callCenterModel">
    <form:errors path = "*" cssClass="errorblock" element="div" />
    <table align="center" class="tablaGeneral">
        <tr>
            <td align="center" class="tituloTabla" colspan="2">Processing Calls In Threads</td>
        </tr>
        <tr>
            <td class="textoFuente12NegritaCellPequeña"><form:label path = "incomingCalls">N° call to process</form:label></td>
            <td class="celdaNormal"><form:input path = "incomingCalls" /></td>
            <td><form:errors path = "incomingCalls" cssClass="smsError" /></td>
        </tr>
        <tr>
            <td class="textoFuente12NegritaCellPequeña"><form:label path = "nOperators">N° Operators Employee</form:label></td>
            <td class="celdaNormal"><form:input path = "nOperators" /></td>
            <td><form:errors path = "nOperators" cssClass="smsError" /></td>
        </tr>
        <tr>
            <td class="textoFuente12NegritaCellPequeña"><form:label path = "nSupervisor">N° Supervisors Employee</form:label></td>
            <td class="celdaNormal"><form:input path = "nSupervisor" /></td>
            <td><form:errors path = "nSupervisor" cssClass="smsError" /></td>
        </tr>
        <tr>
            <td class="textoFuente12NegritaCellPequeña"><form:label path = "nDirectors">N° Directors Employee</form:label></td>
            <td class="celdaNormal"><form:input path = "nDirectors" /></td>
            <td><form:errors path = "nDirectors" cssClass="smsError" /></td>
        </tr>
        <tr>
            <td colspan = "2">
                <input class="boton" type = "submit" value = "Process Calls"/>
            </td>
        </tr>
    </table>  
</form:form>
</body>
</html>
