/*var spa = (function(){
				var initModule = function ($container) {
					$container.html(
						'<h1 style="display:inline-block; margin:25px;">'
							+ 'hello world!'
							+ '</h1>'
					);
				};
				
				return { initModule: initModule };
			}());*/

var app_url = $(function(){  
    //获取当前网址，如： http://localhost:80/ybzx/index.jsp  
    var curPath=window.document.location.href;  
    //获取主机地址之后的目录，如： ybzx/index.jsp  
    var pathName=window.document.location.pathname;  
    var pos=curPath.indexOf(pathName);  
    //获取主机地址，如： http://localhost:80  
    var localhostPaht=curPath.substring(0,pos);  
    //获取带"/"的项目名，如：/ybzx
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);  
    var path = localhostPaht+projectName;
    console.log(path);
    Window.appUrl = path;
    return path;
   
}); 

var spa = (function(){
	var initModule = function($container){
		spa.shell.initModule($container);
	};
	return { initModule : initModule };
}());