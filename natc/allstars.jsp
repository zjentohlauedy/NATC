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

<h1>ALL STARS</h1>
<html:errors/>

<html:form method="POST" action="/Games">

<html:select property="operation">
  <html:option key = "games.option.next"   value="0" />

</html:select>

<html:submit><bean:message key="games.label.next"/></html:submit>

</html:form>

<table class="allstar_teams">
  <tr>
    <td>
      <logic:present name="div0stars">
        <table class="allstars">
          <tr class="label">
	        <td colspan="3"><bean:message key="division.greene"/></td>
	      </tr>
          <tr class="heading">
            <td><bean:message key="awards.label.player" /></td>
            <td><bean:message key="awards.label.team"   /></td>
          </tr>
          <logic:iterate id="star" name="div0stars">
            <tr>
              <td>
                <html:link page="/Player.do" paramId="player_id" paramName="star" paramProperty="player_id">
                  <bean:write name="star" property="last_name" />, <bean:write name="star" property="first_name" />
                </html:link>
                <logic:equal name="star" property="rookie" value="true">
                  <h7><bean:message key="team.label.rookie"/></h7>
                </logic:equal>
              </td>
              <td>
                <html:link page="/Team.do" paramId="team_id" paramName="star" paramProperty="team_id">
                  <bean:write name="star" property="team_abbrev" />
                </html:link>
              </td>
              <td>
              </td>
            </tr>
          </logic:iterate>
        </table>
      </logic:present>
    </td>
    <td>
      <logic:present name="div1stars">
        <table class="allstars">
          <tr class="label">
	        <td colspan="3"><bean:message key="division.davis"/></td>
	      </tr>
          <tr class="heading">
            <td><bean:message key="awards.label.player" /></td>
            <td><bean:message key="awards.label.team"   /></td>
          </tr>
          <logic:iterate id="star" name="div1stars">
            <tr>
              <td>
                <html:link page="/Player.do" paramId="player_id" paramName="star" paramProperty="player_id">
                  <bean:write name="star" property="last_name" />, <bean:write name="star" property="first_name" />
                </html:link>
                <logic:equal name="star" property="rookie" value="true">
                  <h7><bean:message key="team.label.rookie"/></h7>
                </logic:equal>
              </td>
              <td>
                <html:link page="/Team.do" paramId="team_id" paramName="star" paramProperty="team_id">
                  <bean:write name="star" property="team_abbrev" />
                </html:link>
              </td>
              <td>
              </td>
              <td>
              </td>
            </tr>
          </logic:iterate>
        </table>
      </logic:present>
    </td>
  </tr>
  <tr class="separator"></tr>
  <tr>
    <td>
      <logic:present name="div2stars">
        <table class="allstars">
          <tr class="label">
	        <td colspan="3"><bean:message key="division.smith"/></td>
	      </tr>
          <tr class="heading">
            <td><bean:message key="awards.label.player" /></td>
            <td><bean:message key="awards.label.team"   /></td>
          </tr>
          <logic:iterate id="star" name="div2stars">
            <tr>
              <td>
                <html:link page="/Player.do" paramId="player_id" paramName="star" paramProperty="player_id">
                  <bean:write name="star" property="last_name" />, <bean:write name="star" property="first_name" />
                </html:link>
                <logic:equal name="star" property="rookie" value="true">
                  <h7><bean:message key="team.label.rookie"/></h7>
                </logic:equal>
              </td>
              <td>
                <html:link page="/Team.do" paramId="team_id" paramName="star" paramProperty="team_id">
                  <bean:write name="star" property="team_abbrev" />
                </html:link>
              </td>
              <td>
              </td>
              <td>
              </td>
            </tr>
          </logic:iterate>
        </table>
      </logic:present>
    </td>
    <td>
      <logic:present name="div3stars">
        <table class="allstars">
          <tr class="label">
	        <td colspan="3"><bean:message key="division.lawrence"/></td>
	      </tr>
          <tr class="heading">
            <td><bean:message key="awards.label.player" /></td>
            <td><bean:message key="awards.label.team"   /></td>
          </tr>
          <logic:iterate id="star" name="div3stars">
            <tr>
              <td>
                <html:link page="/Player.do" paramId="player_id" paramName="star" paramProperty="player_id">
                  <bean:write name="star" property="last_name" />, <bean:write name="star" property="first_name" />
                </html:link>
                <logic:equal name="star" property="rookie" value="true">
                  <h7><bean:message key="team.label.rookie"/></h7>
                </logic:equal>
              </td>
              <td>
                <html:link page="/Team.do" paramId="team_id" paramName="star" paramProperty="team_id">
                  <bean:write name="star" property="team_abbrev" />
                </html:link>
              </td>
              <td>
              </td>
              <td>
              </td>
            </tr>
          </logic:iterate>
        </table>
      </logic:present>
    </td>
  </tr>
</table>

</body>
</html:html>
