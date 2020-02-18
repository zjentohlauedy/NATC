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

<table class="menuwrap">
  <tr>
    <td>
      <ul class="menu">
        <li><a href="/natc/Main.do?page=games"   ><bean:message key="title.games"   /></a></li>
        <li><a href="/natc/Main.do?page=news"    ><bean:message key="title.news"    /></a></li>
        <li><a href="/natc/Players.do"           ><bean:message key="title.players" /></a></li>
        <li><a href="/natc/Main.do?page=stats"   ><bean:message key="title.stats"   /></a></li>
        <li><a href="/natc/Teams.do"             ><bean:message key="title.teams"   /></a></li>
      </ul>
    </td>
  </tr>
</table>

<h1>FREE AGENTS</h1>
<html:errors/>
<html:form method="POST" action="/Games">

<html:select property="operation">
  <html:option key = "games.option.next"   value="0" />

</html:select>

<html:submit><bean:message key="games.label.next"/></html:submit>

</html:form>

<logic:present name="schedule">
  <h2><bean:write name="schedule" property="scheduled"/></h2>
</logic:present>


<logic:present name="free_agency">
  <table class="freeagency">
  <logic:iterate id="team" name="free_agency">
    <tr class="label">
      <td colspan="5">
        <html:link page="/Team.do" paramId="team_id" paramName="team" paramProperty="team_id">
          <bean:write name="team" property="location" /> <bean:write name="team" property="name" />
        </html:link>
      </td>
    </tr>
    <tr class="heading">
      <td><bean:message key="freeagent.label.status"  /></td>
      <td><bean:message key="freeagent.label.player"  /></td>
      <td><bean:message key="freeagent.label.from"    /></td>
      <td><bean:message key="freeagent.label.rating"  /></td>
      <td><bean:message key="freeagent.label.age"     /></td>
      <td><bean:message key="freeagent.label.seasons" /></td>
    </tr>
    <logic:iterate id="freeAgentView" name="team" property="players">
      <tr>
        <td>
          <logic:equal name="freeAgentView" property="status" value="0">
            <bean:message key="freeagent.action.released" />
          </logic:equal>
          <logic:equal name="freeAgentView" property="status" value="1">
            <bean:message key="freeagent.action.signed" />
          </logic:equal>
        </td>
        <td>
          <html:link page="/Player.do" paramId="player_id" paramName="freeAgentView" paramProperty="player_id">
            <bean:write name="freeAgentView" property="last_name" />, <bean:write name="freeAgentView" property="first_name" />
          </html:link>
        </td>
        <td>
          <logic:equal name="freeAgentView" property="status" value="1">
            <html:link page="/Team.do" paramId="team_id" paramName="freeAgentView" paramProperty="old_team_id">
              <bean:write name="freeAgentView" property="old_team_abbrev" />
            </html:link>
          </logic:equal>
        </td>
        <td>
          <img id='<bean:write name="freeAgentView" property="adjustedRating"/>' src='drawbar2.jsp?background=000000&color1=ff0000&color2=00ff00&width=150&height=16&value1=<bean:write name="freeAgentView" property="adjustedRating"/>&value2=<bean:write name="freeAgentView" property="rating"/>'>
        </td>
        <td><bean:write name="freeAgentView" property="age"            /></td>
        <td><bean:write name="freeAgentView" property="seasons_played" /></td>
      </tr>
    </logic:iterate>
    <tr class="separator"></tr>
  </logic:iterate>
  </table>
</logic:present>

</body>
</html:html>
