<%@ page contentType="text/html; charset=utf-8" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld"   prefix="bean"   %>
<%@ taglib uri="/WEB-INF/struts-html.tld"   prefix="html"   %>
<%@ taglib uri="/WEB-INF/struts-logic.tld"  prefix="logic"  %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>

<%@ page import="natc.data.TeamGame" %>

<html:html locale="true">
<head>
  <html:base/>
  <title><bean:message key="title.team"/></title>
  
  <link rel="stylesheet" type="text/css" href="styles/games_layout.css" media='screen' />
  
  <style type="text/css">
    @import "styles/games_markup.css";
  </style>
</head>
<body>

<ul class="menu">
  <li><a href="/natc/Main.do?page=games"   ><bean:message key="title.games"   /></a></li>
  <li><a href="/natc/Main.do?page=news"    ><bean:message key="title.news"    /></a></li>
  <li><a href="/natc/Players.do"           ><bean:message key="title.players" /></a></li>
  <li><a href="/natc/Main.do?page=stats"   ><bean:message key="title.stats"   /></a></li>
  <li><a href="/natc/Teams.do"             ><bean:message key="title.teams"   /></a></li>
</ul>

<h1>DRAFT</h1>

<table>
  <tr>
    <td>
      <logic:present name="teams">
        <table>
          <logic:iterate id="team" name="teams">
            <tr>
              <td><bean:write name="team" property="location"     /></td>
              <td><bean:write name="team" property="team_id"      /></td>
              <td><bean:write name="team" property="wins"         /></td>
              <td><bean:write name="team" property="playoff_rank" /></td>
            </tr>
          </logic:iterate>
        </table>
      </logic:present>
    </td>
    <td>
      <logic:present name="players">
        <table>
          <logic:iterate id="player" name="players">
            <tr>
              <td><bean:write name="player" property="last_name" />, <bean:write name="player" property="first_name" /></td>
              <td><bean:write name="player" property="rating"    /></td>
              <td><bean:write name="player" property="team_id"   /></td>
            </tr>
          </logic:iterate>
        </table>
      </logic:present>
    </td>
  </tr>
</table>

</body>
</html:html>
