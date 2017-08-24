<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/header.jsp" />
<%@ page import="java.util.ArrayList" %>

        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12 bubna-col">
                    <div class="table-responsive">
                        <table class="table table-hover">
                            <tr>
                                <td>
                                    <button onclick="createContact(document.getElementById('new_contact_name_input').value)" type="button" class="btn btn-default btn-lg">
                                        <span class="glyphicon glyphicon-plus"></span> Add
                                    </button>
                                </td>
                                <td>
                                    <input  onchange="onEdit(document.getElementById('new_contact_name_input'))"
                                            id="new_contact_name_input"
                                            pattern=".{1,}"
                                            class="form-control"
                                            value=""
                                            placeholder="input name of new contact">
                                </td><td></td><td></td><td></td><td></td><td></td>
                            </tr>
                            <c:forEach items="${contacts}" var="contact">
                                <tr id="contact_${contact.name}">
                                    <td><div class="input-group input-group-sm" disabled>
                                        <input id="pKey_contact_${contact.name}" disabled type="text" class="form-control" value="${contact.name}" placeholder="name">
                                    </div></td>
                                    <td onclick="$('#controls_contact_${contact.name}').removeClass('hidden')">
                                        <div class="input-group input-group-sm">
                                        <input onchange="onEdit(document.getElementById('email_contact_${contact.name}'))" id="email_contact_${contact.name}" type="text" class="form-control" value="${contact.email}" placeholder="email">
                                    </div></td>
                                    <td onclick="$('#controls_contact_${contact.name}').removeClass('hidden')">
                                        <div class="input-group input-group-sm">
                                        <input pattern="[0-9]{10}" onchange="onEdit(document.getElementById('num_contact_${contact.name}'))" id="num_contact_${contact.name}" type="text" class="form-control" value="${contact.num}" placeholder="number">
                                    </div></td>
                                    <td onclick="$('#controls_contact_${contact.name}').removeClass('hidden')">
                                        <div class="input-group input-group-sm">
                                        <input onchange="onEdit(document.getElementById('skype_contact_${contact.name}'))" id="skype_contact_${contact.name}" type="text" class="form-control" value="${contact.skype}" placeholder="skype">
                                    </div></td>
                                    <td onclick="$('#controls_contact_${contact.name}').removeClass('hidden')">
                                        <div class="input-group input-group-sm">
                                        <input onchange="onEdit(document.getElementById('telegram_contact_${contact.name}'))" id="telegram_contact_${contact.name}" type="text" class="form-control" value="${contact.telegram}" placeholder="telegram">
                                    </div></td>
                                    <td onclick="$('#controls_contact_${contact.name}').removeClass('hidden')">
                                        <div class="input-group input-group-sm">
                                        <input onchange="onEdit(document.getElementById('gName_contact_${contact.name}'))" id="gName_contact_${contact.name}" type="text" class="form-control" value="${contact.group.name}" placeholder="group name">
                                    </div></td>
                                    <td colspan="1" class="hidden" id="controls_contact_${contact.name}">
                                        <div class="btn-group">
                                        <div class="btn-group">
                                        <button type="button" class="btn btn-warning" onclick="modifyContact('${contact.name}')">
                                        <span class="glyphicon glyphicon-pencil"></span>
                                        </button>
                                        </div>
                                        <div class="btn-group">
                                        <button type="button" class="btn btn-danger" onclick="remContact('${contact.name}')">
                                        <span class="glyphicon glyphicon-minus"></span>
                                        </button>
                                        </div>
                                        <div class="btn-group">
                                        <button type="button" class="btn btn-info" onclick="$('#controls_contact_${contact.name}').addClass('hidden')">
                                        <span class="glyphicon glyphicon-chevron-right"></span>
                                        </button>
                                        </div>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
