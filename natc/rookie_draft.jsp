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

<h1>ROOKIE DRAFT</h1>
<html:errors/>

<html:form method="POST" action="/Games">

<html:select property="operation">
  <html:option key = "games.option.next"   value="0" />

</html:select>

<html:submit><bean:message key="games.label.next"/></html:submit>

</html:form>

<logic:present name="draft_picks">
  <table class="draft">
    <tr class="heading">
      <td><bean:message key="draft.label.pick"/></td>
      <td><bean:message key="draft.label.team"/></td>
      <td><bean:message key="draft.label.player"/></td>
    </tr>
    <logic:iterate id="pick" name="draft_picks">
      <tr>
        <td><bean:write name="pick" property="draft_pick" /></td>
        <td>
          <html:link page="/Team.do" paramId="team_id" paramName="pick" paramProperty="team_id">
            <bean:write name="pick" property="location"   /> <bean:write name="pick" property="team_name"  />
          </html:link>
        </td>
        <td>
          <html:link page="/Player.do" paramId="player_id" paramName="pick" paramProperty="player_id">
            <bean:write name="pick" property="first_name" /> <bean:write name="pick" property="last_name"  />
          </html:link>
        </td>
      </tr>
    </logic:iterate>
  </table>
</logic:present>

</body>
</html:html>
