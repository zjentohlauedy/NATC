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

<h1>AWARDS</h1>
<html:errors/>
<html:form method="POST" action="/Games">

<html:select property="operation">
  <html:option key = "games.option.next"   value="0" />

</html:select>

<html:submit><bean:message key="games.label.next"/></html:submit>

</html:form>

<logic:present name="awards">
  <table class="awards">
    <tr class="heading">
      <td><bean:message key="awards.label.award"      /></td>
      <td><bean:message key="awards.label.player"     /></td>
      <td><bean:message key="awards.label.team"       /></td>
      <td><bean:message key="awards.label.conference" /></td>
      <td><bean:message key="awards.label.division"   /></td>
    </tr>
    <logic:iterate id="awardView" name="awards">
      <tr>
        <td>
          <logic:equal name="awardView" property="award" value="3">
            <bean:message key="awards.label.platinum" />
          </logic:equal>
          <logic:equal name="awardView" property="award" value="2">
            <bean:message key="awards.label.gold" />
          </logic:equal>
          <logic:equal name="awardView" property="award" value="1">
            <bean:message key="awards.label.silver" />
          </logic:equal>
        </td>
        <td>
          <html:link page="/Player.do" paramId="player_id" paramName="awardView" paramProperty="player_id">
            <bean:write name="awardView" property="last_name"/>, <bean:write name="awardView" property="first_name"/>
          </html:link>
          <logic:equal name="awardView" property="rookie" value="true">
            <h7><bean:message key="awards.label.rookie" /></h7>
          </logic:equal>
        </td>
        <td>
          <html:link page="/Team.do" paramId="team_id" paramName="awardView" paramProperty="team_id">
            <bean:write name="awardView" property="team_abbrev" />
          </html:link>
        </td>
        <td>
          <logic:equal name="awardView" property="award" value="2">
            <logic:equal name="awardView" property="conference" value="0">
              <bean:message key="conference.fernandez" />
            </logic:equal>
            <logic:equal name="awardView" property="conference" value="1">
              <bean:message key="conference.williamson" />
            </logic:equal>
          </logic:equal>
        </td>
        <td>
          <logic:equal name="awardView" property="award" value="1">
            <logic:equal name="awardView" property="division" value="0">
              <bean:message key="division.greene" />
            </logic:equal>
            <logic:equal name="awardView" property="division" value="1">
              <bean:message key="division.davis" />
            </logic:equal>
            <logic:equal name="awardView" property="division" value="2">
              <bean:message key="division.smith" />
            </logic:equal>
            <logic:equal name="awardView" property="division" value="3">
              <bean:message key="division.lawrence" />
            </logic:equal>
          </logic:equal>
        </td>
      </tr>
    </logic:iterate>
  </table>
</logic:present>

<logic:present name="manager">
  <table class="awards">
    <tr class="heading">
      <td><bean:message key="awards.label.moty"       /></td>
      <td><bean:message key="awards.label.team"       /></td>
    </tr>
    <tr>
      <td>
        <html:link page="/Manager.do" paramId="manager_id" paramName="manager" paramProperty="manager_id">
          <bean:write name="manager" property="last_name"/>, <bean:write name="manager" property="first_name"/>
        </html:link>
      </td>
      <td>
        <html:link page="/Team.do" paramId="team_id" paramName="manager" paramProperty="team_id">
          <bean:write name="manager" property="team_abbrev" />
        </html:link>
      </td>
    </tr>
  </table>
</logic:present>

</body>
</html:html>
