spa.shell = (function(){
	var configMap = {
			/** 定义给uriAnchor使用的映射，用于验证*/
			anchor_schema_map: {
				chat: {open: true, closed: true}
			},
			main_html : String()
				+ '<div class="spa-shell-head">'
					+ '<div class="spa-shell-head-logo"></div>'
					+ '<div class="spa-shell-head-acct"></div>'
					+ '<div class="spa-shell-head-search"></div>'
				+ '</div>'
					+ '<div class="spa-shell-main">'
					+ '<div class="spa-shell-main-nav"></div>'
					+ '<div class="spa-shell-main-content"></div>'
				+ '</div>'
				+ '<div class="spa-shell-foot"></div>'
				+ '<div class="spa-shell-chat"></div>'
				+ '<div class="spa-shell-modal"></div>',
				
			chat_extend_time : 250,
			chat_retract_time : 300,
			chat_extend_height : 450,
			chat_retract_height : 15,
			chat_extended_title : 'Click to retract',
			chat_retracted_title : 'Click to extend'
			
	},
	stateMap = { 
		$container : null,
		/** 将当前锚的值保存在表示模块状态的映射中*/
		anchor_map : {},
		is_chat_retracted : true
	},
	jqueryMap = {},
	copyAnchorMap, setJqueryMap, toggleChat, 
	changeAnchorPart, onHashchange,
	onClickChat, initModule;
	/** 使用jquery的extend()工具方法来复制对象。这是必须的，因为所有的JavaScript对象都是按引用传递的，
	 * 正确地复制一个对象不是件容易的事。*/
	copyAnchorMap = function(){
		return $.extend(true, {}, stateMap.anchor_map);
	};
	/**对锚进行原子更新。它接受一个映射，是想更改的内容，比如{chat:'open'}，只会更新锚组件中的这个指定键所对应的值*/
	changeAnchorPart = function(arg_map){
		var
			anchor_map_revise = copyAnchorMap(),
			bool_return = true,
			key_name, key_name_dep;
		KEYVAL:
		for(key_name in arg_map){
			if(arg_map.hasOwnProperty(key_name)){
				if(key_name.indexOf('_') === 0){
					continue KEYVAL;
				}
				anchor_map_revise[key_name] = arg_map[key_name];
				key_name_dep = '_'+key_name;
				if(arg_map[key_name_dep]){
					anchor_map_revise[key_name_dep] = arg_map[key_name_dep];
				}else{
					delete anchor_map_revise[key_name_dep];
					delete anchor_map_revise['_s'+key_name_dep];
				}
				
			}
		}
		/** 如果不能通过模式(schema)验证就不设置锚(uriAnchor会抛出异常)。当发生这样的情况时，把锚组件回滚到它之前的状态。*/
		try{
			$.uriAnchor.setAnchor(anchor_map_revise);
		}catch(error){
			$.uriAnchor.setAnchor(stateMap.anchor_map, null, true);
			bool_return = false;
		}
		return bool_return;
	};
	
	/** 添加onHashchange事件处理程序来处理URI锚变化 使用uriAnchor插件来将锚转换为映射，与之前的状态比较，以便确定要采取的动作。
	 * 如果提议的锚变化是无效的，则将锚重置为之前的值 */
	onHashchange = function(event){
		var anchor_map_previous = copyAnchorMap();
		anchor_map_proposed,
		_s_chat_previous, _s_chat_proposed,
		s_chat_proposed;
		try{anchor_map_proposed = $.uriAnchor.makeAnchorMap(); }
		catch(error){
			$.uriAnchor.setAnchor(anchor_map_previous, null, true);
			return false;
		}
		stateMap.anchor_map = anchor_map_proposed;
		
		_s_chat_previous = anchor_map_previous._s_chat;
		_s_chat_proposed = anchor_map_proposed._s_chat;
		
		if(!anchor_map_previous || _s_chat_previous !== _s_chat_proposed){
			s_chat_proposed = anchor_map_proposed.chat;
			switch(s_chat_proposed){
				case 'open':
					toggleChat(true);
					break;
				case 'closed':
					toggleChat(false);
					break;
				default:
					toggleChat(false);
					delete anchor_map_proposed.chat;
					$.uriAnchor.setAnchor(anchor_map_proposed, null, true);
			}
		}
		return false;
	};
	
	/** 只修改锚的chat参数 */
	onClickChat = function(event){
		changeAnchorPart({
			chat: (stateMap.is_chat_retracted ? 'open' : 'closed' )
		});
		return false;
	}
	
	
	setJqueryMap = function(){
		var $container = stateMap.$container;
		jqueryMap = { 
				$container : $container,
				$chat : $container.find('.spa-shell-chat')
		};
	};
	
	toggleChat = function(do_extend, callback){
		var px_chat_ht = jqueryMap.$chat.height(),
			is_open = px_chat_ht === configMap.chat_extend_height,
			is_close = px_chat_ht === configMap.chat_retract_height,
			is_sliding = !is_open && !is_close;
		
		if(is_sliding){
			return false;
		}
		
		if(do_extend){
			jqueryMap.$chat.animate(
				{ height : configMap.chat_extend_height },
				configMap.chat_extend_time,
				function(){
					jqueryMap.$chat.attr(
						'title', configMap.chat_extended_title
					);
					stateMap.is_chat_retracted = false;
					if(callback){
						callback(jqueryMap.$chat);
					}
				}
			);
			return true;
		}
		
		jqueryMap.$chat.animate(
				{ height : configMap.chat_retract_height },
				configMap.chat_retract_time,
				function(){
					jqueryMap.$chat.attr(
						'title', configMap.chat_retracted_title
					);
					stateMap.is_chat_retracted = true;
					if(callback){
						callback(jqueryMap.$chat);
					}
				}
		);
		return true;
	};
	
	onClickChat = function(event){
		if(toggleChat(stateMap.is_chat_retracted)){
			$.uriAnchor.setAnchor({
				chat: (stateMap.is_chat_retracted?'open':'closed')
			});
		}
		return false;
	};
	
	initModule = function($container){
		stateMap.$container = $container;
		$container.html(configMap.main_html);
		setJqueryMap();
		
		stateMap.is_chat_retracted = true;
		jqueryMap.$chat
			.attr('title',configMap.chat_retracted_title)
			.click(onClickChat);
		/** 配置uriAnchor插件，用于检测模式(schema)*/
		$.uriAnchor.configModule({
			schema_map: configMap.anchor_shema_map
		});
		
		spa.chat.configModule({});
		spa.chat.initModule( jqueryMap.$chat );
		
		/** 绑定hashchage事件处理程序并立即触发它，这样模块在初始加载时就会处理书签*/
		$(window)
			.bind('hashchange', onHashchange)
			.trigger('hashchange');
//		setTimeout(function(){toggleChat(true);}, 3000);
//		
//		setTimeout(function(){toggleChat(false);}, 8000);
	};
	
	return { initModule : initModule };
}());