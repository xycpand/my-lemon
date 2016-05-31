<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/taglibs.jsp"%>
<%pageContext.setAttribute("currentHeader", "sendmail");%>
<%pageContext.setAttribute("currentMenu", "sendmail");%>
<!doctype html>
<html lang="en">

  <head>
    <%@include file="/common/meta.jsp"%>
    <title>列表</title>
    <%@include file="/common/s.jsp"%>
    <script type="text/javascript">
var config = {
    id: 'mailHistoryGrid',
    pageNo: ${page.pageNo},
    pageSize: ${page.pageSize},
    totalCount: ${page.totalCount},
    resultSize: ${page.resultSize},
    pageCount: ${page.pageCount},
    orderBy: '${page.orderBy == null ? "" : page.orderBy}',
    asc: ${page.asc},
    params: {
        'mailHistory_receiver': '${param.mailHistory_receiver}'
    },
	selectedItemClass: 'selectedItem',
	gridFormId: 'mailHistoryGridForm',
	exportUrl: 'sendmail-history-export.do'
};

var table;

$(function() {
	table = new Table(config);
    table.configPagination('.m-pagination');
    table.configPageInfo('.m-page-info');
    table.configPageSize('.m-page-size');
});
    </script>
  </head>

  <body>
    <%@include file="/header/sendmail.jsp"%>

    <div class="row-fluid">
	  <%@include file="/menu/sendmail.jsp"%>

	  <!-- start of main -->
      <section id="m-main" class="span10">

	  <article class="m-widget">
        <header class="header">
		  <h4 class="title">查询</h4>
		  <div class="ctrl">
		    <a class="btn"><i id="mailHistorySearchIcon" class="icon-chevron-up"></i></a>
		  </div>
		</header>
        <div id="mailHistorySearch" class="content content-inner">

		  <form name="mailHistoryForm" method="post" action="sendmail-history-list.do" class="form-inline">
		    <label for="mailHistory_receiver">收信人:</label>
		    <input type="text" id="mailHistory_receiver" name="mailHistory_receiver" value="${param.mailHistory_receiver}">
			<button class="btn btn-small a-search" onclick="document.mailHistoryForm.submit()">查询</button>&nbsp;
		  </form>

		</div>
	  </article>

	  <article class="m-blank">
	    <div class="pull-left">
		<%--
		  <button class="btn btn-small a-insert" onclick="location.href='sendmail-history-input.do'">新建</button>
		  <button class="btn btn-small a-remove" onclick="table.removeAll()">删除</button>
		  <button class="btn btn-small a-export" onclick="table.exportExcel()">导出</button>
		--%>
		</div>

		<div class="pull-right">
		  每页显示
		  <select class="m-page-size">
		    <option value="10">10</option>
		    <option value="20">20</option>
		    <option value="50">50</option>
		  </select>
		  条
		</div>

	    <div class="m-clear"></div>
	  </article>

      <article class="m-widget">
        <header class="header">
		  <h4 class="title">列表</h4>
		</header>
        <div class="content">
<form id="mailHistoryGridForm" name="mailHistoryGridForm" method='post' action="sendmail-history-remove.do" class="m-form-blank">
  <table id="mailHistoryGrid" class="m-table table-hover">
    <thead>
      <tr>
        <th width="10" class="m-table-check"><input type="checkbox" name="checkAll" onchange="toggleSelectedItems(this.checked)"></th>
        <th>编号</th>
        <th>创建时间</th>
        <th>模板</th>
        <th>接收者</th>
        <th>状态</th>
        <th width="80">&nbsp;</th>
      </tr>
    </thead>

    <tbody>
      <c:forEach items="${page.result}" var="item">
      <tr>
        <td><input type="checkbox" class="selectedItem a-check" name="selectedItem" value="${item.id}"></td>
        <td>${item.id}</td>
        <td>${item.createTime}</td>
        <td>${item.sendmailTemplate.name}</td>
        <td>${item.receiver}</td>
        <td>${item.status}</td>
        <td>
          <a href="sendmail-history-view.do?id=${item.id}">查看</a>
		  &nbsp;
          <a href="sendmail-history-send.do?id=${item.id}">重发</a>
        </td>
      </tr>
      </c:forEach>
    </tbody>
  </table>
</form>
        </div>
      </article>

	  <article>
	    <div class="m-page-info pull-left">
		  共100条记录 显示1到10条记录
		</div>

		<div class="btn-group m-pagination pull-right">
		  <button class="btn btn-small">&lt;</button>
		  <button class="btn btn-small">1</button>
		  <button class="btn btn-small">&gt;</button>
		</div>

	    <div class="m-clear"></div>
      </article>

      <div class="m-spacer"></div>

      </section>
	  <!-- end of main -->
	</div>

  </body>

</html>
