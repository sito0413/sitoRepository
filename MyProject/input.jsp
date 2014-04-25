<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<%
var y =0;
y++;
y--;
--y;
++y;
System.out.print("test");
//var y = new ArrayList<String>();
	var masterData = request.getAttribute("data");
	var list1 = masterData.get(1);
	var list1Size = list1.size();
	var data = masterData.get(0).get(0);
	var data11 = data.get(11);
	var data12 = data.get(12);
	var data13 = data.get(13);
	var data14 = data.get(14);
	var data15 = data.get(15);
	var data16 = data.get(16);
	var data17 = data.get(17);
	var data18 = data.get(18);
	var data19 = data.get(19);
	var data20 = data.get(20);
	var data21 = data.get(21);
	var data22 = data.get(22);
	var data23 = data.get(23);
	var data24 = data.get(24);
	var data25 = data.get(25);
	var data26 = data.get(26);
	var data27 = data.get(27);
	var data28 = data.get(28);
	var data29 = data.get(29);
	var data30 = data.get(30);
	var data31 = data.get(31);
	var data32 = data.get(32);
	var data33 = data.get(33);
	var data34 = data.get(34);
	var data35 = data.get(35);
	var data36 = data.get(36);
	var data37 = data.get(37);
	var data38 = data.get(38);
	var data39 = data.get(39);
	var data40 = data.get(40);
	var data41 = data.get(41);
	var data42 = data.get(42);
	var data43 = data.get(43);
	var data44 = data.get(44);
	var data45 = data.get(45);
	var data46 = data.get(46);
	var data47 = data.get(47);
	var data48 = data.get(48);
	var data49 = data.get(49);
	var data50 = data.get(50);
	var data51 = data.get(51);
	var data52 = data.get(52);
	var data69 = data.get(69);
	var data70 = data.get(70);
	var data71 = data.get(71);
	var data72 = data.get(72);
	var data73 = data.get(73);
	var data74 = data.get(74);
	var data75 = data.get(75);
	var data81 = data.get(81);
	var data82 = data.get(82);
	var clockH = "　";
	var clockM = "　";
	for(var i = 0;i < list1Size; i++){
		if( list1.get(i).get(0) == data14){
			clockH = list1.get(i).get(1);
			clockM = list1.get(i).get(2);
			break;
		}
	}

%>    <head>
       <meta charset="UTF-8"/>
        <meta name="viewport" content="width=device-width,user-scalable=no,initial-scale=1,maximum-scale=1"/>
        <meta name="format-detection" content="telephone=no">
        <meta name="apple-mobile-web-app-capable" content="yes" />
        <title>予備検査入力</title>
        <link rel="apple-touch-icon" href="/kousyu/resource/icon.png" />
		<link rel="shortcut icon" href="/kousyu/resource/icon.png" />
        <link rel="stylesheet" href="/kousyu/resource/jquery/jquery.mobile-1.4.0.min.css"/>
        <link rel="stylesheet" href="/kousyu/resource/table.css"/>
        <script type="text/javascript" src="/kousyu/resource/jquery/jquery-2.0.3.min.js"></script>
        <script type="text/javascript" src="/kousyu/resource/jquery/jquery.mobile-1.4.0.min.js"></script>
	</head>
    <body>
        <div id="input" data-role="page" data-title="予備検査入力" >
			<script type="text/javascript">
				var startH;
				var startM;
				var clockIndex;
				var a = new Array();
				var b = new Array();
				var c = new Array();
				var d = new Array();
				var at = 0;
				var bt = 0;
				var ct = 0;
				var t = 0;
				var r = 0;
				var editable = false;

				function init() {
					startH = Math.floor(Number("<%=data12%>"));
					startM = Math.floor(Number("<%=data13%>"));
					clockIndex = Math.floor(Number("<%=data14%>"));
					a[0] = Math.floor(Number("<%=data15%>"));
					a[1] = Math.floor(Number("<%=data16%>"));
					a[2] = Math.floor(Number("<%=data17%>"));
					a[3] = Math.floor(Number("<%=data18%>"));
					a[4] = Math.floor(Number("<%=data19%>"));
					a[5] = Math.floor(Number("<%=data20%>"));
		        	at = "<%=data.get(76)%>";
			       	b[0] = "<%if(data21 == -1 && data37 == -1) {%>3<% } else if(data21 == -1) {%>2<% } else if(data37 == -1){%>1<%} else{%>0<%}%>";
		        	b[1] = "<%if(data22 == -1 && data38 == -1) {%>3<% } else if(data22 == -1) {%>2<% } else if(data38 == -1){%>1<%} else{%>0<%}%>";
		        	b[2] = "<%if(data23 == -1 && data39 == -1) {%>3<% } else if(data23 == -1) {%>2<% } else if(data39 == -1){%>1<%} else{%>0<%}%>";
		        	b[3] = "<%if(data24 == -1 && data40 == -1) {%>3<% } else if(data24 == -1) {%>2<% } else if(data40 == -1){%>1<%} else{%>0<%}%>";
		        	b[4] = "<%if(data25 == -1 && data41 == -1) {%>3<% } else if(data25 == -1) {%>2<% } else if(data41 == -1){%>1<%} else{%>0<%}%>";
		        	b[5] = "<%if(data26 == -1 && data42 == -1) {%>3<% } else if(data26 == -1) {%>2<% } else if(data42 == -1){%>1<%} else{%>0<%}%>";
		        	b[6] = "<%if(data27 == -1 && data43 == -1) {%>3<% } else if(data27 == -1) {%>2<% } else if(data43 == -1){%>1<%} else{%>0<%}%>";
			       	b[7] = "<%if(data28 == -1 && data44 == -1) {%>3<% } else if(data28 == -1) {%>2<% } else if(data44 == -1){%>1<%} else{%>0<%}%>";
		        	b[8] = "<%if(data29 == -1 && data45 == -1) {%>3<% } else if(data29 == -1) {%>2<% } else if(data45 == -1){%>1<%} else{%>0<%}%>";
		        	b[9] = "<%if(data30 == -1 && data46 == -1) {%>3<% } else if(data30 == -1) {%>2<% } else if(data46 == -1){%>1<%} else{%>0<%}%>";
		        	b[10] = "<%if(data31 == -1 && data47 == -1) {%>3<% } else if(data31 == -1) {%>2<% } else if(data47 == -1){%>1<%} else{%>0<%}%>";
		        	b[11] = "<%if(data32 == -1 && data48 == -1) {%>3<% } else if(data32 == -1) {%>2<% } else if(data48 == -1){%>1<%} else{%>0<%}%>";
		        	b[12] = "<%if(data33 == -1 && data49 == -1) {%>3<% } else if(data33 == -1) {%>2<% } else if(data49 == -1){%>1<%} else{%>0<%}%>";
		        	b[13] = "<%if(data34 == -1 && data50 == -1) {%>3<% } else if(data34 == -1) {%>2<% } else if(data50 == -1){%>1<%} else{%>0<%}%>";
			       	b[14] = "<%if(data35 == -1 && data51 == -1) {%>3<% } else if(data35 == -1) {%>2<% } else if(data51 == -1){%>1<%} else{%>0<%}%>";
		        	b[15] = "<%if(data36 == -1 && data52 == -1) {%>3<% } else if(data36 == -1) {%>2<% } else if(data52 == -1){%>1<%} else{%>0<%}%>";
			       	bt = "<%=data.get(77)%>";
			       	c[0] = "<%if(data69 == 1) {%>1<% } else {%>0<% }%>";
		        	c[1] = "<%if(data70 == 1) {%>1<% } else {%>0<% }%>";
		        	c[2] = "<%if(data71 == 1) {%>1<% } else {%>0<% }%>";
		        	c[3] = "<%if(data72 == 1) {%>1<% } else {%>0<% }%>";
		        	c[4] = "<%if(data73 == 1) {%>1<% } else {%>0<% }%>";
		        	c[5] = "<%if(data74 == 1) {%>1<% } else {%>0<% }%>";
		        	c[6] = "<%if(data75 == 1) {%>1<% } else {%>0<% }%>";
		          	ct = "<%=data.get(78)%>";
		          	t = "<%=data.get(79)%>";
		          	r = "<%=data.get(80)%>";
		        	d[0] = "<%=data81%>";
		        	d[1] = "<%=data82%>";
					$("#clockH").text("時間の数字（<%=clockH%>）が指示されている");
					$("#clockM").text("分の数字（<%=clockM%>）が指示されている");
		        	editable = "<%=data.get(85)%>" == 1;
		        	if(editable){
		        		alert("受講者確定処理が行われています。\r\n\r\n「受講・検査結果の入力」で\r\n受講者確定解除処理を行ってから検査結果入力を行って下さい。\r\n\r\n内容を変更しても保存されません。");
		        	}
		        	updateA();
		        	updateB();
		        	updateC();
		        }
		        function  updateT(){
		        	t = 0;
		        	r = 0;
					if((at+bt+ct) != 0){
						t = Math.floor(1.15 * at + 1.94 * bt + 2.97 * ct);
						if(t < 49){
							r = 1;
						}
						else if(t < 76){
							r = 2;
						} else{
							r = 3;
						}
					}
					$("#total").text(t);
					$("#result").text(r);
				}
		       	function  updateA(){
		           at = 0;
		           if(Math.abs(Math.floor(Number(a[0])) - <%=request.getAttribute("YEAR")%>) == 0){
		           		at += 5;
		           	  	$("#pointA0").text("5");
		           }else{
		        	   	$("#pointA0").text("0");
		           }
		           if(Math.abs(Math.floor(Number(a[1])) - <%=request.getAttribute("MONTH")%>) == 0){
		           		at += 4;
		           	  	$("#pointA1").text("4");
		           }else{
		        	   	$("#pointA1").text("0");
		           }
		           if(Math.abs(Math.floor(Number(a[2])) - <%=request.getAttribute("DAY_OF_MONTH")%>) == 0){
		           		at += 3;
		           	  	$("#pointA2").text("3");
		           }else{
		        	   	$("#pointA2").text("0");
		           }
		           if(Math.abs(Math.floor(Number(a[3])) - <%=request.getAttribute("DAY_OF_WEEK")%>) == 0){
		           		at += 2;
		           	  	$("#pointA3").text("2");
		           }else{
		        	   	$("#pointA3").text("0");
		           }
		           if( Math.floor(Number(a[4])) != -1 &&  Math.floor(Number(a[5])) != -1 && startH != -1 && startM != -1 && Math.abs(60 * (Math.floor(Number(a[4])) - startH) + (Math.floor(Number(a[5])) - startM)) < 30){
		           		at += 1;
		           	  	$("#pointA4").text("1");
		           }else{
		        	   	$("#pointA4").text("0");
		           }
		           $("#totalA").text(at);
		           updateT()
		     	}
		        function  updateB(){
		          	bt = 0;
		      		for(var i = 0; i < 16 ; i++){
		      			try{
		      				var nvb =  Math.floor(Number(b[i]));
		      				if(nvb == 3){
		      					nvb = 2;
		      				}
		      				bt += nvb;
							$("#c2-" + i + "-3").text(nvb);
		      			}catch(e){
		      			}
		       		}
		            $("#totalB").text(bt);
		            updateT();
		        }
		        function  updateC(){
		       	    ct = 0;
		      		for(var i = 0; i < 7 ; i++){
		      			try{
		      				ct+=Math.floor(Number(c[i]));
		      			}catch(e){
		      			}
		       		}
		            $("#totalC").text(ct);
		            updateT();
		   		}
		        function changeStartH(value) {
		        	startH = Math.floor(Number(value));
		        	updateA();
		        }
		        function changeStartM(value) {
		        	startM = Math.floor(Number(value));
		        	updateA();
		        }
		        function changeClock(value) {
		        	clockIndex = Math.floor(Number(value));
		        	switch (clockIndex) {
<%
	for(var i = 0;i < list1Size; i++){
%>     	        		case <%= list1.get(i).get(0)%>:
							$("#clockH").text("時間の数字（<%= list1.get(i).get(1)%>）が指示されている");
							$("#clockM").text("分の数字（<%= list1.get(i).get(2)%>）が指示されている");
		                    break;
<%
	}
%>
		                default :
		                    $("#clockH").text("時間の数字（　）が指示されている");
		                	$("#clockM").text("分の数字（　）が指示されている");
		                	break;
		        	}
		        }
		        function  changeA(index,value){
					a[index] = value;
					updateA();
		        }
		        function  changeB(index){
	        		var value = 0;
					if($("#c2-" + index + "-1").prop('checked')){
						value += 2;
					}
					if($("#c2-" + index + "-2").prop('checked')){
						value += 1;
					}
					b[index] = value;
					updateB();
		        }
		        function  changeC(index,value){
					c[index] = value;
					updateC();
		        }
		        function  changeD(index,value){
					d[index] = value;
		        }
		        function  sendData(){
	    			if(!editable){
	    	        	$.post("/kousyu/sendData",{
		    				id1:<%=data.get(0)%>,id2:<%=data.get(84)%>,
		    				pt:"<%=data11%>",
							a0:a[0],a1:a[1],a2:a[2],a3:a[3],a4:a[4],a5:a[5],
							ap0:$("#pointA0").text(),ap1:$("#pointA1").text(),ap2:$("#pointA2").text(),ap3:$("#pointA3").text(),ap4:$("#pointA4").text(),at:at,
				        	b0:b[0],b1:b[1],b2:b[2],b3:b[3],b4:b[4],b5:b[5],b6:b[6],b7:b[7],b8:b[8],b9:b[9],b10:b[10],b11:b[11],b12:b[12],b13:b[13],b14:b[14],b15:b[15],bt:bt,
				           	c0:c[0],c1:c[1],c2:c[2],c3:c[3],c4:c[4],c5:c[5],c6:c[6],ct:ct,
				           	t:t,r:r,
				           	d0:d[0],d1:d[1],
				           	startH:startH,startM:startM,clockIndex:clockIndex
		    			}, function (data) {
		    				history.back();
		    			});

	    			}
		        }

		        //numberInput
		        var flg;
		        var numberResult;
		        var numberInputValue = 0;
		        function numberInput(index) {
		        	while(flg){}flg=true;
		            var numberResultValue;
		            if(index >= 10){
		            	numberResultValue = numberInputValue * 100 + index;
		            }else{
		            	numberResultValue = numberInputValue * 10 + index;
		            }
		            if (numberResultValue <= 999999) {
		            	numberInputValue = numberResultValue;
		            	numberResult.text(numberResultValue);
		            }
		            flg=false;
		        }
		        function numberClear() {
	            	numberInputValue = 0
	            	numberResult.text("");
		        }
		        function numberEnter() {
		            $("#yearInput").text("");
		            var data = numberResult.text();
		            var html = "<div class=\"ui-select\"><a class=\"ui-icon-carat-d:after\" onclick=\"showNumberInput()\" data-role=\"button\" data-icon=\"carat-d\" data-iconpos=\"right\">";
		            if (data == "") {
		                html = html + "　</a></div>";
			            changeA(0,0);
      				}
		            else {
		                html = html + data + "年</a></div>";
			            changeA(0,numberInputValue);
	            	}
		            $(html).appendTo("#yearInput").trigger('create');
		        }

		        function showNumberInput() {
		            try {
		            	flg=false;
		                var text = a[0];
		                if (text == '0') {
		                    text = '';
		                    numberInputValue=0;
		                }else{
		                	numberInputValue=a[0]|0;
		                }
		                numberResult = $("#numberResult");
		                numberResult.text(text);
		                $('#numberInputPopUp').popup('open', { positionTo: 'window' });
		            } catch (exp) {
		            }
		        }
			</script>
			<div data-role="header" data-position="fixed">
				<a data-role="button" href="#" onclick="sendData();return false;">戻る</a>
				<h1>予備検査入力</h1>
			</div>
			<div data-role="content">
				<table class="table">
					<tr>
						<th></th>
						<th>氏名</th>
						<th>生年月日</th>
						<th>年</th>
						<th>性</th>
						<th>実施日</th>
						<th>開始時刻</th>
						<th>講習担当</th>
						<th>実施種別</th>
					</tr>
					<tr>
						<td><%=data.get(1)%></td>
						<td><%=data.get(2)%></td>
						<td><%=data.get(3)%></td>
						<td><%=data.get(4)%></td>
						<td><%=data.get(5)%></td>
						<td><%=data.get(6)%></td>
						<td><%=data.get(7)%></td>
						<td><%=data.get(8)%></td>
						<td><%=data.get(9)%></td>
					</tr>
				</table>
				<br/>
				<table class="table">
					<tr>
						<th>検査連番</th>
						<th>パターン</th>
						<th>見当識開始時間</th>
						<th>時計描画開始時間</th>
					</tr>
					<tr>
						<td><%=data.get(10)%></td>
						<td><%=data11%></td>
						<td>
							<div data-role="controlgroup" data-type="horizontal">
								<select onchange="changeStartH(value)" >
									<option value="-1">　</option>
<%
	for(var i = 0;i < 24; i++){
		var s = "" + i;
		if(i < 10){
			s= '0' + i;
		}
%>									<option value="<%=s%>"<%if(s==data12) {%> selected="selected"<% }%>><%=s%>時</option>
<%
	}
%>
								</select>
								<select onchange="changeStartM(value)" >
									<option value="-1">　</option>
<%
	for(var i = 0;i < 60; i++){
		var s = "" + i;
		if(i < 10){
			s= '0' + i;
		}
%>									<option value="<%=s%>"<%if(s==data13) {%> selected="selected"<% }%>><%=s%>分</option>
<%
 	}
%>
								</select>
							</div>
						</td>
						<td>
							<div data-role="controlgroup" data-type="horizontal">
								<select onchange="changeClock(value)" >
									<option value="-1">　</option>
<%
	for(var i = 0;i < list1Size; i++){
		if( list1.get(i).get(0) == data14){
%>									<option value="<%= list1.get(i).get(0)%>" selected="selected"><%=list1.get(i).get(3)%></option>
<%
		}
		else if (false){} else
%>									<option value="<%= list1.get(i).get(0)%>"><%=list1.get(i).get(3)%></option>
<%
		}
	}
%>
								</select>
							</div>
						</td>
					</tr>
				</table>
				<div data-role="collapsible-set" data-theme="d" data-content-theme="d">
					<div data-role="collapsible">
						<h3>検査用紙1　時間の見当識</h3>
						<table class="table">
							<tr>
								<th>
									<label>質問</label>
								</th>
								<th>
									<input type="button" value="ヒント" data-inline="true" data-icon="info" data-iconpos="notext" onclick="$('#popupHintA').popup('open', { positionTo: 'window' });">
								</th>
								<th>基準（誤差）</th>
								<th>回答</th>
								<th>得点</th>
							</tr>
							<tr>
								<td colspan="2">何年</td>
								<td>1年ごと</td>
								<td style="text-align: left;">
									<div id="yearInput">
					                   	<div class="ui-select">
					                       	<a class="ui-icon-carat-d:after" onclick="showNumberInput()" data-role="button" data-icon="carat-d" data-iconpos="right"><%=data15 == "0"? "　" : data15 %><%=data15 == "　" | data15 == "0"? "":"年"%></a>
					               		</div>
									</div>
								</td>
								<td id="pointA0">
								</td>
							</tr>
							<tr>
								<td colspan="2">何月</td>
								<td>1月ごと</td>
								<td style="text-align: left;">
									<div data-role="controlgroup" data-type="horizontal">
										<select onchange="changeA(1, value)" >
											<option value="0">　</option>
<%
	for(var i = 1; i <= 12; i++){
		var s1 = "" + i;
		var s2 = "" + i;
		if(i < 10){
			s2= '0' + i;
		}
%>											<option value="<%=s1%>"<%if(s1==data16) {%> selected="selected"<% }%>><%=s2%>月</option>
<%
 	}
%>										</select>
									</div>
								</td>
								<td id="pointA1">
								</td>
							</tr>
							<tr>
								<td colspan="2">何日</td>
								<td>1日ごと</td>
								<td style="text-align: left;">
									<div data-role="controlgroup" data-type="horizontal">
										<select onchange="changeA(2,value)" >
											<option value="0">　</option>
<%
	for(var i = 1; i <= 31; i++){
		var s1 = "" + i;
		var s2 = "" + i;
		if(i < 10){
			s2= '0' + i;
		}
%>											<option value="<%=s1%>"<%if(s1==data17) {%> selected="selected"<% }%>><%=s2%>日</option>
<%
 	}
%>										</select>
				 					</div>
								</td>
								<td id="pointA2">
								</td>
							</tr>
							<tr>
								<td colspan="2">何曜</td>
								<td>1日ごと</td>
								<td style="text-align: left;">
									<div data-role="controlgroup" data-type="horizontal">
										<select onchange="changeA(3,value)" >
											<option value="0"<%if("0"==data18) {%> selected="selected"<% }%>>　</option>
											<option value="1"<%if("1"==data18) {%> selected="selected"<% }%>>日</option>
											<option value="2"<%if("2"==data18) {%> selected="selected"<% }%>>月</option>
											<option value="3"<%if("3"==data18) {%> selected="selected"<% }%>>火</option>
											<option value="4"<%if("4"==data18) {%> selected="selected"<% }%>>水</option>
											<option value="5"<%if("5"==data18) {%> selected="selected"<% }%>>木</option>
											<option value="6"<%if("6"==data18) {%> selected="selected"<% }%>>金</option>
											<option value="7"<%if("7"==data18) {%> selected="selected"<% }%>>土</option>
											<option value="8"<%if("8"==data18) {%> selected="selected"<% }%>>無効</option>
										</select>
									</div>
								</td>
								<td id="pointA3">
								</td>
							</tr>
							<tr>
								<td colspan="2">何時何分</td>
								<td>30分ごと</td>
								<td style="text-align: left;">
									<div data-role="controlgroup" data-type="horizontal">
										<select onchange="changeA(4,value)" >
											<option value="-1">　</option>
<%
	for(var i = 0; i <= 23; i++){
		var s = "" + i;
		if(i < 10){
			s= '0' + i;
		}
%>											<option value="<%=s%>"<%if(s==data19) {%> selected="selected"<% }%>><%=s%>時</option>
<%
 	}
%>										</select>
										<select onchange="changeA(5,value)" >
											<option value="-1">　</option>
<%
	for(var i = 0; i <= 59; i++){
		var s = "" + i;
		if(i < 10){
			s= '0' + i;
		}
%>											<option value="<%=s%>"<%if(s==data20) {%> selected="selected"<% }%>><%=s%>分</option>
<%
 	}
%>										</select>
									</div>
								</td>
								<td id="pointA4">
								</td>
							</tr>
						</table>
					</div>
					<div data-role="collapsible" data-theme="d" data-content-theme="d">
						<h3>検査用紙3/4　手がかり再生</h3>
						<table class="table">
							<tr>
								<th>番号</th>
								<th>カテゴリー</th>
								<th>正解</th>
								<th>回答</th>
								<th>得点</th>
							</tr>
							<tr>
								<td>1</td>
								<td><%=masterData.get(29).get(0).get(0)%></td>
								<td>
									<select>
<%
	var list5 = masterData.get(5);
	var list5Size = list5.size();
	for(var i = 0;i < list5Size; i++){
%>										<option disabled="disabled"><%=list5.get(i).get(0)%></option>
<%	}%>
									</select>
								</td>
								<td>
									<div data-role="controlgroup" data-type="horizontal">
										<input id="c2-0-1" type="checkbox" <%if(data21 == -1) {%> checked="checked"<% }%> onchange="changeB(0)"><label for="c2-0-1" >自由</label>
										<input id="c2-0-2" type="checkbox" <%if(data37 == -1) {%> checked="checked"<% }%> onchange="changeB(0)"><label for="c2-0-2" >手がかり</label>
									</div>
								</td>
								<td id="c2-0-3" ></td>
							</tr>
							<tr>
								<td>2</td>
								<td><%=masterData.get(29).get(1).get(0)%></td>
								<td>
									<select>
<%
	var list6 = masterData.get(6);
	var list6Size = list6.size();
	for(var i = 0;i < list6Size; i++){
%>										<option disabled="disabled"><%=list6.get(i).get(0)%></option>
<%	}%>
									</select>
								</td>
								<td>
									<div data-role="controlgroup" data-type="horizontal">
										<input id="c2-1-1" type="checkbox" <%if(data22 == -1) {%> checked="checked"<% }%> onchange="changeB(1)"><label for="c2-1-1" >自由</label>
										<input id="c2-1-2" type="checkbox" <%if(data38 == -1) {%> checked="checked"<% }%> onchange="changeB(1)"><label for="c2-1-2" >手がかり</label>
									</div>
								</td>
								<td id="c2-1-3" ></td>
							</tr>
							<tr>
								<td>3</td>
								<td><%=masterData.get(29).get(2).get(0)%></td>
								<td>
									<select>
<%
	var list7 = masterData.get(7);
	var list7Size = list7.size();
	for(var i = 0;i < list7Size; i++){
%>										<option disabled="disabled"><%=list7.get(i).get(0)%></option>
<%	}%>
									</select>
								</td>
								<td>
									<div data-role="controlgroup" data-type="horizontal">
										<input id="c2-2-1" type="checkbox" <%if(data23 == -1) {%> checked="checked"<% }%> onchange="changeB(2)"><label for="c2-2-1" >自由</label>
										<input id="c2-2-2" type="checkbox" <%if(data39 == -1) {%> checked="checked"<% }%> onchange="changeB(2)"><label for="c2-2-2" >手がかり</label>
									</div>
								</td>
								<td id="c2-2-3" ></td>
							</tr>
							<tr>
								<td>4</td>
								<td><%=masterData.get(29).get(3).get(0)%></td>
								<td>
									<select>
<%
	var list8 = masterData.get(8);
	var list8Size = list8.size();
	for(var i = 0;i < list8Size; i++){
%>										<option disabled="disabled"><%=list8.get(i).get(0)%></option>
<%	}%>
									</select>
								</td>
								<td>
									<div data-role="controlgroup" data-type="horizontal">
										<input id="c2-3-1" type="checkbox" <%if(data24 == -1) {%> checked="checked"<% }%> onchange="changeB(3)"><label for="c2-3-1" >自由</label>
										<input id="c2-3-2" type="checkbox" <%if(data40 == -1) {%> checked="checked"<% }%> onchange="changeB(3)"><label for="c2-3-2" >手がかり</label>
									</div>
								</td>
								<td id="c2-3-3" ></td>
							</tr>
							<tr>
								<td>5</td>
								<td><%=masterData.get(29).get(4).get(0)%></td>
								<td>
									<select>
<%
	var list9 = masterData.get(9);
	var list9Size = list9.size();
	for(var i = 0;i < list9Size; i++){
%>										<option disabled="disabled"><%=list9.get(i).get(0)%></option>
<%	}%>
									</select>
								</td>
								<td>
									<div data-role="controlgroup" data-type="horizontal">
										<input id="c2-4-1" type="checkbox" <%if(data25 == -1) {%> checked="checked"<% }%> onchange="changeB(4)"><label for="c2-4-1" >自由</label>
										<input id="c2-4-2" type="checkbox" <%if(data41 == -1) {%> checked="checked"<% }%> onchange="changeB(4)"><label for="c2-4-2" >手がかり</label>
									</div>
								</td>
								<td id="c2-4-3" ></td>
							</tr>
							<tr>
								<td>6</td>
								<td><%=masterData.get(29).get(5).get(0)%></td>
								<td>
									<select>
<%
	var list10 = masterData.get(10);
	var list10Size = list10.size();
	for(var i = 0;i < list10Size; i++){
%>										<option disabled="disabled"><%=list10.get(i).get(0)%></option>
<%	}%>
									</select>
								</td>
								<td>
									<div data-role="controlgroup" data-type="horizontal">
										<input id="c2-5-1" type="checkbox" <%if(data26 == -1) {%> checked="checked"<% }%> onchange="changeB(5)"><label for="c2-5-1" >自由</label>
										<input id="c2-5-2" type="checkbox" <%if(data42 == -1) {%> checked="checked"<% }%> onchange="changeB(5)"><label for="c2-5-2" >手がかり</label>
									</div>
								</td>
								<td id="c2-5-3" ></td>
							</tr>
							<tr>
								<td>7</td>
								<td><%=masterData.get(29).get(6).get(0)%></td>
								<td>
									<select>
<%
	var list11 = masterData.get(11);
	var list11Size = list11.size();

	for(var i = 0;i < list11Size; i++){
%>										<option disabled="disabled"><%=list11.get(i).get(0)%></option>
<%	}%>
									</select>
								</td>
								<td>
									<div data-role="controlgroup" data-type="horizontal">
										<input id="c2-6-1" type="checkbox" <%if(data27 == -1) {%> checked="checked"<% }%> onchange="changeB(6)"><label for="c2-6-1" >自由</label>
										<input id="c2-6-2" type="checkbox" <%if(data43 == -1) {%> checked="checked"<% }%> onchange="changeB(6)"><label for="c2-6-2" >手がかり</label>
									</div>
								</td>
								<td id="c2-6-3" ></td>
							</tr>
							<tr>
								<td>8</td>
								<td><%=masterData.get(29).get(7).get(0)%></td>
								<td>
									<select>
<%
	var list12 = masterData.get(12);
	var list12Size = list12.size();
	for(var i = 0;i < list12Size; i++){
%>										<option disabled="disabled"><%=list12.get(i).get(0)%></option>
<%	}%>
									</select>
								</td>
								<td>
									<div data-role="controlgroup" data-type="horizontal">
										<input id="c2-7-1" type="checkbox" <%if(data28 == -1) {%> checked="checked"<% }%> onchange="changeB(7)"><label for="c2-7-1" >自由</label>
										<input id="c2-7-2" type="checkbox" <%if(data44 == -1) {%> checked="checked"<% }%> onchange="changeB(7)"><label for="c2-7-2" >手がかり</label>
									</div>
								</td>
								<td id="c2-7-3" ></td>
							</tr>
							<tr>
								<td>9</td>
								<td><%=masterData.get(29).get(8).get(0)%></td>
								<td>
									<select>
<%
	var list13 = masterData.get(13);
	var list13Size = list13.size();

	for(var i = 0;i < list13Size; i++){
%>										<option disabled="disabled"><%=list13.get(i).get(0)%></option>
<%	}%>
									</select>
								</td>
								<td>
									<div data-role="controlgroup" data-type="horizontal">
										<input id="c2-8-1" type="checkbox" <%if(data29 == -1) {%> checked="checked"<% }%> onchange="changeB(8)"><label for="c2-8-1" >自由</label>
										<input id="c2-8-2" type="checkbox" <%if(data45 == -1) {%> checked="checked"<% }%> onchange="changeB(8)"><label for="c2-8-2" >手がかり</label>
									</div>
								</td>
								<td id="c2-8-3" ></td>
							</tr>
							<tr>
								<td>10</td>
								<td><%=masterData.get(29).get(9).get(0)%></td>
								<td>
									<select>
<%
	var list14 = masterData.get(14);
	var list14Size = list14.size();
	for(var i = 0;i < list14Size; i++){
%>										<option disabled="disabled"><%=list14.get(i).get(0)%></option>
<%	}%>
									</select>
								</td>
								<td>
									<div data-role="controlgroup" data-type="horizontal">
										<input id="c2-9-1" type="checkbox" <%if(data30 == -1) {%> checked="checked"<% }%> onchange="changeB(9)"><label for="c2-9-1" >自由</label>
										<input id="c2-9-2" type="checkbox" <%if(data46 == -1) {%> checked="checked"<% }%> onchange="changeB(9)"><label for="c2-9-2" >手がかり</label>
									</div>
								</td>
								<td id="c2-9-3" ></td>
							</tr>
							<tr>
								<td>11</td>
								<td><%=masterData.get(29).get(10).get(0)%></td>
								<td>
									<select>
<%
	var list15 = masterData.get(15);
	var list15Size = list15.size();
	for(var i = 0;i < list15Size; i++){
%>										<option disabled="disabled"><%=list15.get(i).get(0)%></option>
<%	}%>
									</select>
								</td>
								<td>
									<div data-role="controlgroup" data-type="horizontal">
										<input id="c2-10-1" type="checkbox" <%if(data31 == -1) {%> checked="checked"<% }%> onchange="changeB(10)"><label for="c2-10-1" >自由</label>
										<input id="c2-10-2" type="checkbox" <%if(data47 == -1) {%> checked="checked"<% }%> onchange="changeB(10)"><label for="c2-10-2" >手がかり</label>
									</div>
								</td>
								<td id="c2-10-3" ></td>
							</tr>
							<tr>
								<td>12</td>
								<td><%=masterData.get(29).get(11).get(0)%></td>
								<td>
									<select>
<%
	var list16 = masterData.get(16);
	var list16Size = list16.size();
	for(var i = 0;i < list16Size; i++){
%>										<option disabled="disabled"><%=list16.get(i).get(0)%></option>
<%	}%>
									</select>
								</td>
								<td>
									<div data-role="controlgroup" data-type="horizontal">
										<input id="c2-11-1" type="checkbox" <%if(data32 == -1) {%> checked="checked"<% }%> onchange="changeB(11)"><label for="c2-11-1" >自由</label>
										<input id="c2-11-2" type="checkbox" <%if(data48 == -1) {%> checked="checked"<% }%> onchange="changeB(11)"><label for="c2-11-2" >手がかり</label>
									</div>
								</td>
								<td id="c2-11-3" ></td>
							</tr>
							<tr>
								<td>13</td>
								<td><%=masterData.get(29).get(12).get(0)%></td>
								<td>
									<select>
<%
	var list17 = masterData.get(17);
	var list17Size = list17.size();
	for(var i = 0;i < list17Size; i++){
%>										<option disabled="disabled"><%=list17.get(i).get(0)%></option>
<%	}%>
									</select>
								</td>
								<td>
									<div data-role="controlgroup" data-type="horizontal">
										<input id="c2-12-1" type="checkbox" <%if(data33 == -1) {%> checked="checked"<% }%> onchange="changeB(12)"><label for="c2-12-1" >自由</label>
										<input id="c2-12-2" type="checkbox" <%if(data49 == -1) {%> checked="checked"<% }%> onchange="changeB(12)"><label for="c2-12-2" >手がかり</label>
									</div>
								</td>
								<td id="c2-12-3" ></td>
							</tr>
							<tr>
								<td>14</td>
								<td><%=masterData.get(29).get(13).get(0)%></td>
								<td>
									<select>
<%
	var list18 = masterData.get(18);
	var list18Size = list18.size();
	for(var i = 0;i < list18Size; i++){
%>										<option disabled="disabled"><%=list18.get(i).get(0)%></option>
<%	}%>
									</select>
								</td>
								<td>
									<div data-role="controlgroup" data-type="horizontal">
										<input id="c2-13-1" type="checkbox" <%if(data34 == -1) {%> checked="checked"<% }%> onchange="changeB(13)"><label for="c2-13-1" >自由</label>
										<input id="c2-13-2" type="checkbox" <%if(data50 == -1) {%> checked="checked"<% }%> onchange="changeB(13)"><label for="c2-13-2" >手がかり</label>
									</div>
								</td>
								<td id="c2-13-3" ></td>
							</tr>
							<tr>
								<td>15</td>
								<td><%=masterData.get(29).get(14).get(0)%></td>
								<td>
									<select>
<%
	var list19 = masterData.get(19);
	var list19Size = list19.size();
	for(var i = 0;i < list19Size; i++){
%>										<option disabled="disabled"><%=list19.get(i).get(0)%></option>
<%	}%>
									</select>
								</td>
								<td>
									<div data-role="controlgroup" data-type="horizontal">
										<input id="c2-14-1" type="checkbox" <%if(data35 == -1) {%> checked="checked"<% }%> onchange="changeB(14)"><label for="c2-14-1" >自由</label>
										<input id="c2-14-2" type="checkbox" <%if(data51 == -1) {%> checked="checked"<% }%> onchange="changeB(14)"><label for="c2-14-2" >手がかり</label>
									</div>
								</td>
								<td id="c2-14-3" ></td>
							</tr>
							<tr>
								<td>16</td>
								<td><%=masterData.get(29).get(15).get(0)%></td>
								<td>
									<select>
<%
	var list20 = masterData.get(20);
	var list20Size = list20.size();
	for(var i = 0;i < list20Size; i++){
%>										<option disabled="disabled"><%=list20.get(i).get(0)%></option>
<%	}%>
									</select>
								</td>
								<td>
									<div data-role="controlgroup" data-type="horizontal">
										<input id="c2-15-1" type="checkbox" <%if(data36 == -1) {%> checked="checked"<% }%> onchange="changeB(15)"><label for="c2-15-1" >自由</label>
										<input id="c2-15-2" type="checkbox" <%if(data52 == -1) {%> checked="checked"<% }%> onchange="changeB(15)"><label for="c2-15-2" >手がかり</label>
									</div>
								</td>
								<td id="c2-15-3" ></td>
							</tr>
						</table>
					</div>
					<div data-role="collapsible" data-theme="d" data-content-theme="d">
						<h3>検査用紙5　時計の描画</h3>
						<table class="table">
							<tr>
								<th>基準番号</th>
								<th>
									<input type="button" value="ヒント"  data-inline="true" data-icon="info" data-iconpos="notext" onclick="$('#popupHintC0').popup('open', { positionTo: 'window' });">
								</th>
								<th>採点基準</th>
								<th>回答</th>
							</tr>
							<tr>
								<td>1</td>
								<td>
									<input type="button" value="ヒント" data-theme="d"  data-inline="true" data-icon="info" data-iconpos="notext" onclick="$('#popupHintC1').popup('open', { positionTo: 'window' });">
								</td>
								<td>１から１２までの数字のみが書かれている</td>
								<td>
									<div data-role="controlgroup" data-type="horizontal">
										<input name="c3-1" id="c3-1-1" type="radio" <%if(data69 == 1) {%> checked="checked"<% }%> onchange="changeC(0,1)"><label for="c3-1-1" >正解</label>
										<input name="c3-1" id="c3-1-2" type="radio" <%if(data69 != 1) {%> checked="checked"<% }%> onchange="changeC(0,0)"><label for="c3-1-2" >不正解</label>
									</div>
								</td>
							</tr>
							<tr>
								<td>2</td>
								<td>
									<input type="button" value="ヒント" data-theme="d"  data-inline="true" data-icon="info" data-iconpos="notext" onclick="$('#popupHintC2').popup('open', { positionTo: 'window' });">
								</td>
								<td>数字の順番が正しい</td>
								<td>
									<div data-role="controlgroup" data-type="horizontal">
										<input name="c3-2" id="c3-2-1" type="radio" <%if(data70 == 1) {%> checked="checked"<% }%> onchange="changeC(1,1)"><label for="c3-2-1" >正解</label>
										<input name="c3-2" id="c3-2-2" type="radio" <%if(data70 != 1) {%> checked="checked"<% }%> onchange="changeC(1,0)"><label for="c3-2-2" >不正解</label>
									</div>
								</td>
							</tr>
							<tr>
								<td>3</td>
								<td>
									<input type="button" value="ヒント" data-theme="d"  data-inline="true" data-icon="info" data-iconpos="notext" onclick="$('#popupHintC3').popup('open', { positionTo: 'window' });">
								</td>
								<td>数字は正しい位置になくてはならない</td>
								<td>
									<div data-role="controlgroup" data-type="horizontal">
										<input name="c3-3" id="c3-3-1" type="radio" <%if(data71 == 1) {%> checked="checked"<% }%> onchange="changeC(2,1)"><label for="c3-3-1" >正解</label>
										<input name="c3-3" id="c3-3-2" type="radio" <%if(data71 != 1) {%> checked="checked"<% }%> onchange="changeC(2,0)"><label for="c3-3-2" >不正解</label>
									</div>
								</td>
							</tr>
							<tr>
								<td>4</td>
								<td>
									<input type="button" value="ヒント" data-theme="d"  data-inline="true" data-icon="info" data-iconpos="notext" onclick="$('#popupHintC4').popup('open', { positionTo: 'window' });">
								</td>
								<td>２つの針がある</td>
								<td>
									<div data-role="controlgroup" data-type="horizontal">
										<input name="c3-4" id="c3-4-1" type="radio" <%if(data72 == 1) {%> checked="checked"<% }%> onchange="changeC(3,1)"><label for="c3-4-1" >正解</label>
										<input name="c3-4" id="c3-4-2" type="radio" <%if(data72 != 1) {%> checked="checked"<% }%> onchange="changeC(3,0)"><label for="c3-4-2" >不正解</label>
									</div>
								</td>
							</tr>
							<tr>
								<td>5</td>
								<td>
									<input type="button" value="ヒント" data-theme="d"  data-inline="true" data-icon="info" data-iconpos="notext" onclick="$('#popupHintC5').popup('open', { positionTo: 'window' });">
								</td>
								<td  id="clockH"></td>
								<td>
									<div data-role="controlgroup" data-type="horizontal">
										<input name="c3-5" id="c3-5-1" type="radio" <%if(data73 == 1) {%> checked="checked"<% }%> onchange="changeC(4,1)"><label for="c3-5-1" >正解</label>
										<input name="c3-5" id="c3-5-2" type="radio" <%if(data73 != 1) {%> checked="checked"<% }%> onchange="changeC(4,0)"><label for="c3-5-2" >不正解</label>
									</div>
								</td>
							</tr>
							<tr>
								<td>6</td>
									<td>
									<input type="button" value="ヒント" data-theme="d"  data-inline="true" data-icon="info" data-iconpos="notext" onclick="$('#popupHintC6').popup('open', { positionTo: 'window' });">
								</td>
								<td id="clockM"></td>
								<td>
									<div data-role="controlgroup" data-type="horizontal">
										<input name="c3-6" id="c3-6-1" type="radio" <%if(data74 == 1) {%> checked="checked"<% }%> onchange="changeC(5,1)"><label for="c3-6-1" >正解</label>
										<input name="c3-6" id="c3-6-2" type="radio" <%if(data74 != 1) {%> checked="checked"<% }%> onchange="changeC(5,0)"><label for="c3-6-2" >不正解</label>
									</div>
								</td>
							</tr>
							<tr>
								<td>7</td>
								<td>
									<input type="button" value="ヒント" data-theme="d"  data-inline="true" data-icon="info" data-iconpos="notext" onclick="$('#popupHintC7').popup('open', { positionTo: 'window' });">
								</td>
								<td>長身と短針が正しい長さの割合になってなければならない</td>
								<td>
									<div data-role="controlgroup" data-type="horizontal">
										<input name="c3-7" id="c3-7-1" type="radio" <%if(data75 == 1) {%> checked="checked"<% }%> onchange="changeC(6,1)"><label for="c3-7-1" >正解</label>
										<input name="c3-7" id="c3-7-2" type="radio" <%if(data75 != 1) {%> checked="checked"<% }%> onchange="changeC(6,0)"><label for="c3-7-2" >不正解</label>
									</div>
								</td>
							</tr>
						</table>
					</div>
				</div>
				<table class="table">
					<tr>
						<th>時間見当計</th>
						<th>手がかり計</th>
						<th>時計描画計</th>
						<th>検査総合点</th>
						<th>検査結果</th>
						<th>予備検査員</th>
						<th>点検者</th>
					</tr>
					<tr>
						<td id ="totalA"><%=data.get(76)%></td>
						<td id ="totalB"><%=data.get(77)%></td>
						<td id ="totalC"><%=data.get(78)%></td>
						<td id ="total"><%=data.get(79)%></td>
						<td id ="result"><%=data.get(80)%></td>
						<td>
							<div data-role="controlgroup" data-type="horizontal">
								<select onchange="changeD(0,value)" >
									<option value="-1">　</option>
<%
	var list2 = masterData.get(2);
	var list2Size = list2.size();
	for(var i = 0;i < list2Size; i++){
%>									<option value="<%= list2.get(i).get(0)%>"<%if(list2.get(i).get(0)==data81) {%> selected="selected"<% }%>><%=list2.get(i).get(1)%></option>
<%
	}
%>								</select>
							</div>
						</td>
						<td>
							<div data-role="controlgroup" data-type="horizontal">
								<select onchange="changeD(1,value)" >
									<option value="-1">　</option>
<%
	var list3 = masterData.get(3);
	var list3Size = list3.size();
	var data82 = data.get(82);
	for(var i = 0;i < list3Size; i++){
%>									<option value="<%= list3.get(i).get(0)%>" <%if(list3.get(i).get(0)==data82) {%> selected="selected"<% }%>><%=list3.get(i).get(1)%></option>
<%
	}
%>								</select>
							</div>
						</td>
					</tr>
				</table>
				<script type="text/javascript">
			        init()
				</script>
			</div>
			<div data-role="popup" id="popupHintA" data-overlay-theme="a" data-theme="d" data-corners="false" draggable="false">
				<table class="table">
<%
	var list4 = masterData.get(4);
	var list4Size = list4.size();
	for(var i = 0;i < list4Size; i++){
%>					<tr><td><%= list4.get(i).get(0)%></td><td><%= list4.get(i).get(1)%></td><td><%= list4.get(i).get(2)%></td></tr>
<%
	}
%>				</table>
			</div>
<%
 	for(var j = 0;j <= 7; j++){
 		var s = j + "";
%>			<div data-role="popup" id="popupHintC<%=s %>" data-overlay-theme="a" data-theme="d" data-corners="false" draggable="false">
				<table class="table">
<%
		var listj = masterData.get(21 + j);
		var listjSize = listj.size();
		for(var i = 0;i < listjSize; i++){
%>					<tr><td><%= listj.get(i).get(0)%></td><td><%= listj.get(i).get(1)%></td></tr>
<%
		}
%>				</table>
			</div>
<%
	}
%>
			<div data-role="popup" id="numberInputPopUp" data-overlay-theme="a" data-theme="d" data-corners="false" draggable="false">
				<table class="numberInput">
					<tr>
						<td id="numberResult"  colspan="2" style="text-align: right; font-weight: bold;	color: #222; text-shadow: 0 1px 0 #fff; font-size: 30pt;">
						</td>
						<td style="text-align: left; font-weight: bold;	color: #222; text-shadow: 0 1px 0 #fff; font-size: 30pt;">年</td>
					</tr>
					<tr>
						<td class="input">
			                <a data-role="button" href="#" onclick="numberInput(7)">&ensp;7&ensp;</a>
			            </td>
			            <td class="input">
			                <a data-role="button" href="#" onclick="numberInput(8)">&ensp;8&ensp;</a>
			            </td>
			            <td class="input">
			                <a data-role="button" href="#" onclick="numberInput(9)">&ensp;9&ensp;</a>
			        	</td>
			        </tr>
			        <tr>
			            <td class="input">
			                <a data-role="button" href="#" onclick="numberInput(4)">&ensp;4&ensp;</a>
			            </td>
			            <td class="input">
			                <a data-role="button" href="#" onclick="numberInput(5)">&ensp;5&ensp;</a>
			            </td>
			            <td class="input">
			                <a data-role="button" href="#" onclick="numberInput(6)">&ensp;6&ensp;</a>
			            </td>
			        </tr>
			        <tr>
			            <td class="input">
			                <a data-role="button" href="#" onclick="numberInput(1)">&ensp;1&ensp;</a>
			            </td>
			            <td class="input">
			                <a data-role="button" href="#" onclick="numberInput(2)">&ensp;2&ensp;</a>
			            </td>
			            <td class="input">
			                <a data-role="button" href="#" onclick="numberInput(3)">&ensp;3&ensp;</a>
			            </td>
			        </tr>
			        <tr>
			            <td class="input">
			                <a data-role="button" href="#" onclick="numberInput(0)">&ensp;0&ensp;</a>
			            </td>
			            <td class="input">
			                <a data-role="button" href="#" onclick="numberInput(20)">&ensp;20&ensp;</a>
			            </td>
			            <td class="input">
			                <a data-role="button" href="#" onclick="numberClear()">削除</a>
						</td>
					</tr>
				    <tr>
				        <td colspan="3">
					        <a data-role="button" href="#" onclick="numberEnter()" data-rel="back" >決定</a>
					        <a data-role="button" href="#" data-rel="back" >キャンセル</a>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</body>
</html>