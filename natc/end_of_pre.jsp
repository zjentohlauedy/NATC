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

<jsp:include page="menu.jsp" />

<h1>END OF PRESEASON</h1>
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

<logic:present name="div0teams">
  <table class="standings">
    <tr>
      <td>
        <table class="standing">
          <tr class="heading">
            <td colspan="3"><bean:message key="division.greene"/></td>
          </tr>
          <logic:iterate id="team" name="div0teams">
            <tr>
              <td>
                <html:link page="/Team.do" paramId="team_id" paramName="team" paramProperty="team_id">
                  <bean:write name="team" property="location" />
                </html:link>
              </td>
              <td><bean:write name="team" property="preseason_wins"     /></td>
              <td><bean:write name="team" property="preseason_losses"   /></td>
            </tr>
          </logic:iterate>
        </table>
      </td>
      <td>&nbsp;</td>
      <td>
        <table class="standing">
          <tr class="heading">
            <td colspan="3"><bean:message key="division.davis"/></td>
          </tr>
          <logic:iterate id="team" name="div1teams">
            <tr>
              <td>
                <html:link page="/Team.do" paramId="team_id" paramName="team" paramProperty="team_id">
                  <bean:write name="team" property="location" />
                </html:link>
              </td>
              <td><bean:write name="team" property="preseason_wins"     /></td>
              <td><bean:write name="team" property="preseason_losses"   /></td>
            </tr>
          </logic:iterate>
        </table>
      </td>
      <td>&nbsp;</td>
      <td>
        <table class="standing">
          <tr class="heading">
            <td colspan="3"><bean:message key="division.smith"/></td>
          </tr>
          <logic:iterate id="team" name="div2teams">
            <tr>
              <td>
                <html:link page="/Team.do" paramId="team_id" paramName="team" paramProperty="team_id">
                  <bean:write name="team" property="location" />
                </html:link>
              </td>
              <td><bean:write name="team" property="preseason_wins"     /></td>
              <td><bean:write name="team" property="preseason_losses"   /></td>
            </tr>
          </logic:iterate>
        </table>
      </td>
      <td>&nbsp;</td>
      <td>
        <table class="standing">
          <tr class="heading">
            <td colspan="3"><bean:message key="division.lawrence"/></td>
          </tr>
          <logic:iterate id="team" name="div3teams">
            <tr>
              <td>
                <html:link page="/Team.do" paramId="team_id" paramName="team" paramProperty="team_id">
                  <bean:write name="team" property="location" />
                </html:link>
              </td>
              <td><bean:write name="team" property="preseason_wins"     /></td>
              <td><bean:write name="team" property="preseason_losses"   /></td>
            </tr>
          </logic:iterate>
        </table>
      </td>
    </tr>
  </table>
</logic:present>

</body>
</html:html>
