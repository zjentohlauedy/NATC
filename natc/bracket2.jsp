<%@ page contentType="text/html; charset=utf-8" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld"   prefix="bean"   %>
<%@ taglib uri="/WEB-INF/struts-html.tld"   prefix="html"   %>
<%@ taglib uri="/WEB-INF/struts-logic.tld"  prefix="logic"  %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>

<html:html locale="true">
<head>
  <html:base/>
  <title><bean:message key="title.news"/></title>
  
  <link rel="stylesheet" type="text/css" href="styles/natc_layout.css" media='screen' />
  <link rel="stylesheet" type="text/css" href="styles/natc_playoff.css" media='screen' />
  
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
        <li><a href="/natc/Champs.do"            ><bean:message key="title.champs"  /></a></li>
      </ul>
    </td>
  </tr>
</table>

<h1>NATC PLAYOFFS</h1>

<logic:present name="round1">
  <table border="1" class="bracket"><tr><td></td></tr></table>

  <logic:iterate id="team" name="round1">
    <table class="playoff_node" id='r1d<bean:write name="team" property="division" />t<bean:write name="team" property="division_rank" />'>
      <tr>
        <logic:equal name="team" property="conference" value="0">
          <td id="rank" align="right" valign="middle" height="24">
            <bean:write name="team" property="division_rank" />
          </td>
          <td align="left">
            <html:link page="/Team.do" paramId="team_id" paramName="team" paramProperty="team_id">
              <bean:write name="team" property="abbrev" />
            </html:link>
          </td>
          <td><bean:write name="team" property="round1_wins" /></td>
        </logic:equal>
        <logic:equal name="team" property="conference" value="1">
          <td><bean:write name="team" property="round1_wins" /></td>
          <td align="right">
            <html:link page="/Team.do" paramId="team_id" paramName="team" paramProperty="team_id">
              <bean:write name="team" property="abbrev" />
            </html:link>
          </td>
          <td id="rank" align="left" valign="middle" height="24">
            <bean:write name="team" property="division_rank" />
          </td>
        </logic:equal>
      </tr>
    </table>
  </logic:iterate>

  <logic:iterate id="team" name="round2">
    <table class="playoff_node" id='r2d<bean:write name="team" property="division" />t<bean:write name="team" property="division_rank" />'>
      <tr>
        <logic:equal name="team" property="conference" value="0">
          <td id="rank" align="right" valign="middle" height="24">
            <bean:write name="team" property="division_rank" />
          </td>
          <td align="left">
            <html:link page="/Team.do" paramId="team_id" paramName="team" paramProperty="team_id">
              <bean:write name="team" property="abbrev" />
            </html:link>
          </td>
          <td><bean:write name="team" property="round2_wins" /></td>
        </logic:equal>
        <logic:equal name="team" property="conference" value="1">
          <td><bean:write name="team" property="round2_wins" /></td>
          <td align="right">
            <html:link page="/Team.do" paramId="team_id" paramName="team" paramProperty="team_id">
              <bean:write name="team" property="abbrev" />
            </html:link>
          </td>
          <td id="rank" align="left" valign="middle" height="24">
            <bean:write name="team" property="division_rank" />
          </td>
        </logic:equal>
      </tr>
    </table>
  </logic:iterate>

  <logic:iterate id="team" name="round3">
    <table class="playoff_node" id='r3d<bean:write name="team" property="division" />'>
      <tr>
        <logic:equal name="team" property="conference" value="0">
          <td id="rank" align="right" valign="middle" height="24">
            <bean:write name="team" property="division_rank" />
          </td>
          <td align="left">
            <html:link page="/Team.do" paramId="team_id" paramName="team" paramProperty="team_id">
              <bean:write name="team" property="abbrev" />
            </html:link>
          </td>
          <td><bean:write name="team" property="round3_wins" /></td>
        </logic:equal>
        <logic:equal name="team" property="conference" value="1">
          <td><bean:write name="team" property="round3_wins" /></td>
          <td align="right">
            <html:link page="/Team.do" paramId="team_id" paramName="team" paramProperty="team_id">
              <bean:write name="team" property="abbrev" />
            </html:link>
          </td>
          <td id="rank" align="left" valign="middle" height="24">
            <bean:write name="team" property="division_rank" />
          </td>
        </logic:equal>
      </tr>
    </table>
  </logic:iterate>

  <logic:iterate id="team" name="round4">
    <table class="playoff_node" id='r4c<bean:write name="team" property="conference" />'>
      <tr>
        <logic:equal name="team" property="conference" value="0">
          <td id="rank" align="right" valign="middle" height="24">
            <bean:write name="team" property="division_rank" />
          </td>
          <td align="left">
            <html:link page="/Team.do" paramId="team_id" paramName="team" paramProperty="team_id">
              <bean:write name="team" property="abbrev" />
            </html:link>
          </td>
          <td></td>
        </logic:equal>
        <logic:equal name="team" property="conference" value="1">
          <td></td>
          <td align="right">
            <html:link page="/Team.do" paramId="team_id" paramName="team" paramProperty="team_id">
              <bean:write name="team" property="abbrev" />
            </html:link>
          </td>
          <td id="rank" align="left" valign="middle" height="24">
            <bean:write name="team" property="division_rank" />
          </td>
        </logic:equal>
      </tr>
    </table>
  </logic:iterate>

  <logic:iterate id="team" name="round5">
    <table class="playoff_node" id="r5">
      <tr>
        <logic:equal name="team" property="conference" value="0">
          <td id="rank" align="right" valign="middle" height="24">
            <bean:write name="team" property="division_rank" />
          </td>
          <td align="left">
            <html:link page="/Team.do" paramId="team_id" paramName="team" paramProperty="team_id">
              <bean:write name="team" property="abbrev" />
            </html:link>
          </td>
          <td></td>
        </logic:equal>
        <logic:equal name="team" property="conference" value="1">
          <td></td>
          <td align="right">
            <html:link page="/Team.do" paramId="team_id" paramName="team" paramProperty="team_id">
              <bean:write name="team" property="abbrev" />
            </html:link>
          </td>
          <td id="rank" align="left" valign="middle" height="24">
            <bean:write name="team" property="division_rank" />
          </td>
        </logic:equal>
      </tr>
    </table>
  </logic:iterate>

</logic:present>

</body>
</html:html>
