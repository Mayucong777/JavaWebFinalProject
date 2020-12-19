<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
	<head>
		<title>WTU翻译网</title>
		<link rel="stylesheet" href="./css/main.css" />
		<script src="js/jquery-3.5.1.min.js" type="text/javascript"></script>
		<script src="js/main.js" type="text/javascript" charset="utf-8"></script>
	</head>
	<body>
		<ul>
			<li style="background-color:white; borader:left;"><img src="./imgs/logo2.png" />&nbsp;</li>
			<li><a class="active" href="#home">WTU中英文翻译网</a></li>
		</ul>
		<HR style="border:3 double #987cb9" width="100%" color=#987cb9 SIZE=3>
		<div class="fanyi" style="min-height: 137px;">
			<div class="fanyi__botton">
				<div class="fanyi__botton__left">
					<div id="langSelect" class="lang-select item-select top">
						<span class="select-text" id="zh-CHS2en">自动检测语言</span>
						<ul id="languageSelect" class="select clear" style="display: none;">
							<li class="default selected" data-value="zh-CHS2en"><a href="javascript:;">自动检测语言</a></li>
							<li data-value="zh-CHS2en"><a href="javascript:;">中文&nbsp; » &nbsp;英语</a></li>
							<li data-value="en2zh-CHS"><a href="javascript:;">英语&nbsp; » &nbsp;中文</a></li>
						</ul>
						<input class="select-input" id="language" name="language" type="hidden" value="AUTO">
					</div>
					<a class="fanyi__operations--machine" id="transMachine">翻译</a>
				</div>
			</div>

			<div class="fanyi__input" style="min-height: 137px;">
				<div class="input__original">
					<div class="fanyi__input__bg">
						<div id="docUploadBg" class="doc__upload--bg hidden">
							<span class="doc-type"></span>
							<div class="doc-infos">
								<p class="doc-name"></p>
								<span class="doc-error-msg hidden"></span>
								<span class="doc-size-msg hidden"></span>
							</div>
							<a class="doc-delete" href="javascript:;"></a>
						</div>
						<a id="inputDelete" class="input__original_delete"></a>
						<div id="inputOriginalCopy" class="input__original__area"></div>
						<textarea id="inputOriginal" name="inputOriginal" dir="auto" class="input__original__area" placeholder="请输入你要翻译的内容" style="font-size: 24px; line-height: 30px; height: auto;"></textarea>
						<div class="input__original__bar" style="visibility: hidden;">
							<div class="input__original__bar--fonts">
								<span class="fonts__over">0</span>/<span class="fonts__limited">5000</span>
							</div>
							<a href="javascript:;" id="originalSpeaker" class="speaker">
								<div class="tips__container speaker__tips">
									<span class="tips__pointer tips__pointer--down"></span>
									<span class="tips__text--short">朗读</span>
								</div>
							</a>
						</div>
					</div>
				</div>
				<div class="input__target">
					<div class="fanyi__input__bg">
						<div class="input__target__error" id="inputTargetError"></div>
						<div id="transTarget" dir="auto" class="input__target__text" style="height: 156px;">
							<p><span id="transTargetText">
							</span></p>
						</div>
						
						<div class="input__target__bar">
							<a class="target__bar__update" id="updateResult">修改翻译结果</a>

							<a href="javascript:;" id="targetCopy" class="copy target__bar__parts">
								<div class="tips__container speaker__tips">
									<span class="tips__pointer tips__pointer--down"></span>
									<span class="tips__text--short">复制</span>
								</div>
							</a>
						</div>
						<div class="input__target__dict">
							<span class="resource">来自有道词典结果</span>
							<div class="dict__word">
								<span class="dict__word--letters">美丽</span>
								<span class="dict__word--phonetic">[měi lì]</span>
							</div>
							<div class="dict__relative">
								<a>comeliness</a>
								<a>fairness</a>
								<a>goodliness</a>
								<a>loveliness</a>
							</div>
							<a class="dict__more clog-js" data-clog="RESULT_DICT_ALL_CLICK" href="javascript:;" target="_blank">查看完整结果&gt;&gt;</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
