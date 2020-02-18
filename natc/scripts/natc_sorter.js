var direction     = 0;
var lastColumn    = 0;
var lastElementId = "";

function resetDirection( dir ) {
	
	lastColumn    = 0;
	lastElementId = "";
	direction     = dir;
}

function normalizeString( str ) {

	str = str.replace( new RegExp("^\\s*|\\s*$", "g"), " " ); // replace multiple whitespace with single space
	str = str.replace( new RegExp("\\s\\s+",     "g"), ""  ); // remove leading or trailing whitespace

	return str;
}

function getTextValue( element ) {

	var str = "";

	for ( var i = 0; i < element.childNodes.length; ++i ) {

		if      ( element.childNodes[i].nodeType == document.TEXT_NODE       ) str += element.childNodes[i].nodeValue;
		else if ( element.childNodes[i].nodeType == document.ELEMENT_NODE &&
				  element.childNodes[i].tagName  == "BR"                     ) str += " ";
		else if ( element.childNodes[i].nodeType == document.ELEMENT_NODE &&
				  element.childNodes[i].tagName  == "IMG"                    ) str += element.childNodes[i].id;
		else                                                                   str += getTextValue( element.childNodes[i] );
	}

	return normalizeString( str );
}

function compareValues( v1, v2 ) {

	if ( v1.indexOf( ":" ) < 0 ) {

		var f1 = parseFloat( v1 );
		var f2 = parseFloat( v2 );

		if ( ! isNaN( f1 )  &&  ! isNaN( f2 ) ) {

			v1 = f1;
			v2 = f2;
		}
	}

	// Compare the two values.
	if (v1 == v2) return 0;
	if (v1 >  v2) return (direction == 0) ? 1 : -1;

	return (direction == 0) ? -1 : 1;
}

function findTBody( element ) {

	while ( element.nodeType != document.ELEMENT_NODE  ||  element.tagName != "TABLE" ) {

		element = element.parentNode;
	}

	for ( var i = 0; i < element.tBodies.length; ++i ) {

		if ( element.tBodies[i].id != "" ) {

			return element.tBodies[i].id;
		}
	}
}

function sortTable( tbodyId, element ) {

	if   ( tbodyId == null ) elementId = findTBody( element );
	else                     elementId = tbodyId;
	
	column = element.parentNode.cellIndex

	if ( lastElementId == elementId  &&  lastColumn == column ) direction = (direction == 0) ? 1 : 0;
	else                                                        direction = 1;

	var plist = document.getElementById( elementId );

	var tmpEl;
	var cmp;

	for (var i = 0; i < plist.rows.length - 1; ++i ) {

		if ( plist.rows[i].className == "totals" ) continue;
		
		var minIndex = i;
		var minValue = getTextValue( plist.rows[i].cells[column] );

		for ( var j = i + 1; j < plist.rows.length; ++j ) {

			if ( plist.rows[j].className == "totals" ) continue;
			
			var nextValue = getTextValue( plist.rows[j].cells[column] );

			if ( compareValues( minValue, nextValue ) > 0 ) {

				minIndex = j;
				minValue = nextValue;
			}
		}

		if ( minIndex > i ) {

			var element = plist.removeChild( plist.rows[minIndex] );

			plist.insertBefore( element, plist.rows[i] );
		}
	}

	lastElementId = elementId;
	lastColumn    = column;

	return false;
}
