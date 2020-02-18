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

<table class="freeagency">
  <logic:present name="resigned_players">
    <tr class="label"><td colspan="3"><bean:message key="resigned.label.resigned"  /></td></tr>
    <tr class="heading">
      <td><bean:message key="resigned.label.player"   /></td>
      <td><bean:message key="resigned.label.released" /></td>
      <td><bean:message key="resigned.label.signed"   /></td>
    </tr>
    <logic:iterate id="player" name="resigned_players">
      <tr>
        <td>
          <html:link page="/Player.do" paramId="player_id" paramName="player" paramProperty="player_id">
            <bean:write name="player" property="last_name" />, <bean:write name="player" property="first_name" />
          </html:link>
        </td>
        <td>
          <html:link page="/Team.do" paramId="team_id" paramName="player" paramProperty="old_team_id">
            <bean:write name="player" property="old_team_abbrev" />
          </html:link>
        </td>
        <td>
          <html:link page="/Team.do" paramId="team_id" paramName="player" paramProperty="new_team_id">
            <bean:write name="player" property="new_team_abbrev" />
          </html:link>
        </td>
      </tr>
    </logic:iterate>
  </logic:present>
  <tr class="separator"></tr>
  <logic:present name="retired_players">
    <tr class="label"><td colspan="3"><bean:message key="retired.label.retired"  /></td></tr>
    <tr class="heading">
      <td><bean:message key="retired.label.player"  /></td>
      <td><bean:message key="retired.label.age"    /></td>
      <td><bean:message key="retired.label.seasons" /></td>
    </tr>
    <logic:iterate id="player" name="retired_players">
      <tr>
        <td>
          <html:link page="/Player.do" paramId="player_id" paramName="player" paramProperty="player_id">
            <bean:write name="player" property="last_name" />, <bean:write name="player" property="first_name" />
          </html:link>
        </td>
        <td><bean:write name="player" property="age"            /></td>
        <td><bean:write name="player" property="seasons_played" /></td>
      </tr>
    </logic:iterate>
  </logic:present>
</table>

</body>
</html:html>
