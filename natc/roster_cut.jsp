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

<h1>ROSTER CUT</h1>
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


<logic:present name="released_players">
  <table class="releases">
  <logic:iterate id="team" name="released_players">
    <tr class="label">
      <td colspan="4">
        <html:link page="/Team.do" paramId="team_id" paramName="team" paramProperty="team_id">
          <bean:write name="team" property="location" /> <bean:write name="team" property="name" />
        </html:link>
      </td>
    </tr>
    <tr class="heading">
      <td><bean:message key="releases.label.player"  /></td>
      <td><bean:message key="releases.label.age"     /></td>
      <td><bean:message key="releases.label.seasons" /></td>
      <td><bean:message key="releases.label.pick"    /></td>
    </tr>
    <logic:iterate id="releaseView" name="team" property="players">
      <tr>
        <td>
          <html:link page="/Player.do" paramId="player_id" paramName="releaseView" paramProperty="player_id">
            <bean:write name="releaseView" property="last_name" />, <bean:write name="releaseView" property="first_name" />
          </html:link>
          <logic:equal name="releaseView" property="rookie" value="true">
            <h7><bean:message key="releases.label.rookie" /></h7>
          </logic:equal>
        </td>
        <td><bean:write name="releaseView" property="age"            /></td>
        <td><bean:write name="releaseView" property="seasons_played" /></td>
        <td><bean:write name="releaseView" property="draft_pick"     /></td>
      </tr>
    </logic:iterate>
    <tr class="separator"></tr>
  </logic:iterate>
  </table>
</logic:present>

</body>
</html:html>
