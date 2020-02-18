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

<h1>RETIRED PLAYERS</h1>
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

<logic:present name="retired_team_players">
  <table class="freeagency">
  <logic:iterate id="team" name="retired_team_players">
    <tr class="label">
      <td colspan="9">
        <html:link page="/Team.do" paramId="team_id" paramName="team" paramProperty="team_id">
          <bean:write name="team" property="location" /> <bean:write name="team" property="name" />
        </html:link>
      </td>
    </tr>
    <tr class="heading">
      <td><bean:message key="retired.label.player"  /></td>
      <td><bean:message key="retired.label.age"     /></td>
      <td><bean:message key="retired.label.seasons" /></td>
      <td><bean:message key="retired.label.goals"   /></td>
      <td><bean:message key="retired.label.assists" /></td>
      <td><bean:message key="retired.label.stops"   /></td>
      <td><bean:message key="retired.label.steals"  /></td>
      <td><bean:message key="retired.label.psm"     /></td>
      <td><bean:message key="retired.label.points"  /></td>
    </tr>
    <logic:iterate id="player" name="team" property="players">
      <tr>
        <td>
          <html:link page="/Player.do" paramId="player_id" paramName="player" paramProperty="player_id">
            <bean:write name="player" property="last_name" />, <bean:write name="player" property="first_name" />
          </html:link>
        </td>
        <td><bean:write name="player" property="age"            /></td>
        <td><bean:write name="player" property="seasons_played" /></td>
        <td><bean:write name="player" property="goals"          /></td>
        <td><bean:write name="player" property="assists"        /></td>
        <td><bean:write name="player" property="stops"          /></td>
        <td><bean:write name="player" property="steals"         /></td>
        <td><bean:write name="player" property="psm"            /></td>
        <td><bean:write name="player" property="points"         /></td>
      </tr>
    </logic:iterate>
    <tr class="separator"></tr>
  </logic:iterate>
  <logic:present name="retired_players">
    <tr class="label">
      <td colspan="9">
        <bean:message key="retired.label.notable" />
      </td>
    </tr>
    <tr class="heading">
      <td><bean:message key="retired.label.player"  /></td>
      <td><bean:message key="retired.label.age"     /></td>
      <td><bean:message key="retired.label.seasons" /></td>
      <td><bean:message key="retired.label.goals"   /></td>
      <td><bean:message key="retired.label.assists" /></td>
      <td><bean:message key="retired.label.stops"   /></td>
      <td><bean:message key="retired.label.steals"  /></td>
      <td><bean:message key="retired.label.psm"     /></td>
      <td><bean:message key="retired.label.points"  /></td>
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
        <td><bean:write name="player" property="goals"          /></td>
        <td><bean:write name="player" property="assists"        /></td>
        <td><bean:write name="player" property="stops"          /></td>
        <td><bean:write name="player" property="steals"         /></td>
        <td><bean:write name="player" property="psm"            /></td>
        <td><bean:write name="player" property="points"         /></td>
      </tr>
    </logic:iterate>
  </logic:present>
  </table>
</logic:present>

</body>
</html:html>
