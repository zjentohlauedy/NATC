var ajax_timer = null;
var finished   = false;

function updateGameScore( gameData ) {
	
	var node;
	var gameTable;
	var column;
	var text;
	
	node = gameData.getElementsByTagName('gameId')[0];
	
	gameTable = document.getElementById( 'g' + node.textContent );
	
	column = gameTable.rows[0].cells[1];
	
	if ( gameData.getElementsByTagName('roadWin')[0].textContent = "true" ) {
		
		column.childNodes[1].innerHTML = '<em>' + gameData.getElementsByTagName('roadScore')[0].textContent + '<em>';
	}
	else {
		
		column.childNodes[1].innerHTML = gameData.getElementsByTagName('roadScore')[0].textContent;
	}
	
	column = gameTable.rows[1].cells[1];

	if ( gameData.getElementsByTagName('roadWin')[0].textContent = "true" ) {
		
		column.childNodes[1].innerHTML = '<em>' + gameData.getElementsByTagName('roadScore')[0].textContent + '<em>';
	}
	else {
		
		column.childNodes[1].innerHTML = gameData.getElementsByTagName('roadScore')[0].textContent;
	}

	node = gameData.getElementsByTagName('state')[0];
	
	text = column.childNodes[1].innerHTML;
	
	if (    gameData.getElementsByTagName('roadWin')[0].textContent = "true" ||
			gameData.getElementsByTagName('homeWin')[0].textContent = "true"    ) {
	
		// overtime
		if ( node.childNodes[3].textContent == "true" ) {
		
			column.childNodes[1].innerHTML = text + '<h7>FOT</h7>';
		}
		else {
		
			column.childNodes[1].innerHTML = text + '<h7>F</h7>';
		}
	}
	else {

		finished = false;
		
		// overtime
		if ( node.childNodes[3].textContent == "true" ) {
		
			column.childNodes[1].innerHTML = text + '<h7>OT</h7>';
		}
		else {
		
			column.childNodes[1].innerHTML = text + '<h7>' + node.childNodes[2].textContent + '</h7>';
		}
	}
}

function updateScores( response ) {

	var gameNode;
	var i;
	
	finished = true;
	
	for ( i = 0; i < response.childNodes.length; ++i ) {

		gameNode = teamNode.childNodes[i];

		updateGameScore( gameNode );
	}
	
	if ( finished == true ) {
		
		window.clearInterval( ajax_timer );
	}
}

function initGames() {

	setAjaxURL( '/natc/Games.do?ajax=1' );
	setAjaxCallback( updateScores );

	ajax_timer  = window.setInterval( 'sendAjaxRequest()', 15000 );
}