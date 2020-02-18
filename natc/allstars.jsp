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
  
</head>
<body>

<jsp:include page="menu.jsp" />

<h1>ALL STARS</h1>
<html:errors/>

<!--
<html:form method="POST" action="/Games">

<html:select property="operation">
  <html:option key = "games.option.next"   value="0" />

</html:select>

<html:submit><bean:message key="games.label.next"/></html:submit>

</html:form>
-->

<logic:present name="schedule">
  <h2><bean:write name="schedule" property="scheduled"/></h2>
</logic:present>

<logic:present name="div0stars">
  <table class="allstars">
    <tr class="label">
	  <td colspan="8"><bean:message key="division.greene"/></td>
	</tr>
	<tr class="heading">
      <td><a href="" onclick="return sortTable( 'div0List', this )"><bean:message key="awards.label.player"  /></a></td>
      <td><a href="" onclick="return sortTable( 'div0List', this )"><bean:message key="awards.label.team"    /></a></td>
      <td><a href="" onclick="return sortTable( 'div0List', this )"><bean:message key="awards.label.points"  /></a></td>
      <td><a href="" onclick="return sortTable( 'div0List', this )"><bean:message key="awards.label.goals"   /></a></td>
      <td><a href="" onclick="return sortTable( 'div0List', this )"><bean:message key="awards.label.assists" /></a></td>
      <td><a href="" onclick="return sortTable( 'div0List', this )"><bean:message key="awards.label.stops"   /></a></td>
      <td><a href="" onclick="return sortTable( 'div0List', this )"><bean:message key="awards.label.steals"  /></a></td>
      <td><a href="" onclick="return sortTable( 'div0List', this )"><bean:message key="awards.label.psm"     /></a></td>
    </tr>
    <tbody id="div0List">
    <logic:iterate id="star" name="div0stars">
      <logic:equal name="star" property="alternate" value="false">
        <tr>
      </logic:equal>
      <logic:equal name="star" property="alternate" value="true">
        <tr class="alternate">
      </logic:equal>
        <td>
          <html:link page="/Player.do" paramId="player_id" paramName="star" paramProperty="player_id">
            <bean:write name="star" property="last_name" />, <bean:write name="star" property="first_name" />
          </html:link>
          <logic:equal name="star" property="rookie" value="true">
            <h7><bean:message key="team.label.rookie"/></h7>
          </logic:equal>
          <logic:equal name="star" property="injured" value="true">
            <h7><bean:message key="team.label.injured"/></h7>
          </logic:equal>
          <logic:equal name="star" property="award" value="1">
            <h7><bean:message key="team.label.silver" /></h7>
          </logic:equal>
          <logic:equal name="star" property="award" value="2">
            <h7><bean:message key="team.label.gold" /></h7>
          </logic:equal>
          <logic:equal name="star" property="award" value="3">
            <h7><bean:message key="team.label.platinum" /></h7>
          </logic:equal>
        </td>
        <td>
          <html:link page="/Team.do" paramId="team_id" paramName="star" paramProperty="team_id">
            <bean:write name="star" property="team_abbrev" />
          </html:link>
        </td>
        <td><bean:write name="star" property="points"  /></td>
        <td><bean:write name="star" property="goals"   /></td>
        <td><bean:write name="star" property="assists" /></td>
        <td><bean:write name="star" property="stops"   /></td>
        <td><bean:write name="star" property="steals"  /></td>
        <td><bean:write name="star" property="psm"     /></td>
      </tr>
    </logic:iterate>
    </tbody>
    <logic:present name="manager0">
      <tr class="heading">
        <td><bean:message key="awards.label.manager"    /></td>
        <td><bean:message key="awards.label.team"       /></td>
        <td><bean:message key="awards.label.record"     /></td>
        <td><bean:message key="awards.label.points"     /></td>
        <td colspan="2"><bean:message key="awards.label.efficiency" /></td>
        <td colspan="2"><bean:message key="awards.label.ps_eff"     /></td>
      </tr>
      <tr>
        <td>
          <html:link page="/Manager.do" paramId="manager_id" paramName="manager0" paramProperty="manager_id">
            <bean:write name="manager0" property="last_name" />, <bean:write name="manager0" property="first_name" />
          </html:link>
          <logic:equal name="manager0" property="award" value="1">
            <h7><bean:message key="manager.label.moty" /></h7>
          </logic:equal>
        </td>
        <td>
          <html:link page="/Team.do" paramId="team_id" paramName="manager0" paramProperty="team_id">
            <bean:write name="manager0" property="team_abbrev" />
          </html:link>
        </td>
        <td><bean:write name="manager0" property="recordDsp"            /></td>
        <td><bean:write name="manager0" property="points"               /></td>
        <td colspan="2"><bean:write name="manager0" property="scoringEfficiencyDsp" /></td>
        <td colspan="2"><bean:write name="manager0" property="psEfficiencyDsp"      /></td>
      </tr>
    </logic:present>
    <tr class="separator"></tr>
    <tr class="label">
      <td colspan="8"><bean:message key="division.davis"/></td>
    </tr>
    <tr class="heading">
      <td><a href="" onclick="return sortTable( 'div1List', this )"><bean:message key="awards.label.player"  /></a></td>
      <td><a href="" onclick="return sortTable( 'div1List', this )"><bean:message key="awards.label.team"    /></a></td>
      <td><a href="" onclick="return sortTable( 'div1List', this )"><bean:message key="awards.label.points"  /></a></td>
      <td><a href="" onclick="return sortTable( 'div1List', this )"><bean:message key="awards.label.goals"   /></a></td>
      <td><a href="" onclick="return sortTable( 'div1List', this )"><bean:message key="awards.label.assists" /></a></td>
      <td><a href="" onclick="return sortTable( 'div1List', this )"><bean:message key="awards.label.stops"   /></a></td>
      <td><a href="" onclick="return sortTable( 'div1List', this )"><bean:message key="awards.label.steals"  /></a></td>
      <td><a href="" onclick="return sortTable( 'div1List', this )"><bean:message key="awards.label.psm"     /></a></td>
    </tr>
    <tbody id="div1List">
    <logic:iterate id="star" name="div1stars">
      <logic:equal name="star" property="alternate" value="false">
        <tr>
      </logic:equal>
      <logic:equal name="star" property="alternate" value="true">
        <tr class="alternate">
      </logic:equal>
        <td>
          <html:link page="/Player.do" paramId="player_id" paramName="star" paramProperty="player_id">
            <bean:write name="star" property="last_name" />, <bean:write name="star" property="first_name" />
          </html:link>
          <logic:equal name="star" property="rookie" value="true">
            <h7><bean:message key="team.label.rookie"/></h7>
          </logic:equal>
          <logic:equal name="star" property="injured" value="true">
            <h7><bean:message key="team.label.injured"/></h7>
          </logic:equal>
          <logic:equal name="star" property="award" value="1">
            <h7><bean:message key="team.label.silver" /></h7>
          </logic:equal>
          <logic:equal name="star" property="award" value="2">
            <h7><bean:message key="team.label.gold" /></h7>
          </logic:equal>
          <logic:equal name="star" property="award" value="3">
            <h7><bean:message key="team.label.platinum" /></h7>
          </logic:equal>
        </td>
        <td>
          <html:link page="/Team.do" paramId="team_id" paramName="star" paramProperty="team_id">
            <bean:write name="star" property="team_abbrev" />
          </html:link>
        </td>
        <td><bean:write name="star" property="points"  /></td>
        <td><bean:write name="star" property="goals"   /></td>
        <td><bean:write name="star" property="assists" /></td>
        <td><bean:write name="star" property="stops"   /></td>
        <td><bean:write name="star" property="steals"  /></td>
        <td><bean:write name="star" property="psm"     /></td>
      </tr>
    </logic:iterate>
    </tbody>
    <logic:present name="manager1">
      <tr class="heading">
        <td><bean:message key="awards.label.manager"    /></td>
        <td><bean:message key="awards.label.team"       /></td>
        <td><bean:message key="awards.label.record"     /></td>
        <td><bean:message key="awards.label.points"     /></td>
        <td colspan="2"><bean:message key="awards.label.efficiency" /></td>
        <td colspan="2"><bean:message key="awards.label.ps_eff"     /></td>
      </tr>
      <tr>
        <td>
          <html:link page="/Manager.do" paramId="manager_id" paramName="manager1" paramProperty="manager_id">
            <bean:write name="manager1" property="last_name" />, <bean:write name="manager1" property="first_name" />
          </html:link>
          <logic:equal name="manager1" property="award" value="1">
            <h7><bean:message key="manager.label.moty" /></h7>
          </logic:equal>
        </td>
        <td>
          <html:link page="/Team.do" paramId="team_id" paramName="manager1" paramProperty="team_id">
            <bean:write name="manager1" property="team_abbrev" />
          </html:link>
        </td>
        <td><bean:write name="manager1" property="recordDsp"            /></td>
        <td><bean:write name="manager1" property="points"               /></td>
        <td colspan="2"><bean:write name="manager1" property="scoringEfficiencyDsp" /></td>
        <td colspan="2"><bean:write name="manager1" property="psEfficiencyDsp"      /></td>
      </tr>
    </logic:present>
    <tr class="separator"></tr>
    <tr class="label">
      <td colspan="8"><bean:message key="division.smith"/></td>
    </tr>
    <tr class="heading">
      <td><a href="" onclick="return sortTable( 'div2List', this )"><bean:message key="awards.label.player"  /></a></td>
      <td><a href="" onclick="return sortTable( 'div2List', this )"><bean:message key="awards.label.team"    /></a></td>
      <td><a href="" onclick="return sortTable( 'div2List', this )"><bean:message key="awards.label.points"  /></a></td>
      <td><a href="" onclick="return sortTable( 'div2List', this )"><bean:message key="awards.label.goals"   /></a></td>
      <td><a href="" onclick="return sortTable( 'div2List', this )"><bean:message key="awards.label.assists" /></a></td>
      <td><a href="" onclick="return sortTable( 'div2List', this )"><bean:message key="awards.label.stops"   /></a></td>
      <td><a href="" onclick="return sortTable( 'div2List', this )"><bean:message key="awards.label.steals"  /></a></td>
      <td><a href="" onclick="return sortTable( 'div2List', this )"><bean:message key="awards.label.psm"     /></a></td>
    </tr>
    <tbody id="div2List">
    <logic:iterate id="star" name="div2stars">
      <logic:equal name="star" property="alternate" value="false">
        <tr>
      </logic:equal>
      <logic:equal name="star" property="alternate" value="true">
        <tr class="alternate">
      </logic:equal>
        <td>
          <html:link page="/Player.do" paramId="player_id" paramName="star" paramProperty="player_id">
            <bean:write name="star" property="last_name" />, <bean:write name="star" property="first_name" />
          </html:link>
          <logic:equal name="star" property="rookie" value="true">
            <h7><bean:message key="team.label.rookie"/></h7>
          </logic:equal>
          <logic:equal name="star" property="injured" value="true">
            <h7><bean:message key="team.label.injured"/></h7>
          </logic:equal>
          <logic:equal name="star" property="award" value="1">
            <h7><bean:message key="team.label.silver" /></h7>
          </logic:equal>
          <logic:equal name="star" property="award" value="2">
            <h7><bean:message key="team.label.gold" /></h7>
          </logic:equal>
          <logic:equal name="star" property="award" value="3">
            <h7><bean:message key="team.label.platinum" /></h7>
          </logic:equal>
        </td>
        <td>
          <html:link page="/Team.do" paramId="team_id" paramName="star" paramProperty="team_id">
            <bean:write name="star" property="team_abbrev" />
          </html:link>
        </td>
        <td><bean:write name="star" property="points"  /></td>
        <td><bean:write name="star" property="goals"   /></td>
        <td><bean:write name="star" property="assists" /></td>
        <td><bean:write name="star" property="stops"   /></td>
        <td><bean:write name="star" property="steals"  /></td>
        <td><bean:write name="star" property="psm"     /></td>
      </tr>
    </logic:iterate>
    </tbody>
    <logic:present name="manager2">
      <tr class="heading">
        <td><bean:message key="awards.label.manager"    /></td>
        <td><bean:message key="awards.label.team"       /></td>
        <td><bean:message key="awards.label.record"     /></td>
        <td><bean:message key="awards.label.points"     /></td>
        <td colspan="2"><bean:message key="awards.label.efficiency" /></td>
        <td colspan="2"><bean:message key="awards.label.ps_eff"     /></td>
      </tr>
      <tr>
        <td>
          <html:link page="/Manager.do" paramId="manager_id" paramName="manager2" paramProperty="manager_id">
            <bean:write name="manager2" property="last_name" />, <bean:write name="manager2" property="first_name" />
          </html:link>
          <logic:equal name="manager2" property="award" value="1">
            <h7><bean:message key="manager.label.moty" /></h7>
          </logic:equal>
        </td>
        <td>
          <html:link page="/Team.do" paramId="team_id" paramName="manager2" paramProperty="team_id">
            <bean:write name="manager2" property="team_abbrev" />
          </html:link>
        </td>
        <td><bean:write name="manager2" property="recordDsp"            /></td>
        <td><bean:write name="manager2" property="points"               /></td>
        <td colspan="2"><bean:write name="manager2" property="scoringEfficiencyDsp" /></td>
        <td colspan="2"><bean:write name="manager2" property="psEfficiencyDsp"      /></td>
      </tr>
    </logic:present>
    <tr class="separator"></tr>
    <tr class="label">
      <td colspan="8"><bean:message key="division.lawrence"/></td>
    </tr>
    <tr class="heading">
      <td><a href="" onclick="return sortTable( 'div3List', this )"><bean:message key="awards.label.player"  /></a></td>
      <td><a href="" onclick="return sortTable( 'div3List', this )"><bean:message key="awards.label.team"    /></a></td>
      <td><a href="" onclick="return sortTable( 'div3List', this )"><bean:message key="awards.label.points"  /></a></td>
      <td><a href="" onclick="return sortTable( 'div3List', this )"><bean:message key="awards.label.goals"   /></a></td>
      <td><a href="" onclick="return sortTable( 'div3List', this )"><bean:message key="awards.label.assists" /></a></td>
      <td><a href="" onclick="return sortTable( 'div3List', this )"><bean:message key="awards.label.stops"   /></a></td>
      <td><a href="" onclick="return sortTable( 'div3List', this )"><bean:message key="awards.label.steals"  /></a></td>
      <td><a href="" onclick="return sortTable( 'div3List', this )"><bean:message key="awards.label.psm"     /></a></td>
    </tr>
    <tbody id="div3List">
    <logic:iterate id="star" name="div3stars">
      <logic:equal name="star" property="alternate" value="false">
        <tr>
      </logic:equal>
      <logic:equal name="star" property="alternate" value="true">
        <tr class="alternate">
      </logic:equal>
        <td>
          <html:link page="/Player.do" paramId="player_id" paramName="star" paramProperty="player_id">
            <bean:write name="star" property="last_name" />, <bean:write name="star" property="first_name" />
          </html:link>
          <logic:equal name="star" property="rookie" value="true">
            <h7><bean:message key="team.label.rookie"/></h7>
          </logic:equal>
          <logic:equal name="star" property="injured" value="true">
            <h7><bean:message key="team.label.injured"/></h7>
          </logic:equal>
          <logic:equal name="star" property="award" value="1">
            <h7><bean:message key="team.label.silver" /></h7>
          </logic:equal>
          <logic:equal name="star" property="award" value="2">
            <h7><bean:message key="team.label.gold" /></h7>
          </logic:equal>
          <logic:equal name="star" property="award" value="3">
            <h7><bean:message key="team.label.platinum" /></h7>
          </logic:equal>
        </td>
        <td>
          <html:link page="/Team.do" paramId="team_id" paramName="star" paramProperty="team_id">
            <bean:write name="star" property="team_abbrev" />
          </html:link>
        </td>
        <td><bean:write name="star" property="points"  /></td>
        <td><bean:write name="star" property="goals"   /></td>
        <td><bean:write name="star" property="assists" /></td>
        <td><bean:write name="star" property="stops"   /></td>
        <td><bean:write name="star" property="steals"  /></td>
        <td><bean:write name="star" property="psm"     /></td>
      </tr>
    </logic:iterate>
    </tbody>
    <logic:present name="manager3">
      <tr class="heading">
        <td><bean:message key="awards.label.manager"    /></td>
        <td><bean:message key="awards.label.team"       /></td>
        <td><bean:message key="awards.label.record"     /></td>
        <td><bean:message key="awards.label.points"     /></td>
        <td colspan="2"><bean:message key="awards.label.efficiency" /></td>
        <td colspan="2"><bean:message key="awards.label.ps_eff"     /></td>
      </tr>
      <tr>
        <td>
          <html:link page="/Manager.do" paramId="manager_id" paramName="manager3" paramProperty="manager_id">
            <bean:write name="manager3" property="last_name" />, <bean:write name="manager3" property="first_name" />
          </html:link>
          <logic:equal name="manager3" property="award" value="1">
            <h7><bean:message key="manager.label.moty" /></h7>
          </logic:equal>
        </td>
        <td>
          <html:link page="/Team.do" paramId="team_id" paramName="manager3" paramProperty="team_id">
            <bean:write name="manager3" property="team_abbrev" />
          </html:link>
        </td>
        <td><bean:write name="manager3" property="recordDsp"            /></td>
        <td><bean:write name="manager3" property="points"               /></td>
        <td colspan="2"><bean:write name="manager3" property="scoringEfficiencyDsp" /></td>
        <td colspan="2"><bean:write name="manager3" property="psEfficiencyDsp"      /></td>
      </tr>
    </logic:present>
  </table>
</logic:present>

</body>
</html:html>
