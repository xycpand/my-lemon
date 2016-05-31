<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/taglibs.jsp"%>
<%pageContext.setAttribute("currentHeader", "cms");%>
<%pageContext.setAttribute("currentMenu", "cms");%>
<!doctype html>
<html>

  <head>
    <%@include file="/common/meta.jsp"%>
    <title>编辑文章</title>
    <%@include file="/common/s.jsp"%>
	<script type="text/javascript" src="${ctx}/s/ckeditor/ckeditor.js"></script>
    <script type="text/javascript">
$(function() {
    $("#cmsArticleForm").validate({
        submitHandler: function(form) {
			bootbox.animate(false);
			var box = bootbox.dialog('<div class="progress progress-striped active" style="margin:0px;"><div class="bar" style="width: 100%;"></div></div>');
            form.submit();
        },
        errorClass: 'validate-error'
    });
	var editor = CKEDITOR.replace('cmsArticle_content');
	// editorObj.config.filebrowserImageUploadUrl = dir + "core/connector/" + ckfinder.ConnectorLanguage + "/connector." + ckfinder.ConnectorLanguage + "?command=QuickUpload&type=" + ( imageType || 'Images' ) ;
	editor.config.filebrowserImageUploadUrl = "${tenantPrefix}/cms/cms-article-uploadImage.do";
})
    </script>
  </head>

  <body>
    <%@include file="/header/cms.jsp"%>

    <div class="row-fluid">
	  <%@include file="/menu/cms.jsp"%>

	<!-- start of main -->
    <section id="m-main" class="span10">

      <article class="m-widget">
        <header class="header">
		  <h4 class="title">编辑文章</h4>
		</header>
		<div class="content content-inner">

<form id="cmsArticleForm" method="post" action="cms-article-save.do" class="form-horizontal">
  <c:if test="${model != null}">
  <input id="cms-article_id" type="hidden" name="id" value="${model.id}">
  </c:if>
  <div class="control-group">
    <label class="control-label" for="cms-article_cmsArticlename">栏目</label>
	<div class="controls">
      <select id="perm_resc" name="cmsCatalogId">
	    <c:forEach items="${cmsCatalogs}" var="item">
	    <option value="${item.id}" ${model.cmsCatalog.id==item.id ? 'selected' : ''}>${item.name}</option>
		</c:forEach>
	  </select>
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="cms-article_cmsArticlename">标题</label>
	<div class="controls">
	  <input id="cms-article_cmsArticlename" type="text" name="title" value="${model.title}" size="40" class="text required" minlength="2" maxlength="50">
    </div>
  </div>
<!--
  <div class="control-group">
    <label class="control-label" for="cms-article_cmsArticlename">短标题</label>
	<div class="controls">
	  <input id="cms-article_cmsArticlename" type="text" name="shortTitle" value="${model.shortTitle}" size="40" class="text" minlength="2" maxlength="50">
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="cms-article_cmsArticlename">副标题</label>
	<div class="controls">
	  <input id="cms-article_cmsArticlename" type="text" name="subTitle" value="${model.subTitle}" size="40" class="text" minlength="2" maxlength="50">
    </div>
  </div>
-->
  <div class="control-group">
    <label class="control-label" for="cmsArticle_summary">摘要</label>
	<div class="controls">
	  <textarea id="cmsArticle_summary" name="summary" maxlength="200">${model.summary}</textarea>
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="cms-article_cmsArticlename">内容</label>
	<div class="controls">
	  <textarea id="cmsArticle_content" name="content" class="text required" minlength="2" maxlength="50">${model.content}</textarea>
    </div>
  </div>
<!--
  <div class="control-group">
    <label class="control-label" for="cms-article_cmsArticlename">图标</label>
	<div class="controls">
	  <input id="cms-article_cmsArticlename" type="text" name="logo" value="${model.logo}" size="40" class="text" minlength="2" maxlength="50">
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="cms-article_cmsArticlename">关键字</label>
	<div class="controls">
	  <input id="cms-article_cmsArticlename" type="text" name="keyword" value="${model.keyword}" size="40" class="text" minlength="2" maxlength="50">
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="cms-article_cmsArticlename">标签</label>
	<div class="controls">
	  <input id="cms-article_cmsArticlename" type="text" name="tags" value="${model.tags}" size="40" class="text" minlength="2" maxlength="50">
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="cms-article_cmsArticlename">来源</label>
	<div class="controls">
	  <input id="cms-article_cmsArticlename" type="text" name="source" value="${model.source}" size="40" class="text" minlength="2" maxlength="50">
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="cms-article_cmsArticlename">允许评论</label>
	<div class="controls">
	  <input id="cms-article_cmsArticlename" type="checkbox" name="allowComment" value="1" class="text" ${model.allowComment == 1 ? 'checked' : ''}>
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="cms-article_cmsArticlename">状态</label>
	<div class="controls">
	  <input id="cms-article_cmsArticlename" type="checkbox" name="status" value="1" class="text" ${model.status == 1 ? 'checked' : ''}>
    </div>
  </div>
-->
<!--
  <div class="control-group">
    <label class="control-label" for="cms-article_cmsArticlename">创建时间</label>
	<div class="controls">
	  <input id="cms-article_cmsArticlename" type="text" name="createTime" value="${model.createTime}" class="text">
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="cms-article_cmsArticlename">发布时间</label>
	<div class="controls">
	  <input id="cms-article_cmsArticlename" type="text" name="publishTime" value="${model.publishTime}" class="text">
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="cms-article_cmsArticlename">关闭时间</label>
	<div class="controls">
	  <input id="cms-article_cmsArticlename" type="text" name="closeTime" value="${model.closeTime}" class="text">
    </div>
  </div>
-->
<!--
  <div class="control-group">
    <label class="control-label" for="cms-article_cmsArticlename">类型</label>
	<div class="controls">
	  <input id="cms-article_cmsArticlename" type="text" name="type" value="${model.type}" class="text">
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="cms-article_cmsArticlename">置顶</label>
	<div class="controls">
	  <input id="cms-article_cmsArticlename" type="text" name="top" value="${model.top}" class="text">
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="cms-article_cmsArticlename">权重</label>
	<div class="controls">
	  <input id="cms-article_cmsArticlename" type="text" name="weight" value="${model.weight}" class="text">
    </div>
  </div>
-->

  <div class="control-group">
    <div class="controls">
      <button id="submitButton" class="btn a-submit"><spring:message code='core.input.save' text='保存'/></button>
      <button type="button" onclick="history.back();" class="btn a-cancel"><spring:message code='core.input.back' text='返回'/></button>
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
