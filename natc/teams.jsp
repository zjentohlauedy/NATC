<%@ page contentType="text/html; charset=utf-8" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld"   prefix="bean"   %>
<%@ taglib uri="/WEB-INF/struts-html.tld"   prefix="html"   %>
<%@ taglib uri="/WEB-INF/struts-logic.tld"  prefix="logic"  %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>

<html:html locale="true">
<head>
  <html:base/>
  <title><bean:message key="title.teams"/></title>
  
  <link rel="stylesheet" type="text/css" href="styles/natc_layout.css" media='screen' />
  
  <style type="text/css">
    @import "styles/natc_markup.css";
  </style>
</head>
<body>

<jsp:include page="menu.jsp" />

<h1>TEAMS</h1>

<logic:present name="teamList">
<table class="teamlist">
  <tr>
    <td>
      <table class="teams">
        <tr class="heading">
	      <td><bean:message key="division.greene"/></td>
	    </tr>
	    <logic:iterate id="team" name="teamList">
	      <logic:equal name="team" property="division" value="0">
	        <tr>
	          <td>
	            <html:link page="/Team.do" paramId="team_id" paramName="team" paramProperty="team_id">
	              <bean:write name="team" property="location"/> <bean:write name="team" property="name"/>
	            </html:link>
	          </td>
	        </tr>
	      </logic:equal>
	    </logic:iterate>
      </table>
    </td>
    <td>
      <table class="teams">
        <tr class="heading">
	      <td><bean:message key="division.davis"/></td>
	    </tr>
	    <logic:iterate id="team" name="teamList">
	      <logic:equal name="team" property="division" value="1">
	        <tr>
	          <td>
	            <html:link page="/Team.do" paramId="team_id" paramName="team" paramProperty="team_id">
	              <bean:write name="team" property="location"/> <bean:write name="team" property="name"/>
	            </html:link>
	          </td>
	        </tr>
	      </logic:equal>
	    </logic:iterate>
      </table>
    </td>
  </tr>
  <tr>
    <td>
      <table class="teams">
        <tr class="heading">
	      <td><bean:message key="division.smith"/></td>
	    </tr>
	    <logic:iterate id="team" name="teamList">
	      <logic:equal name="team" property="division" value="2">
	        <tr>
	          <td>
	            <html:link page="/Team.do" paramId="team_id" paramName="team" paramProperty="team_id">
	              <bean:write name="team" property="location"/> <bean:write name="team" property="name"/>
	            </html:link>
	          </td>
	        </tr>
	      </logic:equal>
	    </logic:iterate>
      </table>
    </td>
    <td>
      <table class="teams">
        <tr class="heading">
	      <td><bean:message key="division.lawrence"/></td>
	    </tr>
	    <logic:iterate id="team" name="teamList">
	      <logic:equal name="team" property="division" value="3">
	        <tr>
	          <td>
	            <html:link page="/Team.do" paramId="team_id" paramName="team" paramProperty="team_id">
	              <bean:write name="team" property="location"/> <bean:write name="team" property="name"/>
	            </html:link>
	          </td>
	        </tr>
	      </logic:equal>
	    </logic:iterate>
      </table>
    </td>
  </tr>
</table>
</logic:present>

</body>
</html:html>
