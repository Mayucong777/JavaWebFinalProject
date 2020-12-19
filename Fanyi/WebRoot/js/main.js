

function putAnswer(answer){
	$("#transTargetText").text(answer);
}

function clearAnswer(){
	$("#transTargetText").text("");
}

function translate(){
	clearAnswer();
	var fromto = $("span.select-text").attr("id");
	var str = fromto.split("2");
	var from = str[0];
	var to = str[1];
	
	var inputOriginal = document.getElementById("inputOriginal").value;

	$.ajax({
		url: "fanyi.do",
		type: "post",
		data: {query:inputOriginal,from:from,to:to},
		dataType: "json",
		success: function(response) {			
			putAnswer(response.translation);
		}
	});
}

$(document).ready(function() {
	$("#inputOriginal").focus(function(){
		$(this).css("border","none");
		$("#fanyi__input__bg").css("border","1px solid #7fa1c8");
	});
	
	$("#transMachine").click(function(){
		translate();
	});
	
	$("#langSelect").click(function(){
		if($("#languageSelect").css("display")=="none"){
			$("#languageSelect").css("display","block");
		}else{
			$("#languageSelect").css("display","none");
		}
	});
	
	$("#langSelect").on("click","ul li",function(){
		$("span.select-text").text($(this).text());
		$("span.select-text").attr("id",$(this).attr("data-value"));
	});
	
	
});