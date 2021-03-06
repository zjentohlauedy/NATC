<%@ page contentType="text/html; charset=utf-8" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld"   prefix="bean"   %>
<%@ taglib uri="/WEB-INF/struts-html.tld"   prefix="html"   %>
<%@ taglib uri="/WEB-INF/struts-logic.tld"  prefix="logic"  %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>

<html:html locale="true">
<head>
  <html:base/>
  <title><bean:message key="title.champs"/></title>
  
  <link rel="stylesheet" type="text/css" href="styles/natc_layout.css" media='screen' />
  
  <style type="text/css">
    @import "styles/natc_markup.css";
  </style>
</head>
<body>

<jsp:include page="menu.jsp" />

<h1>NATC CHAMPIONS</h1>

<logic:present name="championships">
  <table class="champs">
    <logic:iterate id="gameView" name="championships">
      <tr class="label">
        <td colspan="5">
          <bean:write name="gameView" property="yearDsp" />
          <logic:equal name="gameView" property="road_win" value="true">
            <bean:write name="gameView" property="road_team" /> <bean:write name="gameView" property="road_team_name" />
          </logic:equal>
          <logic:equal name="gameView" property="home_win" value="true">
            <bean:write name="gameView" property="home_team" /> <bean:write name="gameView" property="home_team_name" />
          </logic:equal>
        </td>
      </tr>
      <tr class="heading">
        <td id="name" ><bean:message key="games.label.road"  /></td>
        <td id="score"><bean:message key="games.label.score" /></td>
        <td id="name" ><bean:message key="games.label.home"  /></td>
        <td id="score"><bean:message key="games.label.score" /></td>
        <td></td>
      </tr>
      <tr>
        <td><bean:write name="gameView" property="road_team"  /></td>
        <td><bean:write name="gameView" property="road_score" /></td>
        <td><bean:write name="gameView" property="home_team"  /></td>
        <td><bean:write name="gameView" property="home_score" /></td>
        <td>
          <logic:equal name="gameView" property="overtime" value="true">
            <h7><bean:message key="games.label.ot_indicator"/></h7>
          </logic:equal>
        </td>
      </tr>
    </logic:iterate>
  </table>
</logic:present>

</body>
</html:html>
