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

<h1>END OF POSTSEASON</h1>
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

<logic:present name="championship">
  <table class="game_long">
    <tr>
      <td>
        <html:link page="/Team.do" paramId="team_id" paramName="championship" paramProperty="road_team_id">
          <bean:write name="championship" property="road_team"/>
        </html:link>
      </td>
      <td>
        <html:link page="/Game.do" paramId="game_id" paramName="championship" paramProperty="game_id">
          <logic:equal name="championship" property="road_win" value="true">
            <em><bean:write name="championship" property="road_score"/></em>
          </logic:equal>
          <logic:equal name="championship" property="road_win" value="false">
            <bean:write name="championship" property="road_score"/>
          </logic:equal>
        </html:link>
      </td>
      <td></td>
    </tr>
    <tr>
      <td>
        <html:link page="/Team.do" paramId="team_id" paramName="championship" paramProperty="home_team_id">
          <bean:write name="championship" property="home_team"/>
        </html:link>
      </td>
      <td>
        <html:link page="/Game.do" paramId="game_id" paramName="championship" paramProperty="game_id">
          <logic:equal name="championship" property="home_win" value="true">
            <em><bean:write name="championship" property="home_score"/></em>
          </logic:equal>
          <logic:equal name="championship" property="home_win" value="false">
            <bean:write name="championship" property="home_score"/>
          </logic:equal>
        </html:link>
        <logic:equal name="championship" property="overtime" value="true">
          <h7><bean:message key="games.label.ot_indicator"/></h7>
        </logic:equal>
      </td>
      <td></td>
    </tr>
  </table>
</logic:present>

<logic:present name="champion">
  <logic:present name="schedule">
    <h1><bean:write name="schedule" property="year"/> <bean:message key="games.label.champs"/> <bean:write name="champion" property="location"/> <bean:write name="champion" property="name"/></h1>
  </logic:present>
</logic:present>

</body>
</html:html>
