package org.example.models

import org.example.enums.Achievement

abstract class AchievementTemplate {
    abstract val achievement: Achievement
    
    /**
     * Проверяет, получено ли достижение пользователем
     * @param username имя пользователя
     * @return true если достижение получено, false в противном случае
     */
    abstract suspend fun isAchieved(username: String): Boolean
} 