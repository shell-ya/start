package com.starnft.star.interfaces.common;


import lombok.extern.slf4j.Slf4j;

/**
 * Created by Caychen on 2019-08-19。
 */
@Slf4j
public enum StarError {

    //common
    SUCCESS_000000("000000", "SUCCESS"),

    UNKNOWN_ERROR("600000", "未定义的错误"),
    ALGORITHM_EMPTY_ERROR("600001", "加密算法为空"),
    PASSWORD_EMPTY_ERROR("600002", "原始密码为空"),
    ENCRYPT_ERROR("600003", "加密失败"),
    INQUIRY_AD_ERROR("600004", "查无数据"),
    ID_EMPTY_ERROR("600005", "主键id为空"),
    INQUIRY_WELCOME_PAGE_ERROR("600006", "查无数据"),
    CALL_REMOTE_SERVER_ERROR("600007", "远程调用失败"),
    INVALID_PARAMETER_ERROR("600008", "参数错误"),
    INTERNAL_SERVER_ERROR("600009", "服务器内部错误"),
    NULL_POINTER_ERROR("600010", "空指针异常"),
    REMOTE_SERVER_TIME_OUT_ERROR("600011", "远程服务连接超时"),
    VALIDATION_ERROR("600012", "字段验证异常"),
    REQUEST_METHOD_NOT_SUPPORT_ERROR("600013", "请求方式不支持"),
    SQL_SYNTAX_ERROR("600014", "执行SQL异常"),
    RES_PARAM_IS_NULL_ERROR("600015", "请求参数为空"),
    PARAMETER_INVALID_ERROR("600016", "参数不正确，请检查后再试"),
    FILE_PARSER_ERROR("600017", "上传文件解析失败"),
    FILE_UPLOAD_ERROR("600018", "文件上传失败"),
    TOKEN_NOT_EXISTS_ERROR("600019", "token不存在"),
    TOKEN_EXPIRED_ERROR("600020", "token已失效，请重新登录"),
    ACCOUNT_TOKEN_DECRYPT_ERROR("600021", "decryptToken error"),
    ROLE_IS_DISABLE_ERROR("600022", "角色已失效，请切换角色或重新登录"),
    ROLE_NOT_EXISTS_ERROR("600023", "角色不存在，无法跳转"),
    ACCOUNT_LOGIN_OTHER_ERROR("600024", "该账户已在其它地方登录"),
    ACCOUNT_NAME_IS_BLANK_ERROR("600025", "username不存在"),
    FILE_TYPE_ERROR("6000261", "文件类型错误"),
    FILE_NAME_MUST_BE_NOT_NULL("6000262", "文件名称不可为空"),
    FILE_CAN_NOT_EXCEED_100MB("6000263", "文件不可超过100M"),
    CALL_LINUX_SHELL_COMMAND_ERROR("600027", "调用Linux命令异常"),

    //com.starnft.star.interfaces.service,
    ADD_FEEDBACK_ERROR("610001", "新增意见反馈失败"),
    QUERY_APP_VERSION_ERROR("610002", "查询app版本失败"),
    MOBILE_EMPTY_ERROR("610003", "手机号为空"),
    NAME_EMPTY_ERROR("610004", "姓名为空"),
    INQUIRY_USER_ERROR("610005", "查询用户失败"),
    INQUIRY_WEATHER_ERROR("610006", "查询天气失败"),
    INQUIRY_GALA_ERROR("610007", "查询节日失败"),
    ROLE_ID_EMPTY_ERROR("610008", "角色id为空"),
    DEVICE_NOT_EXIST_ERROR("610009", "设备不存在"),//勿改!!! 勿改!!! 勿改!!!
    ROLE_NOT_EXIST_ERROR("610010", "当前查询的角色不存在"),
    VERSION_ID_NONENTITY_ERROR("610011", "版本ID不存在"),
    VIN_NONENTITY_ERROR("610012", "vin不存在"),
    VERSION_NAME_NONENTITY_ERROR("610013", "版本名称不存在"),
    ID_OR_VIN_IS_NUll_ERROR("610014", "beanId或vin码为空"),
    REQUEST_PARAM_FORMAT_ERROR("610015", "请求参数beanSettings格式错误,格式为JSON格式"),

    NO_PERMISSION_ERROR("610900", "您没有该操作权限，请联系管理员"),

    //需求模板导入
    STORY_MAXIMUM_LIMIT_EXCEEDED("613000", "导入记录不允许大于1000行,请修改后再导入"),
    STORY_FORMAT_ERROR("613001", "导入文档中存在不符合规范的数据，请【导出】修改！"),
    STORY_IMPORT_GWID_ERROR("613002", "GWID格式错误"),
    STORY_GWID_CHECK_ERROR("613003", "GWID校验不通过!"),
    STORY_OPERATION_ERROR("613004", "导入的需求不符合规则"),
    STORY_GWID_NOT_FOUND("613005", "导入的用户id不存在！"),
    EXCEL_ANALYSIS_EXCEPTION("613006", "导入文件解析错误！"),
    EXCEL_ERROR("613007", "导入模板错误"),
    STORY_IS_NULL("613008", "导入需求不能为空"),
    STORY_GWID("613009", "GWID"),

    //file upload
    MAX_UPLOAD_SIZE_EXCEEDED("613011", "上传文件过大"),

    STORY_STATUS_ILLEGAL_ERROR("613012", "需求状态值非法"),
    STORY_STATUS_TRANSFORM_ERROR("613013", "需求状态转换非法"),
    STORY_ATTACH_OVER_LIMIT_ERROR("613014", "需求附件超过最大限制"),
    COUNTRY_CODE_ILLEGAL_ERROR("613015", "国家代码非法"),
    FUNCTION_TYPE_ILLEGAL_ERROR("613016", "功能类型值非法"),
    STORY_TYPE_ILLEGAL_ERROR("613017", "需求类型值非法"),
    PRIORITY_LEVEL_ILLEGAL_ERROR("613018", "优先级值非法"),
    FILE_SIZE_OVER_LIMIT("613019", "文件大小超过限制"),
    FILE_TYPE_NOT_SUPPORTED("613019", "文件类型不支持"),
    QUERY_COUNTRY_DATA_FAILED("613020", "查询国家信息失败"),
    RELEASE_NOT_EXIST("613021", "发版工单信息不存在"),
    ONLY_ALLOWED_MODIFY_OWN_DRAFTS("613022", "只允许修改自己的草稿"),
    ONLY_ALLOWED_MODIFY_DRAFTS_NOT_STARTED("613023", "只允许修改未开启流程的草稿"),
    NO_RELEASE_SUBMIT_PRIVILEGE("613024", "没有发版工单提交权限"),
    PROJECT_NOT_EXIST("613025", "项目信息不存在"),
    TASK_INSTANCE_NOT_EXIST("613026", "用户任务不存在"),
    RELEASE_NODE_ERROR("613027", "该节点不允许驳回:请操作通过或总止"),
    TERMINATE_ERROR("613028","您不是需求处理人，没有退回权限！"),
    TERMINATE_STATUS_ERROR("613029","只有待处理状态的需求可以退回！"),
    DEMAND_EMPTY("613030","需求不存在"),
    DELETE_ERROR("613031","您不是需求创建人，没有删除权限"),
    DELETE_STATUS_ERROR("613032","只有待处理和已退回状态的需求可以删除！"),
    SITE_CODE_ILLEGAL_ERROR("613033", "站点代码非法"),
    NO_NEED_TO_EXPORT("613034", "查询需求信息为空，无需导出"),
    STORY_DATA_EXPORT_OVERLIMIT("613035", "需求导出数据超出闲置"),
    STORY_DATA_EXPORT_BLOCKED("613036", "需求导出请求过多，请稍后再试"),
    NO_PROJECT_DELETE_PRIVILEGE("613037","您不是项目创建人，没有删除权限"),
    QUERY_SITE_DATA_FAILED("613038", "查询站点信息失败"),
    PROJECT_DOCUMENT_NOT_EXIST("613039", "项目文档信息不存在"),
    NO_PROJECT_DOCUMENT_DELETE_PRIVILEGE("613040","您不是项目文档创建人，没有删除权限"),
    TASK_NOT_EXIST("613041", "任务信息不存在"),
	TASK_DEPTH_OVER_LIMIT_ERROR("613042", "只能创建四级任务"),
    PROCESS_START_FAILED("613043","流程启动失败,请稍后重试"),
    TASK_START_TIME_ERROR("613044","任务的开始时间不能早于项目"),
    TASK_END_TIME_ERROR("613045","任务的结束时间不能晚于项目"),
    CAR_MODEL_PLAN_NOT_EXIST("613046", "车型计划信息不存在"),
    QUERY_DEPT_LEADER_FAILED("613047", "查询部门负责人失败"),
    PROJECT_PENDING_REVIEW("613048","项目处于待审核,不能创建任务"),
    TASK_LOG_ERROR("613049","此任务已添加日志，无法创建子任务"),
    QUERY_INVOLVED_PROJECT_ERROR("613050","查询我参与的项目失败"),
    NO_INVOLVED_PROJECT_ERROR("613051","您还没有参与的项目"),
    QUERY_USER_BY_DEPT_ERROR("613052", "查询部门人员失败"),
    TASK_DURATION_TIME_ERROR("613053","任务工期不能超过每天12h"),
    QUERY_WORKDAY_INFO_ERROR("613054","查询工作日信息失败"),
    QUERY_PROJECT_INFO_ERROR("613055","查询项目详情失败"),
    QUERY_PROJECT_MAIL("613056","查询项目详情失败"),
    ;

    private String NS = "";

    private String code;

    private String desc;

    StarError(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getNamespace() {
        return NS;
    }

    public String getErrorCode() {
        return code;
    }

    public String getErrorMessage() {
        return desc;
    }
}