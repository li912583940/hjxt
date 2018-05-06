spa.chat = (function(){
	var configMap = {
		main_html : String()
			+ '<div style="padding:lem; color:#fff;">'
			+ 'Say hello to chat'
			+ '</div>',
		
		settable_map: {}	
	},
	
	stateMap = {$container: null},
	jqueryMap = {},
	
	setJqueryMap, configModule, initModule;
	
	setJqueryMap = function(){
		var $container = stateMap.$container;
		jqueryMap = {$container: $container};
	};
	/** 创建configModule方法。每当功能模块接受设置(setting)时，我们总是使用相同的方法名和同一个spa.util.setConfigMap工具方法*/
	configModule = function(input_map){
		spa.util.setConfigMap({
			input_map : input_map,
			settable_map : configMap.settable_map,
			config_map : configMap
		});
		return true;
	};
	
	/** 添加initModule方法。几乎所有的模块都有这个方法。由它开始执行模块*/
	initModule = function($container){
		$container.html(configMap.main_html);
		stateMap.$container = $container;
		setJqueryMap();
		return true;
	};
	
	return{
		configModule : configModule,
		initModule : initModule
	};
}());