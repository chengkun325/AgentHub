package com.chengkun.agenthub.constant;

public interface ScheduleConstant {

    int MISFIRE_DEFAULT = 0; // 默认的误触发策略。

    int MISFIRE_IGNORE_MISFIRES = 1; // 忽略误触发，立即执行下一次调度。

    int MISFIRE_FIRE_AND_PROCEED = 2; // 执行错过的任务，然后继续执行下一次调度。

    int MISFIRE_DO_NOTHING = 3; // 不执行错过的任务，仅执行下一次调度。

    String TASK_CLASS_NAME = "TASK_CLASS_NAME"; // 表示任务类名的键名，用于标识具体执行任务的类。

    String TASK_PROPERTIES = "TASK_PROPERTIES"; // 表示任务属性的键名，用于存储任务相关的配置信息。

}
