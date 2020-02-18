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

<h1>MANAGER CHANGES</h1>
<html:errors/>

<html:form method="POST" action="/Games">

<html:select property="operation">
  <html:option key = "games.option.next"   value="0" />

</html:select>

<html:submit><bean:message key="games.label.next"/></html:submit>

</html:form>

<logic:present name="hired_managers">
  <table class="freeagency">
    <logic:present name="retired_managers">
      <tr class="label"><td colspan="6"><bean:message key="ofs.label.retired"  /></td></tr>
      <tr class="heading">
        <td><bean:message key="ofs.label.manager" /></td>
        <td><bean:message key="ofs.label.team"    /></td>
        <td><bean:message key="ofs.label.ratings" /></td>
        <td><bean:message key="ofs.label.style"   /></td>
        <td><bean:message key="ofs.label.seasons" /></td>
        <td><bean:message key="ofs.label.win_pct" /></td>
      </tr>
      <logic:iterate id="manager" name="retired_managers">
        <tr>
          <td>
            <html:link page="/Manager.do" paramId="manager_id" paramName="manager" paramProperty="manager_id">
              <bean:write name="manager" property="last_name"  />, <bean:write name="manager" property="first_name" />
            </html:link>
          </td>
          <td>
            <logic:equal name="manager" property="team_id" value="0">
              <bean:message key="ofs.label.na" />
            </logic:equal>
            <logic:notEqual name="manager" property="team_id" value="0">
              <html:link page="/Team.do" paramId="team_id" paramName="manager" paramProperty="team_id">
                <bean:write name="manager" property="team_abbrev"/>
              </html:link>
            </logic:notEqual>
          </td>
          <td>
            <img src='drawbar2.jsp?background=000000&color1=ff0000&width=50&height=16&value1=<bean:write name="manager" property="offense"/>&value2=<bean:write name="manager" property="defense"/>&value3=<bean:write name="manager" property="intangible"/>&value4=<bean:write name="manager" property="penalty"/>'>
          </td>
          <td>
            <logic:equal name="manager" property="style" value="1"><bean:message key="manager.label.offensive"  /></logic:equal>
            <logic:equal name="manager" property="style" value="2"><bean:message key="manager.label.defensive"  /></logic:equal>
            <logic:equal name="manager" property="style" value="3"><bean:message key="manager.label.intangible" /></logic:equal>
            <logic:equal name="manager" property="style" value="4"><bean:message key="manager.label.penalty"    /></logic:equal>
            <logic:equal name="manager" property="style" value="5"><bean:message key="manager.label.balanced"   /></logic:equal>
          </td>
          <td><bean:write name="manager" property="seasons"    /></td>
          <td><bean:write name="manager" property="winPctDsp"  /></td>
        </tr>
      </logic:iterate>
      <tr class="separator"></tr>
    </logic:present>
    <logic:present name="fired_managers">
      <tr class="label"><td colspan="6"><bean:message key="ofs.label.fired"  /></td></tr>
      <tr class="heading">
        <td><bean:message key="ofs.label.manager" /></td>
        <td><bean:message key="ofs.label.team"    /></td>
        <td><bean:message key="ofs.label.ratings" /></td>
        <td><bean:message key="ofs.label.style"   /></td>
        <td><bean:message key="ofs.label.seasons" /></td>
        <td><bean:message key="ofs.label.win_pct" /></td>
      </tr>
      <logic:iterate id="manager" name="fired_managers">
        <tr>
          <td>
            <html:link page="/Manager.do" paramId="manager_id" paramName="manager" paramProperty="manager_id">
              <bean:write name="manager" property="last_name"  />, <bean:write name="manager" property="first_name" />
            </html:link>
          </td>
          <td>
            <html:link page="/Team.do" paramId="team_id" paramName="manager" paramProperty="team_id">
              <bean:write name="manager" property="team_abbrev"/>
            </html:link>
          </td>
          <td>
            <img src='drawbar2.jsp?background=000000&color1=ff0000&width=50&height=16&value1=<bean:write name="manager" property="offense"/>&value2=<bean:write name="manager" property="defense"/>&value3=<bean:write name="manager" property="intangible"/>&value4=<bean:write name="manager" property="penalty"/>'>
          </td>
          <td>
            <logic:equal name="manager" property="style" value="1"><bean:message key="manager.label.offensive"  /></logic:equal>
            <logic:equal name="manager" property="style" value="2"><bean:message key="manager.label.defensive"  /></logic:equal>
            <logic:equal name="manager" property="style" value="3"><bean:message key="manager.label.intangible" /></logic:equal>
            <logic:equal name="manager" property="style" value="4"><bean:message key="manager.label.penalty"    /></logic:equal>
            <logic:equal name="manager" property="style" value="5"><bean:message key="manager.label.balanced"   /></logic:equal>
          </td>
          <td><bean:write name="manager" property="seasons"    /></td>
          <td><bean:write name="manager" property="winPctDsp"  /></td>
        </tr>
      </logic:iterate>
      <tr class="separator"></tr>
    </logic:present>
    <tr class="label"><td colspan="6"><bean:message key="ofs.label.hired"  /></td></tr>
    <tr class="heading">
      <td><bean:message key="ofs.label.manager" /></td>
      <td><bean:message key="ofs.label.team"    /></td>
      <td><bean:message key="ofs.label.ratings" /></td>
      <td><bean:message key="ofs.label.style"   /></td>
      <td><bean:message key="ofs.label.seasons" /></td>
      <td><bean:message key="ofs.label.win_pct" /></td>
    </tr>
    <logic:iterate id="manager" name="hired_managers">
      <tr>
        <td>
          <html:link page="/Manager.do" paramId="manager_id" paramName="manager" paramProperty="manager_id">
            <bean:write name="manager" property="last_name"  />, <bean:write name="manager" property="first_name" />
          </html:link>
        </td>
        <td>
          <html:link page="/Team.do" paramId="team_id" paramName="manager" paramProperty="team_id">
            <bean:write name="manager" property="team_abbrev"/>
          </html:link>
        </td>
        <td>
          <img src='drawbar2.jsp?background=000000&color1=ff0000&width=50&height=16&value1=<bean:write name="manager" property="offense"/>&value2=<bean:write name="manager" property="defense"/>&value3=<bean:write name="manager" property="intangible"/>&value4=<bean:write name="manager" property="penalty"/>'>
        </td>
        <td>
          <logic:equal name="manager" property="style" value="1"><bean:message key="manager.label.offensive"  /></logic:equal>
          <logic:equal name="manager" property="style" value="2"><bean:message key="manager.label.defensive"  /></logic:equal>
          <logic:equal name="manager" property="style" value="3"><bean:message key="manager.label.intangible" /></logic:equal>
          <logic:equal name="manager" property="style" value="4"><bean:message key="manager.label.penalty"    /></logic:equal>
          <logic:equal name="manager" property="style" value="5"><bean:message key="manager.label.balanced"   /></logic:equal>
        </td>
        <td><bean:write name="manager" property="seasons"    /></td>
        <td><bean:write name="manager" property="winPctDsp"  /></td>
      </tr>
    </logic:iterate>
  </table>
</logic:present>

</body>
</html:html>
