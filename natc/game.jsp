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
  
  <script type="text/javascript" src="scripts/natc_sorter.js"></script>
  
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

<h1>GAME</h1>

<table class="game_long">
  <logic:present name="roadGame">
    <tr>
      <td>
        <html:link page="/Team.do" paramId="team_id" paramName="roadGame" paramProperty="team_id">
          <bean:write name="roadGame" property="location" />
        </html:link>
      </td>
      <td>
        <logic:equal name="roadGame" property="win" value="true">
          <em><bean:write name="roadGame" property="score"/></em>
        </logic:equal>
        <logic:equal name="roadGame" property="win" value="false">
          <bean:write name="roadGame" property="score"/>
        </logic:equal>
      </td>
    </tr>
  </logic:present>
  <logic:present name="homeGame">
    <tr>
      <td>
        <html:link page="/Team.do" paramId="team_id" paramName="homeGame" paramProperty="team_id">
          <bean:write name="homeGame" property="location" />
        </html:link>
      </td>
      <td>
        <logic:equal name="homeGame" property="win" value="true">
          <em><bean:write name="homeGame" property="score"/></em>
        </logic:equal>
        <logic:equal name="homeGame" property="win" value="false">
          <bean:write name="homeGame" property="score"/>
        </logic:equal>
        <logic:greaterThan name="homeGame" property="ot_psa" value="0">
          <h7><bean:message key="games.label.ot_indicator"/></h7>
        </logic:greaterThan>
      </td>
    </tr>
  </logic:present>
</table>

<table class="boxscores">
  <tr class="heading">
    <td><a href="" onclick="return sortTable( 'roadPlayers', this )"><bean:message key="player.label.name"      /></a></td>
    <td><bean:message key="player.label.poss"      /></td>
    <td><a href="" onclick="return sortTable( 'roadPlayers', this )"><bean:message key="player.label.time"      /></a></td>
    <td><a href="" onclick="return sortTable( 'roadPlayers', this )"><bean:message key="player.label.points"    /></a></td>
    <td><a href="" onclick="return sortTable( 'roadPlayers', this )"><bean:message key="player.label.attempts"  /></a></td>
    <td><a href="" onclick="return sortTable( 'roadPlayers', this )"><bean:message key="player.label.goals"     /></a></td>
    <td><a href="" onclick="return sortTable( 'roadPlayers', this )"><bean:message key="player.label.eff"       /></a></td>
    <td><a href="" onclick="return sortTable( 'roadPlayers', this )"><bean:message key="player.label.assists"   /></a></td>
    <td><a href="" onclick="return sortTable( 'roadPlayers', this )"><bean:message key="player.label.turnovers" /></a></td>
    <td><a href="" onclick="return sortTable( 'roadPlayers', this )"><bean:message key="player.label.stops"     /></a></td>
    <td><a href="" onclick="return sortTable( 'roadPlayers', this )"><bean:message key="player.label.steals"    /></a></td>
    <td><a href="" onclick="return sortTable( 'roadPlayers', this )"><bean:message key="player.label.penalties" /></a></td>
    <td><a href="" onclick="return sortTable( 'roadPlayers', this )"><bean:message key="player.label.off_pen"   /></a></td>
    <td><a href="" onclick="return sortTable( 'roadPlayers', this )"><bean:message key="player.label.psa"       /></a></td>
    <td><a href="" onclick="return sortTable( 'roadPlayers', this )"><bean:message key="player.label.psm"       /></a></td>
    <td><a href="" onclick="return sortTable( 'roadPlayers', this )"><bean:message key="player.label.eff"       /></a></td>
    <td><a href="" onclick="return sortTable( 'roadPlayers', this )"><bean:message key="player.label.ot_psa"    /></a></td>
    <td><a href="" onclick="return sortTable( 'roadPlayers', this )"><bean:message key="player.label.ot_psm"    /></a></td>
    <td><a href="" onclick="return sortTable( 'roadPlayers', this )"><bean:message key="player.label.eff"       /></a></td>
  </tr>
  <logic:present name="roadPlayers">
    <tbody id="roadPlayers">
    <logic:iterate id="player" name="roadPlayers">
      <tr>
        <td>
          <html:link page="/Player.do" paramId="player_id" paramName="player" paramProperty="player_id">
            <bean:write name="player" property="last_name" />, <bean:write name="player" property="first_name" />
          </html:link>
          <logic:equal name="player" property="injured" value="true">
            <h7><bean:message key="player.label.injured" /></h7>
          </logic:equal>
          <logic:equal name="player" property="started" value="true">
            <h7><bean:message key="player.label.started" /></h7>
          </logic:equal>
        </td>
        <td></td>
        <td><bean:write name="player" property="gameTimeDsp"          /></td>
        <td><bean:write name="player" property="points"               /></td>
        <td><bean:write name="player" property="attempts"             /></td>
        <td><bean:write name="player" property="goals"                /></td>
        <td><bean:write name="player" property="scoringEfficiencyDsp" /></td>
        <td><bean:write name="player" property="assists"              /></td>
        <td><bean:write name="player" property="turnovers"            /></td>
        <td><bean:write name="player" property="stops"                /></td>
        <td><bean:write name="player" property="steals"               /></td>
        <td><bean:write name="player" property="penalties"            /></td>
        <td><bean:write name="player" property="offensive_penalties"  /></td>
        <td><bean:write name="player" property="psa"                  /></td>
        <td><bean:write name="player" property="psm"                  /></td>
        <td><bean:write name="player" property="psEfficiencyDsp"      /></td>
        <td><bean:write name="player" property="ot_psa"               /></td>
        <td><bean:write name="player" property="ot_psm"               /></td>
        <td><bean:write name="player" property="otPsEfficiencyDsp"    /></td>
      </tr>
    </logic:iterate>
    </tbody>
  </logic:present>
  <logic:present name="roadGame">
    <tr class="totals">
      <td>
        <html:link page="/Team.do" paramId="team_id" paramName="roadGame" paramProperty="team_id">
          <bean:write name="roadGame" property="name"                 />
        </html:link>
      </td>
      <td><bean:write name="roadGame" property="possessions"          /></td>
      <td><bean:write name="roadGame" property="gameTimeDsp"          /></td>
      <td><bean:write name="roadGame" property="score"                /></td>
      <td><bean:write name="roadGame" property="attempts"             /></td>
      <td><bean:write name="roadGame" property="goals"                /></td>
      <td><bean:write name="roadGame" property="scoringEfficiencyDsp" /></td>
      <td></td>
      <td><bean:write name="roadGame" property="turnovers"            /></td>
      <td></td>
      <td><bean:write name="roadGame" property="steals"               /></td>
      <td><bean:write name="roadGame" property="penalties"            /></td>
      <td><bean:write name="roadGame" property="offensive_penalties"  /></td>
      <td><bean:write name="roadGame" property="psa"                  /></td>
      <td><bean:write name="roadGame" property="psm"                  /></td>
      <td><bean:write name="roadGame" property="psEfficiencyDsp"      /></td>
      <td><bean:write name="roadGame" property="ot_psa"               /></td>
      <td><bean:write name="roadGame" property="ot_psm"               /></td>
      <td><bean:write name="roadGame" property="otPsEfficiencyDsp"    /></td>
    </tr>
  </logic:present>
  <tr class="separator"></tr>
  <tr class="heading">
    <td><a href="" onclick="return sortTable( 'homePlayers', this )"><bean:message key="player.label.name"      /></a></td>
    <td><bean:message key="player.label.poss"      /></td>
    <td><a href="" onclick="return sortTable( 'homePlayers', this )"><bean:message key="player.label.time"      /></a></td>
    <td><a href="" onclick="return sortTable( 'homePlayers', this )"><bean:message key="player.label.points"    /></a></td>
    <td><a href="" onclick="return sortTable( 'homePlayers', this )"><bean:message key="player.label.attempts"  /></a></td>
    <td><a href="" onclick="return sortTable( 'homePlayers', this )"><bean:message key="player.label.goals"     /></a></td>
    <td><a href="" onclick="return sortTable( 'homePlayers', this )"><bean:message key="player.label.eff"       /></a></td>
    <td><a href="" onclick="return sortTable( 'homePlayers', this )"><bean:message key="player.label.assists"   /></a></td>
    <td><a href="" onclick="return sortTable( 'homePlayers', this )"><bean:message key="player.label.turnovers" /></a></td>
    <td><a href="" onclick="return sortTable( 'homePlayers', this )"><bean:message key="player.label.stops"     /></a></td>
    <td><a href="" onclick="return sortTable( 'homePlayers', this )"><bean:message key="player.label.steals"    /></a></td>
    <td><a href="" onclick="return sortTable( 'homePlayers', this )"><bean:message key="player.label.penalties" /></a></td>
    <td><a href="" onclick="return sortTable( 'homePlayers', this )"><bean:message key="player.label.off_pen"   /></a></td>
    <td><a href="" onclick="return sortTable( 'homePlayers', this )"><bean:message key="player.label.psa"       /></a></td>
    <td><a href="" onclick="return sortTable( 'homePlayers', this )"><bean:message key="player.label.psm"       /></a></td>
    <td><a href="" onclick="return sortTable( 'homePlayers', this )"><bean:message key="player.label.eff"       /></a></td>
    <td><a href="" onclick="return sortTable( 'homePlayers', this )"><bean:message key="player.label.ot_psa"    /></a></td>
    <td><a href="" onclick="return sortTable( 'homePlayers', this )"><bean:message key="player.label.ot_psm"    /></a></td>
    <td><a href="" onclick="return sortTable( 'homePlayers', this )"><bean:message key="player.label.eff"       /></a></td>
  </tr>
  <logic:present name="homePlayers">
    <tbody id="homePlayers">
    <logic:iterate id="player" name="homePlayers">
      <tr>
        <td>
          <html:link page="/Player.do" paramId="player_id" paramName="player" paramProperty="player_id">
            <bean:write name="player" property="last_name" />, <bean:write name="player" property="first_name" />
          </html:link>
          <logic:equal name="player" property="injured" value="true">
            <h7><bean:message key="player.label.injured" /></h7>
          </logic:equal>
          <logic:equal name="player" property="started" value="true">
            <h7><bean:message key="player.label.started" /></h7>
          </logic:equal>
        </td>
        <td></td>
        <td><bean:write name="player" property="gameTimeDsp"          /></td>
        <td><bean:write name="player" property="points"               /></td>
        <td><bean:write name="player" property="attempts"             /></td>
        <td><bean:write name="player" property="goals"                /></td>
        <td><bean:write name="player" property="scoringEfficiencyDsp" /></td>
        <td><bean:write name="player" property="assists"              /></td>
        <td><bean:write name="player" property="turnovers"            /></td>
        <td><bean:write name="player" property="stops"                /></td>
        <td><bean:write name="player" property="steals"               /></td>
        <td><bean:write name="player" property="penalties"            /></td>
        <td><bean:write name="player" property="offensive_penalties"  /></td>
        <td><bean:write name="player" property="psa"                  /></td>
        <td><bean:write name="player" property="psm"                  /></td>
        <td><bean:write name="player" property="psEfficiencyDsp"      /></td>
        <td><bean:write name="player" property="ot_psa"               /></td>
        <td><bean:write name="player" property="ot_psm"               /></td>
        <td><bean:write name="player" property="otPsEfficiencyDsp"    /></td>
      </tr>
    </logic:iterate>
    </tbody>
  </logic:present>
  <logic:present name="homeGame">
    <tr class="totals">
      <td>
        <html:link page="/Team.do" paramId="team_id" paramName="homeGame" paramProperty="team_id">
          <bean:write name="homeGame" property="name"                 />
        </html:link>
      </td>
      <td><bean:write name="homeGame" property="possessions"          /></td>
      <td><bean:write name="homeGame" property="gameTimeDsp"          /></td>
      <td><bean:write name="homeGame" property="score"                /></td>
      <td><bean:write name="homeGame" property="attempts"             /></td>
      <td><bean:write name="homeGame" property="goals"                /></td>
      <td><bean:write name="homeGame" property="scoringEfficiencyDsp" /></td>
      <td></td>
      <td><bean:write name="homeGame" property="turnovers"            /></td>
      <td></td>
      <td><bean:write name="homeGame" property="steals"               /></td>
      <td><bean:write name="homeGame" property="penalties"            /></td>
      <td><bean:write name="homeGame" property="offensive_penalties"  /></td>
      <td><bean:write name="homeGame" property="psa"                  /></td>
      <td><bean:write name="homeGame" property="psm"                  /></td>
      <td><bean:write name="homeGame" property="psEfficiencyDsp"      /></td>
      <td><bean:write name="homeGame" property="ot_psa"               /></td>
      <td><bean:write name="homeGame" property="ot_psm"               /></td>
      <td><bean:write name="homeGame" property="otPsEfficiencyDsp"    /></td>
    </tr>
  </logic:present>
</table>

</body>
</html:html>
