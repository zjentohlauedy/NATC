<%@ page contentType="text/html; charset=utf-8" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld"   prefix="bean"   %>
<%@ taglib uri="/WEB-INF/struts-html.tld"   prefix="html"   %>
<%@ taglib uri="/WEB-INF/struts-logic.tld"  prefix="logic"  %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>

<%@ page import="natc.data.TeamGame" %>

<html:html locale="true">
<head>
  <html:base/>
  <title><bean:message key="title.team"/></title>
  
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

<h1>TEAM</h1>

<table class="ratings">
  <tr class="label">
    <td colspan="2"><bean:write name="team" property="location"/> <bean:write name="team" property="name"/></td>
  </tr>
  <tr class="heading">
    <td><bean:message key="team.label.area"   /></td>
    <td><bean:message key="team.label.rating" /></td>
  </tr>
  <tr>
    <td><bean:message key="team.label.offense"    /></td>
    <td><img src='drawbar.jsp?background=000000&foreground=ff0000&width=150&height=16&value=<bean:write name="team" property="offense"    />'></td>
  </tr>
  <tr>
    <td><bean:message key="team.label.defense"    /></td>
    <td><img src='drawbar.jsp?background=000000&foreground=ff0000&width=150&height=16&value=<bean:write name="team" property="defense"    />'></td>
  </tr>
  <tr>
    <td><bean:message key="team.label.discipline" /></td>
    <td><img src='drawbar.jsp?background=000000&foreground=ff0000&width=150&height=16&value=<bean:write name="team" property="discipline" />'></td>
  </tr>
  <tr>
    <td><bean:message key="team.label.ps_offense" /></td>
    <td><img src='drawbar.jsp?background=000000&foreground=ff0000&width=150&height=16&value=<bean:write name="team" property="ps_offense" />'></td>
  </tr>
  <tr>
    <td><bean:message key="team.label.ps_defense" /></td>
    <td><img src='drawbar.jsp?background=000000&foreground=ff0000&width=150&height=16&value=<bean:write name="team" property="ps_defense" />'></td>
  </tr>
  <tr class="separator"></tr>
</table>

<logic:present name="manager">
  <table class="roster">
    <tr class="label">
      <td colspan="3"><bean:message key="team.label.manager"/></td>
    </tr>
    <tr class="heading">
      <td><bean:message key="team.label.manager" /></td>
      <td><bean:message key="team.label.age"     /></td>
      <td><bean:message key="team.label.skill"   /></td>
      <td><bean:message key="team.label.style"   /></td>
    </tr>
    <tr>
      <td>
        <a href='Manager.do?manager_id=<bean:write name="manager" property="manager_id" />&year=<bean:write name="manager" property="year" />'>
          <bean:write name="manager" property="first_name" /> <bean:write name="manager" property="last_name"  />
        </a>
      </td>
      <td>
        <bean:write name="manager" property="age" />
      </td>
      <td>
        <img src='barchart.jsp?background=000000&foreground=ff0000&width=50&height=16&value1=<bean:write name="manager" property="offense"/>&value2=<bean:write name="manager" property="defense"/>&value3=<bean:write name="manager" property="intangible"/>&value4=<bean:write name="manager" property="penalties"/>'>
      </td>
      <td>
        <logic:equal name="manager" property="style" value="1">
          <bean:message key="manager.label.offensive" />
        </logic:equal>
        <logic:equal name="manager" property="style" value="2">
          <bean:message key="manager.label.defensive" />
        </logic:equal>
        <logic:equal name="manager" property="style" value="3">
          <bean:message key="manager.label.intangible" />
        </logic:equal>
        <logic:equal name="manager" property="style" value="4">
          <bean:message key="manager.label.penalty" />
        </logic:equal>
        <logic:equal name="manager" property="style" value="5">
          <bean:message key="manager.label.balanced" />
        </logic:equal>
      </td>
    </tr>
  </table>
</logic:present>

<table class="boxscores">
  <tr class="label">
    <td colspan="9"><bean:write name="team" property="year" /> <bean:message key="team.label.records"/></td>
  </tr>
  <tr class="heading">
    <td><bean:message key="team.label.preseason"  /></td>
    <td><bean:message key="team.label.record"     /></td>
    <td><bean:message key="team.label.division"   /></td>
    <td><bean:message key="team.label.ooc"        /></td>
    <td><bean:message key="team.label.home"       /></td>
    <td><bean:message key="team.label.road"       /></td>
    <td><bean:message key="team.label.overtime"   /></td>
    <td><bean:message key="team.label.postseason" /></td>
    <td><bean:message key="team.label.finish"     /></td>
  </tr>
  <tr>
    <td><bean:write name="team" property="preseason_wins"  />-<bean:write name="team" property="preseason_losses"  /></td>
    <td><bean:write name="team" property="wins"            />-<bean:write name="team" property="losses"            /></td>
    <td><bean:write name="team" property="division_wins"   />-<bean:write name="team" property="division_losses"   /></td>
    <td><bean:write name="team" property="ooc_wins"        />-<bean:write name="team" property="ooc_losses"        /></td>
    <td><bean:write name="team" property="home_wins"       />-<bean:write name="team" property="home_losses"       /></td>
    <td><bean:write name="team" property="road_wins"       />-<bean:write name="team" property="road_losses"       /></td>
    <td><bean:write name="team" property="ot_wins"         />-<bean:write name="team" property="ot_losses"         /></td>
    <td><bean:write name="team" property="postseason_wins" />-<bean:write name="team" property="postseason_losses" /></td>
    <td>
      <logic:equal name="team" property="playoff_rank" value="0">
        <logic:equal name="team" property="division_rank" value="0">
          <bean:message key="team.label.na" />
        </logic:equal>
        <logic:equal name="team" property="division_rank" value="5">
          <bean:message key="team.label.fifth" />
        </logic:equal>
        <logic:equal name="team" property="division_rank" value="6">
          <bean:message key="team.label.sixth" />
        </logic:equal>
        <logic:equal name="team" property="division_rank" value="7">
          <bean:message key="team.label.seventh" />
        </logic:equal>
        <logic:equal name="team" property="division_rank" value="8">
          <bean:message key="team.label.eighth" />
        </logic:equal>
        <logic:equal name="team" property="division_rank" value="9">
          <bean:message key="team.label.ninth" />
        </logic:equal>
        <logic:equal name="team" property="division_rank" value="10">
          <bean:message key="team.label.last" />
        </logic:equal>
      </logic:equal>
      <logic:equal name="team" property="playoff_rank" value="1">
        <logic:equal name="team" property="division_rank" value="1">
          <bean:message key="team.label.first" />
        </logic:equal>
        <logic:equal name="team" property="division_rank" value="2">
          <bean:message key="team.label.second" />
        </logic:equal>
        <logic:equal name="team" property="division_rank" value="3">
          <bean:message key="team.label.third" />
        </logic:equal>
        <logic:equal name="team" property="division_rank" value="4">
          <bean:message key="team.label.fourth" />
        </logic:equal>
      </logic:equal>
      <logic:equal name="team" property="playoff_rank" value="2">
        <bean:message key="team.label.div_runner_up" />
      </logic:equal>
      <logic:equal name="team" property="playoff_rank" value="3">
        <bean:message key="team.label.division_champ" />
      </logic:equal>
      <logic:equal name="team" property="playoff_rank" value="4">
        <bean:message key="team.label.conference_champ" />
      </logic:equal>
      <logic:equal name="team" property="playoff_rank" value="5">
        <bean:message key="team.label.natc_champ" />
      </logic:equal>
    </td>
  </tr>
  <tr class="separator"></tr>
</table>

<table class="boxscores">
<logic:present name="teamOffense">
  <tr class="label"><td colspan="14"><bean:message key="team.label.offense" /></td></tr>
  <tr class="heading">
    <td><bean:message key="team.label.type"          /></td>
    <td><bean:message key="team.label.games"         /></td>
    <td><bean:message key="team.label.poss"          /></td>
    <td><bean:message key="team.label.avg_top"       /></td>
    <td><bean:message key="team.label.points"        /></td>
    <td><bean:message key="team.label.attempts"      /></td>
    <td><bean:message key="team.label.goals"         /></td>
    <td><bean:message key="team.label.eff"           /></td>
    <td><bean:message key="team.label.turnovers"     /></td>
    <td><bean:message key="team.label.steals"        /></td>
    <td><bean:message key="team.label.penalties"     /></td>
    <td><bean:message key="team.label.off_pen"       /></td>
    <td><bean:message key="team.label.psa"           /></td>
    <td><bean:message key="team.label.psm"           /></td>
    <td><bean:message key="team.label.eff"           /></td>
    <td><bean:message key="team.label.ot_psa"        /></td>
    <td><bean:message key="team.label.ot_psm"        /></td>
    <td><bean:message key="team.label.eff"           /></td>
  </tr>
  
  <logic:iterate id="teamStatsView" name="teamOffense">
    <tr>
      <td>
        <a href='Games.do?team_id=<bean:write name="team" property="team_id" />&year=<bean:write name="team" property="year" />&type=<bean:write name="teamStatsView" property="type" />'>
          <logic:equal name="teamStatsView" property="type" value="1">
            <bean:message key="team.label.preseason" />
          </logic:equal>
          <logic:equal name="teamStatsView" property="type" value="2">
            <bean:message key="team.label.regseason" />
          </logic:equal>
          <logic:equal name="teamStatsView" property="type" value="3">
            <bean:message key="team.label.postseason" />
          </logic:equal>
          <logic:equal name="teamStatsView" property="type" value="4">
            <bean:message key="team.label.allstar" />
          </logic:equal>
        </a>
      </td>
      <td><bean:write name="teamStatsView" property="games"                /></td>
      <td><bean:write name="teamStatsView" property="possessions"          /></td>
      <td><bean:write name="teamStatsView" property="avgPossessionTimeDsp" /></td>
      <td><bean:write name="teamStatsView" property="score"                /></td>
      <td><bean:write name="teamStatsView" property="attempts"             /></td>
      <td><bean:write name="teamStatsView" property="goals"                /></td>
      <td><bean:write name="teamStatsView" property="scoringEfficiencyDsp" /></td>
      <td><bean:write name="teamStatsView" property="turnovers"            /></td>
      <td><bean:write name="teamStatsView" property="steals"               /></td>
      <td><bean:write name="teamStatsView" property="penalties"            /></td>
      <td><bean:write name="teamStatsView" property="offensive_penalties"  /></td>
      <td><bean:write name="teamStatsView" property="psa"                  /></td>
      <td><bean:write name="teamStatsView" property="psm"                  /></td>
      <td><bean:write name="teamStatsView" property="psEfficiencyDsp"      /></td>
      <td><bean:write name="teamStatsView" property="ot_psa"               /></td>
      <td><bean:write name="teamStatsView" property="ot_psm"               /></td>
      <td><bean:write name="teamStatsView" property="otPsEfficiencyDsp"    /></td>
    </tr>
  </logic:iterate>
</logic:present>
  <tr class="separator"></tr>
<logic:present name="teamDefense">
  <tr class="label"><td colspan="14"><bean:message key="team.label.defense" /></td></tr>
  <tr class="heading">
    <td><bean:message key="team.label.type"          /></td>
    <td><bean:message key="team.label.games"         /></td>
    <td><bean:message key="team.label.poss"          /></td>
    <td><bean:message key="team.label.avg_top"       /></td>
    <td><bean:message key="team.label.points"        /></td>
    <td><bean:message key="team.label.attempts"      /></td>
    <td><bean:message key="team.label.goals"         /></td>
    <td><bean:message key="team.label.eff"           /></td>
    <td><bean:message key="team.label.turnovers"     /></td>
    <td><bean:message key="team.label.steals"        /></td>
    <td><bean:message key="team.label.penalties"     /></td>
    <td><bean:message key="team.label.off_pen"       /></td>
    <td><bean:message key="team.label.psa"           /></td>
    <td><bean:message key="team.label.psm"           /></td>
    <td><bean:message key="team.label.eff"           /></td>
    <td><bean:message key="team.label.ot_psa"        /></td>
    <td><bean:message key="team.label.ot_psm"        /></td>
    <td><bean:message key="team.label.eff"           /></td>
  </tr>
  <logic:iterate id="teamStatsView" name="teamDefense">
    <tr>
      <td>
        <a href='Games.do?team_id=<bean:write name="team" property="team_id" />&year=<bean:write name="team" property="year" />&type=<bean:write name="teamStatsView" property="type" />'>
          <logic:equal name="teamStatsView" property="type" value="1">
            <bean:message key="team.label.preseason" />
          </logic:equal>
          <logic:equal name="teamStatsView" property="type" value="2">
            <bean:message key="team.label.regseason" />
          </logic:equal>
          <logic:equal name="teamStatsView" property="type" value="3">
            <bean:message key="team.label.postseason" />
          </logic:equal>
          <logic:equal name="teamStatsView" property="type" value="4">
            <bean:message key="team.label.allstar" />
          </logic:equal>
        </a>
      </td>
      <td><bean:write name="teamStatsView" property="games"                /></td>
      <td><bean:write name="teamStatsView" property="possessions"          /></td>
      <td><bean:write name="teamStatsView" property="avgPossessionTimeDsp" /></td>
      <td><bean:write name="teamStatsView" property="score"                /></td>
      <td><bean:write name="teamStatsView" property="attempts"             /></td>
      <td><bean:write name="teamStatsView" property="goals"                /></td>
      <td><bean:write name="teamStatsView" property="scoringEfficiencyDsp" /></td>
      <td><bean:write name="teamStatsView" property="turnovers"            /></td>
      <td><bean:write name="teamStatsView" property="steals"               /></td>
      <td><bean:write name="teamStatsView" property="penalties"            /></td>
      <td><bean:write name="teamStatsView" property="offensive_penalties"  /></td>
      <td><bean:write name="teamStatsView" property="psa"                  /></td>
      <td><bean:write name="teamStatsView" property="psm"                  /></td>
      <td><bean:write name="teamStatsView" property="psEfficiencyDsp"      /></td>
      <td><bean:write name="teamStatsView" property="ot_psa"               /></td>
      <td><bean:write name="teamStatsView" property="ot_psm"               /></td>
      <td><bean:write name="teamStatsView" property="otPsEfficiencyDsp"    /></td>
    </tr>
  </logic:iterate>
</logic:present>
</table>


<logic:present name="teamPlayers">
<table class="roster">
  <tr class="label">
    <td colspan="12"><bean:message key="team.label.players"/></td>
  </tr>
  <tr class="heading">
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="team.label.player"        /></a></td>
    <logic:equal name="team" property="allstar_team" value="true">
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="team.label.team"          /></a></td>
    </logic:equal>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="team.label.age"           /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="team.label.skill"         /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="team.label.games"         /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="team.label.starts"        /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="team.label.time_per_game" /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="team.label.points"        /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="team.label.goals"         /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="team.label.assists"       /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="team.label.stops"         /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="team.label.steals"        /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="team.label.psm"           /></a></td>
  </tr>
  <tbody id="playerList">
  <logic:iterate id="teamPlayer" name="teamPlayers">
    <tr>
      <td>
        <a href='Player.do?player_id=<bean:write name="teamPlayer" property="player_id"/>&year=<bean:write name="team" property="year"/>'>
          <bean:write name="teamPlayer" property="last_name"/>, <bean:write name="teamPlayer" property="first_name"/>
        </a>
        <logic:equal name="teamPlayer" property="rookie" value="true">
          <h7><bean:message key="team.label.rookie" /></h7>
        </logic:equal>
        <logic:equal name="teamPlayer" property="award" value="1">
          <h7><bean:message key="team.label.silver" /></h7>
        </logic:equal>
        <logic:equal name="teamPlayer" property="award" value="2">
          <h7><bean:message key="team.label.gold" /></h7>
        </logic:equal>
        <logic:equal name="teamPlayer" property="award" value="3">
          <h7><bean:message key="team.label.platinum" /></h7>
        </logic:equal>
        <logic:notEqual name="teamPlayer" property="allstar_team_id" value="0">
          <h7><bean:message key="team.label.allstar" /></h7>
        </logic:notEqual>
        <logic:equal name="teamPlayer" property="injured" value="true">
          <h7><bean:message key="team.label.injured" /></h7>
        </logic:equal>
      </td>
      <logic:equal name="team" property="allstar_team" value="true">
      <td>
        <a href='Team.do?team_id=<bean:write name="teamPlayer" property="team_id"/>&year=<bean:write name="team" property="year"/>'>
          <bean:write name="teamPlayer" property="team_abbrev"/>
        </a>
      </td>
      </logic:equal>
      <td><bean:write name="teamPlayer" property="age"            /></td>
      <td>
        <img id='<bean:write name="teamPlayer" property="adjustedRating"/>' src='drawbar2.jsp?background=000000&color1=ff0000&color2=00ff00&width=150&height=16&value1=<bean:write name="teamPlayer" property="adjustedRating"/>&value2=<bean:write name="teamPlayer" property="rating"/>'>
      </td>
      <td><bean:write name="teamPlayer" property="games"          /></td>
      <td><bean:write name="teamPlayer" property="games_started"  /></td>
      <td><bean:write name="teamPlayer" property="timePerGameDsp" /></td>
      <td><bean:write name="teamPlayer" property="points"         /></td>
      <td><bean:write name="teamPlayer" property="goals"          /></td>
      <td><bean:write name="teamPlayer" property="assists"        /></td>
      <td><bean:write name="teamPlayer" property="stops"          /></td>
      <td><bean:write name="teamPlayer" property="steals"         /></td>
      <td><bean:write name="teamPlayer" property="psm"            /></td>
    </tr>
  </logic:iterate>
  </tbody>
  <tr class="separator"></tr>
</table>
</logic:present>

<logic:present name="injuries">
  <table class="injuries">
    <tr class="label"><td colspan="4"><bean:message key="team.label.injuries" /></td></tr>
    <tr class="heading">
      <td><bean:message key="team.label.date"     /></td>
      <td><bean:message key="team.label.player"   /></td>
      <td><bean:message key="team.label.opponent" /></td>
      <td><bean:message key="team.label.duration" /></td>
    </tr>
    <logic:iterate id="teamInjuryView" name="injuries">
      <tr>
        <td>
          <html:link page="/Game.do" paramId="game_id" paramName="teamInjuryView" paramProperty="game_id">
            <bean:write name="teamInjuryView" property="datestamp"/>
          </html:link>
        </td>
        <td>
          <a href='Player.do?player_id=<bean:write name="teamInjuryView" property="player_id"/>&year=<bean:write name="team" property="year"/>'>
            <bean:write name="teamInjuryView" property="last_name"/>, <bean:write name="teamInjuryView" property="first_name"/>
          </a>
        </td>
        <td align="right">
          <logic:equal name="teamInjuryView" property="road_game" value="true">
            <bean:message key="player.label.at" />
          </logic:equal>
          <a href='Team.do?team_id=<bean:write name="teamInjuryView" property="opponent"/>&year=<bean:write name="team" property="year"/>'>
            <bean:write name="teamInjuryView" property="opponent_abbrev"/>
          </a>
        </td>
        <td><bean:write name="teamInjuryView" property="durationDsp"/></td>
      </tr>
    </logic:iterate>
  </table>
</logic:present>

<logic:present name="history">
<table class="boxscores">
  <tr class="label">
    <td><bean:message key="team.label.history"/></td>
  </tr>
  <tr class="heading">
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="team.label.year"       /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="team.label.preseason"  /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="team.label.record"     /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="team.label.division"   /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="team.label.ooc"        /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="team.label.home"       /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="team.label.road"       /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="team.label.overtime"   /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="team.label.postseason" /></a></td>
    <td><bean:message key="team.label.finish"     /></td>
  </tr>
  <tbody id="seasonList">
  <logic:iterate id="entry" name="history">
    <logic:equal name="entry" property="year" value="Total">
      <tr class="totals">
    </logic:equal>
    <logic:notEqual name="entry" property="year" value="Total">
      <tr>
    </logic:notEqual>
      <td>
        <a href='Team.do?team_id=<bean:write name="team" property="team_id" />&year=<bean:write name="entry" property="year" />'>
          <bean:write name="entry" property="year" />
        </a>
      </td>
      <td><bean:write name="entry" property="preseason_wins"  />-<bean:write name="entry" property="preseason_losses"  /></td>
      <td><bean:write name="entry" property="wins"            />-<bean:write name="entry" property="losses"            /></td>
      <td><bean:write name="entry" property="division_wins"   />-<bean:write name="entry" property="division_losses"   /></td>
      <td><bean:write name="entry" property="ooc_wins"        />-<bean:write name="entry" property="ooc_losses"        /></td>
      <td><bean:write name="entry" property="home_wins"       />-<bean:write name="entry" property="home_losses"       /></td>
      <td><bean:write name="entry" property="road_wins"       />-<bean:write name="entry" property="road_losses"       /></td>
      <td><bean:write name="entry" property="ot_wins"         />-<bean:write name="entry" property="ot_losses"         /></td>
      <td><bean:write name="entry" property="postseason_wins" />-<bean:write name="entry" property="postseason_losses" /></td>
      <td>
        <logic:equal name="entry" property="playoff_rank" value="0">
          <logic:equal name="entry" property="division_rank" value="0">
            <bean:message key="team.label.na" />
          </logic:equal>
          <logic:equal name="entry" property="division_rank" value="5">
            <bean:message key="team.label.fifth" />
          </logic:equal>
          <logic:equal name="entry" property="division_rank" value="6">
            <bean:message key="team.label.sixth" />
          </logic:equal>
          <logic:equal name="entry" property="division_rank" value="7">
            <bean:message key="team.label.seventh" />
          </logic:equal>
          <logic:equal name="entry" property="division_rank" value="8">
            <bean:message key="team.label.eighth" />
          </logic:equal>
          <logic:equal name="entry" property="division_rank" value="9">
            <bean:message key="team.label.ninth" />
          </logic:equal>
          <logic:equal name="entry" property="division_rank" value="10">
            <bean:message key="team.label.last" />
          </logic:equal>
        </logic:equal>
        <logic:equal name="entry" property="playoff_rank" value="1">
          <logic:equal name="entry" property="division_rank" value="1">
            <bean:message key="team.label.first" />
          </logic:equal>
          <logic:equal name="entry" property="division_rank" value="2">
            <bean:message key="team.label.second" />
          </logic:equal>
          <logic:equal name="entry" property="division_rank" value="3">
            <bean:message key="team.label.third" />
          </logic:equal>
          <logic:equal name="entry" property="division_rank" value="4">
            <bean:message key="team.label.fourth" />
          </logic:equal>
        </logic:equal>
        <logic:equal name="entry" property="playoff_rank" value="2">
          <bean:message key="team.label.div_runner_up" />
        </logic:equal>
        <logic:equal name="entry" property="playoff_rank" value="3">
          <bean:message key="team.label.division_champ" />
        </logic:equal>
        <logic:equal name="entry" property="playoff_rank" value="4">
          <bean:message key="team.label.conference_champ" />
        </logic:equal>
        <logic:equal name="entry" property="playoff_rank" value="5">
          <bean:message key="team.label.natc_champ" />
        </logic:equal>
      </td>
    </tr>
  </logic:iterate>
  </tbody>
  <tr class="separator"></tr>
</table>
</logic:present>

</body>
</html:html>
