<%@ page contentType="text/html; charset=utf-8" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld"   prefix="bean"   %>
<%@ taglib uri="/WEB-INF/struts-html.tld"   prefix="html"   %>
<%@ taglib uri="/WEB-INF/struts-logic.tld"  prefix="logic"  %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>

<html:html locale="true">
<head>
  <html:base/>
  <title><bean:message key="title.players"/></title>
  
  <link rel="stylesheet" type="text/css" href="styles/natc_layout.css" media='screen' />
  
  <style type="text/css">
    @import "styles/natc_markup.css";
  </style>
</head>
<body>

<jsp:include page="menu.jsp" />

<h1>PLAYERS</h1>

<logic:present name="letters">
  <table class="letters">
    <tr>
      <logic:iterate id="letter" name="letters">
        <td>
          <html:link page="/Players.do" paramId="group" paramName="letter" paramProperty="value">
            <bean:write name="letter" property="value"/>
          </html:link>
        </td>
      </logic:iterate>
    </tr>
  </table>
</logic:present>

<logic:present name="players">
<table class="playerlist">
  <logic:iterate id="player" name="players">
    <tr>
      <td>
        <html:link page="/Player.do" paramId="player_id" paramName="player" paramProperty="player_id">
          <bean:write name="player" property="last_name"/>, <bean:write name="player" property="first_name"/>
        </html:link>
      </td>
    </tr>
  </logic:iterate>
</table>
</logic:present>

</body>
</html:html>
