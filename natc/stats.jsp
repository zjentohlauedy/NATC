<%@ page contentType="text/html; charset=utf-8" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld"   prefix="bean"   %>
<%@ taglib uri="/WEB-INF/struts-html.tld"   prefix="html"   %>
<%@ taglib uri="/WEB-INF/struts-logic.tld"  prefix="logic"  %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>

<html:html locale="true">
<head>
  <html:base/>
  <title><bean:message key="title.stats"/></title>
  
  <link rel="stylesheet" type="text/css" href="styles/natc_layout.css" media='screen' />
  
  <style type="text/css">
    @import "styles/natc_markup.css";
  </style>
</head>
<body>

<jsp:include page="menu.jsp" />

<h1>STATS</h1>
<html:errors/>

<html:form method="POST" action="/Stats">

<html:select property="operation">
  <html:option key = "stats.option.playthisseason"    value="0"  />
  <html:option key = "stats.option.teamoffthisseason" value="1"  />
  <html:option key = "stats.option.teamdefthisseason" value="2"  />
  <html:option key = "stats.option.playbygame"        value="3"  />
  <html:option key = "stats.option.playbyseason"      value="4"  />
  <html:option key = "stats.option.playbycareer"      value="5"  />
  <html:option key = "stats.option.teambygame"        value="6"  />
  <html:option key = "stats.option.teamoffbyseason"   value="7"  />
  <html:option key = "stats.option.teamdefbyseason"   value="8"  />
  <html:option key = "stats.option.teamoffbyhistory"  value="9"  />
  <html:option key = "stats.option.teamdefbyhistory"  value="10" />
</html:select>
<html:submit><bean:message key="stats.label.submit"/></html:submit>
</html:form>

<logic:present name="stats">
  <logic:iterate id="stat" name="stats">
    <table class="stats">
      <logic:iterate id="statsView" name="stat">
        <logic:equal name="statsView" property="type" value="0"  >
          <tr class="heading">
            <td><bean:message name="statsView" property="headingKey1" /></td>
            <td><bean:message name="statsView" property="headingKey2" /></td>
            <td><bean:message name="statsView" property="headingKey3" /></td>
            <td><bean:message name="statsView" property="headingKey4" /></td>
          </tr>
        </logic:equal>
        <logic:equal name="statsView" property="type" value="1"  >
          <tr>
            <td>
              <html:link page="/Game.do" paramId="game_id" paramName="statsView" paramProperty="game_id">
                <bean:write name="statsView" property="stat"/>
              </html:link>
            </td>
            <td>
              <html:link page="/Team.do" paramId="team_id" paramName="statsView" paramProperty="team_id">
                <bean:write name="statsView" property="team_abbrev" />
              </html:link>
            </td>
            <td><bean:write name="statsView" property="year"/></td>
            <td>
              <logic:equal name="statsView" property="road" value="true"  >at</logic:equal>
              <logic:equal name="statsView" property="road" value="false" >vs</logic:equal>
              <html:link page="/Team.do" paramId="team_id" paramName="statsView" paramProperty="opponent_id">
                <bean:write name="statsView" property="opponent_abbrev" />
              </html:link>
            </td>
          </tr>
        </logic:equal>
        <logic:equal name="statsView" property="type" value="2"  >
          <tr>
            <td>
              <bean:write name="statsView" property="stat"/>
            </td>
            <td>
              <html:link page="/Team.do" paramId="team_id" paramName="statsView" paramProperty="team_id">
                <bean:write name="statsView" property="team_abbrev" />
              </html:link>
            </td>
            <td><bean:write name="statsView" property="year"/></td>
            <td>
            </td>
          </tr>
        </logic:equal>
        <logic:equal name="statsView" property="type" value="3"  >
          <tr>
            <td>
              <bean:write name="statsView" property="stat"/>
            </td>
            <td>
              <html:link page="/Team.do" paramId="team_id" paramName="statsView" paramProperty="team_id">
                <bean:write name="statsView" property="team_abbrev" />
              </html:link>
            </td>
            <td>
            </td>
            <td>
            </td>
          </tr>
        </logic:equal>
        <logic:equal name="statsView" property="type" value="4"  >
          <tr>
            <td>
              <html:link page="/Game.do" paramId="game_id" paramName="statsView" paramProperty="game_id">
                <bean:write name="statsView" property="stat"/>
              </html:link>
            </td>
            <td>
              <html:link page="/Player.do" paramId="player_id" paramName="statsView" paramProperty="player_id">
                <bean:write name="statsView" property="first_name" /> <bean:write name="statsView" property="last_name" />
              </html:link>
            </td>
            <td><bean:write name="statsView" property="year"/></td>
            <td>
            </td>
          </tr>
        </logic:equal>
        <logic:equal name="statsView" property="type" value="5"  >
          <tr>
            <td>
              <bean:write name="statsView" property="stat"/>
            </td>
            <td>
              <html:link page="/Player.do" paramId="player_id" paramName="statsView" paramProperty="player_id">
                <bean:write name="statsView" property="first_name" /> <bean:write name="statsView" property="last_name" />
              </html:link>
            </td>
            <td><bean:write name="statsView" property="year"/></td>
            <td>
            </td>
          </tr>
        </logic:equal>
        <logic:equal name="statsView" property="type" value="6"  >
          <tr>
            <td>
              <bean:write name="statsView" property="stat"/>
            </td>
            <td>
              <html:link page="/Player.do" paramId="player_id" paramName="statsView" paramProperty="player_id">
                <bean:write name="statsView" property="first_name" /> <bean:write name="statsView" property="last_name" />
              </html:link>
            </td>
            <td>
              <bean:write name="statsView" property="first_year" />-<bean:write name="statsView" property="last_year" />
            </td>
            <td>
              <bean:write name="statsView" property="seasons_played"/>
            </td>
          </tr>
        </logic:equal>
      </logic:iterate>
    </table>
  </logic:iterate>
</logic:present>

</body>
</html:html>
