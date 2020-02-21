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

<h1>END OF ALLSTAR GAMES</h1>
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

<logic:present name="allstarTeams">
  <table class="allstar_standings">
    <tr>
      <td>
        <table class="standing">
          <tr class="heading">
            <td colspan="3"><bean:message key="games.label.allstars"/></td>
          </tr>
          <logic:iterate id="team" name="allstarTeams">
            <tr>
              <td>
                <html:link page="/Team.do" paramId="team_id" paramName="team" paramProperty="team_id">
                  <bean:write name="team" property="location" />
                </html:link>
              </td>
              <td><bean:write name="team" property="wins"     /></td>
              <td><bean:write name="team" property="losses"   /></td>
            </tr>
          </logic:iterate>
        </table>
      </td>
    </tr>
  </table>
</logic:present>

<logic:iterate id="team" name="allstarTeams">
  <logic:equal name="team" property="wins" value="2">
    <h1><bean:write name="schedule" property="year"/> <bean:message key="games.label.asgchamps"/> <bean:write name="team" property="location"/> <bean:write name="team" property="name"/></h1>
  </logic:equal>
</logic:iterate>

</body>
</html:html>
