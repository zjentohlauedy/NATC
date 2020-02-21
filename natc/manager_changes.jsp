<%@ page contentType="text/html; charset=utf-8" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld"   prefix="bean"   %>
<%@ taglib uri="/WEB-INF/struts-html.tld"   prefix="html"   %>
<%@ taglib uri="/WEB-INF/struts-logic.tld"  prefix="logic"  %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>

<html:html locale="true">
<head>
  <html:base/>
  <title><bean:message key="title.games"/></title>
  
  <link rel="stylesheet" type="text/css" href="styles/natc_layout.css" media='screen' />
  
  <style type="text/css">
    @import "styles/natc_markup.css";
  </style>
</head>
<body>

<jsp:include page="menu.jsp" />

<h1>MANAGER CHANGES</h1>
<html:errors/>

<!--
<html:form method="POST" action="/Games">

<html:select property="operation">
  <html:option key = "games.option.next"   value="0" />

</html:select>

<html:submit><bean:message key="games.label.next"/></html:submit>

</html:form>
-->

<logic:present name="schedule">
  <h2><bean:write name="schedule" property="scheduled"/></h2>
</logic:present>

<logic:present name="manager_changes">
  <table class="freeagency">
  <logic:iterate id="team" name="manager_changes">
    <tr class="label">
      <td colspan="9">
        <logic:notEqual name="team" property="team_id" value="0">
        <html:link page="/Team.do" paramId="team_id" paramName="team" paramProperty="team_id">
          <bean:write name="team" property="location" /> <bean:write name="team" property="name" />
        </html:link>
        </logic:notEqual>
        <logic:equal name="team" property="team_id" value="0">
          <bean:message key="ofs.label.notable" />
        </logic:equal>
      </td>
    </tr>
    <tr class="heading">
      <td><bean:message key="ofs.label.status"  /></td>
      <td><bean:message key="ofs.label.manager" /></td>
      <td><bean:message key="ofs.label.from"    /></td>
      <td><bean:message key="ofs.label.ratings" /></td>
      <td><bean:message key="ofs.label.style"   /></td>
      <td><bean:message key="ofs.label.seasons" /></td>
      <td><bean:message key="ofs.label.win_pct" /></td>
      <td><bean:message key="ofs.label.moty"    /></td>
      <td><bean:message key="ofs.label.allstar" /></td>
    </tr>
    <logic:iterate id="managerView" name="team" property="players">
      <tr>
        <td>
          <logic:equal name="managerView" property="status" value="1">
            <bean:message key="ofs.action.retired" />
          </logic:equal>
          <logic:equal name="managerView" property="status" value="2">
            <bean:message key="ofs.action.fired" />
          </logic:equal>
          <logic:equal name="managerView" property="status" value="3">
            <bean:message key="ofs.action.hired" />
          </logic:equal>
        </td>
        <td>
          <html:link page="/Manager.do" paramId="manager_id" paramName="managerView" paramProperty="manager_id">
            <bean:write name="managerView" property="last_name"  />, <bean:write name="managerView" property="first_name" />
          </html:link>
        </td>
        <td>
          <logic:notEqual name="managerView" property="team_id" value="0">
            <html:link page="/Team.do" paramId="team_id" paramName="managerView" paramProperty="team_id">
              <bean:write name="managerView" property="team_abbrev"/>
            </html:link>
          </logic:notEqual>
        </td>
        <td>
          <img src='barchart.jsp?background=000000&foreground=ff0000&width=50&height=16&value1=<bean:write name="managerView" property="offense"/>&value2=<bean:write name="managerView" property="defense"/>&value3=<bean:write name="managerView" property="intangible"/>&value4=<bean:write name="managerView" property="penalty"/>'>
        </td>
        <td>
          <logic:equal name="managerView" property="style" value="1"><bean:message key="manager.label.offensive"  /></logic:equal>
          <logic:equal name="managerView" property="style" value="2"><bean:message key="manager.label.defensive"  /></logic:equal>
          <logic:equal name="managerView" property="style" value="3"><bean:message key="manager.label.intangible" /></logic:equal>
          <logic:equal name="managerView" property="style" value="4"><bean:message key="manager.label.penalty"    /></logic:equal>
          <logic:equal name="managerView" property="style" value="5"><bean:message key="manager.label.balanced"   /></logic:equal>
        </td>
        <td><bean:write name="managerView" property="seasons"       /></td>
        <td><bean:write name="managerView" property="winPctDsp"     /></td>
        <td><bean:write name="managerView" property="award_count"   /></td>
        <td><bean:write name="managerView" property="allstar_count" /></td>
      </tr>
    </logic:iterate>
    <tr class="separator"></tr>
  </logic:iterate>
  </table>
</logic:present>

</body>
</html:html>
