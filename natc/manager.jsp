<%@ page contentType="text/html; charset=utf-8" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld"   prefix="bean"   %>
<%@ taglib uri="/WEB-INF/struts-html.tld"   prefix="html"   %>
<%@ taglib uri="/WEB-INF/struts-logic.tld"  prefix="logic"  %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>

<html:html locale="true">
<head>
  <html:base/>
  <title><bean:message key="title.manager"/></title>
  
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

<h1>MANAGER</h1>

<table class="ratings">
  <tr class="label">
    <td colspan="2">
      <bean:write name="manager" property="first_name"/> <bean:write name="manager" property="last_name"/>
    </td>
  </tr>
  <logic:present name="team">
  <tr class="sublabel">
    <td colspan="2">
      <a href='Team.do?team_id=<bean:write name="team" property="team_id"/>&year=<bean:write name="manager" property="year"/>'>
        <bean:write name="team" property="location"/> <bean:write name="team" property="name"/>
      </a>
    </td>
  </tr>
  </logic:present>
  <tr class="heading">
    <td><bean:message key="manager.label.area"   /></td>
    <td><bean:message key="manager.label.rating" /></td>
  </tr>
  <tr>
    <td><bean:message key="manager.label.offense"    /></td>
    <td><img src='drawbar.jsp?background=000000&foreground=ff0000&width=150&height=16&value=<bean:write name="manager" property="offense"    />'></td>
  </tr>
  <tr>
    <td><bean:message key="manager.label.defense"    /></td>
    <td><img src='drawbar.jsp?background=000000&foreground=ff0000&width=150&height=16&value=<bean:write name="manager" property="defense"    />'></td>
  </tr>
  <tr>
    <td><bean:message key="manager.label.intangible" /></td>
    <td><img src='drawbar.jsp?background=000000&foreground=ff0000&width=150&height=16&value=<bean:write name="manager" property="intangible" />'></td>
  </tr>
  <tr>
    <td><bean:message key="manager.label.penalties" /></td>
    <td><img src='drawbar.jsp?background=000000&foreground=ff0000&width=150&height=16&value=<bean:write name="manager" property="penalties" />'></td>
  </tr>
  <tr>
    <td><bean:message key="manager.label.style" /></td>
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

<logic:notEqual name="manager" property="player_id" value="0">
  <table class="ratings">
    <tr class="sublabel">
      <td>
        <html:link page="/Player.do" paramId="player_id" paramName="manager" paramProperty="player_id">
          <bean:message key="manager.label.as_player" />
        </html:link>
      </td>
    </tr>
  </table>
</logic:notEqual>

<logic:present name="team">

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
    <td><bean:message key="team.label.score"         /></td>
  </tr>
  
  <logic:iterate id="teamStatsView" name="teamOffense">
    <tr>
      <td>
        <a href='Games.do?team_id=<bean:write name="team" property="team_id" />&year=<bean:write name="manager" property="year" />&type=<bean:write name="teamStatsView" property="type" />'>
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
      <td><bean:write name="teamStatsView" property="score"                /></td>
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
    <td><bean:message key="team.label.score"         /></td>
  </tr>
  <logic:iterate id="teamStatsView" name="teamDefense">
    <tr>
      <td>
        <a href='Games.do?team_id=<bean:write name="team" property="team_id" />&year=<bean:write name="manager" property="year" />&type=<bean:write name="teamStatsView" property="type" />'>
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
      <td><bean:write name="teamStatsView" property="score"                /></td>
    </tr>
  </logic:iterate>
</logic:present>
</table>

</logic:present>

<logic:present name="history">
  <table class="boxscores">
    <tr class="label">
      <td colspan="13"><bean:message key="manager.label.history"/></td>
    </tr>
    <tr class="heading">
      <td colspan="4"></td>
      <td colspan="4"><bean:message key="manager.label.offense" /></td>
      <td colspan="4"><bean:message key="manager.label.defense" /></td>
      <td></td>
    </tr>
    <tr class="heading">
      <td><a href="" onclick="return sortTable( null, this )"><bean:message key="manager.label.team"    /></a></td>
      <td><a href="" onclick="return sortTable( null, this )"><bean:message key="manager.label.year"    /></a></td>
      <td><a href="" onclick="return sortTable( null, this )"><bean:message key="manager.label.record"  /></a></td>
      <td><bean:message key="manager.label.finish"  /></td>
      <td><a href="" onclick="return sortTable( null, this )"><bean:message key="manager.label.avg_top" /></a></td>
      <td><a href="" onclick="return sortTable( null, this )"><bean:message key="manager.label.score"   /></a></td>
      <td><a href="" onclick="return sortTable( null, this )"><bean:message key="manager.label.eff"     /></a></td>
      <td><a href="" onclick="return sortTable( null, this )"><bean:message key="manager.label.ps_eff"  /></a></td>
      <td><a href="" onclick="return sortTable( null, this )"><bean:message key="manager.label.avg_top" /></a></td>
      <td><a href="" onclick="return sortTable( null, this )"><bean:message key="manager.label.score"   /></a></td>
      <td><a href="" onclick="return sortTable( null, this )"><bean:message key="manager.label.eff"     /></a></td>
      <td><a href="" onclick="return sortTable( null, this )"><bean:message key="manager.label.ps_eff"  /></a></td>
      <td><bean:message key="manager.label.awards"  /></td>
    </tr>
    <tbody id="seasonList">
    <logic:iterate id="managerStatsView" name="history">
      <logic:equal name="managerStatsView" property="year" value="Total">
        <tr class="totals">
      </logic:equal>
      <logic:notEqual name="managerStatsView" property="year" value="Total">
        <tr>
      </logic:notEqual>
        <td>
          <a href='Team.do?team_id=<bean:write name="managerStatsView" property="team_id" />&year=<bean:write name="managerStatsView" property="year" />'>
            <bean:write name="managerStatsView" property="team_abbrev" />
          </a>
        </td>
        <td>
          <a href='Manager.do?manager_id=<bean:write name="manager" property="manager_id" />&year=<bean:write name="managerStatsView" property="year" />'>
            <bean:write name="managerStatsView" property="year" />
          </a>
        </td>
        <td><bean:write name="managerStatsView" property="wins" />-<bean:write name="managerStatsView" property="losses" /></td>
        <td>
          <logic:equal name="managerStatsView" property="playoff_rank" value="0">
            <logic:equal name="managerStatsView" property="division_rank" value="0">
              <bean:message key="team.label.na" />
            </logic:equal>
            <logic:equal name="managerStatsView" property="division_rank" value="5">
              <bean:message key="team.label.fifth" />
            </logic:equal>
            <logic:equal name="managerStatsView" property="division_rank" value="6">
              <bean:message key="team.label.sixth" />
            </logic:equal>
            <logic:equal name="managerStatsView" property="division_rank" value="7">
              <bean:message key="team.label.seventh" />
            </logic:equal>
            <logic:equal name="managerStatsView" property="division_rank" value="8">
              <bean:message key="team.label.eighth" />
            </logic:equal>
            <logic:equal name="managerStatsView" property="division_rank" value="9">
              <bean:message key="team.label.ninth" />
            </logic:equal>
            <logic:equal name="managerStatsView" property="division_rank" value="10">
              <bean:message key="team.label.last" />
            </logic:equal>
          </logic:equal>
          <logic:equal name="managerStatsView" property="playoff_rank" value="1">
            <logic:equal name="managerStatsView" property="division_rank" value="1">
              <bean:message key="team.label.first" />
            </logic:equal>
            <logic:equal name="managerStatsView" property="division_rank" value="2">
              <bean:message key="team.label.second" />
            </logic:equal>
            <logic:equal name="managerStatsView" property="division_rank" value="3">
              <bean:message key="team.label.third" />
            </logic:equal>
            <logic:equal name="managerStatsView" property="division_rank" value="4">
              <bean:message key="team.label.fourth" />
            </logic:equal>
          </logic:equal>
          <logic:equal name="managerStatsView" property="playoff_rank" value="2">
            <bean:message key="team.label.div_runner_up" />
          </logic:equal>
          <logic:equal name="managerStatsView" property="playoff_rank" value="3">
            <bean:message key="team.label.division_champ" />
          </logic:equal>
          <logic:equal name="managerStatsView" property="playoff_rank" value="4">
            <bean:message key="team.label.conference_champ" />
          </logic:equal>
          <logic:equal name="managerStatsView" property="playoff_rank" value="5">
            <bean:message key="team.label.natc_champ" />
          </logic:equal>
        </td>
        <td><bean:write name="managerStatsView" property="offAvgPossessionTimeDsp" /></td>
        <td><bean:write name="managerStatsView" property="off_points"              /></td>
        <td><bean:write name="managerStatsView" property="offScoringEfficiencyDsp" /></td>
        <td><bean:write name="managerStatsView" property="offPsEfficiencyDsp"      /></td>
        <td><bean:write name="managerStatsView" property="defAvgPossessionTimeDsp" /></td>
        <td><bean:write name="managerStatsView" property="def_points"              /></td>
        <td><bean:write name="managerStatsView" property="defScoringEfficiencyDsp" /></td>
        <td><bean:write name="managerStatsView" property="defPsEfficiencyDsp"      /></td>
        <td>
          <logic:equal name="managerStatsView" property="award" value="1">
            <bean:message key="manager.label.moty" />
          </logic:equal>
          <logic:notEqual name="managerStatsView" property="allstar_team_id" value="0">
            <bean:message key="manager.label.allstar_award" />
          </logic:notEqual>
        </td>
      </tr>
    </logic:iterate>
    </tbody>
  </table>
</logic:present>

</body>
</html:html>
