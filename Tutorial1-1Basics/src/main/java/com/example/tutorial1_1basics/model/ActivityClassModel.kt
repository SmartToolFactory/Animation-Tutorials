package com.example.tutorial1_1basics.model

data class ActivityClassModel(val clazz: Class<*>, val description: String = clazz.name)
