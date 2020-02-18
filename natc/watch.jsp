<%@ page contentType="text/html; charset=utf-8" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld"   prefix="bean"   %>
<%@ taglib uri="/WEB-INF/struts-html.tld"   prefix="html"   %>
<%@ taglib uri="/WEB-INF/struts-logic.tld"  prefix="logic"  %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>

<html:html locale="true">
<head>
  <title><bean:message key="title.stats"/></title>
  
  <link rel="stylesheet" type="text/css" href="styles/natc_layout.css" media='screen' />
  
  <style type="text/css">
    @import "styles/natc_markup.css";
  </style>
  
  <script type="text/javascript" src="scripts/natc_sorter.js"></script>
  <script type="text/javascript" src="scripts/natc_ajax.js"></script>

</head>
<body>

  <logic:present name="gameId">
    <script type="text/javascript">
      function updatePlayerTotals( tableBody, playerNode ) {

    	  for ( var i = 0; i < tableBody.childNodes.length; ++i ) {

    		  if ( tableBody.childNodes[i].nodeType == 1 ) {

    			  var tableRow   = tableBody.childNodes[i];
    			  var playerName = (playerNode.childNodes[1].textContent + ', ' + playerNode.childNodes[0].textContent)

    			  if ( tableRow.childNodes[1].childNodes[1].innerHTML == playerName ) {

    				  tableRow.childNodes[ 5].innerHTML = playerNode.childNodes[10].textContent;
    				  tableRow.childNodes[ 7].innerHTML = playerNode.childNodes[11].textContent;
    				  tableRow.childNodes[ 9].innerHTML = playerNode.childNodes[12].textContent;
    				  tableRow.childNodes[11].innerHTML = playerNode.childNodes[13].textContent;
    				  tableRow.childNodes[13].innerHTML = playerNode.childNodes[14].textContent;
    				  tableRow.childNodes[15].innerHTML = playerNode.childNodes[15].textContent;
    				  tableRow.childNodes[17].innerHTML = playerNode.childNodes[16].textContent;
    				  tableRow.childNodes[19].innerHTML = playerNode.childNodes[17].textContent;
    				  tableRow.childNodes[21].innerHTML = playerNode.childNodes[18].textContent;
    				  tableRow.childNodes[23].innerHTML = playerNode.childNodes[19].textContent;
    				  tableRow.childNodes[25].innerHTML = playerNode.childNodes[20].textContent;
    				  tableRow.childNodes[27].innerHTML = playerNode.childNodes[21].textContent;
    				  tableRow.childNodes[29].innerHTML = playerNode.childNodes[22].textContent;
    				  tableRow.childNodes[31].innerHTML = playerNode.childNodes[23].textContent;
    				  tableRow.childNodes[33].innerHTML = playerNode.childNodes[24].textContent;
    				  tableRow.childNodes[35].innerHTML = playerNode.childNodes[25].textContent;
    				  tableRow.childNodes[37].innerHTML = playerNode.childNodes[26].textContent;
    			  }
    		  }
    	  }
      }
    
      function updateTeamTotals( tableRow, teamNode ) {

    	  tableRow.childNodes[ 3].innerHTML = teamNode.childNodes[ 4].textContent;
    	  tableRow.childNodes[ 5].innerHTML = teamNode.childNodes[ 5].textContent;
    	  tableRow.childNodes[ 7].innerHTML = teamNode.childNodes[25].textContent;
    	  tableRow.childNodes[ 9].innerHTML = teamNode.childNodes[ 6].textContent;
    	  tableRow.childNodes[11].innerHTML = teamNode.childNodes[ 7].textContent;
    	  tableRow.childNodes[13].innerHTML = teamNode.childNodes[ 8].textContent;
    	  tableRow.childNodes[17].innerHTML = teamNode.childNodes[ 9].textContent;
    	  tableRow.childNodes[21].innerHTML = teamNode.childNodes[10].textContent;
    	  tableRow.childNodes[23].innerHTML = teamNode.childNodes[11].textContent;
    	  tableRow.childNodes[25].innerHTML = teamNode.childNodes[12].textContent;
    	  tableRow.childNodes[27].innerHTML = teamNode.childNodes[13].textContent;
    	  tableRow.childNodes[29].innerHTML = teamNode.childNodes[14].textContent;
    	  tableRow.childNodes[31].innerHTML = teamNode.childNodes[15].textContent;
    	  tableRow.childNodes[33].innerHTML = teamNode.childNodes[16].textContent;
    	  tableRow.childNodes[35].innerHTML = teamNode.childNodes[17].textContent;
    	  tableRow.childNodes[37].innerHTML = teamNode.childNodes[18].textContent;
      }
    
      function updateStats( response ) {

    	  var node;
    	  var teamNode;
    	  var playerNode;
    	  var pageElement;
    	  var i;

    	  // home team
    	  if ( (teamNode = response.getElementsByTagName('homeTeam')[0]) == null ) {

    		  return;
    	  }

    	  if ( (pageElement = document.getElementById('homeScore')) == null ) {

    		  return;
    	  }

    	  pageElement.innerHTML = teamNode.childNodes[25].textContent;
    	  document.getElementById('homeP1Score').innerHTML = teamNode.childNodes[19].textContent;
    	  document.getElementById('homeP2Score').innerHTML = teamNode.childNodes[20].textContent;
    	  document.getElementById('homeP3Score').innerHTML = teamNode.childNodes[21].textContent;
    	  document.getElementById('homeP4Score').innerHTML = teamNode.childNodes[22].textContent;
    	  document.getElementById('homeP5Score').innerHTML = teamNode.childNodes[23].textContent;
    	  document.getElementById('homeOtScore').innerHTML = teamNode.childNodes[24].textContent;

    	  if ( (pageElement = document.getElementById('homeTotals')) == null ) {

    		  return;
    	  }

    	  updateTeamTotals( pageElement, teamNode );

    	  if ( (pageElement = document.getElementById('homePlayers')) == null ) {

    		  return;
    	  }

    	  for ( i = 0; i < teamNode.childNodes[27].childNodes.length; ++i ) {

    		  playerNode = teamNode.childNodes[27].childNodes[i];
    		  
    		  updatePlayerTotals( pageElement, playerNode );
    	  }

    	  resetDirection( 0 );

    	  sortTable( 'homePlayers', document.getElementById('homePtsSort') );
    	  
    	  // road team
    	  if ( (teamNode = response.getElementsByTagName('roadTeam')[0]) == null ) {

    		  return;
    	  }

    	  if ( (pageElement = document.getElementById('roadScore')) == null ) {

    		  return;
    	  }

    	  pageElement.innerHTML = teamNode.childNodes[25].textContent;
    	  document.getElementById('roadP1Score').innerHTML = teamNode.childNodes[19].textContent;
    	  document.getElementById('roadP2Score').innerHTML = teamNode.childNodes[20].textContent;
    	  document.getElementById('roadP3Score').innerHTML = teamNode.childNodes[21].textContent;
    	  document.getElementById('roadP4Score').innerHTML = teamNode.childNodes[22].textContent;
    	  document.getElementById('roadP5Score').innerHTML = teamNode.childNodes[23].textContent;
    	  document.getElementById('roadOtScore').innerHTML = teamNode.childNodes[24].textContent;

    	  if ( (pageElement = document.getElementById('roadTotals')) == null ) {

    		  return;
    	  }

    	  updateTeamTotals( pageElement, teamNode );

    	  if ( (pageElement = document.getElementById('roadPlayers')) == null ) {

    		  return;
    	  }

    	  for ( i = 0; i < teamNode.childNodes[27].childNodes.length; ++i ) {

    		  playerNode = teamNode.childNodes[27].childNodes[i];
    		  
    		  updatePlayerTotals( pageElement, playerNode );
    	  }

    	  resetDirection( 0 );

    	  sortTable( 'roadPlayers', document.getElementById('roadPtsSort') );
      }
    
      setAjaxURL( '/natc/Game.do?game_id=' + '<bean:write name="gameId" property="value" />' + '&ajax=1' );
      setAjaxCallback( updateStats );
    
      window.setInterval( 'sendAjaxRequest()', 15000 );
    </script>
  </logic:present>
  
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

<h1>GAME</h1>

<table class="game_long" id="scoreboard">
  <tr class="heading">
    <td></td>
    <td></td>
    <td><bean:message key="game.label.period1"  /></td>
    <td><bean:message key="game.label.period2"  /></td>
    <td><bean:message key="game.label.period3"  /></td>
    <td><bean:message key="game.label.period4"  /></td>
    <td><bean:message key="game.label.period5"  /></td>
    <td><bean:message key="game.label.overtime" /></td>
    <td><bean:message key="game.label.score"    /></td>
  </tr>
  <logic:present name="roadGame">
    <tr>
      <td id="roadPoss"></td>
      <td>
        <html:link page="/Team.do" paramId="team_id" paramName="roadGame" paramProperty="team_id">
          <bean:write name="roadGame" property="location" />
        </html:link>
      </td>
      <td id="roadP1Score">
        <bean:write name="roadGame" property="period1_score"/>
      </td>
      <td id="roadP2Score">
        <bean:write name="roadGame" property="period2_score"/>
      </td>
      <td id="roadP3Score">
        <bean:write name="roadGame" property="period3_score"/>
      </td>
      <td id="roadP4Score">
        <bean:write name="roadGame" property="period4_score"/>
      </td>
      <td id="roadP5Score">
        <bean:write name="roadGame" property="period5_score"/>
      </td>
      <td id="roadOtScore">
        <bean:write name="roadGame" property="overtime_score"/>
      </td>
      <td id="roadScore">
        <bean:write name="roadGame" property="total_score"/>
      </td>
    </tr>
  </logic:present>
  <logic:present name="homeGame">
    <tr>
      <td id="homePoss"></td>
      <td>
        <html:link page="/Team.do" paramId="team_id" paramName="homeGame" paramProperty="team_id">
          <bean:write name="homeGame" property="location" />
        </html:link>
      </td>
      <td id="homeP1Score">
        <bean:write name="homeGame" property="period1_score"/>
      </td>
      <td id="homeP2Score">
        <bean:write name="homeGame" property="period2_score"/>
      </td>
      <td id="homeP3Score">
        <bean:write name="homeGame" property="period3_score"/>
      </td>
      <td id="homeP4Score">
        <bean:write name="homeGame" property="period4_score"/>
      </td>
      <td id="homeP5Score">
        <bean:write name="homeGame" property="period5_score"/>
      </td>
      <td id="homeOtScore">
        <bean:write name="homeGame" property="overtime_score"/>
      </td>
      <td id="homeScore">
        <bean:write name="homeGame" property="total_score"/>
        <!-- <h7><bean:message key="games.label.ot_indicator"/></h7> -->
      </td>
    </tr>
  </logic:present>
</table>

<table class="boxscores">
  <tr class="heading">
    <td><a href="" onclick="return sortTable( 'roadPlayers', this )"><bean:message key="player.label.name"      /></a></td>
    <td><bean:message key="player.label.poss"      /></td>
    <td><a href="" onclick="return sortTable( 'roadPlayers', this )"><bean:message key="player.label.time"      /></a></td>
    <td><a id="roadPtsSort" href="" onclick="return sortTable( 'roadPlayers', this )"><bean:message key="player.label.points"    /></a></td>
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
    <tr class="totals" id="roadTotals">
      <td>
        <html:link page="/Team.do" paramId="team_id" paramName="roadGame" paramProperty="team_id">
          <bean:write name="roadGame" property="name"                 />
        </html:link>
      </td>
      <td><bean:write name="roadGame" property="possessions"          /></td>
      <td><bean:write name="roadGame" property="gameTimeDsp"          /></td>
      <td><bean:write name="roadGame" property="total_score"          /></td>
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
    <td><a id="homePtsSort" href="" onclick="return sortTable( 'homePlayers', this )"><bean:message key="player.label.points"    /></a></td>
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
    <tr class="totals" id="homeTotals">
      <td>
        <html:link page="/Team.do" paramId="team_id" paramName="homeGame" paramProperty="team_id">
          <bean:write name="homeGame" property="name"                 />
        </html:link>
      </td>
      <td><bean:write name="homeGame" property="possessions"          /></td>
      <td><bean:write name="homeGame" property="gameTimeDsp"          /></td>
      <td><bean:write name="homeGame" property="total_score"          /></td>
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

<div id="ajaxTest"></div>

</body>
</html:html>
