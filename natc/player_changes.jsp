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

<h1>PLAYER CHANGES</h1>
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

<logic:present name="player_changes">
  <table class="freeagency">
  <logic:iterate id="team" name="player_changes">
    <tr class="label">
      <td colspan="15">
        <logic:notEqual name="team" property="team_id" value="0">
        <html:link page="/Team.do" paramId="team_id" paramName="team" paramProperty="team_id">
          <bean:write name="team" property="location" /> <bean:write name="team" property="name" />
        </html:link>
        </logic:notEqual>
        <logic:equal name="team" property="team_id" value="0">
          <bean:message key="retired.label.notable" />
        </logic:equal>
      </td>
    </tr>
    <tr class="heading">
      <td><bean:message key="retired.label.status"   /></td>
      <td><bean:message key="retired.label.player"   /></td>
      <td><bean:message key="retired.label.from"     /></td>
      <td><bean:message key="retired.label.age"      /></td>
      <td><bean:message key="retired.label.seasons"  /></td>
      <td><bean:message key="retired.label.points"   /></td>
      <td><bean:message key="retired.label.goals"    /></td>
      <td><bean:message key="retired.label.assists"  /></td>
      <td><bean:message key="retired.label.stops"    /></td>
      <td><bean:message key="retired.label.steals"   /></td>
      <td><bean:message key="retired.label.psm"      /></td>
      <td><bean:message key="retired.label.platinum" /></td>
      <td><bean:message key="retired.label.gold"     /></td>
      <td><bean:message key="retired.label.silver"   /></td>
      <td><bean:message key="retired.label.allstar"  /></td>
    </tr>
    <logic:iterate id="playerView" name="team" property="players">
      <tr>
        <td>
          <logic:equal name="playerView" property="status" value="1">
            <bean:message key="retired.action.retired" />
          </logic:equal>
          <logic:equal name="playerView" property="status" value="2">
            <bean:message key="retired.action.released" />
          </logic:equal>
          <logic:equal name="playerView" property="status" value="3">
            <bean:message key="retired.action.signed" />
          </logic:equal>
        </td>
        <td>
          <html:link page="/Player.do" paramId="player_id" paramName="playerView" paramProperty="player_id">
            <bean:write name="playerView" property="last_name" />, <bean:write name="playerView" property="first_name" />
          </html:link>
        </td>
        <td>
          <logic:notEqual name="playerView" property="former_team_id" value="0">
            <html:link page="/Team.do" paramId="team_id" paramName="playerView" paramProperty="former_team_id">
              <bean:write name="playerView" property="former_team_abbrev"/>
            </html:link>
          </logic:notEqual>
        </td>
        <td><bean:write name="playerView" property="age"            /></td>
        <td><bean:write name="playerView" property="seasons_played" /></td>
        <td><bean:write name="playerView" property="pointsDsp"      /></td>
        <td><bean:write name="playerView" property="goalsDsp"       /></td>
        <td><bean:write name="playerView" property="assistsDsp"     /></td>
        <td><bean:write name="playerView" property="stopsDsp"       /></td>
        <td><bean:write name="playerView" property="stealsDsp"      /></td>
        <td><bean:write name="playerView" property="psmDsp"         /></td>
        <td><bean:write name="playerView" property="platinum_count" /></td>
        <td><bean:write name="playerView" property="gold_count"     /></td>
        <td><bean:write name="playerView" property="silver_count"   /></td>
        <td><bean:write name="playerView" property="allstar_count"  /></td>
      </tr>
    </logic:iterate>
    <tr class="separator"></tr>
  </logic:iterate>
  </table>
</logic:present>

</body>
</html:html>
