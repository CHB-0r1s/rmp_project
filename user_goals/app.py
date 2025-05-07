from flask import Flask, request, jsonify
from flask_sqlalchemy import SQLAlchemy
import os

app = Flask(__name__)
app.config['SQLALCHEMY_DATABASE_URI'] = os.getenv(
    'DATABASE_URL', 'postgresql://postgres:password@db:5432/mobile'
)
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False

db = SQLAlchemy(app)

class UserGoal(db.Model):
    __tablename__ = 'user_goals'
    goal_id = db.Column(db.Integer, primary_key=True)
    user_id = db.Column(db.String(50))
    goal_type = db.Column(db.String(20))
    activity_level = db.Column(db.String(10))
    weekly_target = db.Column(db.Float)
    calorie_goal = db.Column(db.Integer)
    water_goal = db.Column(db.Integer)
    steps_goal = db.Column(db.Integer)
    bju_goal = db.Column(db.String(20))

@app.route('/goals', methods=['POST'])
def create_goal():
    data = request.get_json()
    new_goal = UserGoal(**data)
    db.session.add(new_goal)
    db.session.commit()
    return jsonify({"message": "Goal created"}), 201

@app.route('/goals/<user_id>', methods=['GET'])
def get_goals(user_id):
    try:
        goals = UserGoal.query.filter_by(user_id=user_id).all()

        # Serialize each goal object to a dictionary
        serialized_goals = [
            {
                "goal_id": goal.goal_id,
                "user_id": goal.user_id,
                "goal_type": goal.goal_type,
                "activity_level": goal.activity_level,
                "weekly_target": goal.weekly_target,
                "calorie_goal": goal.calorie_goal,
                "water_goal": goal.water_goal,
                "steps_goal": goal.steps_goal,
                "bju_goal": goal.bju_goal
            }
            for goal in goals
        ]

        return jsonify(serialized_goals), 200
    except Exception as e:
        return jsonify({"error": str(e)}), 500


@app.route('/goals/<int:goal_id>', methods=['PUT'])
def update_goal(goal_id):
    data = request.get_json()
    goal = UserGoal.query.get(goal_id)
    if not goal:
        return jsonify({"error": "Goal not found"}), 404
    for key, value in data.items():
        setattr(goal, key, value)
    db.session.commit()
    return jsonify({"message": "Goal updated"})

@app.route('/goals/<int:goal_id>', methods=['DELETE'])
def delete_goal(goal_id):
    goal = UserGoal.query.get(goal_id)
    if not goal:
        return jsonify({"error": "Goal not found"}), 404
    db.session.delete(goal)
    db.session.commit()
    return jsonify({"message": "Goal deleted"})

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5015)
