<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/taglibs.jsp"%>
<%pageContext.setAttribute("currentHeader", "template");%>
<%pageContext.setAttribute("currentMenu", "template");%>
<!doctype html>
<html lang="en">

  <head>
    <%@include file="/common/meta.jsp"%>
    <title><spring:message code="template-info.template-info.input.title" text="编辑"/></title>
    <%@include file="/common/s.jsp"%>
    <script type="text/javascript">
$(function() {
    $("#template-infoForm").validate({
        submitHandler: function(form) {
			bootbox.animate(false);
			var box = bootbox.dialog('<div class="progress progress-striped active" style="margin:0px;"><div class="bar" style="width: 100%;"></div></div>');
            form.submit();
        },
        errorClass: 'validate-error'
    });

<c:if test="${model.type != 'manual'}">
	var editor = CKEDITOR.replace('templateField_content');
</c:if>

})
    </script>
  </head>

  <body>
    <%@include file="/header/template-info.jsp"%>

    <div class="row-fluid">
	  <%@include file="/menu/template-info.jsp"%>

	  <!-- start of main -->
      <section id="m-main" class="span10">

      <article class="m-widget">
        <header class="header">
		  <h4 class="title"><spring:message code="template-info.template-info.input.title" text="编辑"/></h4>
		</header>

		<div class="content content-inner">

<form id="template-infoForm" method="post" action="template-field-save.do" class="form-horizontal">
  <c:if test="${model != null}">
  <input id="template-info_id" type="hidden" name="id" value="${model.id}">
  </c:if>
  <div class="control-group">
    <label class="control-label" for="template-info_name"><spring:message code="template-info.template-info.input.name" text="名称"/></label>
	<div class="controls">
	  <input id="template-info_name" type="text" name="name" value="${model.name}" size="40" class="text required" minlength="2" maxlength="10">
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="templateField_infoId">模板</label>
	<div class="controls">
	  <select id="templateField_infoId" name="infoId">
	    <c:forEach items="${templateInfos}" var="item">
		<option value="${item.id}">${item.name}</option>
		</c:forEach>
	  </select>
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="template-info_code">类型</label>
	<div class="controls">
	  <label><input id="mailTemplate_manual0" type="radio" name="type" value="manual" ${model.type == 'manual' ? 'checked' : ''}>手工</label>
	  <label><input id="mailTemplate_manual0" type="radio" name="type" value="ckeditor" ${empty model.type || model.type == 'ckeditor' ? 'checked' : ''}>ckeditor</label>
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="templateField_content">内容</label>
	<div class="controls">
	  <textarea id="templateField_content" name="content">${model.content}</textarea>
    </div>
  </div>
  <div class="control-group">
    <div class="controls">
      <button type="submit" class="btn a-submit"><spring:message code='core.input.save' text='保存'/></button>
	  &nbsp;
      <button type="button" class="btn a-cancel" onclick="history.back();"><spring:message code='core.input.back' text='返回'/></button>
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
