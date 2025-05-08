package org.example
import org.example.models.*
object UserGoalsRepository {

    /* upsert‑style create/replace */
    fun saveGoal(goal: UserGoal): Boolean {
        val sql = """
            INSERT INTO user_goals (user_id, goal_type, activity_level, weekly_target,
                                    calorie_goal, water_goal, steps_goal, bju_goal)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            ON CONFLICT (user_id)               -- same user => replace
            DO UPDATE SET
                goal_type      = EXCLUDED.goal_type,
                activity_level = EXCLUDED.activity_level,
                weekly_target  = EXCLUDED.weekly_target,
                calorie_goal   = EXCLUDED.calorie_goal,
                water_goal     = EXCLUDED.water_goal,
                steps_goal     = EXCLUDED.steps_goal,
                bju_goal       = EXCLUDED.bju_goal
        """
        Database.getConnection().use { c ->
            c.prepareStatement(sql).use { st ->
                st.setString(1, goal.user_id);   st.setString(2, goal.goal_type)
                st.setString(3, goal.activity_level); st.setDouble(4, goal.weekly_target)
                st.setInt(5, goal.calorie_goal); st.setInt(6, goal.water_goal)
                st.setInt(7, goal.steps_goal);   st.setString(8, goal.bju_goal)
                return st.executeUpdate() > 0
            }
        }
    }

    fun getGoal(userId: String): UserGoal? =
        Database.getConnection().use { c ->
            c.prepareStatement("SELECT * FROM user_goals WHERE user_id = ?").use { st ->
                st.setString(1, userId)
                st.executeQuery().let { rs ->
                    if (rs.next()) rs.toEntity() else null
                }
            }
        }

    fun deleteGoal(userId: String): Boolean =
        Database.getConnection().use { c ->
            c.prepareStatement("DELETE FROM user_goals WHERE user_id = ?").use { st ->
                st.setString(1, userId)
                st.executeUpdate() > 0
            }
        }

    private fun java.sql.ResultSet.toEntity() = UserGoal(
        user_id       = getString("user_id"),
        goal_type     = getString("goal_type"),
        activity_level= getString("activity_level"),
        weekly_target = getDouble("weekly_target"),
        calorie_goal  = getInt("calorie_goal"),
        water_goal    = getInt("water_goal"),
        steps_goal    = getInt("steps_goal"),
        bju_goal      = getString("bju_goal")
    )
}
