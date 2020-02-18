var time_remaining = 0;
var state_sequence = 0;
var possession     = 0;
var period         = 0;
var overtime       = false;
var clock_stopped  = false;

function displayTime() {

	var mins = Math.floor( time_remaining / 60 );
	var secs = time_remaining % 60;

	if ( mins < 10 ) mins = '0' + mins;
	if ( secs < 10 ) secs = '0' + secs;

	document.getElementById('clock').innerHTML = mins + ':' + secs;
}

function displayPeriod() {

	switch ( period ) {

	case 1: document.getElementById('period').innerHTML = '1st'; break;
	case 2: document.getElementById('period').innerHTML = '2nd'; break;
	case 3: document.getElementById('period').innerHTML = '3rd'; break;
	case 4: document.getElementById('period').innerHTML = '4th'; break;
	case 5: document.getElementById('period').innerHTML = '5th'; break;
	case 0: 

		if ( overtime ) {

			document.getElementById('period').innerHTML = 'OT';
		}
		else {

			document.getElementById('period').innerHTML = 'F';
		}

		break;

	default: document.getElementById('period').innerHTML = '???';
	}
}

function updatePossession() {

	if ( possession == 0 ) {

		document.getElementById('roadPoss').innerHTML = '';
		document.getElementById('homePoss').innerHTML = '';
	}
	else if ( possession == 1 ) {

		document.getElementById('roadPoss').innerHTML = '';
		document.getElementById('homePoss').innerHTML = '>';
	}
	else if ( possession == 2 ) {

		document.getElementById('roadPoss').innerHTML = '>';
		document.getElementById('homePoss').innerHTML = '';
	}
}

function manageClock() {

	if ( clock_stopped == true ) return;

	time_remaining--;

	if ( time_remaining == 0 ) clock_stopped = true;

	displayTime();
}

function updateGameState( state ) {

	if ( state.childNodes[1].textContent == state_sequence ) return;

	state_sequence = parseInt( state.childNodes[1].textContent );
	period         = parseInt( state.childNodes[2].textContent );
	time_remaining = parseInt( state.childNodes[4].textContent );
	possession     = parseInt( state.childNodes[6].textContent );

	overtime       = (state.childNodes[3].textContent == "true");
	clock_stopped  = (state.childNodes[5].textContent == "true");

	displayTime();
	displayPeriod();
	updatePossession();

	document.getElementById('event').innerHTML = state.childNodes[7].textContent;
}

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

	node = response.getElementsByTagName('gameState')[0];

	updateGameState( node );

	// home team
	teamNode = response.getElementsByTagName('homeTeam')[0];

	document.getElementById('homeP1Score').innerHTML = teamNode.childNodes[19].textContent;
	document.getElementById('homeP2Score').innerHTML = teamNode.childNodes[20].textContent;
	document.getElementById('homeP3Score').innerHTML = teamNode.childNodes[21].textContent;
	document.getElementById('homeP4Score').innerHTML = teamNode.childNodes[22].textContent;
	document.getElementById('homeP5Score').innerHTML = teamNode.childNodes[23].textContent;
	document.getElementById('homeOtScore').innerHTML = teamNode.childNodes[24].textContent;

	if ( teamNode.childNodes[26].textContent == 'true' ) {
		
		document.getElementById('homeScore'  ).innerHTML = '<em>' + teamNode.childNodes[25].textContent + '</em>';
	}
	else {
		
		document.getElementById('homeScore'  ).innerHTML = teamNode.childNodes[25].textContent;
	}

	updateTeamTotals( document.getElementById('homeTotals'), teamNode );

	pageElement = document.getElementById('homePlayers');

	for ( i = 0; i < teamNode.childNodes[27].childNodes.length; ++i ) {

		playerNode = teamNode.childNodes[27].childNodes[i];

		updatePlayerTotals( pageElement, playerNode );
	}

	resetDirection( 1 );

	sortTable( 'homePlayers', document.getElementById('homePtsSort') );

	// road team
	teamNode = response.getElementsByTagName('roadTeam')[0];

	document.getElementById('roadP1Score').innerHTML = teamNode.childNodes[19].textContent;
	document.getElementById('roadP2Score').innerHTML = teamNode.childNodes[20].textContent;
	document.getElementById('roadP3Score').innerHTML = teamNode.childNodes[21].textContent;
	document.getElementById('roadP4Score').innerHTML = teamNode.childNodes[22].textContent;
	document.getElementById('roadP5Score').innerHTML = teamNode.childNodes[23].textContent;
	document.getElementById('roadOtScore').innerHTML = teamNode.childNodes[24].textContent;
	
	if ( teamNode.childNodes[26].textContent == 'true' ) {
		
		document.getElementById('roadScore'  ).innerHTML = '<em>' + teamNode.childNodes[25].textContent + '</em>';
	}
	else {
		
		document.getElementById('roadScore'  ).innerHTML = teamNode.childNodes[25].textContent;
	}

	updateTeamTotals( document.getElementById('roadTotals'), teamNode );

	pageElement = document.getElementById('roadPlayers');

	for ( i = 0; i < teamNode.childNodes[27].childNodes.length; ++i ) {

		playerNode = teamNode.childNodes[27].childNodes[i];

		updatePlayerTotals( pageElement, playerNode );
	}

	resetDirection( 1 );

	sortTable( 'roadPlayers', document.getElementById('roadPtsSort') );
}

function initGame( gameId, seq, prd, ot, poss, time, clock ) {

	state_sequence = parseInt( seq  );
	period         = parseInt( prd  );
	possession     = parseInt( poss );
	time_remaining = parseInt( time );
	
	overtime       = (ot    == 'true');
	clock_stopped  = (clock == 'true');
	
	setAjaxURL( '/natc/Game.do?game_id=' + gameId + '&ajax=1' );
	setAjaxCallback( updateStats );

	window.setInterval( 'sendAjaxRequest()', 15000 );
	window.setInterval( 'manageClock()', 1000 );

	displayTime();
	displayPeriod();
	updatePossession();
}
