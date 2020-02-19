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
  
  <script type="text/javascript" src="scripts/natc_sorter.js"></script>
  <script type="text/javascript" src="scripts/natc_ajax.js"></script>
  <script type="text/javascript" src="scripts/natc_games.js"></script>
  
</head>
<body onload="init()">

<logic:present name="schedule">
  <logic:equal name="schedule" property="status" value="1" >
    <script type="text/javascript">
      function init() {
   	    initGames();
      }
    </script>
  </logic:equal>
</logic:present>
  
<jsp:include page="menu.jsp" />

<h1>GAMES</h1>
<html:errors/>

<!--
<html:form method="POST" action="/Games">

<html:select property="operation">
  <html:option key = "games.option.day"   value="0" />
  <html:option key = "games.option.week"  value="1" />
  <html:option key = "games.option.month" value="2" />
  <html:option key = "games.option.year"  value="3" />
</html:select>
<html:submit><bean:message key="games.label.next"/></html:submit>
</html:form>
-->

<logic:present name="schedule">
  <h2><bean:write name="schedule" property="scheduled"/></h2>
</logic:present>

<logic:present name="div0teams">
  <table class="standings">
    <tr>
      <td>
        <table class="standing">
          <tr class="heading">
            <td colspan="4"><bean:message key="division.greene"/></td>
          </tr>
          <logic:iterate id="team" name="div0teams">
            <tr>
              <td>
                <html:link page="/Team.do" paramId="team_id" paramName="team" paramProperty="team_id">
                  <bean:write name="team" property="location" />
                </html:link>
              </td>
              <logic:equal name="team" property="games" value="0" >
                <td><bean:write name="team" property="preseason_wins"     /></td>
                <td><bean:write name="team" property="preseason_losses"   /></td>
                <td></td>
              </logic:equal>
              <logic:greaterThan name="team" property="games" value="0" >
                <td><bean:write name="team" property="wins"     /></td>
                <td><bean:write name="team" property="losses"   /></td>
                <td><bean:write name="team" property="streak_wins" />-<bean:write name="team" property="streak_losses" /></td>
              </logic:greaterThan>
            </tr>
          </logic:iterate>
        </table>
      </td>
      <td>&nbsp;</td>
      <td>
        <table class="standing">
          <tr class="heading">
            <td colspan="4"><bean:message key="division.davis"/></td>
          </tr>
          <logic:iterate id="team" name="div1teams">
            <tr>
              <td>
                <html:link page="/Team.do" paramId="team_id" paramName="team" paramProperty="team_id">
                  <bean:write name="team" property="location" />
                </html:link>
              </td>
              <logic:equal name="team" property="games" value="0" >
                <td><bean:write name="team" property="preseason_wins"     /></td>
                <td><bean:write name="team" property="preseason_losses"   /></td>
                <td></td>
              </logic:equal>
              <logic:greaterThan name="team" property="games" value="0" >
                <td><bean:write name="team" property="wins"     /></td>
                <td><bean:write name="team" property="losses"   /></td>
                <td><bean:write name="team" property="streak_wins" />-<bean:write name="team" property="streak_losses" /></td>
              </logic:greaterThan>
            </tr>
          </logic:iterate>
        </table>
      </td>
      <td>&nbsp;</td>
      <td>
        <table class="standing">
          <tr class="heading">
            <td colspan="4"><bean:message key="division.smith"/></td>
          </tr>
          <logic:iterate id="team" name="div2teams">
            <tr>
              <td>
                <html:link page="/Team.do" paramId="team_id" paramName="team" paramProperty="team_id">
                  <bean:write name="team" property="location" />
                </html:link>
              </td>
              <logic:equal name="team" property="games" value="0" >
                <td><bean:write name="team" property="preseason_wins"     /></td>
                <td><bean:write name="team" property="preseason_losses"   /></td>
                <td></td>
              </logic:equal>
              <logic:greaterThan name="team" property="games" value="0" >
                <td><bean:write name="team" property="wins"     /></td>
                <td><bean:write name="team" property="losses"   /></td>
                <td><bean:write name="team" property="streak_wins" />-<bean:write name="team" property="streak_losses" /></td>
              </logic:greaterThan>
            </tr>
          </logic:iterate>
        </table>
      </td>
      <td>&nbsp;</td>
      <td>
        <table class="standing">
          <tr class="heading">
            <td colspan="4"><bean:message key="division.lawrence"/></td>
          </tr>
          <logic:iterate id="team" name="div3teams">
            <tr>
              <td>
                <html:link page="/Team.do" paramId="team_id" paramName="team" paramProperty="team_id">
                  <bean:write name="team" property="location" />
                </html:link>
              </td>
              <logic:equal name="team" property="games" value="0" >
                <td><bean:write name="team" property="preseason_wins"     /></td>
                <td><bean:write name="team" property="preseason_losses"   /></td>
                <td></td>
              </logic:equal>
              <logic:greaterThan name="team" property="games" value="0" >
                <td><bean:write name="team" property="wins"     /></td>
                <td><bean:write name="team" property="losses"   /></td>
                <td><bean:write name="team" property="streak_wins" />-<bean:write name="team" property="streak_losses" /></td>
              </logic:greaterThan>
            </tr>
          </logic:iterate>
        </table>
      </td>
    </tr>
  </table>
</logic:present>

<logic:present name="allstarTeams">
  <table class="allstar_standings">
    <tr>
      <td>
        <table class="standing">
          <tr class="heading">
            <td colspan="4"><bean:message key="games.label.allstars"/></td>
          </tr>
          <logic:iterate id="team" name="allstarTeams">
            <tr>
              <td>
                <html:link page="/Team.do" paramId="team_id" paramName="team" paramProperty="team_id">
                  <bean:write name="team" property="location" />
                </html:link>
              </td>
              <td><bean:write name="team" property="wins"     /></td>
              <td><bean:write name="team" property="losses"   /></td>
              <td></td>
            </tr>
          </logic:iterate>
        </table>
      </td>
    </tr>
  </table>
</logic:present>

<logic:present name="round1">
  <table class="bracket">
    <tr>
    <td>
      <table class="round">
        <logic:iterate id="team" name="round1">
          <tr>
            <logic:equal name="team" property="playoff_rank" value="0">
              <td height="24" valign="middle"></td>
              <td height="24" valign="middle"></td>
            </logic:equal>
            <logic:greaterThan name="team" property="playoff_rank" value="0">
              <td id="rank" align="right" valign="middle" height="24">
                <bean:write name="team" property="division_rank" />
              </td>
              <td height="24" valign="middle">
                <html:link page="/Team.do" paramId="team_id" paramName="team" paramProperty="team_id">
                  <bean:write name="team" property="abbrev" />
                </html:link>
              </td>
              <td height="24" valign="middle"><bean:write name="team" property="round1_wins" /></td>
            </logic:greaterThan>
          </tr>
        </logic:iterate>
      </table>
    </td>
    <td>
      <table class="round">
        <logic:iterate id="team" name="round2">
          <tr>
            <logic:equal name="team" property="playoff_rank" value="0">
              <td height="50" valign="middle"></td>
              <td height="50" valign="middle"></td>
            </logic:equal>
            <logic:greaterThan name="team" property="playoff_rank" value="0">
              <td id="rank" align="right" valign="middle" height="50">
                <bean:write name="team" property="division_rank" />
              </td>
              <td height="50" valign="middle">
                <html:link page="/Team.do" paramId="team_id" paramName="team" paramProperty="team_id">
                  <bean:write name="team" property="abbrev"      />
                </html:link>
              </td>
              <td height="50" valign="middle"><bean:write name="team" property="round2_wins" /></td>
            </logic:greaterThan>
          </tr>
        </logic:iterate>
      </table>
    </td>
    <td>
      <table class="round">
        <logic:iterate id="team" name="round3">
          <tr>
            <logic:equal name="team" property="playoff_rank" value="0">
              <td height="102" valign="middle"></td>
              <td height="102" valign="middle"></td>
            </logic:equal>
            <logic:greaterThan name="team" property="playoff_rank" value="0">
              <td id="rank" align="right" valign="middle" height="102">
                <bean:write name="team" property="division_rank" />
              </td>
              <td height="102" valign="middle">
                <html:link page="/Team.do" paramId="team_id" paramName="team" paramProperty="team_id">
                  <bean:write name="team" property="abbrev"      />
                </html:link>
              </td>
              <td height="102" valign="middle"><bean:write name="team" property="round3_wins" /></td>
            </logic:greaterThan>
          </tr>
        </logic:iterate>
      </table>
    </td>
    <td>
      <table class="round">
        <logic:iterate id="team" name="round4">
          <tr>
            <logic:equal name="team" property="playoff_rank" value="0">
              <td height="206" valign="middle"></td>
              <td height="206" valign="middle"></td>
            </logic:equal>
            <logic:greaterThan name="team" property="playoff_rank" value="0">
              <td id="rank" align="right" valign="middle" height="206">
                <bean:write name="team" property="division_rank" />
              </td>
              <td height="206" valign="middle">
                <html:link page="/Team.do" paramId="team_id" paramName="team" paramProperty="team_id">
                  <bean:write name="team" property="abbrev"      />
                </html:link>
              </td>
              <td height="206" valign="middle"></td>
            </logic:greaterThan>
          </tr>
        </logic:iterate>
      </table>
    </td>
    <td>
      <table class="round">
        <logic:iterate id="team" name="round5">
          <tr>
            <logic:equal name="team" property="playoff_rank" value="0">
              <td height="414" valign="middle"></td>
              <td height="414" valign="middle"></td>
            </logic:equal>
            <logic:greaterThan name="team" property="playoff_rank" value="0">
              <td id="rank" align="right" valign="middle" height="414">
                <bean:write name="team" property="division_rank" />
              </td>
              <td height="414" valign="middle">
                <html:link page="/Team.do" paramId="team_id" paramName="team" paramProperty="team_id">
                  <bean:write name="team" property="abbrev"      />
                </html:link>
              </td>
              <td height="414" valign="middle"></td>
            </logic:greaterThan>
          </tr>
        </logic:iterate>
      </table>
    </td>
    </tr>
  </table>
</logic:present>

<logic:present name="games">
  <logic:iterate id="game" name="games">
    <table class="game" id='g<bean:write name="game" property="game_id" />'>
      <tr>
        <td>
          <html:link page="/Team.do" paramId="team_id" paramName="game" paramProperty="road_team_id">
            <bean:write name="game" property="road_team"/>
          </html:link>
        </td>
        <td>
          <html:link page="/Watch.do" paramId="game_id" paramName="game" paramProperty="game_id">
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
          <html:link page="/Watch.do" paramId="game_id" paramName="game" paramProperty="game_id">
            <logic:equal name="game" property="home_win" value="true">
              <em><bean:write name="game" property="home_score"/></em>
            </logic:equal>
            <logic:equal name="game" property="home_win" value="false">
              <bean:write name="game" property="home_score"/>
            </logic:equal>
          </html:link>
          <h7>
            <logic:equal name="game" property="home_win" value="true">
              <logic:equal name="game" property="overtime" value="true">
                <bean:message key="games.label.final_indicator"/><bean:message key="games.label.ot_indicator"/>
              </logic:equal>
              <logic:equal name="game" property="overtime" value="false">
                <bean:message key="games.label.final_indicator"/>
              </logic:equal>
            </logic:equal>
            <logic:equal name="game" property="road_win" value="true">
              <logic:equal name="game" property="overtime" value="true">
                <bean:message key="games.label.final_indicator"/><bean:message key="games.label.ot_indicator"/>
              </logic:equal>
              <logic:equal name="game" property="overtime" value="false">
                <bean:message key="games.label.final_indicator"/>
              </logic:equal>
            </logic:equal>
            <logic:equal name="game" property="home_win" value="false">
              <logic:equal name="game" property="road_win" value="false">
                <logic:equal name="game" property="overtime" value="true">
                  <bean:message key="games.label.ot_indicator"/>
                </logic:equal>
              </logic:equal>
            </logic:equal>
            <logic:equal name="game" property="started" value="false">
              <bean:write name="game" property="startTimeDsp"/>
            </logic:equal>
            <logic:notEqual name="game" property="period" value="0">
              <bean:write name="game" property="period"/>
            </logic:notEqual>
          </h7>
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

<logic:present name="preseasonTeamGames">
  <h1><bean:message key="games.label.preseason"/></h1>
  <logic:iterate id="game" name="preseasonTeamGames">
    <table class="gameday">
      <tr class="gamedate"><td><bean:write name="game" property="dateDsp"/></td></tr>
      <tr>
        <td>
    <table class="teamgame">
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
        </td>
      </tr>
    </table>
  </logic:iterate>
</logic:present>

<logic:present name="seasonTeamGames">
  <h1><bean:message key="games.label.regseason"/></h1>
  <logic:iterate id="game" name="seasonTeamGames">
    <table class="gameday">
      <tr class="gamedate"><td><bean:write name="game" property="dateDsp"/></td></tr>
      <tr>
        <td>
    <table class="teamgame">
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
        </td>
      </tr>
    </table>
  </logic:iterate>
</logic:present>

<logic:present name="postseasonTeamGames">
  <h1><bean:message key="games.label.postseason"/></h1>
  <logic:iterate id="game" name="postseasonTeamGames">
    <table class="gameday">
      <tr class="gamedate"><td><bean:write name="game" property="dateDsp"/></td></tr>
      <tr>
        <td>
    <table class="teamgame">
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
        </td>
      </tr>
    </table>
  </logic:iterate>
</logic:present>

<logic:present name="preseasonPlayerGames">
<table class="boxscores">
  <tr class="label">
    <td colspan="19"><bean:message key="player.label.preseason"/></td>
  </tr>
  <tr class="heading">
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.date"      /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.opponent"  /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.time"      /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.points"    /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.attempts"  /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.goals"     /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.eff"       /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.assists"   /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.turnovers" /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.stops"     /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.steals"    /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.penalties" /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.off_pen"   /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.psa"       /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.psm"       /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.eff"       /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.ot_psa"    /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.ot_psm"    /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.eff"       /></a></td>
  </tr>
    <tbody id="prePlayerGames">
    <logic:iterate id="playerGameView" name="preseasonPlayerGames">
      <tr>
        <td>
          <html:link page="/Game.do" paramId="game_id" paramName="playerGameView" paramProperty="game_id">
            <bean:write name="playerGameView" property="datestamp" />
          </html:link>
        </td>
        <td>
          <logic:equal name="playerGameView" property="road" value="true">
            <h7><bean:message key="player.label.at" /></h7>
          </logic:equal>
          <html:link page="/Team.do" paramId="team_id" paramName="playerGameView" paramProperty="opponent">
            <bean:write name="playerGameView" property="opponent_abbrev" />
          </html:link>
        </td>
        <td>
          <bean:write name="playerGameView" property="gameTimeDsp"          />
          <logic:equal name="playerGameView" property="started" value="true">
            <h7><bean:message key="player.label.started" /></h7>
          </logic:equal>
          <logic:equal name="playerGameView" property="injured" value="true">
            <h7><bean:message key="player.label.injured" /></h7>
          </logic:equal>
        </td>
        <td><bean:write name="playerGameView" property="points"               /></td>
        <td><bean:write name="playerGameView" property="attempts"             /></td>
        <td><bean:write name="playerGameView" property="goals"                /></td>
        <td><bean:write name="playerGameView" property="scoringEfficiencyDsp" /></td>
        <td><bean:write name="playerGameView" property="assists"              /></td>
        <td><bean:write name="playerGameView" property="turnovers"            /></td>
        <td><bean:write name="playerGameView" property="stops"                /></td>
        <td><bean:write name="playerGameView" property="steals"               /></td>
        <td><bean:write name="playerGameView" property="penalties"            /></td>
        <td><bean:write name="playerGameView" property="offensive_penalties"  /></td>
        <td><bean:write name="playerGameView" property="psa"                  /></td>
        <td><bean:write name="playerGameView" property="psm"                  /></td>
        <td><bean:write name="playerGameView" property="psEfficiencyDsp"      /></td>
        <td><bean:write name="playerGameView" property="ot_psa"               /></td>
        <td><bean:write name="playerGameView" property="ot_psm"               /></td>
        <td><bean:write name="playerGameView" property="otPsEfficiencyDsp"    /></td>
      </tr>
    </logic:iterate>
    </tbody>
</table>
</logic:present>

<logic:present name="seasonPlayerGames">
<table class="boxscores">
  <tr class="label">
    <td colspan="19"><bean:message key="player.label.regseason"/></td>
  </tr>
  <tr class="heading">
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.date"      /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.opponent"  /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.time"      /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.points"    /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.attempts"  /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.goals"     /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.eff"       /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.assists"   /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.turnovers" /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.stops"     /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.steals"    /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.penalties" /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.off_pen"   /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.psa"       /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.psm"       /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.eff"       /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.ot_psa"    /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.ot_psm"    /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.eff"       /></a></td>
  </tr>
    <tbody id="regPlayerGames">
    <logic:iterate id="playerGameView" name="seasonPlayerGames">
      <tr>
        <td>
          <html:link page="/Game.do" paramId="game_id" paramName="playerGameView" paramProperty="game_id">
            <bean:write name="playerGameView" property="datestamp" />
          </html:link>
        </td>
        <td>
          <logic:equal name="playerGameView" property="road" value="true">
            <h7><bean:message key="player.label.at" /></h7>
          </logic:equal>
          <html:link page="/Team.do" paramId="team_id" paramName="playerGameView" paramProperty="opponent">
            <bean:write name="playerGameView" property="opponent_abbrev" />
          </html:link>
        </td>
        <td>
          <bean:write name="playerGameView" property="gameTimeDsp"          />
          <logic:equal name="playerGameView" property="started" value="true">
            <h7><bean:message key="player.label.started" /></h7>
          </logic:equal>
          <logic:equal name="playerGameView" property="injured" value="true">
            <h7><bean:message key="player.label.injured" /></h7>
          </logic:equal>
        </td>
        <td><bean:write name="playerGameView" property="points"               /></td>
        <td><bean:write name="playerGameView" property="attempts"             /></td>
        <td><bean:write name="playerGameView" property="goals"                /></td>
        <td><bean:write name="playerGameView" property="scoringEfficiencyDsp" /></td>
        <td><bean:write name="playerGameView" property="assists"              /></td>
        <td><bean:write name="playerGameView" property="turnovers"            /></td>
        <td><bean:write name="playerGameView" property="stops"                /></td>
        <td><bean:write name="playerGameView" property="steals"               /></td>
        <td><bean:write name="playerGameView" property="penalties"            /></td>
        <td><bean:write name="playerGameView" property="offensive_penalties"  /></td>
        <td><bean:write name="playerGameView" property="psa"                  /></td>
        <td><bean:write name="playerGameView" property="psm"                  /></td>
        <td><bean:write name="playerGameView" property="psEfficiencyDsp"      /></td>
        <td><bean:write name="playerGameView" property="ot_psa"               /></td>
        <td><bean:write name="playerGameView" property="ot_psm"               /></td>
        <td><bean:write name="playerGameView" property="otPsEfficiencyDsp"    /></td>
      </tr>
    </logic:iterate>
    </tbody>
</table>
</logic:present>

<logic:present name="postseasonPlayerGames">
<table class="boxscores">
  <tr class="label">
    <td colspan="19"><bean:message key="player.label.postseason"/></td>
  </tr>
  <tr class="heading">
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.date"      /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.opponent"  /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.time"      /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.points"    /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.attempts"  /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.goals"     /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.eff"       /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.assists"   /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.turnovers" /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.stops"     /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.steals"    /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.penalties" /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.off_pen"   /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.psa"       /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.psm"       /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.eff"       /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.ot_psa"    /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.ot_psm"    /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.eff"       /></a></td>
  </tr>
    <tbody id="postPlayerGames">
    <logic:iterate id="playerGameView" name="postseasonPlayerGames">
      <tr>
        <td>
          <html:link page="/Game.do" paramId="game_id" paramName="playerGameView" paramProperty="game_id">
            <bean:write name="playerGameView" property="datestamp" />
          </html:link>
        </td>
        <td>
          <logic:equal name="playerGameView" property="road" value="true">
            <h7><bean:message key="player.label.at" /></h7>
          </logic:equal>
          <html:link page="/Team.do" paramId="team_id" paramName="playerGameView" paramProperty="opponent">
            <bean:write name="playerGameView" property="opponent_abbrev" />
          </html:link>
        </td>
        <td>
          <bean:write name="playerGameView" property="gameTimeDsp"          />
          <logic:equal name="playerGameView" property="started" value="true">
            <h7><bean:message key="player.label.started" /></h7>
          </logic:equal>
          <logic:equal name="playerGameView" property="injured" value="true">
            <h7><bean:message key="player.label.injured" /></h7>
          </logic:equal>
        </td>
        <td><bean:write name="playerGameView" property="points"               /></td>
        <td><bean:write name="playerGameView" property="attempts"             /></td>
        <td><bean:write name="playerGameView" property="goals"                /></td>
        <td><bean:write name="playerGameView" property="scoringEfficiencyDsp" /></td>
        <td><bean:write name="playerGameView" property="assists"              /></td>
        <td><bean:write name="playerGameView" property="turnovers"            /></td>
        <td><bean:write name="playerGameView" property="stops"                /></td>
        <td><bean:write name="playerGameView" property="steals"               /></td>
        <td><bean:write name="playerGameView" property="penalties"            /></td>
        <td><bean:write name="playerGameView" property="offensive_penalties"  /></td>
        <td><bean:write name="playerGameView" property="psa"                  /></td>
        <td><bean:write name="playerGameView" property="psm"                  /></td>
        <td><bean:write name="playerGameView" property="psEfficiencyDsp"      /></td>
        <td><bean:write name="playerGameView" property="ot_psa"               /></td>
        <td><bean:write name="playerGameView" property="ot_psm"               /></td>
        <td><bean:write name="playerGameView" property="otPsEfficiencyDsp"    /></td>
      </tr>
    </logic:iterate>
    </tbody>
</table>
</logic:present>

<logic:present name="allstarPlayerGames">
<table class="boxscores">
  <tr class="label">
    <td colspan="19"><bean:message key="player.label.allstar"/></td>
  </tr>
  <tr class="heading">
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.date"      /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.opponent"  /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.time"      /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.points"    /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.attempts"  /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.goals"     /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.eff"       /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.assists"   /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.turnovers" /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.stops"     /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.steals"    /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.penalties" /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.off_pen"   /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.psa"       /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.psm"       /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.eff"       /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.ot_psa"    /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.ot_psm"    /></a></td>
    <td><a href="" onclick="return sortTable( null, this )"><bean:message key="player.label.eff"       /></a></td>
  </tr>
    <tbody id="asPlayerGames">
    <logic:iterate id="playerGameView" name="allstarPlayerGames">
      <tr>
        <td>
          <html:link page="/Game.do" paramId="game_id" paramName="playerGameView" paramProperty="game_id">
            <bean:write name="playerGameView" property="datestamp" />
          </html:link>
        </td>
        <td>
          <logic:equal name="playerGameView" property="road" value="true">
            <h7><bean:message key="player.label.at" /></h7>
          </logic:equal>
          <html:link page="/Team.do" paramId="team_id" paramName="playerGameView" paramProperty="opponent">
            <bean:write name="playerGameView" property="opponent_abbrev" />
          </html:link>
        </td>
        <td>
          <bean:write name="playerGameView" property="gameTimeDsp"          />
          <logic:equal name="playerGameView" property="started" value="true">
            <h7><bean:message key="player.label.started" /></h7>
          </logic:equal>
          <logic:equal name="playerGameView" property="injured" value="true">
            <h7><bean:message key="player.label.injured" /></h7>
          </logic:equal>
        </td>
        <td><bean:write name="playerGameView" property="points"               /></td>
        <td><bean:write name="playerGameView" property="attempts"             /></td>
        <td><bean:write name="playerGameView" property="goals"                /></td>
        <td><bean:write name="playerGameView" property="scoringEfficiencyDsp" /></td>
        <td><bean:write name="playerGameView" property="assists"              /></td>
        <td><bean:write name="playerGameView" property="turnovers"            /></td>
        <td><bean:write name="playerGameView" property="stops"                /></td>
        <td><bean:write name="playerGameView" property="steals"               /></td>
        <td><bean:write name="playerGameView" property="penalties"            /></td>
        <td><bean:write name="playerGameView" property="offensive_penalties"  /></td>
        <td><bean:write name="playerGameView" property="psa"                  /></td>
        <td><bean:write name="playerGameView" property="psm"                  /></td>
        <td><bean:write name="playerGameView" property="psEfficiencyDsp"      /></td>
        <td><bean:write name="playerGameView" property="ot_psa"               /></td>
        <td><bean:write name="playerGameView" property="ot_psm"               /></td>
        <td><bean:write name="playerGameView" property="otPsEfficiencyDsp"    /></td>
      </tr>
    </logic:iterate>
    </tbody>
</table>
</logic:present>

</body>
</html:html>
  