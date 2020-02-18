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

<h1>GAMES</h1>
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

</body>
</html:html>
