<%@ page contentType="text/html; charset=utf-8" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld"   prefix="bean"   %>
<%@ taglib uri="/WEB-INF/struts-html.tld"   prefix="html"   %>
<%@ taglib uri="/WEB-INF/struts-logic.tld"  prefix="logic"  %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>

<html:html locale="true">
<head>
  <html:base/>
  <title><bean:message key="title.player"/></title>
  
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

<h1>PLAYER</h1>

<table class="ratings">
  <tr class="label">
    <td colspan="2">
      <bean:write name="player" property="first_name"/> <bean:write name="player" property="last_name"/>
      <logic:equal name="player" property="rookie" value="true">
        <bean:message key="team.label.rookie" />
      </logic:equal>
    </td>
  </tr>
  <logic:present name="team">
  <tr class="sublabel">
    <td colspan="2">
      <html:link page="/Team.do" paramId="team_id" paramName="team" paramProperty="team_id">
        <bean:write name="team" property="location"/> <bean:write name="team" property="name"/>
      </html:link>
    </td>
  </tr>
  </logic:present>
  <tr class="heading">
    <td><bean:message key="player.label.area"   /></td>
    <td><bean:message key="player.label.rating" /></td>
  </tr>
  <tr>
    <td><bean:message key="player.label.scoring"         /></td>
    <td><img src='drawbar2.jsp?background=000000&color1=ff0000&color2=00ff00&width=150&height=16&value1=<bean:write name="player" property="adjustedScoring"         />&value2=<bean:write name="player" property="scoring"         />'></td>
  </tr>
  <tr>
    <td><bean:message key="player.label.passing"         /></td>
    <td><img src='drawbar2.jsp?background=000000&color1=ff0000&color2=00ff00&width=150&height=16&value1=<bean:write name="player" property="adjustedPassing"         />&value2=<bean:write name="player" property="passing"         />'></td>
  </tr>
  <tr>
    <td><bean:message key="player.label.blocking"        /></td>
    <td><img src='drawbar2.jsp?background=000000&color1=ff0000&color2=00ff00&width=150&height=16&value1=<bean:write name="player" property="adjustedBlocking"        />&value2=<bean:write name="player" property="blocking"        />'></td>
  </tr>
  <tr>&nbsp;</tr>
  <tr>
    <td><bean:message key="player.label.tackling"        /></td>
    <td><img src='drawbar2.jsp?background=000000&color1=ff0000&color2=00ff00&width=150&height=16&value1=<bean:write name="player" property="adjustedTackling"        />&value2=<bean:write name="player" property="tackling"        />'></td>
  </tr>
  <tr>
    <td><bean:message key="player.label.stealing"        /></td>
    <td><img src='drawbar2.jsp?background=000000&color1=ff0000&color2=00ff00&width=150&height=16&value1=<bean:write name="player" property="adjustedStealing"        />&value2=<bean:write name="player" property="stealing"        />'></td>
  </tr>
  <tr>
    <td><bean:message key="player.label.presence"        /></td>
    <td><img src='drawbar2.jsp?background=000000&color1=ff0000&color2=00ff00&width=150&height=16&value1=<bean:write name="player" property="adjustedPresence"        />&value2=<bean:write name="player" property="presence"        />'></td>
  </tr>
  <tr>&nbsp;</tr>
  <tr>
    <td><bean:message key="player.label.discipline"      /></td>
    <td><img src='drawbar2.jsp?background=000000&color1=ff0000&color2=00ff00&width=150&height=16&value1=<bean:write name="player" property="adjustedDiscipline"      />&value2=<bean:write name="player" property="discipline"      />'></td>
  </tr>
  <tr>&nbsp;</tr>
  <tr>
    <td><bean:message key="player.label.endurance"      /></td>
    <td><img src='drawbar2.jsp?background=000000&color1=ff0000&color2=00ff00&width=150&height=16&value1=<bean:write name="player" property="adjustedEndurance"       />&value2=<bean:write name="player" property="endurance"       />'></td>
  </tr>
  <tr>&nbsp;</tr>
  <tr>
    <td><bean:message key="player.label.penalty_shot"    /></td>
    <td><img src='drawbar2.jsp?background=000000&color1=ff0000&color2=00ff00&width=150&height=16&value1=<bean:write name="player" property="adjustedPenalty_shot"    />&value2=<bean:write name="player" property="penalty_shot"    />'></td>
  </tr>
  <tr>
    <td><bean:message key="player.label.penalty_offense" /></td>
    <td><img src='drawbar2.jsp?background=000000&color1=ff0000&color2=00ff00&width=150&height=16&value1=<bean:write name="player" property="adjustedPenalty_offense" />&value2=<bean:write name="player" property="penalty_offense" />'></td>
  </tr>
  <tr>
    <td><bean:message key="player.label.penalty_defense" /></td>
    <td><img src='drawbar2.jsp?background=000000&color1=ff0000&color2=00ff00&width=150&height=16&value1=<bean:write name="player" property="adjustedPenalty_defense" />&value2=<bean:write name="player" property="penalty_defense" />'></td>
  </tr>
</table>

<table class="boxscores">
<logic:present name="playerStats">
  <tr class="label"><td colspan="15"><bean:write name="player" property="year"/> <bean:message key="player.label.stats" /></td></tr>
  <tr class="heading">
    <td><bean:message key="player.label.team"          /></td>
    <td><bean:message key="player.label.type"          /></td>
    <td><bean:message key="player.label.games"         /></td>
    <td><bean:message key="player.label.time_per_game" /></td>
    <td><bean:message key="player.label.attempts"      /></td>
    <td><bean:message key="player.label.goals"         /></td>
    <td><bean:message key="player.label.eff"           /></td>
    <td><bean:message key="player.label.assists"       /></td>
    <td><bean:message key="player.label.turnovers"     /></td>
    <td><bean:message key="player.label.stops"         /></td>
    <td><bean:message key="player.label.steals"        /></td>
    <td><bean:message key="player.label.penalties"     /></td>
    <td><bean:message key="player.label.off_pen"       /></td>
    <td><bean:message key="player.label.psa"           /></td>
    <td><bean:message key="player.label.psm"           /></td>
    <td><bean:message key="player.label.eff"           /></td>
    <td><bean:message key="player.label.ot_psa"        /></td>
    <td><bean:message key="player.label.ot_psm"        /></td>
    <td><bean:message key="player.label.eff"           /></td>
    <td><bean:message key="player.label.awards"        /></td>
  </tr>
  <logic:iterate id="playerStatsView" name="playerStats">
    <tr>
      <td>
        <logic:present name="team">
        <html:link page="/Team.do" paramId="team_id" paramName="team" paramProperty="team_id">
          <bean:write name="team" property="abbrev"/>
        </html:link>
        </logic:present>
      </td>
      <td>
        <logic:equal name="playerStatsView" property="type" value="1">
          <bean:message key="player.label.preseason" />
        </logic:equal>
        <logic:equal name="playerStatsView" property="type" value="2">
          <bean:message key="player.label.regseason" />
        </logic:equal>
        <logic:equal name="playerStatsView" property="type" value="3">
          <bean:message key="player.label.postseason" />
        </logic:equal>
        <logic:equal name="playerStatsView" property="type" value="4">
          <bean:message key="player.label.allstar" />
        </logic:equal>
      </td>
      <td><bean:write name="playerStatsView" property="games"                /></td>
      <td><bean:write name="playerStatsView" property="timePerGameDsp"       /></td>
      <td><bean:write name="playerStatsView" property="attempts"             /></td>
      <td><bean:write name="playerStatsView" property="goals"                /></td>
      <td><bean:write name="playerStatsView" property="scoringEfficiencyDsp" /></td>
      <td><bean:write name="playerStatsView" property="assists"              /></td>
      <td><bean:write name="playerStatsView" property="turnovers"            /></td>
      <td><bean:write name="playerStatsView" property="stops"                /></td>
      <td><bean:write name="playerStatsView" property="steals"               /></td>
      <td><bean:write name="playerStatsView" property="penalties"            /></td>
      <td><bean:write name="playerStatsView" property="offensive_penalties"  /></td>
      <td><bean:write name="playerStatsView" property="psa"                  /></td>
      <td><bean:write name="playerStatsView" property="psm"                  /></td>
      <td><bean:write name="playerStatsView" property="psEfficiencyDsp"      /></td>
      <td><bean:write name="playerStatsView" property="ot_psa"               /></td>
      <td><bean:write name="playerStatsView" property="ot_psm"               /></td>
      <td><bean:write name="playerStatsView" property="otPsEfficiencyDsp"    /></td>
      <td>
        <logic:equal name="playerStatsView" property="type" value="2">
          <logic:equal name="player" property="award" value="1">
            <bean:message key="player.label.silver" />
          </logic:equal>
          <logic:equal name="player" property="award" value="2">
            <bean:message key="player.label.gold" />
          </logic:equal>
          <logic:equal name="player" property="award" value="3">
            <bean:message key="player.label.platinum" />
          </logic:equal>
          <logic:notEqual name="player" property="allstar_team_id" value="0">
            <bean:message key="player.label.allstar_award" />
          </logic:notEqual>
        </logic:equal>
      </td>
    </tr>
  </logic:iterate>
</logic:present>
  <tr class="separator"></tr>
<logic:present name="history">
    <tr class="label"><td colspan="15"><bean:message key="player.label.history" /></td></tr>
    <tr class="heading">
      <td><bean:message key="player.label.team"          /></td>
      <td><bean:message key="player.label.year"          /></td>
      <td><bean:message key="player.label.games"         /></td>
      <td><bean:message key="player.label.time_per_game" /></td>
      <td><bean:message key="player.label.attempts"      /></td>
      <td><bean:message key="player.label.goals"         /></td>
      <td><bean:message key="player.label.eff"           /></td>
      <td><bean:message key="player.label.assists"       /></td>
      <td><bean:message key="player.label.turnovers"     /></td>
      <td><bean:message key="player.label.stops"         /></td>
      <td><bean:message key="player.label.steals"        /></td>
      <td><bean:message key="player.label.penalties"     /></td>
      <td><bean:message key="player.label.off_pen"       /></td>
      <td><bean:message key="player.label.psa"           /></td>
      <td><bean:message key="player.label.psm"           /></td>
      <td><bean:message key="player.label.eff"           /></td>
      <td><bean:message key="player.label.ot_psa"        /></td>
      <td><bean:message key="player.label.ot_psm"        /></td>
      <td><bean:message key="player.label.eff"           /></td>
      <td><bean:message key="player.label.awards"        /></td>
    </tr>
    <logic:iterate id="playerStatsView" name="history">
      <logic:equal name="playerStatsView" property="year" value="Total">
        <tr class="totals">
      </logic:equal>
      <logic:notEqual name="playerStatsView" property="year" value="Total">
        <tr>
      </logic:notEqual>
        <td>
          <html:link page="/Team.do" paramId="team_id" paramName="playerStatsView" paramProperty="team_id">
            <bean:write name="playerStatsView" property="team_abbrev"/>
          </html:link>
        </td>
        <td><bean:write name="playerStatsView" property="year"                 /></td>
        <td><bean:write name="playerStatsView" property="games"                /></td>
        <td><bean:write name="playerStatsView" property="timePerGameDsp"       /></td>
        <td><bean:write name="playerStatsView" property="attempts"             /></td>
        <td><bean:write name="playerStatsView" property="goals"                /></td>
        <td><bean:write name="playerStatsView" property="scoringEfficiencyDsp" /></td>
        <td><bean:write name="playerStatsView" property="assists"              /></td>
        <td><bean:write name="playerStatsView" property="turnovers"            /></td>
        <td><bean:write name="playerStatsView" property="stops"                /></td>
        <td><bean:write name="playerStatsView" property="steals"               /></td>
        <td><bean:write name="playerStatsView" property="penalties"            /></td>
        <td><bean:write name="playerStatsView" property="offensive_penalties"  /></td>
        <td><bean:write name="playerStatsView" property="psa"                  /></td>
        <td><bean:write name="playerStatsView" property="psm"                  /></td>
        <td><bean:write name="playerStatsView" property="psEfficiencyDsp"      /></td>
        <td><bean:write name="playerStatsView" property="ot_psa"               /></td>
        <td><bean:write name="playerStatsView" property="ot_psm"               /></td>
        <td><bean:write name="playerStatsView" property="otPsEfficiencyDsp"    /></td>
        <td>
          <logic:equal name="playerStatsView" property="award" value="1">
            <bean:message key="player.label.silver" />
          </logic:equal>
          <logic:equal name="playerStatsView" property="award" value="2">
            <bean:message key="player.label.gold" />
          </logic:equal>
          <logic:equal name="playerStatsView" property="award" value="3">
            <bean:message key="player.label.platinum" />
          </logic:equal>
          <logic:notEqual name="playerStatsView" property="allstar_team_id" value="0">
            <bean:message key="player.label.allstar_award" />
          </logic:notEqual>
        </td>
      </tr>
    </logic:iterate>
</logic:present>
</table>

</body>
</html:html>
