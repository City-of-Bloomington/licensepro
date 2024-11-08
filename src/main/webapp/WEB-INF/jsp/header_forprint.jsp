<?xml version="1.0" encoding="UTF-8" ?>
<!--
     * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
     * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
     * @author W. Sibo <sibow@bloomington.in.gov>
     *
-->
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
    <%
    response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
    response.setHeader("Pragma","no-cache"); //HTTP 1.0
    response.setDateHeader ("Expires", 0);
    %>
    <head>
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <s:head />
  <meta http-equiv="Content-Type" content="application/xhtml+xml; charset=utf-8" />
  <title>LicensePro</title>
  <script type="text/javascript">
    var APPLICATION_URL = '<s:property value='#application.url' />';
  </script>
</head>
<body>

