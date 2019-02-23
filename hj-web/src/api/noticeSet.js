// 验证通知设置
import request from '@/utils/request'


export const FindNotice = params => { return request.get('/sysNoticeConf/findNotice', { params: params } ).then(res => res) }

export const NoticeChange = params => { return request.post('/sysNoticeConf/noticeChange', params ).then(res => res) }
