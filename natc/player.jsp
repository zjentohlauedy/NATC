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
  
  <script type="text/javascript" src="scripts/natc_sorter.js"></script>
  
</head>
<body>

<jsp:include page="menu.jsp" />

<h1>PLAYER</h1>

<table class="ratings">
  <tr class="label">
    <td colspan="2">
      <bean:write name="player" property="first_name"/> <bean:write name="player" property="last_name"/>
      <logic:equal name="player" property="rookie" value="true">
        <bean:message key="player.label.rookie" />
      </logic:equal>
      <logic:equal name="player" property="injured" value="true">
        <bean:message key="player.label.injured" />
      </logic:equal>
    </td>
  </tr>
  <logic:equal name="player" property="injured" value="true">
    <tr>
      <td colspan="2">
        <bean:message key="player.label.returns" /> <bean:write name="player" property="return_date"/>
      </td>
    </tr>
  </logic:equal>
  <tr>
    <td colspan="2">
      <logic:equal name="player" property="retired" value="true">
        <bean:message key="player.label.retired" />
      </logic:equal>
      <logic:equal name="player" property="retired" value="false">
        <logic:equal name="player" property="team_id" value="0">
          <bean:message key="player.label.freeagent" />
        </logic:equal>
      </logic:equal>
      <logic:present name="team">
        <a href='Team.do?team_id=<bean:write name="team" property="team_id"/>&year=<bean:write name="player" property="year"/>'>
          <bean:write name="team" property="location"/> <bean:write name="team" property="name"/>
        </a>
      </logic:present>
      <logic:present name="rookieInfoView">
        ( Drafted #<bean:write name="rookieInfoView" property="pick"/> in <bean:write name="rookieInfoView" property="year"/> by 
        <a href='Team.do?team_id=<bean:write name="rookieInfoView" property="team_id"/>&year=<bean:write name="rookieInfoView" property="year"/>'>
          <bean:write name="rookieInfoView" property="team_abbrev"/>
        </a>
        )
      </logic:present>
    </td>
  </tr>
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

<logic:present name="injuries">
  <table class="injuries">
    <tr class="label"><td colspan="3"><bean:write name="player" property="year"/> <bean:message key="player.label.injuries" /></td></tr>
    <tr class="heading">
      <td><bean:message key="player.label.date"     /></td>
      <td><bean:message key="player.label.opponent" /></td>
      <td><bean:message key="player.label.duration" /></td>
    </tr>
    <logic:iterate id="playerInjuryView" name="injuries">
      <tr>
        <td>
          <html:link page="/Game.do" paramId="game_id" paramName="playerInjuryView" paramProperty="game_id">
            <bean:write name="playerInjuryView" property="datestamp"/>
          </html:link>
        </td>
        <td>
          <logic:equal name="playerInjuryView" property="road_game" value="true">
              <bean:message key="player.label.at" />
          </logic:equal>
          <a href='Team.do?team_id=<bean:write name="playerInjuryView" property="opponent"/>&year=<bean:write name="player" property="year"/>'>
            <bean:write name="playerInjuryView" property="opponent_abbrev"/>
          </a>
        </td>
        <td><bean:write name="playerInjuryView" property="durationDsp"/></td>
      </tr>
    </logic:iterate>
  </table>
</logic:present>

<table class="boxscores">
<logic:present name="playerStats">
  <tr class="label"><td colspan="22"><bean:write name="player" property="year"/> <bean:message key="player.label.stats" /></td></tr>
  <tr class="heading">
    <td><bean:message key="player.label.team"          /></td>
    <td><bean:message key="player.label.type"          /></td>
    <td><bean:message key="player.label.games"         /></td>
    <td><bean:message key="player.label.starts"        /></td>
    <td><bean:message key="player.label.time_per_game" /></td>
    <td><bean:message key="player.label.points"        /></td>
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
          <a href='Team.do?team_id=<bean:write name="team" property="team_id"/>&year=<bean:write name="player" property="year"/>'>
            <bean:write name="team" property="abbrev"/>
          </a>
        </logic:present>
      </td>
      <td>
        <a href='Games.do?player_id=<bean:write name="player" property="player_id" />&year=<bean:write name="player" property="year" />&type=<bean:write name="playerStatsView" property="type" />'>
          <logic:equal name="playerStatsView" property="type" value="1">
            <bean:message key="team.label.preseason" />
          </logic:equal>
          <logic:equal name="playerStatsView" property="type" value="2">
            <bean:message key="team.label.regseason" />
          </logic:equal>
          <logic:equal name="playerStatsView" property="type" value="3">
            <bean:message key="team.label.postseason" />
          </logic:equal>
          <logic:equal name="playerStatsView" property="type" value="4">
            <bean:message key="team.label.allstar" />
          </logic:equal>
        </a>
      </td>
      <td><bean:write name="playerStatsView" property="games"                /></td>
      <td><bean:write name="playerStatsView" property="games_started"        /></td>
      <td><bean:write name="playerStatsView" property="timePerGameDsp"       /></td>
      <td><bean:write name="playerStatsView" property="points"               /></td>
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
<logic:present name="preseason">
    <tr class="label"><td colspan="22"><bean:message key="player.label.prehistory" /></td></tr>
    <tr class="heading">
      <td><a href="" onclick="return sortTable( 'preHist', this )"><bean:message key="player.label.team"          /></a></td>
      <td><a href="" onclick="return sortTable( 'preHist', this )"><bean:message key="player.label.year"          /></a></td>
      <td><a href="" onclick="return sortTable( 'preHist', this )"><bean:message key="player.label.games"         /></a></td>
      <td><a href="" onclick="return sortTable( 'preHist', this )"><bean:message key="player.label.starts"        /></a></td>
      <td><a href="" onclick="return sortTable( 'preHist', this )"><bean:message key="player.label.time_per_game" /></a></td>
      <td><a href="" onclick="return sortTable( 'preHist', this )"><bean:message key="player.label.points"        /></a></td>
      <td><a href="" onclick="return sortTable( 'preHist', this )"><bean:message key="player.label.attempts"      /></a></td>
      <td><a href="" onclick="return sortTable( 'preHist', this )"><bean:message key="player.label.goals"         /></a></td>
      <td><a href="" onclick="return sortTable( 'preHist', this )"><bean:message key="player.label.eff"           /></a></td>
      <td><a href="" onclick="return sortTable( 'preHist', this )"><bean:message key="player.label.assists"       /></a></td>
      <td><a href="" onclick="return sortTable( 'preHist', this )"><bean:message key="player.label.turnovers"     /></a></td>
      <td><a href="" onclick="return sortTable( 'preHist', this )"><bean:message key="player.label.stops"         /></a></td>
      <td><a href="" onclick="return sortTable( 'preHist', this )"><bean:message key="player.label.steals"        /></a></td>
      <td><a href="" onclick="return sortTable( 'preHist', this )"><bean:message key="player.label.penalties"     /></a></td>
      <td><a href="" onclick="return sortTable( 'preHist', this )"><bean:message key="player.label.off_pen"       /></a></td>
      <td><a href="" onclick="return sortTable( 'preHist', this )"><bean:message key="player.label.psa"           /></a></td>
      <td><a href="" onclick="return sortTable( 'preHist', this )"><bean:message key="player.label.psm"           /></a></td>
      <td><a href="" onclick="return sortTable( 'preHist', this )"><bean:message key="player.label.eff"           /></a></td>
      <td><a href="" onclick="return sortTable( 'preHist', this )"><bean:message key="player.label.ot_psa"        /></a></td>
      <td><a href="" onclick="return sortTable( 'preHist', this )"><bean:message key="player.label.ot_psm"        /></a></td>
      <td><a href="" onclick="return sortTable( 'preHist', this )"><bean:message key="player.label.eff"           /></a></td>
      <td></td>
    </tr>
    <tbody id="preHist">
    <logic:iterate id="playerStatsView" name="preseason">
      <logic:equal name="playerStatsView" property="year" value="Total">
        <tr class="totals">
      </logic:equal>
      <logic:notEqual name="playerStatsView" property="year" value="Total">
        <tr>
      </logic:notEqual>
        <td>
          <a href='Team.do?team_id=<bean:write name="playerStatsView" property="team_id"/>&year=<bean:write name="playerStatsView" property="year"/>'>
            <bean:write name="playerStatsView" property="team_abbrev"/>
          </a>
        </td>
        <td>
          <a href='Player.do?player_id=<bean:write name="player" property="player_id"/>&year=<bean:write name="playerStatsView" property="year"/>'>
            <bean:write name="playerStatsView" property="year"                 />
          </a>
        </td>
        <td><bean:write name="playerStatsView" property="games"                /></td>
        <td><bean:write name="playerStatsView" property="games_started"        /></td>
        <td><bean:write name="playerStatsView" property="timePerGameDsp"       /></td>
        <td><bean:write name="playerStatsView" property="points"               /></td>
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
        <td></td>
      </tr>
    </logic:iterate>
    </tbody>
</logic:present>
<logic:present name="regseason">
    <tr class="label"><td colspan="22"><bean:message key="player.label.reghistory" /></td></tr>
    <tr class="heading">
      <td><a href="" onclick="return sortTable( 'regHist', this )"><bean:message key="player.label.team"          /></a></td>
      <td><a href="" onclick="return sortTable( 'regHist', this )"><bean:message key="player.label.year"          /></a></td>
      <td><a href="" onclick="return sortTable( 'regHist', this )"><bean:message key="player.label.games"         /></a></td>
      <td><a href="" onclick="return sortTable( 'regHist', this )"><bean:message key="player.label.starts"        /></a></td>
      <td><a href="" onclick="return sortTable( 'regHist', this )"><bean:message key="player.label.time_per_game" /></a></td>
      <td><a href="" onclick="return sortTable( 'regHist', this )"><bean:message key="player.label.points"        /></a></td>
      <td><a href="" onclick="return sortTable( 'regHist', this )"><bean:message key="player.label.attempts"      /></a></td>
      <td><a href="" onclick="return sortTable( 'regHist', this )"><bean:message key="player.label.goals"         /></a></td>
      <td><a href="" onclick="return sortTable( 'regHist', this )"><bean:message key="player.label.eff"           /></a></td>
      <td><a href="" onclick="return sortTable( 'regHist', this )"><bean:message key="player.label.assists"       /></a></td>
      <td><a href="" onclick="return sortTable( 'regHist', this )"><bean:message key="player.label.turnovers"     /></a></td>
      <td><a href="" onclick="return sortTable( 'regHist', this )"><bean:message key="player.label.stops"         /></a></td>
      <td><a href="" onclick="return sortTable( 'regHist', this )"><bean:message key="player.label.steals"        /></a></td>
      <td><a href="" onclick="return sortTable( 'regHist', this )"><bean:message key="player.label.penalties"     /></a></td>
      <td><a href="" onclick="return sortTable( 'regHist', this )"><bean:message key="player.label.off_pen"       /></a></td>
      <td><a href="" onclick="return sortTable( 'regHist', this )"><bean:message key="player.label.psa"           /></a></td>
      <td><a href="" onclick="return sortTable( 'regHist', this )"><bean:message key="player.label.psm"           /></a></td>
      <td><a href="" onclick="return sortTable( 'regHist', this )"><bean:message key="player.label.eff"           /></a></td>
      <td><a href="" onclick="return sortTable( 'regHist', this )"><bean:message key="player.label.ot_psa"        /></a></td>
      <td><a href="" onclick="return sortTable( 'regHist', this )"><bean:message key="player.label.ot_psm"        /></a></td>
      <td><a href="" onclick="return sortTable( 'regHist', this )"><bean:message key="player.label.eff"           /></a></td>
      <td><bean:message key="player.label.awards"        /></td>
    </tr>
    <tbody id="regHist">
    <logic:iterate id="playerStatsView" name="regseason">
      <logic:equal name="playerStatsView" property="year" value="Total">
        <tr class="totals">
      </logic:equal>
      <logic:notEqual name="playerStatsView" property="year" value="Total">
        <tr>
      </logic:notEqual>
        <td>
          <a href='Team.do?team_id=<bean:write name="playerStatsView" property="team_id"/>&year=<bean:write name="playerStatsView" property="year"/>'>
            <bean:write name="playerStatsView" property="team_abbrev"/>
          </a>
        </td>
        <td>
          <a href='Player.do?player_id=<bean:write name="player" property="player_id"/>&year=<bean:write name="playerStatsView" property="year"/>'>
            <bean:write name="playerStatsView" property="year"                 />
          </a>
        </td>
        <td><bean:write name="playerStatsView" property="games"                /></td>
        <td><bean:write name="playerStatsView" property="games_started"        /></td>
        <td><bean:write name="playerStatsView" property="timePerGameDsp"       /></td>
        <td><bean:write name="playerStatsView" property="points"               /></td>
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
    </tbody>
</logic:present>
<logic:present name="postseason">
    <tr class="label"><td colspan="22"><bean:message key="player.label.posthistory" /></td></tr>
    <tr class="heading">
      <td><a href="" onclick="return sortTable( 'postHist', this )"><bean:message key="player.label.team"          /></a></td>
      <td><a href="" onclick="return sortTable( 'postHist', this )"><bean:message key="player.label.year"          /></a></td>
      <td><a href="" onclick="return sortTable( 'postHist', this )"><bean:message key="player.label.games"         /></a></td>
      <td><a href="" onclick="return sortTable( 'postHist', this )"><bean:message key="player.label.starts"        /></a></td>
      <td><a href="" onclick="return sortTable( 'postHist', this )"><bean:message key="player.label.time_per_game" /></a></td>
      <td><a href="" onclick="return sortTable( 'postHist', this )"><bean:message key="player.label.points"        /></a></td>
      <td><a href="" onclick="return sortTable( 'postHist', this )"><bean:message key="player.label.attempts"      /></a></td>
      <td><a href="" onclick="return sortTable( 'postHist', this )"><bean:message key="player.label.goals"         /></a></td>
      <td><a href="" onclick="return sortTable( 'postHist', this )"><bean:message key="player.label.eff"           /></a></td>
      <td><a href="" onclick="return sortTable( 'postHist', this )"><bean:message key="player.label.assists"       /></a></td>
      <td><a href="" onclick="return sortTable( 'postHist', this )"><bean:message key="player.label.turnovers"     /></a></td>
      <td><a href="" onclick="return sortTable( 'postHist', this )"><bean:message key="player.label.stops"         /></a></td>
      <td><a href="" onclick="return sortTable( 'postHist', this )"><bean:message key="player.label.steals"        /></a></td>
      <td><a href="" onclick="return sortTable( 'postHist', this )"><bean:message key="player.label.penalties"     /></a></td>
      <td><a href="" onclick="return sortTable( 'postHist', this )"><bean:message key="player.label.off_pen"       /></a></td>
      <td><a href="" onclick="return sortTable( 'postHist', this )"><bean:message key="player.label.psa"           /></a></td>
      <td><a href="" onclick="return sortTable( 'postHist', this )"><bean:message key="player.label.psm"           /></a></td>
      <td><a href="" onclick="return sortTable( 'postHist', this )"><bean:message key="player.label.eff"           /></a></td>
      <td><a href="" onclick="return sortTable( 'postHist', this )"><bean:message key="player.label.ot_psa"        /></a></td>
      <td><a href="" onclick="return sortTable( 'postHist', this )"><bean:message key="player.label.ot_psm"        /></a></td>
      <td><a href="" onclick="return sortTable( 'postHist', this )"><bean:message key="player.label.eff"           /></a></td>
      <td></td>
    </tr>
    <tbody id="postHist">
    <logic:iterate id="playerStatsView" name="postseason">
      <logic:equal name="playerStatsView" property="year" value="Total">
        <tr class="totals">
      </logic:equal>
      <logic:notEqual name="playerStatsView" property="year" value="Total">
        <tr>
      </logic:notEqual>
        <td>
          <a href='Team.do?team_id=<bean:write name="playerStatsView" property="team_id"/>&year=<bean:write name="playerStatsView" property="year"/>'>
            <bean:write name="playerStatsView" property="team_abbrev"/>
          </a>
        </td>
        <td>
          <a href='Player.do?player_id=<bean:write name="player" property="player_id"/>&year=<bean:write name="playerStatsView" property="year"/>'>
            <bean:write name="playerStatsView" property="year"                 />
          </a>
        </td>
        <td><bean:write name="playerStatsView" property="games"                /></td>
        <td><bean:write name="playerStatsView" property="games_started"        /></td>
        <td><bean:write name="playerStatsView" property="timePerGameDsp"       /></td>
        <td><bean:write name="playerStatsView" property="points"               /></td>
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
        <td></td>
      </tr>
    </logic:iterate>
    </tbody>
</logic:present>
<logic:present name="allstar">
    <tr class="label"><td colspan="22"><bean:message key="player.label.ashistory" /></td></tr>
    <tr class="heading">
      <td><a href="" onclick="return sortTable( 'asgHist', this )"><bean:message key="player.label.team"          /></a></td>
      <td><a href="" onclick="return sortTable( 'asgHist', this )"><bean:message key="player.label.year"          /></a></td>
      <td><a href="" onclick="return sortTable( 'asgHist', this )"><bean:message key="player.label.games"         /></a></td>
      <td><a href="" onclick="return sortTable( 'asgHist', this )"><bean:message key="player.label.starts"        /></a></td>
      <td><a href="" onclick="return sortTable( 'asgHist', this )"><bean:message key="player.label.time_per_game" /></a></td>
      <td><a href="" onclick="return sortTable( 'asgHist', this )"><bean:message key="player.label.points"        /></a></td>
      <td><a href="" onclick="return sortTable( 'asgHist', this )"><bean:message key="player.label.attempts"      /></a></td>
      <td><a href="" onclick="return sortTable( 'asgHist', this )"><bean:message key="player.label.goals"         /></a></td>
      <td><a href="" onclick="return sortTable( 'asgHist', this )"><bean:message key="player.label.eff"           /></a></td>
      <td><a href="" onclick="return sortTable( 'asgHist', this )"><bean:message key="player.label.assists"       /></a></td>
      <td><a href="" onclick="return sortTable( 'asgHist', this )"><bean:message key="player.label.turnovers"     /></a></td>
      <td><a href="" onclick="return sortTable( 'asgHist', this )"><bean:message key="player.label.stops"         /></a></td>
      <td><a href="" onclick="return sortTable( 'asgHist', this )"><bean:message key="player.label.steals"        /></a></td>
      <td><a href="" onclick="return sortTable( 'asgHist', this )"><bean:message key="player.label.penalties"     /></a></td>
      <td><a href="" onclick="return sortTable( 'asgHist', this )"><bean:message key="player.label.off_pen"       /></a></td>
      <td><a href="" onclick="return sortTable( 'asgHist', this )"><bean:message key="player.label.psa"           /></a></td>
      <td><a href="" onclick="return sortTable( 'asgHist', this )"><bean:message key="player.label.psm"           /></a></td>
      <td><a href="" onclick="return sortTable( 'asgHist', this )"><bean:message key="player.label.eff"           /></a></td>
      <td><a href="" onclick="return sortTable( 'asgHist', this )"><bean:message key="player.label.ot_psa"        /></a></td>
      <td><a href="" onclick="return sortTable( 'asgHist', this )"><bean:message key="player.label.ot_psm"        /></a></td>
      <td><a href="" onclick="return sortTable( 'asgHist', this )"><bean:message key="player.label.eff"           /></a></td>
      <td></td>
    </tr>
    <tbody id="asgHist">
    <logic:iterate id="playerStatsView" name="allstar">
      <logic:equal name="playerStatsView" property="year" value="Total">
        <tr class="totals">
      </logic:equal>
      <logic:notEqual name="playerStatsView" property="year" value="Total">
        <tr>
      </logic:notEqual>
        <td>
          <a href='Team.do?team_id=<bean:write name="playerStatsView" property="team_id"/>&year=<bean:write name="playerStatsView" property="year"/>'>
            <bean:write name="playerStatsView" property="team_abbrev"/>
          </a>
        </td>
        <td>
          <a href='Player.do?player_id=<bean:write name="player" property="player_id"/>&year=<bean:write name="playerStatsView" property="year"/>'>
            <bean:write name="playerStatsView" property="year"                 />
          </a>
        </td>
        <td><bean:write name="playerStatsView" property="games"                /></td>
        <td><bean:write name="playerStatsView" property="games_started"        /></td>
        <td><bean:write name="playerStatsView" property="timePerGameDsp"       /></td>
        <td><bean:write name="playerStatsView" property="points"               /></td>
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
        <td></td>
      </tr>
    </logic:iterate>
    </tbody>
</logic:present>
</table>

<logic:present name="injury_history">
  <table class="injuries">
    <tr class="label"><td colspan="3"><bean:message key="player.label.injury_history" /></td></tr>
    <tr class="heading">
      <td><bean:message key="player.label.date"     /></td>
      <td><bean:message key="player.label.opponent" /></td>
      <td><bean:message key="player.label.duration" /></td>
    </tr>
    <logic:iterate id="playerInjuryView" name="injury_history">
      <tr>
        <td>
          <html:link page="/Game.do" paramId="game_id" paramName="playerInjuryView" paramProperty="game_id">
            <bean:write name="playerInjuryView" property="datestamp"/>
          </html:link>
        </td>
        <td>
          <logic:equal name="playerInjuryView" property="road_game" value="true">
              <bean:message key="player.label.at" />
          </logic:equal>
          <a href='Team.do?team_id=<bean:write name="playerInjuryView" property="opponent"/>&year=<bean:write name="playerInjuryView" property="year"/>'>
            <bean:write name="playerInjuryView" property="opponent_abbrev"/>
          </a>
        </td>
        <td><bean:write name="playerInjuryView" property="durationDsp"/></td>
      </tr>
    </logic:iterate>
  </table>
</logic:present>

</body>
</html:html>
