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
                                    <button onclick="createGroup(document.getElementById('new_group_name_input').value)" type="button" class="btn btn-default btn-lg">
                                        <span class="glyphicon glyphicon-plus"></span> Add
                                    </button>
                                </td>
                                <td>
                                    <input  onchange="onEdit(document.getElementById('new_group_name_input'))"
                                            pattern=".{1,}"
                                            id="new_group_name_input"
                                            class="form-control"
                                            value=""
                                            placeholder="input name of new contact">
                                </td>
                            </tr>
                            <c:forEach items="${groups}" var="group">
                                <tr id="group_${group.name}">
                                <td><div class="input-group input-group-sm" disabled>
                                <input id="pKey_group_${group.name}" disabled type="text" class="form-control" value="${group.name}" placeholder="name">
                                </div></td>
                                <td onclick="$('#controls_group_${group.name}').removeClass('hidden')">
                                <div class="input-group input-group-sm">
                                  <input pattern="[0-9]{10}" onchange="onEdit(document.getElementById('color_group_${group.name}'))" id="color_group_${group.name}" type="text" class="form-control" value="${group.color}" placeholder="color">
                                </div></td>
                                <td colspan="1" class="hidden" id="controls_group_${group.name}">
                                <div class="btn-group">
                                <div class="btn-group">
                                <button type="button" class="btn btn-warning" onclick="modifyGroup('${group.name}')">
                                <span class="glyphicon glyphicon-pencil"></span>
                                </button>
                                </div>
                                <div class="btn-group">
                                <button type="button" class="btn btn-danger" onclick="remGroup('${group.name}')">
                                <span class="glyphicon glyphicon-minus"></span>
                                </button>
                                </div>
                                <div class="btn-group">
                                <button type="button" class="btn btn-info" onclick="$('#controls_group_${group.name}').addClass('hidden')">
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
