<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<link rel="stylesheet" href="webjars/datatables/1.10.12/css/jquery.dataTables.min.css">
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron">
    <div class="container">
        <div class="shadow">
            <h3>Список еды</h3>
            <div class="view-box">
                <form id="filter" method="post" role="form" class="form-horizontal">
                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="startdate"><fmt:message key="meals.startDate"/>:</label>
                        <div class="col-sm-2">
                            <input class="form-control" name="startdate" id="startdate" type="date">
                        </div>
                        <label class="control-label col-sm-2" for="enddate"><fmt:message key="meals.endDate"/>:</label>
                        <div class="col-sm-2">
                            <input class="form-control" name="enddate" id="enddate" type="date">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label" form="starttime" for="starttime"><fmt:message key="meals.startTime"/>:</label>
                        <div class="col-sm-2">
                            <input class="form-control" name="starttime" id="starttime" type="time">
                        </div>
                        <label class="control-label col-sm-2" for="endtime"><fmt:message key="meals.endTime"/>:</label>
                        <div class="col-sm-2">
                            <input class="form-control" name="endtime" id="endtime" type="time">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-6"></div>
                        <div class="col-sm-1">
                            <button class="btn bg-primary" type="button" onclick="add()"><fmt:message key="meals.filter"/></button>
                        </div>
                    </div>
                </form>
            </div>
            <div class="container">
            <a class="btn btn-sm btn-info" onclick=""><fmt:message key="meals.add"/></a>
            </div>
            <hr/>
            <%--<div class="dataTables_wrapper form-inline dt-bootstrap no-footer" id="datateble_wrapper">--%>
                <table class="table table-striped display" id="datatable">
                <thead>
                    <tr>
                        <th><fmt:message key="meals.dateTime"/></th>
                        <th><fmt:message key="meals.description"/></th>
                        <th><fmt:message key="meals.calories"/></th>
                        <th></th>
                        <th></th>
                    </tr>
                </thead>
                <c:forEach items="${meals}" var="meal">
                    <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealWithExceed"/>
                    <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                        <td>
                                <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                                <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                                ${fn:formatDateTime(meal.dateTime)}
                        </td>
                        <td>${meal.description}</td>
                        <td>${meal.calories}</td>
                        <td><a class="btn btn-xs btn-primary edit" id="${meal.id}"><fmt:message key="common.update"/></a></td>
                        <td><a class="btn btn-xs btn-danger delete" id="${meal.id}"><fmt:message key="common.delete"/></a></td>
                    </tr>
                </c:forEach>
                </table>
            <%--</div>--%>
        </div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
<script type="text/javascript" src="webjars/jquery/2.2.4/jquery.min.js"></script>
<script type="text/javascript" src="webjars/bootstrap/3.3.7-1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="webjars/datatables/1.10.12/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="webjars/noty/2.3.8/js/noty/packaged/jquery.noty.packaged.min.js"></script>
<script type="text/javascript" src="resources/js/datatablesUtil.js"></script>
<script>
    var ajaxUrl = "profile/ajax/meals/";
    var datatableApi;

    function updateTable() {
        $.ajax({
            type: "POST",
            url: ajaxUrl+"/filter",
            data: $("#filter").serialize(),
            success: function (data) {
                updateTableByData(data);
            }
        });
        return false;
    }

    $(document).ready(function () {
        datatableApi = $('#datatable').DataTable({
            "bPaginate": false,
            "bInfo": true,
            "aoColumns":[
                {
                    "mData" : "datetime"
                },
                {
                    "mData" : "description"
                },
                {
                    "mData" : "calories"
                },
                {
                    "sDefaultContent": "Edit",
                    "bSortable": false
                },
                {
                    "sDefaultContent": "Delete",
                    "bSortable": false
                }
            ]
        });
        $("#filter").submit(function () {
            updateTable();
            return false;
        });
        makeEditable();
    });
</script>
</html>