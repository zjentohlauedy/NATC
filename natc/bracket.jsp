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
<html:errors/>

<html:form method="POST" action="/Games">

<html:select property="operation">
  <html:option key = "games.option.day"   value="0" />
  <html:option key = "games.option.week"  value="1" />
  <html:option key = "games.option.month" value="2" />
  <html:option key = "games.option.year"  value="3" />
</html:select>
<html:submit><bean:message key="games.label.next"/></html:submit>
</html:form>

<logic:present name="schedule">
  <h2><bean:write name="schedule" property="scheduled"/></h2>
</logic:present>

<logic:present name="round1">
  <table class="bracket"><tr><td><img src="images/bracket.png" /></td></tr></table>
  <div class="seed" id="d0t1">1</div>
  <div class="seed" id="d0t2">2</div>
  <div class="seed" id="d0t3">3</div>
  <div class="seed" id="d0t4">4</div>
  <div class="seed" id="d1t1">1</div>
  <div class="seed" id="d1t2">2</div>
  <div class="seed" id="d1t3">3</div>
  <div class="seed" id="d1t4">4</div>
  <div class="seed" id="d2t1">1</div>
  <div class="seed" id="d2t2">2</div>
  <div class="seed" id="d2t3">3</div>
  <div class="seed" id="d2t4">4</div>
  <div class="seed" id="d3t1">1</div>
  <div class="seed" id="d3t2">2</div>
  <div class="seed" id="d3t3">3</div>
  <div class="seed" id="d3t4">4</div>

  <logic:iterate id="team" name="round1">
    <div class="playoff_team" id='r1d<bean:write name="team" property="division" />t<bean:write name="team" property="division_rank" />'>
      <html:link page="/Team.do" paramId="team_id" paramName="team" paramProperty="team_id">
        <bean:write name="team" property="abbrev" />
      </html:link>
    </div>
  </logic:iterate>
</logic:present>

<logic:present name="round2">
  <logic:iterate id="team" name="round2">
    <div class="playoff_team" id='r2d<bean:write name="team" property="division" />t<bean:write name="team" property="division_rank" />'>
      <html:link page="/Team.do" paramId="team_id" paramName="team" paramProperty="team_id">
        <bean:write name="team" property="abbrev" />
      </html:link>
    </div>
  </logic:iterate>
</logic:present>

<logic:present name="round3">
  <logic:iterate id="team" name="round3">
    <div class="playoff_team" id='r3d<bean:write name="team" property="division" />'>
      <html:link page="/Team.do" paramId="team_id" paramName="team" paramProperty="team_id">
        <bean:write name="team" property="abbrev" />
      </html:link>
    </div>
  </logic:iterate>
</logic:present>

<logic:present name="round4">
  <logic:iterate id="team" name="round4">
    <div class="playoff_team" id='r4c<bean:write name="team" property="conference" />'>
      <html:link page="/Team.do" paramId="team_id" paramName="team" paramProperty="team_id">
        <bean:write name="team" property="abbrev" />
      </html:link>
    </div>
  </logic:iterate>
</logic:present>

<logic:present name="round5">
  <logic:iterate id="team" name="round5">
    <div class="playoff_team" id="r5">
      <html:link page="/Team.do" paramId="team_id" paramName="team" paramProperty="team_id">
        <bean:write name="team" property="abbrev" />
      </html:link>
    </div>
  </logic:iterate>
</logic:present>

<logic:present name="playoffGameInfo">
  <logic:iterate id="game" name="playoffGameInfo">
    <logic:equal name="game" property="playoff_round" value="1">
      <div id='r<bean:write name="game" property="playoff_round" />d<bean:write name="game" property="division" />t<bean:write name="game" property="division_rank" />g<bean:write name="game" property="game_num" />'>
    </logic:equal>
    <logic:equal name="game" property="playoff_round" value="2">
      <div id='r<bean:write name="game" property="playoff_round" />d<bean:write name="game" property="division" />t<bean:write name="game" property="division_rank" />g<bean:write name="game" property="game_num" />'>
    </logic:equal>
    <logic:equal name="game" property="playoff_round" value="3">
      <div id='r<bean:write name="game" property="playoff_round" />d<bean:write name="game" property="division" />g<bean:write name="game" property="game_num" />'>
    </logic:equal>
    <logic:equal name="game" property="playoff_round" value="4">
      <div id='r<bean:write name="game" property="playoff_round" />c<bean:write name="game" property="conference" />g<bean:write name="game" property="game_num" />'>
    </logic:equal>
        <html:link page="/Game.do" paramId="game_id" paramName="game" paramProperty="game_id">
          <logic:equal name="game" property="road" value="true">
            <logic:equal name="game" property="win" value="true">
              <img src="images/roadwin.gif" />
            </logic:equal>
            <logic:equal name="game" property="win" value="false">
              <img src="images/roadloss.gif" />
            </logic:equal>
          </logic:equal>
          <logic:equal name="game" property="road" value="false">
            <logic:equal name="game" property="win" value="true">
              <img src="images/homewin.gif" />
            </logic:equal>
            <logic:equal name="game" property="win" value="false">
              <img src="images/homeloss.gif" />
            </logic:equal>
          </logic:equal>
        </html:link>
      </div>
  </logic:iterate>
</logic:present>

<logic:present name="games">
  <logic:iterate id="game" name="games">
    <table class="game">
      <tr>
        <td>
          <html:link page="/Team.do" paramId="team_id" paramName="game" paramProperty="road_team_id">
            <bean:write name="game" property="road_team"/>
          </html:link>
        </td>
        <td>
          <html:link page="/Game.do" paramId="game_id" paramName="game" paramProperty="game_id">
            <logic:equal name="game" property="road_win" value="true">
              <em><bean:write name="game" property="road_score"/></em>
            </logic:equal>
            <logic:equal name="game" property="road_win" value="false">
              <bean:write name="game" property="road_score"/>
            </logic:equal>
          </html:link>
        </td>
        <td></td>
      </tr>
      <tr>
        <td>
          <html:link page="/Team.do" paramId="team_id" paramName="game" paramProperty="home_team_id">
            <bean:write name="game" property="home_team"/>
          </html:link>
        </td>
        <td>
          <html:link page="/Game.do" paramId="game_id" paramName="game" paramProperty="game_id">
            <logic:equal name="game" property="home_win" value="true">
              <em><bean:write name="game" property="home_score"/></em>
            </logic:equal>
            <logic:equal name="game" property="home_win" value="false">
              <bean:write name="game" property="home_score"/>
            </logic:equal>
          </html:link>
          <logic:equal name="game" property="overtime" value="true">
            <h7><bean:message key="games.label.ot_indicator"/></h7>
          </logic:equal>
        </td>
        <td></td>
      </tr>
    </table>
  </logic:iterate>
</logic:present>

<logic:present name="injuries">
  <table class="injuries">
    <tr class="label">
      <td colspan="3"><bean:message key="games.label.injuries"/></td>
    </tr>
    <tr class="heading">
      <td><bean:message key="player.label.name"     /></td>
      <td><bean:message key="player.label.team"     /></td>
      <td><bean:message key="injury.label.duration" /></td>
    </tr>
    <logic:iterate id="injury" name="injuries">
      <tr>
        <td>
          <html:link page="/Player.do" paramId="player_id" paramName="injury" paramProperty="player_id">
            <bean:write name="injury" property="last_name"/>, <bean:write name="injury" property="first_name"/>
          </html:link>
        </td>
        <td>
          <html:link page="/Team.do" paramId="team_id" paramName="injury" paramProperty="team_id">
            <bean:write name="injury" property="team_abbrev"/>
          </html:link>
        </td>
        <td><bean:write name="injury" property="durationDsp"/></td>
      </tr>
    </logic:iterate>
  </table>
</logic:present>

</body>
</html:html>
