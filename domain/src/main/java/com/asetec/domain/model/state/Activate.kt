package com.asetec.domain.model.state

import kotlinx.serialization.Serializable

@Serializable
data class Activate(
    val index: String = "",
    val name: String = "",
    val description: String = "",
    val assets: String = "",

    /**
     * Google ID
     */
    val googleId: String = "",

    /** 활동 종류 **/
    var activateResId: Int = 2131165314,
    var activateName: String = "달리기",

    /** 측정 상태 **/
    var activateButtonName: String = "측정하기!",

    /** 만보기 걸음 수 **/
    var prevPedometerCount: Int = 0,
    var pedometerCount: Int = 0,

    /**
     * 현재 러닝 진행 중인지 상태를 보여줌
     */
    var showRunningStatus: Boolean = false,
    var isRunning: Boolean = false,

    /**
     * 이모티콘
     */
    var statusIcon: Int = 0,
    var statusName: String = "",

    /**
     *
     */
    var runningTitle: String = "",

    /**
     * 시간
     */
    var time: Long = 0L

): ActivityType