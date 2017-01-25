function confirmOperation() {
	return confirm('Are you sure?');
}

function addTagToTextArea(textAreaId, tagBegin, tagEnd) {
	var area = document.getElementById(textAreaId);
	var text = area.value;
	var textOut = "";
		
	if (typeof(area.selectionStart) == "number") {
		var start = area.selectionStart;
		var end = area.selectionEnd;
		
		if (start <= end) {
			textOut = text.substring(0,start) + "<" + tagBegin + ">" + text.substring(start,end);
			textOut = textOut +"</" + tagEnd + ">" + text.substring(end, text.length);
		} 
	} 
	
	if (textOut == "") {
		text = text + "<" + tagBegin + "></" + tagEnd + ">";
	}
		
	document.getElementById(textAreaId).value = textOut;
}

function addVoidTagToTextArea(textAreaId, tagText) {
	var area = document.getElementById(textAreaId);
	var text = area.value;
	var textOut = "";
		
	if (typeof(area.selectionStart) == "number") {
		var start = area.selectionStart;
		
		textOut = text.substring(0,start) + "<" + tagText + ">" + text.substring(start, text.length); 
	} 
	
	if (textOut == "") {
		text = text + "<" + tagText + ">";
	}
		
	document.getElementById(textAreaId).value = textOut;
}