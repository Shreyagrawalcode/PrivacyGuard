package com.example.myapplication

val permissionRiskMap = mapOf(
    "android.permission.CAMERA"                 to RiskLevel.HIGH,
    "android.permission.RECORD_AUDIO"           to RiskLevel.HIGH,
    "android.permission.ACCESS_FINE_LOCATION"   to RiskLevel.HIGH,
    "android.permission.ACCESS_COARSE_LOCATION" to RiskLevel.HIGH,
    "android.permission.READ_CONTACTS"          to RiskLevel.HIGH,
    "android.permission.READ_SMS"               to RiskLevel.HIGH,
    "android.permission.SEND_SMS"               to RiskLevel.HIGH,
    "android.permission.READ_CALL_LOG"          to RiskLevel.MEDIUM,
    "android.permission.GET_ACCOUNTS"           to RiskLevel.MEDIUM,
    "android.permission.READ_EXTERNAL_STORAGE"  to RiskLevel.MEDIUM,
    "android.permission.WRITE_EXTERNAL_STORAGE" to RiskLevel.MEDIUM,
)

fun getAppRiskLevel(permissions: List<String>): RiskLevel {
    return permissions
        .mapNotNull { permissionRiskMap[it] }
        .maxByOrNull { it.ordinal }
        ?: RiskLevel.LOW
}