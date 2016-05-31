<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/taglibs.jsp"%>
<%pageContext.setAttribute("currentHeader", "sendmail");%>
<%pageContext.setAttribute("currentMenu", "sendmail");%>
<!doctype html>
<html lang="en">

  <head>
    <%@include file="/common/meta.jsp"%>
    <title>测试</title>
    <%@include file="/common/s.jsp"%>
  </head>

  <body>
    <%@include file="/header/sendmail.jsp"%>

    <div class="row-fluid">
	  <%@include file="/menu/sendmail.jsp"%>

	  <!-- start of main -->
      <section id="m-main" class="span10">

      <article class="m-widget">
        <header class="header">
		  <h4 class="title">编辑</h4>
		</header>

		<div class="content content-inner">

<form id="mailHistoryForm" class="form-horizontal">
  <div class="control-group">
    <label class="control-label" for="mailHistory_sender">发件人</label>
	<div class="controls">
	  <c:out value="${mailHistory.sender}"/>
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="mailHistory_receiver">收件人</label>
	<div class="controls">
	  <c:out value="${mailHistory.receiver}"/>
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="mailHistory_cc">抄送</label>
	<div class="controls">
	  <c:out value="${mailHistory.cc}"/>
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="mailHistory_bcc">暗送</label>
	<div class="controls">
	  <c:out value="${mailHistory.bcc}"/>
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="mailHistory_subject">标题</label>
	<div class="controls">
	  ${mailHistory.subject}
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="mailHistory_content">内容</label>
	<div class="controls">
	  ${mailHistory.content}
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="mailHistory_content">数据</label>
	<div class="controls">
	  ${mailHistory.data}
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="mailHistory_attachment">附件</label>
	<div class="controls">
      <c:forEach items="${mailHistory.sendmailTemplate.sendmailAttachments}" var="item">
		<a href="sendmail-attachment-download.do?id=${item.id}"><i class="badge">${item.name}</i></a>
      </c:forEach>
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="mailHistory_mailTemplateId">模板</label>
	<div class="controls">
	  ${mailHistory.sendmailTemplate.name}
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="mailHistory_mailConfigId">SMTP服务器</label>
	<div class="controls">
	  ${mailHistory.sendmailConfig.name}
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="mailHistory_mailConfigId">发送结果</label>
	<div class="controls">
	  ${mailHistory.status}
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="mailHistory_mailConfigId">结果备注</label>
	<div class="controls">
	  ${mailHistory.info}
    </div>
  </div>
</form>
        </div>
      </article>

      </section>
	  <!-- end of main -->
	</div>

  </body>

</html>
