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

<h1>TRAINING CAMP</h1>
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

<table class="training">
  <logic:present name="standout_players">
    <tr class="label"><td colspan="3"><bean:message key="training.label.standouts"  /></td></tr>
    <tr class="heading">
      <td><bean:message key="training.label.player"  /></td>
      <td><bean:message key="training.label.team"    /></td>
      <td><bean:message key="training.label.seasons" /></td>
    </tr>
    <logic:iterate id="player" name="standout_players">
      <tr>
        <td>
          <html:link page="/Player.do" paramId="player_id" paramName="player" paramProperty="player_id">
            <bean:write name="player" property="last_name" />, <bean:write name="player" property="first_name" />
          </html:link>
        </td>
        <td>
          <html:link page="/Team.do" paramId="team_id" paramName="player" paramProperty="team_id">
            <bean:write name="player" property="team_abbrev" />
          </html:link>
        </td>
        <td><bean:write name="player" property="seasons_played" /></td>
      </tr>
    </logic:iterate>
  </logic:present>
  <tr class="separator"></tr>
  <logic:present name="standout_rookies">
    <tr class="label"><td colspan="3"><bean:message key="training.label.rookies"  /></td></tr>
    <tr class="heading">
      <td><bean:message key="training.label.player"  /></td>
      <td><bean:message key="training.label.team"    /></td>
      <td><bean:message key="training.label.pick" /></td>
    </tr>
    <logic:iterate id="player" name="standout_rookies">
      <tr>
        <td>
          <html:link page="/Player.do" paramId="player_id" paramName="player" paramProperty="player_id">
            <bean:write name="player" property="last_name" />, <bean:write name="player" property="first_name" />
          </html:link>
        </td>
        <td>
          <html:link page="/Team.do" paramId="team_id" paramName="player" paramProperty="team_id">
            <bean:write name="player" property="team_abbrev" />
          </html:link>
        </td>
        <td><bean:write name="player" property="draft_pick" /></td>
      </tr>
    </logic:iterate>
  </logic:present>
  <tr class="separator"></tr>
  <logic:present name="most_improved">
    <tr class="label"><td colspan="3"><bean:message key="training.label.improved"  /></td></tr>
    <tr class="heading">
      <td><bean:message key="training.label.player"  /></td>
      <td><bean:message key="training.label.team"    /></td>
      <td><bean:message key="training.label.seasons" /></td>
    </tr>
    <logic:iterate id="player" name="most_improved">
      <tr>
        <td>
          <html:link page="/Player.do" paramId="player_id" paramName="player" paramProperty="player_id">
            <bean:write name="player" property="last_name" />, <bean:write name="player" property="first_name" />
          </html:link>
        </td>
        <td>
          <html:link page="/Team.do" paramId="team_id" paramName="player" paramProperty="team_id">
            <bean:write name="player" property="team_abbrev" />
          </html:link>
        </td>
        <td><bean:write name="player" property="seasons_played" /></td>
      </tr>
    </logic:iterate>
  </logic:present>
</table>

</body>
</html:html>
