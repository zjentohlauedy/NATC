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

<h1>NEWS</h1>
  <table class="freeagency">
    <tr class="label"><td colspan="4">Fired Managers</td></tr>
    <tr class="heading">
      <td>Manager</td>
      <td>Team</td>
      <td>Ratings</td>
      <td>Style</td>
      <td>Seasons</td>
      <td>Win Pct.</td>
    </tr>
      <tr>
        <td>
          <a href="/natc/Manager.do?manager_id=66">Aquino, Jack</a>
        </td>
        <td>
          <a href="/natc/Team.do?team_id=8">OAK.</a>
        </td>
        <td><img src='barchart.jsp?background=000000&foreground=ff0000&width=50&height=16&value1=.25&value2=.75&value3=.33&value4=.6'></td>
        <td>Balanced</td>
        <td>3</td>
        <td>.490</td>
      </tr>
      <tr>
        <td>
          <a href="/natc/Manager.do?manager_id=58">Briones, Allen</a>
        </td>
        <td>
          <a href="/natc/Team.do?team_id=36">MIL.</a>
        </td>
        <td><img src='barchart.jsp?background=000000&foreground=ff0000&width=50&height=16&value1=.25&value2=.5&value3=.75&value4=1'></td>
        <td>Balanced</td>
        <td>3</td>
        <td>.393</td>
      </tr>
      <tr>
        <td>
          <a href="/natc/Manager.do?manager_id=76">Bryant, Carl</a>
        </td>
        <td>
          <a href="/natc/Team.do?team_id=30">MEX.</a>
        </td>
        <td><img src='barchart.jsp?background=000000&foreground=ff0000&width=50&height=16&value1=.2&value2=.9&value3=.1&value4=.5'></td>
        <td>Balanced</td>
        <td>3</td>
        <td>.407</td>
      </tr>
      <tr>
        <td>
          <a href="/natc/Manager.do?manager_id=26">Callender, Brian</a>
        </td>
        <td>
          <a href="/natc/Team.do?team_id=9">VAN.</a>
        </td>
        <td><img src='barchart.jsp?background=000000&foreground=ff0000&width=50&height=16&value1=.25&value2=.75&value3=.33&value4=.6'></td>
        <td>Balanced</td>
        <td>3</td>
        <td>.453</td>
      </tr>
      <tr>
        <td>
          <a href="/natc/Manager.do?manager_id=6">Johnson, Michael</a>
        </td>
        <td>
          <a href="/natc/Team.do?team_id=3">K.C.</a>
        </td>
        <td><img src='barchart.jsp?background=000000&foreground=ff0000&width=50&height=16&value1=.25&value2=.75&value3=.33&value4=.6'></td>
        <td>Balanced</td>
        <td>3</td>
        <td>.353</td>
      </tr>
      <tr>
        <td>
          <a href="/natc/Manager.do?manager_id=53">Morales, Edward</a>
        </td>
        <td>
          <a href="/natc/Team.do?team_id=15">DET.</a>
        </td>
        <td><img src='barchart.jsp?background=000000&foreground=ff0000&width=50&height=16&value1=.25&value2=.75&value3=.33&value4=.6'></td>
        <td>Balanced</td>
        <td>3</td>
        <td>.483</td>
      </tr>
      <tr>
        <td>
          <a href="/natc/Manager.do?manager_id=9">Pearson, John</a>
        </td>
        <td>
          <a href="/natc/Team.do?team_id=17">BAL.</a>
        </td>
        <td><img src='barchart.jsp?background=000000&foreground=ff0000&width=50&height=16&value1=.25&value2=.75&value3=.33&value4=.6'></td>
        <td>Balanced</td>
        <td>3</td>
        <td>.487</td>
      </tr>
      <tr>
        <td>
          <a href="/natc/Manager.do?manager_id=62">Rios, Charles</a>
        </td>
        <td>
          <a href="/natc/Team.do?team_id=25">CHI.</a>
        </td>
        <td><img src='barchart.jsp?background=000000&foreground=ff0000&width=50&height=16&value1=.25&value2=.75&value3=.33&value4=.6'></td>
        <td>Balanced</td>
        <td>3</td>
        <td>.460</td>
      </tr>
      <tr>
        <td>
          <a href="/natc/Manager.do?manager_id=78">Wolle, Eduardo</a>
        </td>
        <td>
          <a href="/natc/Team.do?team_id=24">N.Y.</a>
        </td>
        <td><img src='barchart.jsp?background=000000&foreground=ff0000&width=50&height=16&value1=.25&value2=.75&value3=.33&value4=.6'></td>
        <td>Balanced</td>
        <td>3</td>
        <td>.480</td>
      </tr>
      <tr>
        <td>
          <a href="/natc/Manager.do?manager_id=68">Woods, Eric</a>
        </td>
        <td>
          <a href="/natc/Team.do?team_id=32">CLE.</a>
        </td>
        <td><img src='barchart.jsp?background=000000&foreground=ff0000&width=50&height=16&value1=.25&value2=.75&value3=.33&value4=.6'></td>
        <td>Balanced</td>
        <td>3</td>
        <td>.427</td>
      </tr>
  </table>


</body>
</html:html>
